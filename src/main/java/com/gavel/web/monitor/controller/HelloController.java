package com.gavel.web.monitor.controller;


import com.gavel.monitor.persistence.repository.EnvinfoJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HelloController {

    @Autowired
    private EnvinfoJdbcRepository repository;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @RequestMapping("/datas")
    public String datas(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("productList", repository.findAll());
        return "datas";
    }



}
