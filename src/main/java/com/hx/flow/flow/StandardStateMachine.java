package com.hx.flow.flow;

import com.hx.flow.flow.interf.Action;
import com.hx.flow.flow.interf.State;
import com.hx.flow.flow.interf.StateMachine;
import com.hx.flow.flow.interf.TransferHandler;
import com.hx.log.util.Tools;
import java.util.*;

/**
 * file name : StandardStateMachine.java
 * created at : 15:25  2017-03-19
 * created by 970655147
 */
public class StandardStateMachine extends GenericStateMachine<State, Action> {

    public StandardStateMachine(State initialState,
                                Map<State, Map<Action, GenericStateMachine.StateAndHandler<State, Action>>> statusAction2Handler) {
        super(initialState, statusAction2Handler);
    }

}

