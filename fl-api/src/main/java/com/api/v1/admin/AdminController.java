package com.api.v1.admin;

import com.core.domain.user.service.UserPaymentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * admin 관련 기능이 많아지면 추후 모듈 분리
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserPaymentService userPaymentService;

    @GetMapping({"", "/"})
    public String findWithdrawRequests(final HttpSession session, final Model model) {
        model.addAttribute("withdraws", userPaymentService.findWithDraws());
        return "withdraw/list";
    }

    @PostMapping("/complete")
    public String completeWithdraw(
            @RequestParam final Long userAccountId,
            @RequestParam final Long pointHistoryId,
            @RequestParam final String token,
            Model model) {
        try {
            userPaymentService.completeWithDraw(userAccountId, pointHistoryId, token);
            model.addAttribute("successMessage", "환전 처리가 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "환전 처리 실패: " + e.getMessage());
        }
        model.addAttribute("withdraws", userPaymentService.findWithDraws());
        return "withdraw/list";
    }

}