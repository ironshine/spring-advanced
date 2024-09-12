package org.example.expert.domain.accesslog.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.expert.domain.accesslog.entity.AccessLog;
import org.example.expert.domain.accesslog.repository.AccessLogRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j(topic = "AccessLogAop")
@Aspect
@Component
@RequiredArgsConstructor
public class AccessLogAop {

    private final AccessLogRepository accessLogRepository;

    @Pointcut("execution(* org.example.expert.domain.comment.controller.CommentAdminController.*(..))")
    private void commentAdmin() {
    }

    @Pointcut("execution(* org.example.expert.domain.user.controller.UserAdminController.*(..))")
    private void userAdmin() {
    }

    @Around("commentAdmin() || userAdmin()")
    public void execute(){
        // 측정 시작 시간
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Long userId = (Long) request.getAttribute("userId");
        String requestUrl = String.valueOf(request.getRequestURL());
        String requestTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // 로그인 회원이 없는 경우, 기록하지 않음
        if (userId != null) {
            AccessLog accessLog = new AccessLog(userId, requestUrl, requestTime);

            log.info("[API] UserId: " + accessLog.getUserId()
                    + ", Time : " + accessLog.getRequestTime()
                    + ", RequestURL : " + accessLog.getRequestUrl());
            accessLogRepository.save(accessLog);
        }
    }
}
