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
     * ��ȡ��ǰengine, ����flow������
     *
     * @return true if deploy success or else
     * @author 970655147 created at 2017-03-19 15:50
     */
    Set<String> flows();

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
     * @param flow ����id
     * @return null if flow does not exists, else return the taskId
     * @author 970655147 created at 2017-03-19 15:53
     */
    String startFlowInstance(String flow);

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
