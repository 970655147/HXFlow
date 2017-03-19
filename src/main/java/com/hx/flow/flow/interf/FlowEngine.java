package com.hx.flow.flow.interf;

import java.util.Set;
import net.sf.json.JSONObject;

/**
 * file name : FlowEngine.java
 * created at : 15:49  2017-03-19
 * created by 970655147
 */
public interface FlowEngine<StateType extends State, ActionType extends Action> {

    /**
     * 获取当前engine, 所有flow的名称
     *
     * @return true if deploy success or else
     * @author 970655147 created at 2017-03-19 15:50
     */
    Set<String> flows();

    /**
     * 部署一个流程
     *
     * @param flow         流程id
     * @param stateMachine 流程对应的状态机
     * @return true if deploy success or else
     * @author 970655147 created at 2017-03-19 15:50
     */
    boolean deploy(String flow, StateMachine<StateType, ActionType> stateMachine);

    /**
     * 部署一个流程
     *
     * @param flow         流程id
     * @param stateMachine 流程对应的状态机
     * @param force        是否强制部署流程
     * @return true if deploy flow success or else
     * @author 970655147 created at 2017-03-19 15:50
     */
    boolean deploy(String flow, StateMachine<StateType, ActionType> stateMachine, boolean force);

    /**
     * 部署一个流程
     *
     * @param flow      流程id
     * @param flowGraph 流程定义的数据
     * @param state     state[用于构造State]
     * @param action    action[用于构造Action]
     * @return true if deploy success or else
     * @author 970655147 created at 2017-03-19 15:50
     */
    boolean deploy(String flow, JSONObject flowGraph, StateType state, ActionType action,
                   TransferHandlerFactory<StateType, ActionType> transferHandlerFactory);

    /**
     * 部署一个流程
     *
     * @param flow          流程id
     * @param flowGraphPath 流程定义的数据的文件的路径
     * @param state         state[用于构造State]
     * @param action        action[用于构造Action]
     * @return true if deploy success or else
     * @author 970655147 created at 2017-03-19 15:50
     */
    boolean deploy(String flow, String flowGraphPath, StateType state, ActionType action,
                   TransferHandlerFactory<StateType, ActionType> transferHandlerFactory);

    /**
     * 启动一个流程实例
     *
     * @param flow 流程id
     * @return null if flow does not exists, else return the taskId
     * @author 970655147 created at 2017-03-19 15:53
     */
    String startFlowInstance(String flow);

    /**
     * 获取taskId对应的task
     *
     * @param taskId 流程实例id
     * @return null if the pair of (taskId, task) does not exists or else return the corresponding task
     * @author 970655147 created at 2017-03-19 16:25
     */
    FlowTaskFacade<StateType, ActionType> getTask(String taskId);

    /**
     * 处理实例的状态切换
     *
     * @param taskId 流程实例的id
     * @param action 采取的action
     * @param extra  额外的信息
     * @return true if 'complete' success or else[have no task, have no action, etc]
     * @throws Exception throw if any Exception happends
     * @author 970655147 created at 2017-03-19 15:55
     */
    boolean complete(String taskId, ActionType action, Object extra) throws Exception;

}
