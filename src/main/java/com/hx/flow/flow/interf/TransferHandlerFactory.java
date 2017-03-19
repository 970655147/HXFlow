package com.hx.flow.flow.interf;

/**
 * file name : TransferHandlerFactory.java
 * created at : 19:22  2017-03-19
 * created by 970655147
 */
public interface TransferHandlerFactory<StateType extends State, ActionType extends Action> {

    /**
     * ���ݸ�����handlerId����һ��handler
     *
     * @param handlerId handlerId
     * @return
     * @throws
     * @author 970655147 created at 2017-03-19 19:23
     */
    public TransferHandler<StateType, ActionType> create(String handlerId);

}
