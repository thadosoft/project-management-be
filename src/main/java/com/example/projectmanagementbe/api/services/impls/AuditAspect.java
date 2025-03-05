package com.example.projectmanagementbe.api.services.impls;

import com.example.projectmanagementbe.api.models.AuditTrail;
import com.example.projectmanagementbe.api.repositories.AuditLog.AuditLogRepository;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@AllArgsConstructor
public class AuditAspect {

  private final AuditLogRepository auditLogRepository;

  @Before("execution(* com.example.projectmanagementbe.api.controllers..*(..))")
  public void logActivity(JoinPoint joinPoint) {
    System.out.println("LOGGING: " + joinPoint.getSignature().getName());
    String username = "AnonymousUser";
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated()
        && !(authentication instanceof AnonymousAuthenticationToken)) {
      username = authentication.getName();
    }

    String methodName = joinPoint.getSignature().getName();

    String resource = joinPoint.getTarget().getClass().getSimpleName();

    AuditTrail log = new AuditTrail();
    log.setUsername(username);
    log.setAction(joinPoint.getSignature().getName());
    log.setResource(resource);
    log.setDetails("Method " + methodName + " was invoked");

    auditLogRepository.save(log);
  }
}