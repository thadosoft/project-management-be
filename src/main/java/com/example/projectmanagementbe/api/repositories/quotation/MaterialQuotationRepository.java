package com.example.projectmanagementbe.api.repositories.quotation;

import com.example.projectmanagementbe.api.models.MaterialQuotation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialQuotationRepository extends JpaRepository<MaterialQuotation, Long> {

}