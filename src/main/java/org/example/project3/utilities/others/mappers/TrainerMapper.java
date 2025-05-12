package org.example.project3.utilities.others.mappers;

import com.example.bodybuild.beans.TrainerBean;
import com.example.bodybuild.model.Trainer;

public class TrainerMapper implements BeanAndModelMapper<TrainerBean,Trainer> {
    private final CredentialsMapper credentialsMapper = new CredentialsMapper();
    @Override
    public Trainer fromBeanToModel(TrainerBean bean) {
        if(bean.getName() != null)
            return new Trainer(credentialsMapper.fromBeanToModel(bean.getCredentialsBean()), bean.getName(), bean.getSurname(), bean.isOnline());
        else
            return new Trainer(credentialsMapper.fromBeanToModel(bean.getCredentialsBean()));
    }

    @Override
    public TrainerBean fromModelToBean(Trainer model) {
        if(model.getName() != null)
            return new TrainerBean(credentialsMapper.fromModelToBean(model.getCredentials()), model.getName(), model.getSurname(), model.isOnline());
        else
            return new TrainerBean(credentialsMapper.fromModelToBean(model.getCredentials()));
    }

}
