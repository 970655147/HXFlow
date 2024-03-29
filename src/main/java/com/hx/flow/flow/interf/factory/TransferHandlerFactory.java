package com.hx.flow.flow.interf.factory;

import com.hx.flow.flow.interf.Action;
import com.hx.flow.flow.interf.State;
import com.hx.flow.flow.interf.TransferHandler;

/**
 * file name : TransferHandlerFactory.java
 * created at : 19:22  2017-03-19
 * created by 970655147
 */
public interface TransferHandlerFactory<StateType extends State, ActionType extends Action> {

    /**
     * 根据给定的handlerId创建一个handler
     *
     * @param handlerId handlerId
     * @param others 任务相关的其他信息
     * @return an instance of TransferHandler corresponding handlerId
     * @author 970655147 created at 2017-03-19 19:23
     */
    public TransferHandler<StateType, ActionType> create(String handlerId, Object others);

}
