package com.hx.flow.flow;

import com.hx.flow.flow.interf.Action;
import com.hx.flow.flow.interf.State;
import com.hx.flow.flow.interf.StateMachine;
import com.hx.flow.flow.interf.TransferHandler;
import com.hx.log.util.Tools;
import java.util.HashMap;
import java.util.Map;

/**
 * file name : StandardStateMachine.java
 * created at : 15:25  2017-03-19
 * created by 970655147
 */
public class StandardStateMachine implements StateMachine<State, Action> {

    /**
     * ��ǰ״̬����״̬�б�ĳ�ʼ״̬
     */
    private State initialState;
    /**
     * (status, action) -> [resultStatus, transferHandler]
     */
    private Map<State, Map<Action, StateAndHandler>> statusAction2Handler;

    public StandardStateMachine(State initialState,
                                Map<State, Map<Action, StateAndHandler>> statusAction2Handler) {
        Tools.assert0(statusAction2Handler != null, "'statusAction2Handler' can't be null !");
        this.initialState = initialState;
        this.statusAction2Handler = statusAction2Handler;
    }

    @Override
    public State initialState() {
        return initialState;
    }

    @Override
    public boolean hasNextState(State now) {
        Map<Action, StateAndHandler> action2Handler = statusAction2Handler.get(now);
        return ((action2Handler == null) || (action2Handler.isEmpty()) );
    }

    @Override
    public State getState(State now, Action action) {
        StateAndHandler pair = getPairByStatusAndAction(now, action);
        return (pair == null) ? null : pair.status;
    }

    @Override
    public TransferHandler<State, Action> getHandler(State now, Action action) {
        StateAndHandler pair = getPairByStatusAndAction(now, action);
        return (pair == null) ? null : pair.handler;
    }

    /**
     * ���ݵ�ǰ״̬ �Ͳ�ȡ��action, ��ȡ��Ӧ����һ��״̬ ��handler ��pair
     * ���û�ж�Ӧ��ԾǨ, ����null
     *
     * @param now    ��ǰ״̬
     * @param action ��ǰ״̬��ȡ��action
     * @return com.hx.test03.Test07StateMachine.StatusMachineImpl.StateAndHandler
     * @author Jerry.X.He
     * @since 2017/3/15 16:28
     */
    private StateAndHandler getPairByStatusAndAction(State now, Action action) {
        Map<Action, StateAndHandler> action2Handler = statusAction2Handler.get(now);
        if (action2Handler == null) {
            return null;
        }

        StateAndHandler pair = action2Handler.get(action);
        if (pair == null) {
            return null;
        }
        return pair;
    }

    /**
     * status & handler
     */
    private static class StateAndHandler {
        State status;
        TransferHandler<State, Action> handler;

        StateAndHandler(State status, TransferHandler<State, Action> handler) {
            this.status = status;
            this.handler = handler;
        }
    }

    /**
     * ����״̬����״̬�л�����
     *
     * @author Jerry.X.He
     * @since 2017/3/15 16:32
     */
    public static class TransferMapBuilder {

        private Map<State, Map<Action, StandardStateMachine.StateAndHandler>> statusAction2Handler;

        private TransferMapBuilder() {
            statusAction2Handler = new HashMap<>();
        }

        public static TransferMapBuilder start() {
            return new TransferMapBuilder();
        }

        public TransferMapBuilder add(State now, Action action, State resultStatus, TransferHandler<State, Action> handler) {
            if (statusAction2Handler == null) {
                statusAction2Handler = new HashMap<>();
            }

            Map<Action, StateAndHandler> action2Handler = statusAction2Handler.get(now);
            if (action2Handler == null) {
                action2Handler = new HashMap<>();
                statusAction2Handler.put(now, action2Handler);
            }

            action2Handler.put(action, new StateAndHandler(resultStatus, handler));
            return this;
        }

        public Map<State, Map<Action, StateAndHandler>> build() {
            return statusAction2Handler;
        }
    }

}

