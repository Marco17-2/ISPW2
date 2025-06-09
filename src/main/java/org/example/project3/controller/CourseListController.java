package org.example.project3.controller;

import org.example.project3.dao.CourseDAO;
import org.example.project3.patterns.factory.BeanAndModelMapperFactory;
import org.example.project3.patterns.factory.FactoryDAO;
import org.example.project3.utilities.others.mappers.BeanAndModelMapper;

import org.example.project3.beans.*;
import org.example.project3.model.*;

import java.util.ArrayList;
import java.util.List;

public class CourseListController
{
    private final BeanAndModelMapperFactory factory;
    private final CourseDAO courseDAO;

    public CourseListController(){
        this.factory = BeanAndModelMapperFactory.getInstance();
        this.courseDAO = FactoryDAO.getCourseDAO();
    }

    public void retrieveCourses(List<CourseBean> coursesBean){

        List<Course> courses = new ArrayList<>();
        courseDAO.searchCourses(courses);
        CourseBean courseBean;

        for(Course course: courses){
            courseBean = factory.fromModelToBean(course, Course.class);
            coursesBean.add(courseBean);
        }
    }
}
