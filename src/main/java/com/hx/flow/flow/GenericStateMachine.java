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
     * 当前状态机的状态列表的初始状态
     */
    private StateType initialState;
    /**
     * (state, action) -> [resultState, transferHandler]
     */
    private Map<StateType, Map<ActionType, StateAndHandler<StateType, ActionType>>> stateAction2Handler;

    public GenericStateMachine(StateType initialState,
                                Map<StateType, Map<ActionType, StateAndHandler<StateType, ActionType>>> stateAction2Handler) {
        Tools.assert0(stateAction2Handler != null, "'stateAction2Handler' can't be null !");
        this.initialState = initialState;
        this.stateAction2Handler = stateAction2Handler;
    }

    @Override
    public StateType initialState() {
        return initialState;
    }

    @Override
    public boolean hasNextState(StateType now) {
        Map<ActionType, StateAndHandler<StateType, ActionType>> action2Handler = stateAction2Handler.get(now);
        return ((action2Handler == null) || (action2Handler.isEmpty()) );
    }

    @Override
    public List<ActionType> nextActions(StateType now) {
        Map<ActionType, StateAndHandler<StateType, ActionType>> action2Handler = stateAction2Handler.get(now);
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
        StateAndHandler<StateType, ActionType> pair = getPairByStateAndAction(now, action);
        return (pair == null) ? null : pair.state;
    }

    @Override
    public TransferHandler<StateType, ActionType> getHandler(StateType now, ActionType action) {
        StateAndHandler<StateType, ActionType> pair = getPairByStateAndAction(now, action);
        return (pair == null) ? null : pair.handler;
    }

    /**
     * 根据当前状态 和采取的action, 获取对应的下一个状态 和handler 的pair
     * 如果没有对应的跃迁, 返回null
     *
     * @param now    当前状态
     * @param action 当前状态采取的action
     * @return StateAndHandler
     * @author Jerry.X.He
     * @since 2017/3/15 16:28
     */
    private StateAndHandler<StateType, ActionType> getPairByStateAndAction(StateType now, ActionType action) {
        Map<ActionType, StateAndHandler<StateType, ActionType>> action2Handler = stateAction2Handler.get(now);
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
     * state & handler
     */
    static class StateAndHandler<StateType extends State, ActionType extends Action> {
        StateType state;
        TransferHandler<StateType, ActionType> handler;

        StateAndHandler(StateType state, TransferHandler<StateType, ActionType> handler) {
            this.state = state;
            this.handler = handler;
        }
    }

    /**
     * 构造状态机的状态切换数据
     *
     * @author Jerry.X.He
     * @since 2017/3/15 16:32
     */
    public static class TransferMapBuilder<StateType extends State, ActionType extends Action> {

        private Map<StateType, Map<ActionType, StateAndHandler<StateType, ActionType>>> stateAction2Handler;

        protected TransferMapBuilder() {
            stateAction2Handler = new HashMap<>();
        }

        public static <StateType extends State, ActionType extends Action> TransferMapBuilder<StateType, ActionType> start() {
            return new TransferMapBuilder<>();
        }

        public TransferMapBuilder<StateType, ActionType> add(StateType now, ActionType action, StateType resultState, TransferHandler<StateType, ActionType> handler) {
            if (stateAction2Handler == null) {
                stateAction2Handler = new HashMap<>();
            }

            Map<ActionType, StateAndHandler<StateType, ActionType>> action2Handler = stateAction2Handler.get(now);
            if (action2Handler == null) {
                action2Handler = new HashMap<>();
                stateAction2Handler.put(now, action2Handler);
            }

            action2Handler.put(action, new StateAndHandler<>(resultState, handler));
            return this;
        }

        public Map<StateType, Map<ActionType, StateAndHandler<StateType, ActionType>>> build() {
            return stateAction2Handler;
        }
    }


}
