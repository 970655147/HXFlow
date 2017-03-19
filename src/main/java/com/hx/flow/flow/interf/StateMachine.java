package com.hx.flow.flow.interf;

/**
 * file name : StateMachine.java
 * created at : 15:20  2017-03-19
 * created by 970655147
 */
public interface StateMachine<StateType extends State, ActionType extends Action> {

    /**
     * ��ȡ��ǰ״̬����Ӧ��״̬�ĳ�ʼ״̬
     *
     * @return the initial state of current state graph
     * @author 970655147 created at 2017-03-19 16:17
     */
    StateType initialState();

    /**
     * ��ȡ��ǰ״̬�Ƿ�����һ��״̬
     *
     * @return true if 'now' could transfer to another state
     * @author 970655147 created at 2017-03-19 16:18
     */
    boolean hasNextState(StateType now);

    /**
     * ���ݵ�ǰ��״̬ �Լ���ȡ�Ķ�����ȡ��һ��״̬
     * ���û�ж�Ӧ��ԾǨ, ����null
     *
     * @param now    ��ǰ״̬
     * @param action ��ǰ״̬��ȡ��action
     * @return StateType return result status while 'now' invoke 'action'
     * @author Jerry.X.He
     * @since 2017/3/15 16:01
     */
    StateType getState(StateType now, ActionType action);

    /**
     * ���ݵ�ǰ��״̬ �Լ���ȡ�Ķ�����ȡ����ǰת����handler
     * ���û�ж�Ӧ��ԾǨ, ����null
     *
     * @param now    ��ǰ״̬
     * @param action ��ǰ״̬��ȡ��action
     * @return TransferHandler return handler while 'now' invoke 'action'
     * @author Jerry.X.He
     * @since 2017/3/15 16:01
     */
    TransferHandler<StateType, ActionType> getHandler(StateType now, ActionType action);

}
