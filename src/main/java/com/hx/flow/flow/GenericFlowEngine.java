package com.hx.flow.flow;

import com.hx.flow.flow.factory.SeqTaskIdGenerator;
import com.hx.flow.flow.interf.*;
import com.hx.flow.flow.interf.factory.*;
import com.hx.flow.util.HXFlowConstants;
import com.hx.log.json.util.JSONUtils;
import com.hx.log.util.Log;
import com.hx.log.util.Tools;
import java.io.File;
import java.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * file name : GenericFlowEngine.java
 * created at : 21:23  2017-03-24
 * created by 970655147
 */
public class GenericFlowEngine<StateType extends State, ActionType extends Action>
        implements FlowEngine<StateType, ActionType> {

    /**
     * flow -> statusMachine
     */
    private final Map<String, StateMachine<StateType, ActionType>> flow2StatusMachine;
    /**
     * t正在运行的askId -> flowTask
     */
    private final Map<String, FlowTask<StateType, ActionType>> runningId2Task = new HashMap<>();
    /**
     * 已经完成的taskId -> flowTask
     */
    private final Map<String, FlowTask<StateType, ActionType>> finishedId2Task = new HashMap<>();

    /**
     * 生成taskId的工具
     */
    private TaskIdGenerator taskIdGenerator;
    /**
     * taskFactory : 创建FlowTask的工具
     */
    private FlowTaskFactory<StateType, ActionType> taskFactory;
    /**
     * taskFacadeFactory : 创建FlowTaskFacadeFactory的工具
     */
    private FlowTaskFacadeFactory<StateType, ActionType> taskFacadeFactory;
    /**
     * transferContextFactory : 创建TransferContextFactory的工具
     */
    private TransferContextFactory<StateType, ActionType> transferContextFactory;

    public GenericFlowEngine(FlowTaskFactory<StateType, ActionType> taskFactory,
                             FlowTaskFacadeFactory<StateType, ActionType> taskFacadeFactory,
                             TransferContextFactory<StateType, ActionType> transferContextFactory) {
        this(taskFactory, taskFacadeFactory, transferContextFactory, new HashMap<String, StateMachine<StateType, ActionType>>() );
    }

    public GenericFlowEngine(FlowTaskFactory<StateType, ActionType> taskFactory,
                             FlowTaskFacadeFactory<StateType, ActionType> taskFacadeFactory,
                             TransferContextFactory<StateType, ActionType> transferContextFactory,
                             Map<String, StateMachine<StateType, ActionType>> flow2StateMachine) {
        this(taskFactory, taskFacadeFactory, transferContextFactory, flow2StateMachine, new SeqTaskIdGenerator());
    }

    public GenericFlowEngine(FlowTaskFactory<StateType, ActionType> taskFactory,
                             FlowTaskFacadeFactory<StateType, ActionType> taskFacadeFactory,
                             TransferContextFactory<StateType, ActionType> transferContextFactory,
                             Map<String, StateMachine<StateType, ActionType>> flow2StateMachine,
                             TaskIdGenerator taskIdGenerator) {
        Tools.assert0(taskFactory != null, "'taskFactory' can't be null !");
        Tools.assert0(taskFacadeFactory != null, "'taskFacadeFactory' can't be null !");
        Tools.assert0(transferContextFactory != null, "'transferContextFactory' can't be null !");
        Tools.assert0(flow2StateMachine != null, "'flow2StatusMachine' can't be null !");
        Tools.assert0(taskIdGenerator != null, "'taskIdGenerator' can't be null !");

        this.taskFactory = taskFactory;
        this.taskFacadeFactory = taskFacadeFactory;
        this.transferContextFactory = transferContextFactory;
        this.flow2StatusMachine = flow2StateMachine;
        this.taskIdGenerator = taskIdGenerator;
    }

    @Override
    public Set<String> flows() {
        return extractKeySetFrom(flow2StatusMachine);
    }

    @Override
    public Set<String> runningTasks() {
        return extractKeySetFrom(runningId2Task);
    }

    @Override
    public Set<String> finishedTasks() {
        return extractKeySetFrom(finishedId2Task);
    }

    @Override
    public StateMachine<StateType, ActionType> getStateMachine(String flow) {
        return flow2StatusMachine.get(flow);
    }

    @Override
    public FlowTaskFacade<StateType, ActionType> getTask(String taskId, Object taskFacadeOthers) {
        FlowTask<StateType, ActionType> task = runningId2Task.get(taskId);
        if (task == null) {
            return null;
        }

        return taskFacadeFactory.create(task, taskFacadeOthers);
    }

    @Override
    public boolean deploy(String flow, StateMachine<StateType, ActionType> stateMachine) {
        return deploy(flow, stateMachine, false);
    }

    @Override
    public boolean deploy(String flow, StateMachine<StateType, ActionType> stateMachine, boolean force) {
        Tools.assert0(flow != null, "'flow' can't be null !");
        Tools.assert0(stateMachine != null, "'stateMachine' can't be null !");

        StateMachine<StateType, ActionType> oldStateMachine = flow2StatusMachine.get(flow);
        if ((oldStateMachine != null) && (!force)) {
            return false;
        }

        synchronized (flow2StatusMachine) {
            flow2StatusMachine.put(flow, stateMachine);
        }
        return true;
    }

    @Override
    public boolean deploy(String flow, JSONObject flowGraph, StateType state, ActionType action,
                          TransferHandlerFactory<StateType, ActionType> transferHandlerFactory, Object transferHandlerOthers) {
        Tools.assert0(flowGraph != null, "'flowGraph' can't be null !");
        Tools.assert0(state != null, "'state' can't be null !");
        Tools.assert0(action != null, "'action' can't be null !");
        Tools.assert0(transferHandlerFactory != null, "'transferHandlerFactory' can't be null !");

        Collection<String> requiredAttrs = Arrays.asList(HXFlowConstants.ACTIONS, HXFlowConstants.ACTIONS,
                HXFlowConstants.INITIAL_STATE, HXFlowConstants.TRANSFERS);
        boolean valid = JSONUtils.checkJSONObj(flowGraph, requiredAttrs, new GenericFlowEngine.CheckDeployAttributeFunc());
        if (!valid) {
            return false;
        }

        initStateAndAction(flowGraph, state, action);
        StateMachine<StateType, ActionType> stateMachine = buildStateMachine(flowGraph, state, action,
                transferHandlerFactory, transferHandlerOthers);
        return deploy(flow, stateMachine, true);
    }

    @Override
    public boolean deploy(String flow, String flowGraphPath, StateType state, ActionType action,
                          TransferHandlerFactory<StateType, ActionType> transferHandlerFactory, Object transferHandlerOthers) {
        File flowGraphFile = new File(flowGraphPath);
        if ((!flowGraphFile.exists()) || (flowGraphFile.isDirectory())) {
            Log.err("the file : " + flowGraphFile.getAbsolutePath() + " does not exists !");
            return false;
        }

        JSONObject flowGraph = null;
        try {
            String content = Tools.getContent(flowGraphFile);
            flowGraph = JSONObject.fromObject(content);
        } catch (Exception e) {
            Log.err("the file : " + flowGraphPath + "'s content is bad format[not valid json] !");
            return false;
        }

        return deploy(flow, flowGraph, state, action, transferHandlerFactory, transferHandlerOthers);
    }

    @Override
    public String startFlowInstance(String flow, Object extra, Object taskOthers) {
        return startFlowInstance(flow, extra, taskOthers, taskFactory);
    }

    @Override
    public String startFlowInstance(String flow, Object extra, Object taskOthers,
                                    FlowTaskFactory<StateType, ActionType> flowTaskFactory) {
        Tools.assert0(flow != null, "'flow' can't be null !");
        Tools.assert0(flowTaskFactory != null, "'flowTaskFactory' can't be null !");

        StateMachine<StateType, ActionType> stateMachine = flow2StatusMachine.get(flow);
        if (stateMachine == null) {
            return null;
        }

        String taskId = taskIdGenerator.nextId();
        FlowTask<StateType, ActionType> task = flowTaskFactory.create(taskId, flow, stateMachine.initialState(), extra, taskOthers);
        if(task == null) {
            return null;
        }

        addFlowInstance(task);
        return taskId;
    }

    @Override
    public boolean addFlowInstance(String taskId, String flow, StateType state,
                                   Object extra, Object taskOthers) {
        return addFlowInstance(taskId, flow, state, extra, taskOthers, taskFactory);
    }


    @Override
    public boolean addFlowInstance(String taskId, String flow, StateType state, Object extra, Object taskOthers,
                                   FlowTaskFactory<StateType, ActionType> flowTaskFactory) {
        Tools.assert0(taskId != null, "'taskId' can't be null !");
        Tools.assert0(flow != null, "'flow' can't be null !");
        Tools.assert0(state != null, "'state' can't be null !");
        Tools.assert0(flowTaskFactory != null, "'flowTaskFactory' can't be null !");

        StateMachine<StateType, ActionType> stateMachine = flow2StatusMachine.get(flow);
        if (stateMachine == null) {
            return false;
        }

        FlowTask<StateType, ActionType> task = flowTaskFactory.create(taskId, flow, state, extra, taskOthers);
        if(task == null) {
            return false;
        }

        return addFlowInstance(task);
    }

    @Override
    public boolean complete(String taskId, ActionType action, Object extra, Object taskFacadeAndTransferContextOthers) throws Exception {
        Tools.assert0(taskId != null, "'taskId' can't be null !");

        FlowTask<StateType, ActionType> task = runningId2Task.get(taskId);
        if (task == null) {
            return false;
        }
        StateMachine<StateType, ActionType> stateMachine = flow2StatusMachine.get(task.flow());
        if (stateMachine == null) {
            return false;
        }
        if (stateMachine.hasNextState(task.now())) {
            transferFinishedTask(task.id(), task);
            return false;
        }
        FlowTaskFacade<StateType, ActionType> taskFacade = taskFacadeFactory.create(task, taskFacadeAndTransferContextOthers);
        if(taskFacade == null) {
            return false;
        }

        StateType srcState = task.now();
        StateType dstState = stateMachine.getState(srcState, action);
        TransferHandler<StateType, ActionType> handler = stateMachine.getHandler(srcState, action);
        if (handler == null) {
            return false;
        }

        TransferContext<StateType, ActionType> context = transferContextFactory.create(stateMachine, taskFacade,
                srcState, action, dstState, handler, extra, taskFacadeAndTransferContextOthers);
        boolean handleResult = handler.handle(context);
        if(handleResult) {
            task.transfer(dstState);
            if (stateMachine.hasNextState(dstState)) {
                transferFinishedTask(taskId, task);
            }
        }

        return handleResult;
    }

    /**
     * 根据流程图的数据, 初始化state & action
     *
     * @param flowGraph 流程图的数据
     * @param state     state[用于构造State]
     * @param action    action[用于构造Action]
     * @return void return void
     * @author 970655147 created at 2017-03-19 19:14
     */
    private void initStateAndAction(JSONObject flowGraph, StateType state, ActionType action) {
        JSONArray states = flowGraph.getJSONArray(HXFlowConstants.STATES);
        for (int i = 0, len = states.size(); i < len; i++) {
            JSONObject stateObj = states.getJSONObject(i);
            state.create(stateObj.getString(HXFlowConstants.STATE_ID), stateObj.get(HXFlowConstants.STATE_EXTRA));
        }
        JSONArray actions = flowGraph.getJSONArray(HXFlowConstants.ACTIONS);
        for (int i = 0, len = actions.size(); i < len; i++) {
            JSONObject actionObj = actions.getJSONObject(i);
            action.create(actionObj.getString(HXFlowConstants.ACTION_ID), actionObj.get(HXFlowConstants.ACTION_EXTRA));
        }
    }

    /**
     * 根据流程图, 构造状态机
     *
     * @param flowGraph 流程图的数据
     * @return com.hx.flow.flow.interf.StateMachine<com.hx.flow.flow.interf.State,com.hx.flow.flow.interf.Action>
     * @author 970655147 created at 2017-03-19 19:19
     */
    private StateMachine<StateType, ActionType> buildStateMachine(JSONObject flowGraph, StateType state, ActionType action,
                                                          TransferHandlerFactory<StateType, ActionType> transferHandlerFactory,
                                                          Object transferHandlerOthers) {
        JSONArray transfers = flowGraph.getJSONArray(HXFlowConstants.TRANSFERS);
        StandardStateMachine.TransferMapBuilder<StateType, ActionType> builder = StandardStateMachine.TransferMapBuilder.start();
        for (int i = 0, len = transfers.size(); i < len; i++) {
            JSONObject transferObj = transfers.getJSONObject(i);
            String srcStr = transferObj.getString(HXFlowConstants.TRANSFER_SRC);
            String actionStr = transferObj.getString(HXFlowConstants.TRANSFER_ACTION);
            String dstStr = transferObj.getString(HXFlowConstants.TRANSFER_DST);
            String handlerStr = transferObj.getString(HXFlowConstants.TRANSFER_HANDLER);
            TransferHandler<StateType, ActionType> handler = transferHandlerFactory.create(handlerStr, transferHandlerOthers);
            if(handler == null) {
                Log.warn("got null handler with handlerStr : " + handlerStr + ", others : " + transferObj);
            }

            State srcStat = state.idOf(srcStr);
            Action actionNow = action.idOf(actionStr);
            State dstStat = state.idOf(dstStr);
            if ((srcStat == null) || (actionNow == null) || (dstStat == null)) {
                Log.err("unknown state or action : srcState : " + srcStr + ", action : " + actionStr + ", dstState : " + dstStr);
                return null;
            }
            builder.add((StateType) srcStat, (ActionType) actionNow, (StateType) dstStat, handler);
        }

        String initStatStr = flowGraph.getString(HXFlowConstants.INITIAL_STATE);
        StateType initStat = (StateType) state.idOf(initStatStr);
        if (initStat == null) {
            Log.err("unknown initial state : " + initStatStr);
            return null;
        }

        return new GenericStateMachine<>(initStat, builder.build());
    }

    /**
     * 从给定的map中获取对应的keySet
     *
     * @param map 给定的map
     * @return java.util.Set<java.lang.String>
     * @author 970655147 created at 2017-03-22 20:43
     */
    private Set<String> extractKeySetFrom(final Map<String, ?> map) {
        Set<String> result = new HashSet<>(Tools.estimateMapSize(map.size()));
        synchronized (map) {
            for (String flow : map.keySet()) {
                result.add(flow);
            }
        }
        return result;
    }

    /**
     * 将给定的task从running 迁移到finished
     *
     * @param taskId task的标志
     * @param task   给定的task
     * @return void
     * @author 970655147 created at 2017-03-22 20:48
     */
    private void transferFinishedTask(String taskId, FlowTask<StateType, ActionType> task) {
        synchronized (runningId2Task) {
            runningId2Task.remove(taskId);
        }
        synchronized (finishedId2Task) {
            finishedId2Task.put(taskId, task);
        }
    }

    /**
     * 向flowEngine中增加一个流程实例
     *
     * @param task                   给定的任务的需要添加的任务
     * @return boolean return true if add flowInstance success
     * @author 970655147 created at 2017-03-22 23:39
     */
    private boolean addFlowInstance(FlowTask<StateType, ActionType> task) {
        synchronized (runningId2Task) {
            runningId2Task.put(task.id(), task);
        }
        return true;
    }

    /**
     * 校验deploy流程 配置的func
     *
     * @author 970655147 created at 2017-03-19 19:01
     */
    private static class CheckDeployAttributeFunc implements JSONUtils.CheckRequiredAttributeFunc {
        @Override
        public boolean check(JSONObject obj, String attr) {
            Collection<String> requiredAttrs = null;
            if (HXFlowConstants.STATES.equals(attr)) {
                requiredAttrs = Arrays.asList(HXFlowConstants.STATE_ID, HXFlowConstants.STATE_EXTRA);
            } else if (HXFlowConstants.ACTIONS.equals(attr)) {
                requiredAttrs = Arrays.asList(HXFlowConstants.ACTION_ID, HXFlowConstants.ACTION_EXTRA);
            } else if (HXFlowConstants.INITIAL_STATE.equals(attr)) {
                String initialState = obj.getString(attr);
                boolean valid = !Tools.isEmpty(initialState);
                if (!valid) {
                    Log.err("initialState is not defined !");
                }
                return valid;
            } else if (HXFlowConstants.TRANSFERS.equals(attr)) {
                requiredAttrs = Arrays.asList(HXFlowConstants.TRANSFER_SRC, HXFlowConstants.TRANSFER_ACTION,
                        HXFlowConstants.TRANSFER_DST, HXFlowConstants.TRANSFER_HANDLER);
            } else {
                Log.err("have not this attribute : " + attr + " !");
                return false;
            }

            JSONArray childArr = obj.getJSONArray(attr);
            if (Tools.isEmpty(childArr)) {
                Log.err("the obj's attr['" + attr + "'] is not defined, obj : " + String.valueOf(obj));
                return false;
            }
            boolean valid = JSONUtils.checkJSONArr(childArr, requiredAttrs, null);
            if (!valid) {
                Log.err("the obj's attr['" + attr + "'] is not defined, obj : " + String.valueOf(obj));
            }
            return valid;
        }
    }

}
