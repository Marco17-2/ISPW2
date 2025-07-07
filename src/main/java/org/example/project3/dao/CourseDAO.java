package org.example.project3.dao;

import org.example.project3.exceptions.DbOperationException;
import org.example.project3.exceptions.NoResultException;
import org.example.project3.model.Course;

import java.util.List;

public interface CourseDAO {

    void searchCourses(List<Course> courses) throws NoResultException;
    void addCourse(Course course, String email) throws NoResultException;
    void removeCourse(Course course) throws DbOperationException;

}
