package com.hx.flow.flow;

import com.hx.flow.flow.interf.Action;
import com.hx.flow.flow.interf.FlowTask;
import com.hx.flow.flow.interf.FlowTaskFacade;
import com.hx.flow.flow.interf.State;
import com.hx.log.util.Tools;

/**
 * file name : StandardFlowTask.java
 * created at : 15:58  2017-03-19
 * created by 970655147
 */
public class StandardFlowTaskFacade implements FlowTaskFacade<State, Action> {

    private FlowTask<State, Action> flowTask;

    public StandardFlowTaskFacade(FlowTask<State, Action> flowTask) {
        Tools.assert0(flowTask != null, "'flowTask' can't be null !");
        this.flowTask = flowTask;
    }

    @Override
    public String id() {
        return flowTask.id();
    }

    @Override
    public String flow() {
        return flowTask.flow();
    }

    @Override
    public State now() {
        return flowTask.now();
    }
}
