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


@Transactional
@Service
@RequiredArgsConstructor
public class EmailRedisService implements EmailVerificationService {

    private final VerificationCodeRepository verificationCodeRepository;
    private final JavaMailSender mailSender;

    @Override
    public void sendVerificationCode(String email) {
        Optional<VerificationCode> existingCodeOpt = verificationCodeRepository.findById(email);
        if (existingCodeOpt.isPresent()) {
            String randomCode = generateVerificationCode();
            VerificationCode newCode = new VerificationCode(email, randomCode);
            verificationCodeRepository.save(newCode);
        } else {
            String randomCode = generateVerificationCode();
            VerificationCode newCode = new VerificationCode(email, randomCode);
            verificationCodeRepository.save(newCode);
            sendEmail(email, randomCode);
        }
    }

    @Override
    public boolean validateVerificationCode(String email, String code) {
        Optional<VerificationCode> storedCodeOpt = verificationCodeRepository.findById(email);
        if (storedCodeOpt.isEmpty() || !storedCodeOpt.get().getCode().equals(code)) {
            return false;
        }
        verificationCodeRepository.deleteById(email);
        return true;
    }


    private void sendEmail(String toEmail, String verificationCode) {
        String subject = "Cozy Share Sign-Up Verification Code Issuance";
        String body = "Verification Code:" + verificationCode;

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new InvalidDataException(e.getMessage());
        }
    }



}