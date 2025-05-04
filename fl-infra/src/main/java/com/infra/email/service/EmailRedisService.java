package com.infra.email.service;

import com.infra.email.model.VerificationCode;
import com.infra.email.repository.VerificationCodeRepository;
import com.infra.exception.custom.InvalidDataException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.infra.email.utils.EmailCodeGenerator.generateVerificationCode;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailRedisService implements EmailVerificationService {

    private static final String EMAIL_SUBJECT = "Cozy Share Sign-Up Verification Code Issuance";
    private static final String EMAIL_BODY_TEMPLATE = "Verification Code: %s";

    private final VerificationCodeRepository verificationCodeRepository;
    private final JavaMailSender mailSender;

    @Override
    public void sendVerificationCode(String email) {
        String code = generateVerificationCode();
        VerificationCode verificationCode = new VerificationCode(email, code);
        verificationCodeRepository.save(verificationCode);
        if (verificationCodeRepository.findById(email).isEmpty()) {
            sendEmail(email, code);
        }
    }

    @Override
    public boolean validateVerificationCode(String email, String code) {
        return verificationCodeRepository.findById(email)
                .filter(stored -> stored.getCode().equals(code))
                .map(valid -> {
                    verificationCodeRepository.deleteById(email);
                    return true;
                })
                .orElse(false);
    }

    private void sendEmail(String toEmail, String verificationCode) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject(EMAIL_SUBJECT);
            helper.setText(String.format(EMAIL_BODY_TEMPLATE, verificationCode), true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new InvalidDataException("이메일 전송 실패: " + e.getMessage());
        }
    }
}