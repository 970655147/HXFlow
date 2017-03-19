package com.hx.flow.flow;

import com.hx.flow.flow.interf.Action;
import com.hx.flow.flow.interf.FlowTask;
import com.hx.flow.flow.interf.State;

/**
 * file name : StandardFlowTask.java
 * created at : 15:58  2017-03-19
 * created by 970655147
 */
public class StandardFlowTask implements FlowTask<State, Action> {

    private String id;
    private String flow;
    private State state;

    public StandardFlowTask(String id, String flow, State state) {
        this.id = id;
        this.flow = flow;
        this.state = state;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String flow() {
        return flow;
    }

    @Override
    public State now() {
        return state;
    }

    @Override
    public boolean transfer(State newState) {
        this.state = newState;
        return true;
    }
}
