package com.hx.flow.flow.interf;

/**
 * file name : TransferContext.java
 * created at : 14:56  2017-03-19
 * created by 970655147
 */
public interface TransferContext<StateType extends State, ActionType extends Action> {

    /**
     * 获取当前状态转换关联的状态机
     *
     * @return return current task
     * @author 970655147 created at 2017-03-19 15:05
     */
    StateMachine<StateType, ActionType> stateMachine();

    /**
     * 获取当前状态转换的task
     *
     * @return return current task
     * @author 970655147 created at 2017-03-19 15:05
     */
    FlowTaskFacade<StateType, ActionType> task();

    /**
     * 获取当前状态转换的源状态
     *
     * @return return current state
     * @author 970655147 created at 2017-03-19 15:05
     */
    StateType srcState();

    /**
     * 获取当前状态转换的action
     *
     * @return return current action
     * @author 970655147 created at 2017-03-19 15:05
     */
    ActionType action();

    /**
     * 获取当前状态转换的目标状态
     *
     * @return return result state
     * @author 970655147 created at 2017-03-19 15:05
     */
    StateType dstState();

    /**
     * 获取当前状态转换的handler
     *
     * @return return current handler
     * @author 970655147 created at 2017-03-19 15:05
     */
    TransferHandler<StateType, ActionType> handler();

    /**
     * 获取当前状态转换的一些附加的信息
     *
     * @return  some extra info bind on current context
     * @author 970655147 created at 2017-03-19 15:05
     */
    Object extra();

}
