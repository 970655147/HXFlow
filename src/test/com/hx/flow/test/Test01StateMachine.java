package com.hx.flow.test;

import com.hx.flow.flow.DoNothingTransferHandler;
import com.hx.flow.flow.GenericStateMachine;
import com.hx.flow.flow.StandardAction;
import com.hx.flow.flow.StandardStateMachine;
import com.hx.flow.flow.interf.Action;
import com.hx.flow.flow.interf.State;
import com.hx.flow.flow.interf.StateMachine;
import com.hx.flow.flow.interf.TransferHandler;
import com.hx.log.util.IdxGenerator;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

/**
 * file name : Test01StateMachine.java
 * created at : 15:35  2017-03-19
 * created by 970655147
 */
public class Test01StateMachine {

    @Test
    public void statusMachine() {

        StandardAction accept = StandardAction.getInstance("accept", "acceptExtra");
        StandardAction reject = StandardAction.getInstance("reject", "rejectExtra");
        StandardAction timeout = StandardAction.getInstance("timeout", "timeoutExtra");

        GenericStateMachine.TransferMapBuilder<State, Action> builder =
                StandardStateMachine.TransferMapBuilder.start()
                .add(StoreCreateState.STATUS_APPLY, accept, StoreCreateState.STATUS_RM_AUTH, DoNothingTransferHandler.getInstance())
                .add(StoreCreateState.STATUS_RM_AUTH, accept, StoreCreateState.STATUS_SUCCESS, DoNothingTransferHandler.getInstance())
                .add(StoreCreateState.STATUS_RM_AUTH, reject, StoreCreateState.STATUS_APPLY, DoNothingTransferHandler.getInstance())
                .add(StoreCreateState.STATUS_RM_AUTH, timeout, StoreCreateState.STATUS_FAIL, DoNothingTransferHandler.getInstance());

        StateMachine<State, Action> machine = new StandardStateMachine(StoreCreateState.STATUS_APPLY, builder.build());

        State resultStatus = machine.getState(StoreCreateState.STATUS_RM_AUTH, reject);
        System.out.println(resultStatus);
        TransferHandler handler = machine.getHandler(StoreCreateState.STATUS_RM_AUTH, reject);
        System.out.println(handler);

    }


    private static enum StoreCreateState implements State<StoreCreateState> {
        STATUS_APPLY, STATUS_RM_AUTH, STATUS_SUCCESS, STATUS_FAIL;

        private String id;
        private IdxGenerator idxGenerator = new IdxGenerator();
        Map<String, StoreCreateState> id2Instance = new HashMap<>();

        private StoreCreateState() {
            this.id = String.valueOf(idxGenerator.nextId());
            id2Instance.put(this.id, this);
        }


        @Override
        public String id() {
            return id;
        }

        @Override
        public StoreCreateState create(String id, Object extra) {
            return idOf(id);
        }

        @Override
        public StoreCreateState idOf(String id) {
            return id2Instance.get(id);
        }

        @Override
        public StoreCreateState status() {
            return this;
        }

        @Override
        public Object extra() {
            return null;
        }
    }

}
