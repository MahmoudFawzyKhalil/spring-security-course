package com.example.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;

    @JoinColumn(table = "users", referencedColumnName = "id")
    public Long creatorId;

    public Course(Long creatorId, String title) {
        Objects.requireNonNull(creatorId, "must provide creator userId");
        this.creatorId = creatorId;
        this.title = title;
    }
}
