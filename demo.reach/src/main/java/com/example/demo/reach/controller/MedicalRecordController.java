package com.example.demo.reach.controller;

import com.example.demo.reach.entity.MedicalRecord;
import com.example.demo.reach.repository.MedicalRecordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Controller
public class MedicalRecordController {

    // make use of slf4j logger - further improvement is to use Log4j2
    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);

    @Autowired
    private MedicalRecordRepository repository;

    @GetMapping("/")
    public String getAllRecords(Model model) {
        logger.info("Getting all medical records");
        model.addAttribute("records", repository.findAll());
        logger.info("Returning all medical records");
        return "list"; // Corresponds to list.xhtml
    }

    @GetMapping("/showNewRecordForm")
    public String showNewRecordForm(Model model) {
        logger.info("Showing new medical record form");
        MedicalRecord record = new MedicalRecord();
        model.addAttribute("record", record);
        logger.info("Returning new medical record form");
        return "form"; // Corresponds to form.xhtml
    }

    @PostMapping("/saveRecord")
    public String saveRecord(@ModelAttribute("record") MedicalRecord record) {
        logger.info("Saving record for name: {}", record.getName());
        repository.save(record);
        logger.info("Successfully saved record for name: {}", record.getName());
        return "redirect:/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        logger.info("Showing form for update with id: {}", id);
        MedicalRecord record = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid record Id:" + id));
        model.addAttribute("record", record);
        logger.info("Returning form for update with id: {}", id);
        return "form";
    }

    @GetMapping("/deleteRecord/{id}")
    public String deleteRecord(@PathVariable(value = "id") long id) {
        logger.info("Deleting record with id: {}", id);
        this.repository.deleteById(id);
        logger.info("Successfully deleted record with id: {}", id);
        return "redirect:/";
    }
}
