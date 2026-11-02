package com.wagle.wagleserver.service;

import com.wagle.wagleserver.entity.EmailVerification;
import com.wagle.wagleserver.repository.EmailVerificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class EmailVerificationService {

    private final EmailVerificationRepository emailVerificationRepository;
    private final EmailService emailService;

    public EmailVerificationService(EmailVerificationRepository emailVerificationRepository,EmailService emailService) {
        this.emailVerificationRepository = emailVerificationRepository;
        this.emailService = emailService;
    }

    public void sendCode(String email) {
        String code = generateCode();

        EmailVerification verification = new EmailVerification();
        verification.setEmail(email);
        verification.setCode(code);
        verification.setExpiredAt(LocalDateTime.now().plusMinutes(5));
        verification.setVerified(false);

        emailVerificationRepository.save(verification);
        emailService.sendVerificationEmail(email, code);
    }

    public boolean verifyCode(String email, String inputCode) {
        EmailVerification verification = emailVerificationRepository
                .findTopByEmailOrderByIdDesc(email)
                .orElseThrow(() -> new IllegalArgumentException("인증 요청 내역이 없습니다."));

        if (verification.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("인증번호가 만료되었습니다.");
        }

        if (!verification.getCode().equals(inputCode)) {
            throw new IllegalArgumentException("인증번호가 일치하지 않습니다.");
        }

        verification.setVerified(true);
        return true;
    }

    private String generateCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // 6자리
        return String.valueOf(code);
    }
}