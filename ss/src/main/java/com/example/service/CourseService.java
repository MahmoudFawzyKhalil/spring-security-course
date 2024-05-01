package com.example.service;

import com.example.controller.CourseController.CreateCourseRequest;
import com.example.entity.Course;
import com.example.repository.CourseRepository;
import com.example.security.SecuritySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;


    @Transactional(readOnly = true)
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course createCourse(CreateCourseRequest createCourseRequest) {
        Course course = new Course(SecuritySupport.getCurrentUserId(), createCourseRequest.title());
        return courseRepository.save(course);
    }

    public void deleteCourse(Long courseId) {
        // TODO show broken object-level access control then adjust using best way: filtering at db level not getting the object first and bla bla,
        //  and Authorizer for auditing - central place at cost of performance
        courseRepository.deleteById(courseId);
    }
}
