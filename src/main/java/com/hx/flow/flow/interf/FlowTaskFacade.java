package com.hx.flow.flow.interf;

/**
 * file name : FlowTask.java
 * created at : 15:56  2017-03-19
 * created by 970655147
 */
public interface FlowTaskFacade<StateType extends State, ActionType extends Action> {

    /**
     * 获取当前任务的id
     *
     * @return return current task's id
     * @author 970655147 created at 2017-03-19 15:57
     */
    String id();

    /**
     * 获取当前任务的flow
     *
     * @return return current flow
     * @author 970655147 created at 2017-03-19 15:57
     */
    String flow();

    /**
     * 获取当前任务的状态
     *
     * @return return current state
     * @author 970655147 created at 2017-03-19 15:58
     */
    StateType now();

}
