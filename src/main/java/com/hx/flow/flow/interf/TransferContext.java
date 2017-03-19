package com.hx.flow.flow.interf;

/**
 * file name : TransferContext.java
 * created at : 14:56  2017-03-19
 * created by 970655147
 */
public interface TransferContext<StateType extends State, ActionType extends Action> {

    /**
     * ��ȡ��ǰ״̬ת��������״̬��
     *
     * @return return current task
     * @author 970655147 created at 2017-03-19 15:05
     */
    StateMachine<StateType, ActionType> stateMachine();

    /**
     * ��ȡ��ǰ״̬ת����task
     *
     * @return return current task
     * @author 970655147 created at 2017-03-19 15:05
     */
    FlowTaskFacade<StateType, ActionType> task();

    /**
     * ��ȡ��ǰ״̬ת����Դ״̬
     *
     * @return return current state
     * @author 970655147 created at 2017-03-19 15:05
     */
    StateType srcState();

    /**
     * ��ȡ��ǰ״̬ת����action
     *
     * @return return current action
     * @throws
     * @author 970655147 created at 2017-03-19 15:05
     */
    ActionType action();

    /**
     * ��ȡ��ǰ״̬ת����Ŀ��״̬
     *
     * @return return result state
     * @author 970655147 created at 2017-03-19 15:05
     */
    StateType dstState();

    /**
     * ��ȡ��ǰ״̬ת����handler
     *
     * @return return current handler
     * @author 970655147 created at 2017-03-19 15:05
     */
    TransferHandler<StateType, ActionType> handler();

    /**
     * ��ȡ��ǰ״̬ת����һЩ���ӵ���Ϣ
     *
     * @return  some extra info bind on current context
     * @author 970655147 created at 2017-03-19 15:05
     */
    Object extra();

}
