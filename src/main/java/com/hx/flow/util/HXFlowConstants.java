package com.hx.flow.util;

import com.hx.log.util.Tools;

/**
 * file name : HXFlowConstants.java
 * created at : 18:48  2017-03-19
 * created by 970655147
 */
public final class HXFlowConstants {

    // disable constructor
    private HXFlowConstants() {
        Tools.assert0("can't instantiate !");
    }

    /**
     * deploy 流程, 的配置的第一级结点的key
     */
    public static final String STATES = "states";
    public static final String INITIAL_STATE = "initialState";
    public static final String ACTIONS = "actions";
    public static final String TRANSFERS = "transfers";

    /**
     * deploy 流程, 的配置的第二级结点的key
     */
    public static final String STATE_ID = "id";
    public static final String STATE_EXTRA = "extra";
    public static final String ACTION_ID = "id";
    public static final String ACTION_EXTRA = "extra";
    public static final String TRANSFER_SRC = "src";
    public static final String TRANSFER_ACTION = "action";
    public static final String TRANSFER_DST = "dst";
    public static final String TRANSFER_HANDLER = "handler";

}
