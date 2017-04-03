package com.hx.flow.flow.interf.factory;

import com.hx.flow.flow.interf.Action;
import com.hx.flow.flow.interf.FlowTask;
import com.hx.flow.flow.interf.State;

/**
 * file name : FlowTaskFactory.java
 * created at : 20:21  2017-03-24
 * created by 970655147
 */
public interface FlowTaskFactory<StateType extends State, ActionType extends Action> {

    /**
     * ����taskId, flow, initState, extra, other����һ��FlowTask��ʵ��
     *
     * @param taskId    �����id
     * @param flow      �����Ӧ��flow
     * @param initState ����ĳ�ʼ״̬
     * @param extra     ������صĶ�����Ϣ
     * @param others    ������ص�������Ϣ
     * @return return an instance create by params above
     * @author 970655147 created at 2017-03-24 20:24
     */
    FlowTask<StateType, ActionType> create(String taskId, String flow, StateType initState, Object extra, Object others);

}
