package com.hx.flow.flow.factory;

import com.hx.flow.flow.StandardTransferContext;
import com.hx.flow.flow.interf.*;
import com.hx.flow.flow.interf.factory.TransferContextFactory;

/**
 * file name : StandardTransferContextFactory.java
 * created at : 21:50  2017-03-24
 * created by 970655147
 */
public class StandardTransferContextFactory implements TransferContextFactory<State, Action> {

    @Override
    public TransferContext<State, Action> create(StateMachine<State, Action> stateMachine,
                                                FlowTaskFacade<State, Action> task, State srcState, Action action, State dstState,
                                                TransferHandler<State, Action> handler, Object extra, Object others) {
        return new StandardTransferContext(stateMachine, task, srcState, action, dstState, handler, extra);
    }
}
