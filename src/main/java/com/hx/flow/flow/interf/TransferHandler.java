package com.hx.flow.flow.interf;

/**
 * file name : TransferHandler.java
 * created at : 14:55  2017-03-19
 * created by 970655147
 */
public interface TransferHandler<StateType extends State, ActionType extends Action> {

    /**
     * ����ת����handler
     *
     * @param context ��ǰת���漰��������
     * @return return void
     * @author Jerry.X.He
     * @since 2017/3/15 16:02
     */
    boolean handle(TransferContext<StateType, ActionType> context);

}
