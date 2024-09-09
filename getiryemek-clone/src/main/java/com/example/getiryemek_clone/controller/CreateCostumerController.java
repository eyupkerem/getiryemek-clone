package com.example.getiryemek_clone.controller;

import com.example.getiryemek_clone.service.CostumerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class CreateCostumerController {
    private final CostumerService costumerService;

    @GetMapping("/create-password")
    public String createPassword(@RequestParam("costumerId") Long costumerId, Model model) {

        model.addAttribute("costumerId", costumerId);

        return "createPassword";
    }


    @PostMapping("reset-password")
    public String resetPassword(@RequestParam("costumerId") Long costumerId,
                                @RequestParam("newPassword") String newPassword) {
        try {
            costumerService.createPassword(costumerId, newPassword);
            return "redirect:/success";
        } catch (Exception e) {
            return "redirect:/error";
        }
    }

}


