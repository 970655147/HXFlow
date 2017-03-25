package com.hx.flow.flow.interf;

/**
 * $note : you'd better define subclass of 'Action' as enum if available
 * file name : Action.java
 * created at : 14:54  2017-03-19
 * created by 970655147
 */
public interface Action<T extends Action> {

    /**
     * ��ȡ��ǰ��action��id
     *
     * @return T return current action's id
     * @author Jerry.X.He
     * @since 2017/3/15 16:02
     */
    String id();

    /**
     * ����һ����ǰAction��ʵ��
     *
     * @param id action��id
     * @param extra action�ĸ�����Ϣ
     * @return T an State instance associate 'id'
     * @author 970655147 created at 2017-03-19 18:34
     */
    T create(String id, Object extra);

    /**
     * ��ȡid��Ӧ��action
     *
     * @return T return null if no (id, action) pair, or return corresponding action
     * @author Jerry.X.He
     * @since 2017/3/15 16:02
     */
    T idOf(String id);

    /**
     * ��ȡ��ǰ��action
     *
     * @return T return current actionObject
     * @author Jerry.X.He
     * @since 2017/3/15 16:02
     */
    T action();

    /**
     * ��ȡ��ǰaction��һЩ���ӵ���Ϣ
     *
     * @return java.lang.Object some extra info bind on current action
     * @author 970655147 created at 2017-03-19 15:05
     */
    Object extra();

}
