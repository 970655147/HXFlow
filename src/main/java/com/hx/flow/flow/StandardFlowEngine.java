package com.hx.flow.flow;

import com.hx.flow.flow.interf.*;
import com.hx.flow.util.HXFlowConstants;
import com.hx.log.util.IdxGenerator;
import com.hx.log.util.JSONUtils;
import com.hx.log.util.Log;
import com.hx.log.util.Tools;
import java.io.File;
import java.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import static com.hx.log.util.JSONUtils.checkJSONObj;

/**
 * file name : StandardFlowEngine.java
 * created at : 15:59  2017-03-19
 * created by 970655147
 */
public class StandardFlowEngine implements FlowEngine<State, Action> {

    /**
     * flow -> statusMachine
     */
    private Map<String, StateMachine<State, Action>> flow2StatusMachine;
    /**
     * taskId -> flowTask
     */
    private Map<String, FlowTask<State, Action>> taskId2Task = new HashMap<>();
    /**
     * 生成taskId的工具
     */
    private IdxGenerator taskIdGenerator = new IdxGenerator();

    public StandardFlowEngine() {
        this(new HashMap<String, StateMachine<State, Action>>());
    }

    public StandardFlowEngine(Map<String, StateMachine<State, Action>> flow2StatusMachine) {
        Tools.assert0(flow2StatusMachine != null, "'flow2StatusMachine' can't be null !");
        this.flow2StatusMachine = flow2StatusMachine;
    }

    @Override
    public Set<String> flows() {
        return flow2StatusMachine.keySet();
    }

    @Override
    public boolean deploy(String flow, StateMachine<State, Action> stateMachine) {
        return deploy(flow, stateMachine, false);
    }

    @Override
    public boolean deploy(String flow, StateMachine<State, Action> stateMachine, boolean force) {
        StateMachine<State, Action> oldStateMachine = flow2StatusMachine.get(flow);
        if ((oldStateMachine != null) && (!force)) {
            return false;
        }

        flow2StatusMachine.put(flow, stateMachine);
        return true;
    }

    @Override
    public boolean deploy(String flow, JSONObject flowGraph, State state, Action action,
                          TransferHandlerFactory<State, Action> transferHandlerFactory) {
        Tools.assert0(flowGraph != null, "'flowGraph' can't be null !");
        Tools.assert0(state != null, "'state' can't be null !");
        Tools.assert0(action != null, "'action' can't be null !");

        Collection<String> requiredAttrs = Arrays.asList(HXFlowConstants.ACTIONS, HXFlowConstants.ACTIONS,
                HXFlowConstants.INITIAL_STATE, HXFlowConstants.TRANSFERS);
        boolean valid = JSONUtils.checkJSONObj(flowGraph, requiredAttrs, new CheckDeployAttributeFunc());
        if (!valid) {
            return false;
        }

        initStateAndAction(flowGraph, state, action);
        StateMachine<State, Action> stateMachine = buildStateMachine(flowGraph, state, action, transferHandlerFactory);
        return deploy(flow, stateMachine, true);
    }

    @Override
    public boolean deploy(String flow, String flowGraphPath, State state, Action action,
                          TransferHandlerFactory<State, Action> transferHandlerFactory) {
        File flowGraphFile = new File(flowGraphPath);
        if((! flowGraphFile.exists()) || (flowGraphFile.isDirectory())) {
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

        return deploy(flow, flowGraph, state, action, transferHandlerFactory);
    }

    @Override
    public String startFlowInstance(String flow) {
        StateMachine<State, Action> stateMachine = flow2StatusMachine.get(flow);
        if (stateMachine == null) {
            return null;
        }

        String taskId = taskId(taskIdGenerator.nextId());
        FlowTask<State, Action> task = new StandardFlowTask(taskId, flow, stateMachine.initialState());
        taskId2Task.put(taskId, task);
        return taskId;
    }

    @Override
    public FlowTaskFacade<State, Action> getTask(String taskId) {
        FlowTask<State, Action> flowTask = taskId2Task.get(taskId);
        if (flowTask == null) {
            return null;
        }

        return new StandardFlowTaskFacade(flowTask);
    }

    @Override
    public boolean complete(String taskId, Action action, Object extra) throws Exception {
        FlowTask<State, Action> task = taskId2Task.get(taskId);
        if (task == null) {
            return false;
        }
        StateMachine<State, Action> stateMachine = flow2StatusMachine.get(task.flow());
        if (stateMachine == null) {
            return false;
        }

        FlowTaskFacade<State, Action> taskFacade = new StandardFlowTaskFacade(task);
        State srcState = task.now();
        State dstState = stateMachine.getState(srcState, action);
        TransferHandler<State, Action> handler = stateMachine.getHandler(srcState, action);
        if (handler == null) {
            return false;
        }

        TransferContext<State, Action> context = new StandardTransferContext(stateMachine, taskFacade,
                srcState, action, dstState, handler, extra);
        boolean handleResult = handler.handle(context);
        task.transfer(dstState);
        return handleResult;
    }

    /**
     * 获取taskId
     *
     * @param id 给定的id
     * @return java.lang.String
     * @author 970655147 created at 2017-03-19 16:49
     */
    private String taskId(int id) {
        return "task - " + id;
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
    private void initStateAndAction(JSONObject flowGraph, State state, Action action) {
        JSONArray states = flowGraph.getJSONArray(HXFlowConstants.STATES);
        for(int i=0, len=states.size(); i<len; i++) {
            JSONObject stateObj = states.getJSONObject(i);
            state.create(stateObj.getString(HXFlowConstants.STATE_ID), stateObj.get(HXFlowConstants.STATE_EXTRA));
        }
        JSONArray actions = flowGraph.getJSONArray(HXFlowConstants.ACTIONS);
        for(int i=0, len=actions.size(); i<len; i++) {
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
    private StateMachine<State, Action> buildStateMachine(JSONObject flowGraph, State state, Action action,
                                                          TransferHandlerFactory<State, Action> transferHandlerFactory) {
        JSONArray transfers = flowGraph.getJSONArray(HXFlowConstants.TRANSFERS);
        StandardStateMachine.TransferMapBuilder builder = StandardStateMachine.TransferMapBuilder.start();
        for(int i=0, len=transfers.size(); i<len; i++) {
            JSONObject transferObj = transfers.getJSONObject(i);
            String srcStr = transferObj.getString(HXFlowConstants.TRANSFER_SRC);
            String actionStr = transferObj.getString(HXFlowConstants.TRANSFER_ACTION);
            String dstStr = transferObj.getString(HXFlowConstants.TRANSFER_DST);
            String handlerStr = transferObj.getString(HXFlowConstants.TRANSFER_HANDLER);
            TransferHandler<State, Action> handler = transferHandlerFactory.create(handlerStr);

            State srcStat = state.idOf(srcStr);
            Action actionNow = action.idOf(actionStr);
            State dstStat = state.idOf(dstStr);
            if((srcStat == null) || (actionNow == null) || (dstStat == null) ) {
                Log.err("unknown state or action : srcState : " + srcStr + ", action : " + actionStr + ", dstState : " + dstStr);
                return null;
            }
            builder.add(srcStat, actionNow, dstStat, handler);
        }

        String initStatStr = flowGraph.getString(HXFlowConstants.INITIAL_STATE);
        State initStat = state.idOf(initStatStr);
        if(initStat == null) {
            Log.err("unknown initial state : " + initStatStr);
            return null;
        }

        return new StandardStateMachine(initStat, builder.build());
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
