package com.hx.flow.flow;

import com.hx.flow.flow.interf.State;
import java.util.HashMap;
import java.util.Map;

/**
 * file name : StandardState.java
 * created at : 17:49  2017-03-19
 * created by 970655147
 */
public class StandardState implements State<StandardState> {

    /**
     * id -> state
     */
    private static final Map<String, StandardState> ID_2_STATE = new HashMap<>();
    /**
     * dummy
     */
    public static final StandardState DUMMY = StandardState.getInstance("dummy", "dummy");

    /**
     * id, extra
     */
    private String id;
    private Object extra;

    private StandardState(String id, Object extra) {
        this.id = id;
        this.extra = extra;
    }

    /**
     * 获取一个StandardState实例
     *
     * @param id    state的id
     * @param extra state的附加属性
     * @return com.hx.flow.flow.StandardState an new instance of StandardState(id, extra)
     * @throws
     * @author 970655147 created at 2017-03-19 18:08
     */
    public static StandardState getInstance(String id, Object extra) {
        StandardState state = new StandardState(id, extra);
        ID_2_STATE.put(id, state);
        return state;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public StandardState create(String id, Object extra) {
        return StandardState.getInstance(id, extra);
    }

    @Override
    public StandardState idOf(String id) {
        return ID_2_STATE.get(id);
    }

    @Override
    public StandardState status() {
        return this;
    }

    @Override
    public Object extra() {
        return extra;
    }

}
