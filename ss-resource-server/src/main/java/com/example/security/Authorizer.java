package com.example.security;

import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.stereotype.Component;

@Component
public class Authorizer {

    public boolean canReadCourses(MethodSecurityExpressionOperations ops) {
        return ops.hasAuthority("course:read");
    }

    public boolean canCreateCourses(MethodSecurityExpressionOperations ops) {
        return ops.hasAuthority("course:write");
    }

    public boolean canDeleteCourses(MethodSecurityExpressionOperations ops) {
        return ops.hasAuthority("course:delete");
    }
}
