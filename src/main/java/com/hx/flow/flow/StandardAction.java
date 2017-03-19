package com.hx.flow.flow;

import com.hx.flow.flow.interf.Action;
import java.util.HashMap;
import java.util.Map;

/**
 * file name : StandardAction.java
 * created at : 15:15  2017-03-19
 * created by 970655147
 */
public class StandardAction implements Action<StandardAction> {

    /**
     * id -> state
     */
    private static final Map<String, StandardAction> ID_2_ACTION = new HashMap<>();
    /**
     * dummy
     */
    public static final StandardAction DUMMY = StandardAction.getInstance("dummy", "dummy");

    /**
     * id, extra
     */
    private String id;
    private Object extra;

    private StandardAction(String id, Object extra) {
        this.id = id;
        this.extra = extra;
    }

    /**
     * 获取一个StandardState实例
     *
     * @param id    state的id
     * @param extra state的附加属性
     * @return com.hx.flow.flow.StandardState an new instance of StandardState(id, extra)
     * @author 970655147 created at 2017-03-19 18:08
     */
    public static StandardAction getInstance(String id, Object extra) {
        StandardAction state = new StandardAction(id, extra);
        ID_2_ACTION.put(id, state);
        return state;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public StandardAction create(String id, Object extra) {
        return StandardAction.getInstance(id, extra);
    }

    @Override
    public StandardAction idOf(String id) {
        return ID_2_ACTION.get(id);
    }


    @Override
    public StandardAction action() {
        return this;
    }

    @Override
    public Object extra() {
        return extra;
    }

}
