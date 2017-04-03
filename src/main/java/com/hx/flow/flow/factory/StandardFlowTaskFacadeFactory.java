package com.hx.flow.flow.factory;

import com.hx.flow.flow.StandardFlowTaskFacade;
import com.hx.flow.flow.interf.Action;
import com.hx.flow.flow.interf.FlowTask;
import com.hx.flow.flow.interf.FlowTaskFacade;
import com.hx.flow.flow.interf.State;
import com.hx.flow.flow.interf.factory.FlowTaskFacadeFactory;

/**
 * file name : StandardFlowTaskFacadeFactory.java
 * created at : 21:43  2017-03-24
 * created by 970655147
 */
public class StandardFlowTaskFacadeFactory implements FlowTaskFacadeFactory<State, Action> {

    @Override
    public FlowTaskFacade<State, Action> create(FlowTask<State, Action> task, Object others) {
        return new StandardFlowTaskFacade(task);
    }

}
