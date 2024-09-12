package org.example.expert.domain.accesslog.repository;

import org.example.expert.domain.accesslog.entity.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {
}
