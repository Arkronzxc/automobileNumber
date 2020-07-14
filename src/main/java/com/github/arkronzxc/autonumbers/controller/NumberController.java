package com.github.arkronzxc.autonumbers.controller;

import com.github.arkronzxc.autonumbers.service.NumberService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.TEXT_PLAIN_VALUE)
public class NumberController {

    private final NumberService numberService;

    public NumberController(NumberService numberService) {
        this.numberService = numberService;
    }

    @GetMapping("next")
    public ResponseEntity<String> nextNumber() {
        return ResponseEntity.ok(numberService.generateNextNumber());
    }

    @GetMapping("random")
    public ResponseEntity<String> randomNumber() {
        return ResponseEntity.ok(numberService.generateRandomNumber());
    }
}
