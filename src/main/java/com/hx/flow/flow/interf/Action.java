package com.hx.flow.flow.interf;

/**
 * $note : you'd better define subclass of 'Action' as enum if available
 * file name : Action.java
 * created at : 14:54  2017-03-19
 * created by 970655147
 */
public interface Action<T extends Action> {

    /**
     * 获取当前的action的id
     *
     * @return T return current action's id
     * @author Jerry.X.He
     * @since 2017/3/15 16:02
     */
    String id();

    /**
     * 创建一个当前Action的实例
     *
     * @param id action的id
     * @param extra action的附加信息
     * @return T an State instance associate 'id'
     * @author 970655147 created at 2017-03-19 18:34
     */
    T create(String id, Object extra);

    /**
     * 获取id对应的action
     *
     * @return T return null if no (id, action) pair, or return corresponding action
     * @author Jerry.X.He
     * @since 2017/3/15 16:02
     */
    T idOf(String id);

    /**
     * 获取当前的action
     *
     * @return T return current actionObject
     * @author Jerry.X.He
     * @since 2017/3/15 16:02
     */
    T action();

    /**
     * 获取当前action的一些附加的信息
     *
     * @return java.lang.Object some extra info bind on current action
     * @author 970655147 created at 2017-03-19 15:05
     */
    Object extra();

}
