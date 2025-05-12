package org.example.project3.utilities.others.mappers;

import com.example.bodybuild.beans.RequestBean;
import com.example.bodybuild.model.Request;
import com.example.bodybuild.model.Schedule;

public class RequestMapper implements BeanAndModelMapper<RequestBean, Request>{
    ScheduleMapper scheduleMapper = new ScheduleMapper();
    ExerciseMapper exerciseMapper = new ExerciseMapper();
    @Override
    public Request fromBeanToModel(RequestBean bean) {
        return new Request(bean.getID(), scheduleMapper.fromBeanToModel(bean.getSchedule()), exerciseMapper.fromBeanToModel(bean.getExercise()), bean.getReason());
    }

    @Override
    public RequestBean fromModelToBean(Request model) {
        return new RequestBean(model.getID(), scheduleMapper.fromModelToBean(model.getSchedule()), exerciseMapper.fromModelToBean(model.getExercise()), model.getReason());
    }
}
