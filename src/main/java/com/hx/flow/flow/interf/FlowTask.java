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
     * @author 970655147 created at 2017-03-19 15:57
     */
    String id();

    /**
     * ��ȡ��ǰ�����flow
     *
     * @return return current flow
     * @author 970655147 created at 2017-03-19 15:57
     */
    String flow();

    /**
     * ��ȡ��ǰ�����״̬
     *
     * @return return current state
     * @author 970655147 created at 2017-03-19 15:58
     */
    StateType now();

    /**
     * ���õ�ǰtask��extra��Ϣ
     *
     * @return
     * @author 970655147 created at 2017-03-23 0:15
     */
    void extra(Object extra);

    /**
     * ��ȡ��ǰtask�����extra��Ϣ
     *
     * @return return the extra info binding on this task
     * @author 970655147 created at 2017-03-23 0:16
     */
    Object extra();

    /**
     * ת����ǰtask��state
     *
     * @return true if transfer success or else
     * @author 970655147 created at 2017-03-19 15:58
     */
    boolean transfer(StateType newState);

}
