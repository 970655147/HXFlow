package com.hx.flow.flow.interf.factory;

import com.hx.flow.flow.interf.Action;
import com.hx.flow.flow.interf.FlowTask;
import com.hx.flow.flow.interf.State;

/**
 * file name : FlowTaskFactory.java
 * created at : 20:21  2017-03-24
 * created by 970655147
 */
public interface FlowTaskFactory<StateType extends State, ActionType extends Action> {

    /**
     * 根据taskId, flow, initState, extra, other创建一个FlowTask的实例
     *
     * @param taskId    任务的id
     * @param flow      任务对应的flow
     * @param initState 任务的初始状态
     * @param extra     任务相关的额外信息
     * @param others    任务相关的其他信息
     * @return return an instance create by params above
     * @author 970655147 created at 2017-03-24 20:24
     */
    FlowTask<StateType, ActionType> create(String taskId, String flow, StateType initState, Object extra, Object others);

}
