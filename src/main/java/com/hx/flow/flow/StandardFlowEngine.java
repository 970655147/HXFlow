package com.hx.flow.flow;

import com.hx.flow.flow.factory.SeqTaskIdGenerator;
import com.hx.flow.flow.factory.StandardFlowTaskFacadeFactory;
import com.hx.flow.flow.factory.StandardFlowTaskFactory;
import com.hx.flow.flow.factory.StandardTransferContextFactory;
import com.hx.flow.flow.interf.*;
import com.hx.flow.flow.interf.factory.*;

import java.util.*;

/**
 * file name : StandardFlowEngine.java
 * created at : 15:59  2017-03-19
 * created by 970655147
 */
public class StandardFlowEngine extends GenericFlowEngine<State, Action> {

    public StandardFlowEngine() {
        this(new HashMap<String, StateMachine<State, Action>>());
    }

    public StandardFlowEngine(Map<String, StateMachine<State, Action>> flow2StateMachine) {
        this(flow2StateMachine, new SeqTaskIdGenerator());
    }

    public StandardFlowEngine(Map<String, StateMachine<State, Action>> flow2StateMachine,
                              TaskIdGenerator taskIdGenerator) {
        super(new StandardFlowTaskFactory(), new StandardFlowTaskFacadeFactory(), new StandardTransferContextFactory(),
                flow2StateMachine, taskIdGenerator);
    }

    public StandardFlowEngine(FlowTaskFactory<State, Action> taskFactory,
                              FlowTaskFacadeFactory<State, Action> taskFacadeFactory,
                              TransferContextFactory<State, Action> transferContextFactory) {
        super(taskFactory, taskFacadeFactory, transferContextFactory);
    }

    public StandardFlowEngine(FlowTaskFactory<State, Action> taskFactory,
                              FlowTaskFacadeFactory<State, Action> taskFacadeFactory,
                              TransferContextFactory<State, Action> transferContextFactory,
                              Map<String, StateMachine<State, Action>> flow2StateMachine) {
        super(taskFactory, taskFacadeFactory, transferContextFactory, flow2StateMachine);
    }

    public StandardFlowEngine(FlowTaskFactory<State, Action> taskFactory,
                              FlowTaskFacadeFactory<State, Action> taskFacadeFactory,
                              TransferContextFactory<State, Action> transferContextFactory,
                              Map<String, StateMachine<State, Action>> flow2StateMachine,
                              TaskIdGenerator taskIdGenerator) {
        super(taskFactory, taskFacadeFactory, transferContextFactory, flow2StateMachine, taskIdGenerator);
    }

}
