package com.hx.flow.flow;

import com.hx.flow.flow.interf.*;

/**
 * file name : StandardTransferContext.java
 * created at : 16:38  2017-03-19
 * created by 970655147
 */
public class StandardTransferContext implements TransferContext<State, Action> {

    private StateMachine<State, Action> stateMachine;
    private FlowTaskFacade<State, Action> task;
    private State srcState;
    private Action action;
    private State dstState;
    private TransferHandler<State, Action> handler;
    private Object extra;

    public StandardTransferContext(StateMachine<State, Action> stateMachine, FlowTaskFacade<State, Action> task,
                                   State srcState, Action action, State dstState,
                                   TransferHandler<State, Action> handler, Object extra) {
        this.stateMachine = stateMachine;
        this.task = task;
        this.srcState = srcState;
        this.action = action;
        this.dstState = dstState;
        this.handler = handler;
        this.extra = extra;
    }

    @Override
    public StateMachine<State, Action> stateMachine() {
        return stateMachine;
    }

    @Override
    public FlowTaskFacade<State, Action> task() {
        return task;
    }

    @Override
    public State srcState() {
        return srcState;
    }

    @Override
    public Action action() {
        return action;
    }

    @Override
    public State dstState() {
        return dstState;
    }

    @Override
    public TransferHandler<State, Action> handler() {
        return handler;
    }

    @Override
    public Object extra() {
        return extra;
    }
}
