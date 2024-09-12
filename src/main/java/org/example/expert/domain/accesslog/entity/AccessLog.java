package org.example.expert.domain.accesslog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.user.entity.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "accesslogs")
@EntityListeners(AuditingEntityListener.class)
public class AccessLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(updatable = false)
    private String requestTime;

    @Column(nullable = false)
    private String requestUrl;

    public AccessLog(Long userId, String requestUrl, String requestTime) {
        this.userId = userId;
        this.requestUrl = requestUrl;
        this.requestTime = requestTime;
    }
}
