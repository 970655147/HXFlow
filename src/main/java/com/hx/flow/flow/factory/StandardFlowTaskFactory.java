package com.hx.flow.flow.factory;

import com.hx.flow.flow.StandardFlowTask;
import com.hx.flow.flow.interf.Action;
import com.hx.flow.flow.interf.FlowTask;
import com.hx.flow.flow.interf.State;
import com.hx.flow.flow.interf.factory.FlowTaskFactory;

/**
 * file name : StandardFlowTaskFactory.java
 * created at : 20:31  2017-03-24
 * created by 970655147
 */
public class StandardFlowTaskFactory implements FlowTaskFactory<State, Action> {

    @Override
    public FlowTask<State, Action> create(String taskId, String flow, State initState, Object extra, Object others) {
        return new StandardFlowTask(taskId, flow, initState, extra);
    }
}
