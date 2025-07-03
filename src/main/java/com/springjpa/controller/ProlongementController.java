package com.springjpa.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springjpa.service.ProlongementService;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/prolongement")
public class ProlongementController {

    @Autowired
    private ProlongementService prolongementService;

    @PostMapping("/prolonger")
    @ResponseBody
    public String prolonger(
        @RequestParam("idPret") Integer idPret,
        @RequestParam("date") LocalDateTime date
    ) throws Exception {
        try {
            prolongementService.prolongerPret(idPret, date);
        } catch (Exception e) {
            throw e;
        }      
        return "success";
    }
    
}