package com.hx.flow.flow.interf.factory;

import com.hx.flow.flow.interf.*;

/**
 * file name : TransferContextFactory.java
 * created at : 21:44  2017-03-24
 * created by 970655147
 */
public interface TransferContextFactory<StateType extends State, ActionType extends Action> {

    /**
     * ����task, other����һ��FlowTask��ʵ��
     *
     * @param stateMachine ��ǰcontext��Ӧ�������״̬��
     * @param task         ��ǰcontext��Ӧ������
     * @param srcState     ��ǰ����ת��ǰ��״̬
     * @param action       ��ǰ����ת����action
     * @param dstState     ��ǰ����ת�����״̬
     * @param handler      ��ǰ����ת����handler
     * @param extra        ��ǰcontext�Ķ�����Ϣ
     * @param others       ������ǰContext��������Ϣ[��չ]
     * @return return an instance create by params above
     * @author 970655147 created at 2017-03-24 20:24
     */
    TransferContext<StateType, ActionType> create(StateMachine<StateType, ActionType> stateMachine,
                                                  FlowTaskFacade<StateType, ActionType> task,
                                                  StateType srcState, ActionType action, StateType dstState,
                                                 TransferHandler<StateType, ActionType> handler, Object extra, Object others);

}
