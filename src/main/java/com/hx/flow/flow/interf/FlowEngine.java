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
     * ��ȡ��ǰengine, ����flow������
     *
     * @return return all flow
     * @author 970655147 created at 2017-03-19 15:50
     */
    Set<String> flows();

    /**
     * ��ȡ��ǰengine, ��������ִ�е������id
     *
     * @return return all running tasks
     * @author 970655147 created at 2017-03-22 20:36
     */
    Set<String> runningTasks();

    /**
     * ��ȡ��ǰengine, �����Ѿ���ɵ������id
     *
     * @return return all finished tasks
     * @author 970655147 created at 2017-03-22 20:36
     */
    Set<String> finishedTasks();

    /**
     * ����һ������
     *
     * @param flow         ����id
     * @param stateMachine ���̶�Ӧ��״̬��
     * @return true if deploy success or else
     * @author 970655147 created at 2017-03-19 15:50
     */
    boolean deploy(String flow, StateMachine<StateType, ActionType> stateMachine);

    /**
     * ����һ������
     *
     * @param flow         ����id
     * @param stateMachine ���̶�Ӧ��״̬��
     * @param force        �Ƿ�ǿ�Ʋ�������
     * @return true if deploy flow success or else
     * @author 970655147 created at 2017-03-19 15:50
     */
    boolean deploy(String flow, StateMachine<StateType, ActionType> stateMachine, boolean force);

    /**
     * ����һ������
     *
     * @param flow      ����id
     * @param flowGraph ���̶��������
     * @param state     state[���ڹ���State]
     * @param action    action[���ڹ���Action]
     * @return true if deploy success or else
     * @author 970655147 created at 2017-03-19 15:50
     */
    boolean deploy(String flow, JSONObject flowGraph, StateType state, ActionType action,
                   TransferHandlerFactory<StateType, ActionType> transferHandlerFactory);

    /**
     * ����һ������
     *
     * @param flow          ����id
     * @param flowGraphPath ���̶�������ݵ��ļ���·��
     * @param state         state[���ڹ���State]
     * @param action        action[���ڹ���Action]
     * @return true if deploy success or else
     * @author 970655147 created at 2017-03-19 15:50
     */
    boolean deploy(String flow, String flowGraphPath, StateType state, ActionType action,
                   TransferHandlerFactory<StateType, ActionType> transferHandlerFactory);

    /**
     * ����һ������ʵ��
     *
     * @param flow   ����id
     * @param extra  ����task��extra��Ϣ
     * @param others ����task��Ҫ��������Ϣ
     * @return null if flow does not exists, else return the taskId
     * @author 970655147 created at 2017-03-19 15:53
     */
    String startFlowInstance(String flow, Object extra, Object others);

    /**
     * ����һ������ʵ��
     *
     * @param flow            ����id
     * @param flowTaskFactory ����flowTask��factory
     * @param extra           ����task��extra��Ϣ
     * @param others          ����task��Ҫ��������Ϣ
     * @return null if flow does not exists, else return the taskId
     * @author 970655147 created at 2017-03-19 15:53
     */
    String startFlowInstance(String flow, FlowTaskFactory<StateType, ActionType> flowTaskFactory, Object extra, Object others);

    /**
     * ��flowEngine������һ������ʵ��
     *
     * @param taskId ����id
     * @param flow   ����id
     * @param state  ����ĳ�ʼ״̬
     * @param extra  ����task��extra��Ϣ
     * @param others ����task��Ҫ��������Ϣ
     * @return null if flow does not exists, else return the taskId
     * @author 970655147 created at 2017-03-19 15:53
     */
    boolean addFlowInstance(String taskId, String flow, StateType state, Object extra, Object others);

    /**
     * ��flowEngine������һ������ʵ��
     *
     * @param taskId          ����id
     * @param flow            ����id
     * @param state           ����ĳ�ʼ״̬
     * @param flowTaskFactory ����flowTask��factory
     * @param extra           ����task��extra��Ϣ
     * @param others          ����task��Ҫ��������Ϣ
     * @return null if flow does not exists, else return the taskId
     * @author 970655147 created at 2017-03-19 15:53
     */
    boolean addFlowInstance(String taskId, String flow, StateType state,
                            FlowTaskFactory<StateType, ActionType> flowTaskFactory, Object extra, Object others);

    /**
     * ��ȡ���������̵�״̬��
     *
     * @param flow ���������̵�id
     * @return com.hx.flow.flow.interf.StateMachine<StateType,ActionType> return `StateMachine` corresponding `flow` or else null returned
     * @author 970655147 created at 2017-03-22 22:38
     */
    StateMachine<StateType, ActionType> getStateMachine(String flow);

    /**
     * ��ȡtaskId��Ӧ��task
     *
     * @param taskId ����ʵ��id
     * @return null if the pair of (taskId, task) does not exists or else return the corresponding task
     * @author 970655147 created at 2017-03-19 16:25
     */
    FlowTaskFacade<StateType, ActionType> getTask(String taskId);

    /**
     * ����ʵ����״̬�л�
     *
     * @param taskId ����ʵ����id
     * @param action ��ȡ��action
     * @param extra  �������Ϣ
     * @return true if 'complete' success or else[have no task, have no action, etc]
     * @throws Exception throw if any Exception happends
     * @author 970655147 created at 2017-03-19 15:55
     */
    boolean complete(String taskId, ActionType action, Object extra) throws Exception;

}
