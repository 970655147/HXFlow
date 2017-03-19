package com.hx.flow.flow.interf;

/**
 * file name : StateMachine.java
 * created at : 15:20  2017-03-19
 * created by 970655147
 */
public interface StateMachine<StateType extends State, ActionType extends Action> {

    /**
     * 获取当前状态机对应的状态的初始状态
     *
     * @return the initial state of current state graph
     * @throws
     * @author 970655147 created at 2017-03-19 16:17
     */
    StateType initialState();

    /**
     * 获取当前状态是否还有下一个状态
     *
     * @return true if 'now' could transfer to another state
     * @throws 
     * @author 970655147 created at 2017-03-19 16:18
     */
    boolean hasNextState(StateType now);

    /**
     * 根据当前的状态 以及采取的动作获取下一个状态
     * 如果没有对应的跃迁, 返回null
     *
     * @param now    当前状态
     * @param action 当前状态采取的action
     * @return StateType return result status while 'now' invoke 'action'
     * @author Jerry.X.He
     * @since 2017/3/15 16:01
     */
    StateType getState(StateType now, ActionType action);

    /**
     * 根据当前的状态 以及采取的动作获取处理当前转换的handler
     * 如果没有对应的跃迁, 返回null
     *
     * @param now    当前状态
     * @param action 当前状态采取的action
     * @return TransferHandler return handler while 'now' invoke 'action'
     * @author Jerry.X.He
     * @since 2017/3/15 16:01
     */
    TransferHandler<StateType, ActionType> getHandler(StateType now, ActionType action);

}
