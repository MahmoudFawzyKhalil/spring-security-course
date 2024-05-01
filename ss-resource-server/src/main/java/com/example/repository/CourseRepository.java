package com.example.repository;

import com.example.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {


    @Modifying
    @Query("""
            DELETE FROM Course c
            WHERE c.id = :courseId
            AND c.creatorId = ?#{ principal?.getId() }
            """)
    @Override
    @PreAuthorize("@authorizer.canDeleteCourses(#root)")
    void deleteById(Long courseId);

    @Override
    @PreAuthorize("@authorizer.canReadCourses(#root)")
    List<Course> findAll();

    @Override
    @PreAuthorize("@authorizer.canCreateCourses(#root)")
    <S extends Course> S save(S entity);
}
