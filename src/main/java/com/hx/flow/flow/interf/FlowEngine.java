package com.hx.flow.flow.interf;

import com.hx.flow.flow.interf.factory.FlowTaskFactory;
import com.hx.flow.flow.interf.factory.TransferHandlerFactory;
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
     * @return return all flow
     * @author 970655147 created at 2017-03-19 15:50
     */
    Set<String> flows();

    /**
     * 获取当前engine, 所有正在执行的任务的id
     *
     * @return return all running tasks
     * @author 970655147 created at 2017-03-22 20:36
     */
    Set<String> runningTasks();

    /**
     * 获取当前engine, 所有已经完成的任务的id
     *
     * @return return all finished tasks
     * @author 970655147 created at 2017-03-22 20:36
     */
    Set<String> finishedTasks();

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
     * @param flow   流程id
     * @param extra  创建task的extra信息
     * @param others 创建task需要的其他信息
     * @return null if flow does not exists, else return the taskId
     * @author 970655147 created at 2017-03-19 15:53
     */
    String startFlowInstance(String flow, Object extra, Object others);

    /**
     * 启动一个流程实例
     *
     * @param flow            流程id
     * @param flowTaskFactory 创建flowTask的factory
     * @param extra           创建task的extra信息
     * @param others          创建task需要的其他信息
     * @return null if flow does not exists, else return the taskId
     * @author 970655147 created at 2017-03-19 15:53
     */
    String startFlowInstance(String flow, FlowTaskFactory<StateType, ActionType> flowTaskFactory, Object extra, Object others);

    /**
     * 向flowEngine中增加一个流程实例
     *
     * @param taskId 任务id
     * @param flow   流程id
     * @param state  任务的初始状态
     * @param extra  创建task的extra信息
     * @param others 创建task需要的其他信息
     * @return null if flow does not exists, else return the taskId
     * @author 970655147 created at 2017-03-19 15:53
     */
    boolean addFlowInstance(String taskId, String flow, StateType state, Object extra, Object others);

    /**
     * 向flowEngine中增加一个流程实例
     *
     * @param taskId          任务id
     * @param flow            流程id
     * @param state           任务的初始状态
     * @param flowTaskFactory 创建flowTask的factory
     * @param extra           创建task的extra信息
     * @param others          创建task需要的其他信息
     * @return null if flow does not exists, else return the taskId
     * @author 970655147 created at 2017-03-19 15:53
     */
    boolean addFlowInstance(String taskId, String flow, StateType state,
                            FlowTaskFactory<StateType, ActionType> flowTaskFactory, Object extra, Object others);

    /**
     * 获取给定的流程的状态机
     *
     * @param flow 给定的流程的id
     * @return com.hx.flow.flow.interf.StateMachine<StateType,ActionType> return `StateMachine` corresponding `flow` or else null returned
     * @author 970655147 created at 2017-03-22 22:38
     */
    StateMachine<StateType, ActionType> getStateMachine(String flow);

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
