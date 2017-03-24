package com.hx.flow.flow;

import com.hx.flow.flow.factory.SeqTaskIdGenerator;
import com.hx.flow.flow.factory.StandardFlowTaskFacadeFactory;
import com.hx.flow.flow.factory.StandardFlowTaskFactory;
import com.hx.flow.flow.factory.StandardTransferContextFactory;
import com.hx.flow.flow.interf.*;
import com.hx.flow.flow.interf.factory.*;
import com.hx.flow.util.HXFlowConstants;
import com.hx.log.util.JSONUtils;
import com.hx.log.util.Log;
import com.hx.log.util.Tools;
import java.io.File;
import java.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * file name : StandardFlowEngine.java
 * created at : 15:59  2017-03-19
 * created by 970655147
 */
public class StandardFlowEngine extends GenericFlowEngine<State, Action> {

    public StandardFlowEngine() {
        this(new HashMap<String, StateMachine<State, Action>>());
    }

    public StandardFlowEngine(Map<String, StateMachine<State, Action>> flow2StatusMachine) {
        this(flow2StatusMachine, new SeqTaskIdGenerator());
    }

    public StandardFlowEngine(Map<String, StateMachine<State, Action>> flow2StatusMachine,
                              TaskIdGenerator taskIdGenerator) {
        super(new StandardFlowTaskFactory(), new StandardFlowTaskFacadeFactory(), new StandardTransferContextFactory(),
                flow2StatusMachine, taskIdGenerator);
    }

    public StandardFlowEngine(FlowTaskFactory<State, Action> taskFactory,
                              FlowTaskFacadeFactory<State, Action> taskFacadeFactory,
                              TransferContextFactory<State, Action> transferContextFactory) {
        super(taskFactory, taskFacadeFactory, transferContextFactory);
    }

    public StandardFlowEngine(FlowTaskFactory<State, Action> taskFactory,
                              FlowTaskFacadeFactory<State, Action> taskFacadeFactory,
                              TransferContextFactory<State, Action> transferContextFactory,
                              Map<String, StateMachine<State, Action>> flow2StatusMachine) {
        super(taskFactory, taskFacadeFactory, transferContextFactory, flow2StatusMachine);
    }

    public StandardFlowEngine(FlowTaskFactory<State, Action> taskFactory,
                              FlowTaskFacadeFactory<State, Action> taskFacadeFactory,
                              TransferContextFactory<State, Action> transferContextFactory,
                              Map<String, StateMachine<State, Action>> flow2StatusMachine,
                              TaskIdGenerator taskIdGenerator) {
        super(taskFactory, taskFacadeFactory, transferContextFactory, flow2StatusMachine, taskIdGenerator);
    }

}
