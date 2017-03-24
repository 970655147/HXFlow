package com.hx.flow.test;

import com.hx.flow.flow.*;
import com.hx.flow.flow.factory.StandardFlowTaskFacadeFactory;
import com.hx.flow.flow.factory.StandardFlowTaskFactory;
import com.hx.flow.flow.factory.StandardTransferContextFactory;
import com.hx.flow.flow.interf.*;
import com.hx.flow.flow.interf.factory.FlowTaskFacadeFactory;
import com.hx.flow.flow.interf.factory.TransferHandlerFactory;
import com.hx.log.util.IdxGenerator;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

import static com.hx.log.util.Log.info;
import static com.hx.log.util.Log.infoHorizon;

/**
 * file name : Test02FlowEngine.java
 * created at : 16:46  2017-03-19
 * created by 970655147
 */
public class Test02FlowEngine {

    @Test
    public void flowEngine() throws Exception {

        StandardAction accept = StandardAction.getInstance("accept", "acceptExtra");
        StandardAction reject = StandardAction.getInstance("reject", "rejectExtra");
        StandardAction timeout = StandardAction.getInstance("timeout", "timeoutExtra");

        StandardStateMachine.TransferMapBuilder<State, Action> builder = StandardStateMachine.TransferMapBuilder.start()
                .add(StoreCreateState.STATUS_APPLY, accept, StoreCreateState.STATUS_RM_AUTH, new StatusApplyHandler() )
                .add(StoreCreateState.STATUS_RM_AUTH, accept, StoreCreateState.STATUS_SUCCESS, DoNothingTransferHandler.getInstance())
                .add(StoreCreateState.STATUS_RM_AUTH, reject, StoreCreateState.STATUS_APPLY, DoNothingTransferHandler.getInstance())
                .add(StoreCreateState.STATUS_RM_AUTH, timeout, StoreCreateState.STATUS_FAIL, DoNothingTransferHandler.getInstance());

        StateMachine<State, Action> machine = new StandardStateMachine(StoreCreateState.STATUS_APPLY, builder.build());

        String flow = "flow01";
        FlowEngine<State, Action> flowEngine = new StandardFlowEngine();
        boolean succ = flowEngine.deploy(flow, machine);
        info(succ);

        String taskId = flowEngine.startFlowInstance(flow, null, null);
        info(flowEngine.getTask(taskId, null).now());

        infoHorizon();
        succ = flowEngine.complete(taskId, accept, "extra", null);
        infoHorizon();

        info(succ);
        info(flowEngine.getTask(taskId, null).now());
        info(taskId);
    }

    @Test
    public void deployByFile() throws Exception {

        String flow = "flow01";
        String flowGraphPath = "./src/main/resources/StoreCreate.json";

        FlowEngine<State, Action> flowEngine = new StandardFlowEngine();
        flowEngine.deploy(flow, flowGraphPath, StandardState.DUMMY, StandardAction.DUMMY, new TransferHandlerFactoryImpl(), null);

        info(flowEngine.flows() );

        String taskId = flowEngine.startFlowInstance(flow, null, null);
        infoHorizon();
        flowEngine.complete(taskId, StandardAction.DUMMY.idOf("accept"), "extra", null);
        infoHorizon();

        info(flowEngine.getTask(taskId, null).now().id() );

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

    private static class StatusApplyHandler implements TransferHandler<State, Action> {

        @Override
        public boolean handle(TransferContext<State, Action> context) {
            info(context.task() );
            info(context.srcState() );
            info(context.action() );
            info(context.dstState() );
            info(context.handler() );
            info(context.extra() );

            return true;
        }
    }

    private static class TransferHandlerFactoryImpl implements TransferHandlerFactory<State, Action> {
        @Override
        public TransferHandler<State, Action> create(String handlerId, Object others) {
            if("submitApplyHandler".equals(handlerId) ) {
                return new StatusApplyHandler();
            }
            return DoNothingTransferHandler.getInstance();
        }
    }

}
