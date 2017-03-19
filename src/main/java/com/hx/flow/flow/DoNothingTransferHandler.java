package com.hx.flow.flow;

import com.hx.flow.flow.interf.Action;
import com.hx.flow.flow.interf.State;
import com.hx.flow.flow.interf.TransferContext;
import com.hx.flow.flow.interf.TransferHandler;

/**
 * file name : DoNothingTransferHandler.java
 * created at : 15:11  2017-03-19
 * created by 970655147
 */
public class DoNothingTransferHandler implements TransferHandler<State, Action> {

    private static TransferHandler<State, Action> INSTANCE;

    private DoNothingTransferHandler() {
    }

    /**
     * 获取DoNothingTransferHandler的实例
     *
     * @return com.hx.test03.Test07StateMachine.DoNothingTransferHandler
     * @author Jerry.X.He
     * @since 2017/3/15 16:15
     */
    public static TransferHandler<State, Action> getInstance() {
        if (INSTANCE == null) {
            synchronized (DoNothingTransferHandler.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DoNothingTransferHandler();
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public boolean handle(TransferContext<State, Action> context) {
        return true;
    }

}
