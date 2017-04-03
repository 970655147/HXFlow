package com.hx.flow.flow.factory;

import com.hx.flow.flow.interf.factory.TaskIdGenerator;
import com.hx.log.util.IdxGenerator;

/**
 * file name : SeqTaskIdGenerator.java
 * created at : 0:35  2017-03-23
 * created by 970655147
 */
public class SeqTaskIdGenerator implements TaskIdGenerator {

    /**
     * ����taskId�Ĺ���
     */
    private IdxGenerator taskIdGenerator = new IdxGenerator();

    @Override
    public String nextId() {
        return taskId(taskIdGenerator.nextId());
    }

    /**
     * ��ȡtaskId
     *
     * @param id ������id
     * @return java.lang.String
     * @author 970655147 created at 2017-03-19 16:49
     */
    private String taskId(int id) {
        return "task - " + id;
    }

}
