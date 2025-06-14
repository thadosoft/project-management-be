package com.example.projectmanagementbe.api.specifications;

import com.example.projectmanagementbe.api.models.employee.CaptureDatum;
import com.example.projectmanagementbe.api.models.dto.requests.timekeeping.SearchCaptureDatumRequest;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CaptureDatumSpecification {

    public static Specification<CaptureDatum> withFilters(SearchCaptureDatumRequest request) {
        return (root, query, cb) -> {
            var predicates = cb.conjunction();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            if (request.getStartDate() != null && request.getEndDate() != null) {
                LocalDateTime start = LocalDateTime.parse(request.getStartDate() + "T00:00:00");
                LocalDateTime end = LocalDateTime.parse(request.getEndDate() + "T23:59:59");
                predicates.getExpressions().add(cb.between(root.get("createdAt"), start, end));
            }

            if (request.getPersonName() != null && !request.getPersonName().isBlank()) {
                predicates.getExpressions().add(
                        cb.like(cb.lower(root.get("personName")), "%" + request.getPersonName().toLowerCase() + "%")
                );
            }

            return predicates;
        };
    }
}
