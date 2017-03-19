package com.hx.flow.flow.interf;

/**
 * file name : FlowTask.java
 * created at : 15:56  2017-03-19
 * created by 970655147
 */
public interface FlowTask<StateType extends State, ActionType extends Action> {

    /**
     * ��ȡ��ǰ�����id
     *
     * @return return current task's id
     * @throws
     * @author 970655147 created at 2017-03-19 15:57
     */
    String id();

    /**
     * ��ȡ��ǰ�����flow
     *
     * @return return current flow
     * @throws
     * @author 970655147 created at 2017-03-19 15:57
     */
    String flow();

    /**
     * ��ȡ��ǰ�����״̬
     *
     * @return return current state
     * @throws
     * @author 970655147 created at 2017-03-19 15:58
     */
    StateType now();

    /**
     * ת����ǰtask��state
     *
     * @return true if transfer success or else
     * @throws
     * @author 970655147 created at 2017-03-19 15:58
     */
    boolean transfer(StateType newState);

}
