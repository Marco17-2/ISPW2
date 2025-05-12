package org.example.project3.utilities.others.mappers;

import com.example.bodybuild.beans.*;
import com.example.bodybuild.model.*;
import com.example.bodybuild.patterns.factory.BeanAndModelMapperFactory;

public class MapperRegistration {
    //Questo metodo viene chiamato nel main per registrare tutti i mappers

    private MapperRegistration() {
    }
    public static void registerMappers() {
        BeanAndModelMapperFactory factory = BeanAndModelMapperFactory.getInstance();
        factory.registerMapper(CustomerBean.class, Customer.class, new CustomerMapper());
        factory.registerMapper(TrainerBean.class, Trainer.class, new TrainerMapper());
        factory.registerMapper(CredentialsBean.class, Credentials.class, new CredentialsMapper());
        //factory.registerMapper(SubscriptionBean.class, Subscription.class, new SubscriptionMapper());
        factory.registerMapper(RequestBean.class, Request.class, new RequestMapper());
        factory.registerMapper(ScheduleBean.class, Schedule.class, new ScheduleMapper());
        factory.registerMapper(ExerciseBean.class, Exercise.class, new ExerciseMapper());
    }
}
