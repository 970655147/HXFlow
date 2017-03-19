package com.hx.flow.flow.interf;

/**
 * $note : you'd better define subclass of 'State' as enum if available
 * file name : State.java
 * created at : 14:53  2017-03-19
 * created by 970655147
 */
public interface State<T extends State> {

    /**
     * ��ȡ��ǰ״̬��id
     *
     * @return StatusType return current state' id
     * @author Jerry.X.He
     * @since 2017/3/15 16:02
     */
    String id();

    /**
     * ����һ����ǰState��ʵ��
     *
     * @param id action��id
     * @param extra action�ĸ�����Ϣ
     * @return an State instance associate 'id'
     * @author 970655147 created at 2017-03-19 18:34
     */
    T create(String id, Object extra);

    /**
     * ��ȡid��Ӧ��state
     *
     * @return StatusType return null if no (id, action) pair, or return corresponding action
     * @author Jerry.X.He
     * @since 2017/3/15 16:02
     */
    T idOf(String id);

    /**
     * ��ȡ��ǰ��״̬
     *
     * @return StatusType return current state Object
     * @author Jerry.X.He
     * @since 2017/3/15 16:02
     */
    T status();

    /**
     * ��ȡ��ǰ״̬��һЩ���ӵ���Ϣ
     *
     * @return some extra info bind on current state
     * @author 970655147 created at 2017-03-19 15:05
     */
    Object extra();

}
