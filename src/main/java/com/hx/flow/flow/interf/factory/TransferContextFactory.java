package com.hx.flow.flow.interf.factory;

import com.hx.flow.flow.interf.*;

/**
 * file name : TransferContextFactory.java
 * created at : 21:44  2017-03-24
 * created by 970655147
 */
public interface TransferContextFactory<StateType extends State, ActionType extends Action> {

    /**
     * 根据task, other创建一个FlowTask的实例
     *
     * @param stateMachine 当前context对应的任务的状态机
     * @param task         当前context对应的任务
     * @param srcState     当前任务转换前的状态
     * @param action       当前任务转换的action
     * @param dstState     当前任务转换后的状态
     * @param handler      当前任务转换的handler
     * @param extra        当前context的额外信息
     * @param others       创建当前Context的其他信息[扩展]
     * @return return an instance create by params above
     * @author 970655147 created at 2017-03-24 20:24
     */
    TransferContext<StateType, ActionType> create(StateMachine<StateType, ActionType> stateMachine,
                                                  FlowTaskFacade<StateType, ActionType> task,
                                                  StateType srcState, ActionType action, StateType dstState,
                                                 TransferHandler<StateType, ActionType> handler, Object extra, Object others);

}
