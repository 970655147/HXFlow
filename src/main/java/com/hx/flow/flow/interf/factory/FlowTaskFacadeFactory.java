package com.hx.flow.flow.interf.factory;

import com.hx.flow.flow.interf.Action;
import com.hx.flow.flow.interf.FlowTask;
import com.hx.flow.flow.interf.FlowTaskFacade;
import com.hx.flow.flow.interf.State;

/**
 * file name : FlowTaskFacadeFactory.java
 * created at : 21:41  2017-03-24
 * created by 970655147
 */
public interface FlowTaskFacadeFactory<StateType extends State, ActionType extends Action> {

    /**
     * 根据task, other创建一个FlowTask的实例
     *
     * @param task  需要委托的任务
     * @param others 任务相关的其他信息
     * @return return an instance create by params above
     * @author 970655147 created at 2017-03-24 20:24
     */
    FlowTaskFacade<StateType, ActionType> create(FlowTask<StateType, ActionType> task, Object others);

}
