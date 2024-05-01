package com.example.controller;

import com.example.entity.Course;
import com.example.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
@Slf4j
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getCourses() {
        log.info("GET request to /courses");
        List<CourseResponse> courses = courseService.getAllCourses()
                .stream()
                .map(CourseResponse::from)
                .toList();
        return ResponseEntity.ok(courses);
    }

    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(@RequestBody CreateCourseRequest createCourseRequest) {
        log.info("POST request to /courses");
        Course course = courseService.createCourse(createCourseRequest);
        return ResponseEntity.ok(CourseResponse.from(course));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<CourseResponse> createCourse(@PathVariable Long courseId) {
        log.info("DELETE request to /courses/{}", courseId);
        courseService.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }

    public record CourseResponse(Long id, String title) {
        public static CourseResponse from(Course course) {
            return new CourseResponse(course.getId(), course.getTitle());
        }
    }

    public record CreateCourseRequest(String title) {
    }

}
