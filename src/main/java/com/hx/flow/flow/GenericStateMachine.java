package com.hx.flow.flow;

import com.hx.flow.flow.interf.Action;
import com.hx.flow.flow.interf.State;
import com.hx.flow.flow.interf.StateMachine;
import com.hx.flow.flow.interf.TransferHandler;
import com.hx.log.util.Tools;
import java.util.*;

/**
 * file name : GenericStateMachine.java
 * created at : 21:05  2017-03-24
 * created by 970655147
 */
public class GenericStateMachine<StateType extends State, ActionType extends Action>
        implements StateMachine<StateType, ActionType> {

    /**
     * ��ǰ״̬����״̬�б��ĳ�ʼ״̬
     */
    private StateType initialState;
    /**
     * (status, action) -> [resultStatus, transferHandler]
     */
    private Map<StateType, Map<ActionType, StateAndHandler<StateType, ActionType>>> statusAction2Handler;

    public GenericStateMachine(StateType initialState,
                                Map<StateType, Map<ActionType, StateAndHandler<StateType, ActionType>>> statusAction2Handler) {
        Tools.assert0(statusAction2Handler != null, "'statusAction2Handler' can't be null !");
        this.initialState = initialState;
        this.statusAction2Handler = statusAction2Handler;
    }

    @Override
    public StateType initialState() {
        return initialState;
    }

    @Override
    public boolean hasNextState(StateType now) {
        Map<ActionType, StateAndHandler<StateType, ActionType>> action2Handler = statusAction2Handler.get(now);
        return ((action2Handler == null) || (action2Handler.isEmpty()) );
    }

    @Override
    public List<ActionType> nextActions(StateType now) {
        Map<ActionType, StateAndHandler<StateType, ActionType>> action2Handler = statusAction2Handler.get(now);
        if((action2Handler == null) || (action2Handler.isEmpty()) ) {
            return Collections.emptyList();
        }

        List<ActionType> result = new ArrayList<>(action2Handler.size());
        for(ActionType action : action2Handler.keySet()) {
            result.add(action);
        }
        return result;
    }

    @Override
    public StateType getState(StateType now, ActionType action) {
        StateAndHandler<StateType, ActionType> pair = getPairByStatusAndAction(now, action);
        return (pair == null) ? null : pair.status;
    }

    @Override
    public TransferHandler<StateType, ActionType> getHandler(StateType now, ActionType action) {
        StateAndHandler<StateType, ActionType> pair = getPairByStatusAndAction(now, action);
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
    private StateAndHandler<StateType, ActionType> getPairByStatusAndAction(StateType now, ActionType action) {
        Map<ActionType, StateAndHandler<StateType, ActionType>> action2Handler = statusAction2Handler.get(now);
        if (action2Handler == null) {
            return null;
        }

        StateAndHandler<StateType, ActionType> pair = action2Handler.get(action);
        if (pair == null) {
            return null;
        }
        return pair;
    }

    /**
     * status & handler
     */
    static class StateAndHandler<StateType extends State, ActionType extends Action> {
        StateType status;
        TransferHandler<StateType, ActionType> handler;

        StateAndHandler(StateType status, TransferHandler<StateType, ActionType> handler) {
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
    public static class TransferMapBuilder<StateType extends State, ActionType extends Action> {

        private Map<StateType, Map<ActionType, StateAndHandler<StateType, ActionType>>> statusAction2Handler;

        protected TransferMapBuilder() {
            statusAction2Handler = new HashMap<>();
        }

        public static <StateType extends State, ActionType extends Action> TransferMapBuilder<StateType, ActionType> start() {
            return new TransferMapBuilder<>();
        }

        public TransferMapBuilder<StateType, ActionType> add(StateType now, ActionType action, StateType resultStatus, TransferHandler<StateType, ActionType> handler) {
            if (statusAction2Handler == null) {
                statusAction2Handler = new HashMap<>();
            }

            Map<ActionType, StateAndHandler<StateType, ActionType>> action2Handler = statusAction2Handler.get(now);
            if (action2Handler == null) {
                action2Handler = new HashMap<>();
                statusAction2Handler.put(now, action2Handler);
            }

            action2Handler.put(action, new StateAndHandler<>(resultStatus, handler));
            return this;
        }

        public Map<StateType, Map<ActionType, StateAndHandler<StateType, ActionType>>> build() {
            return statusAction2Handler;
        }
    }


}