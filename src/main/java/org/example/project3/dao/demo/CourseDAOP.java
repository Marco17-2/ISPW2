package org.example.project3.dao.demo;

import org.example.project3.dao.CourseDAO;
import org.example.project3.dao.demo.shared.SharedResources;
import org.example.project3.exceptions.NoResultException;
import org.example.project3.model.Course;

import java.util.*;

public class CourseDAOP implements CourseDAO {

    @Override
    public void searchCourses(List<Course> courses) throws NoResultException {
        for(Course c : SharedResources.getInstance().getCourses().values()){
            if(c.getTrainer() != null && c.getTrainer().getCredentials() != null){
                courses.add(c);
                }
            }
        }
}
