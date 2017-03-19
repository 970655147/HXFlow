package com.hx.flow.flow.interf;

/**
 * $note : you'd better define subclass of 'State' as enum if available
 * file name : State.java
 * created at : 14:53  2017-03-19
 * created by 970655147
 */
public interface State<T extends State> {

    /**
     * 获取当前状态的id
     *
     * @return StatusType return current state' id
     * @author Jerry.X.He
     * @since 2017/3/15 16:02
     */
    String id();

    /**
     * 创建一个当前State的实例
     *
     * @param id action的id
     * @param extra action的附加信息
     * @return an State instance associate 'id'
     * @author 970655147 created at 2017-03-19 18:34
     */
    T create(String id, Object extra);

    /**
     * 获取id对应的state
     *
     * @return StatusType return null if no (id, action) pair, or return corresponding action
     * @author Jerry.X.He
     * @since 2017/3/15 16:02
     */
    T idOf(String id);

    /**
     * 获取当前的状态
     *
     * @return StatusType return current state Object
     * @author Jerry.X.He
     * @since 2017/3/15 16:02
     */
    T status();

    /**
     * 获取当前状态的一些附加的信息
     *
     * @return some extra info bind on current state
     * @author 970655147 created at 2017-03-19 15:05
     */
    Object extra();

}
