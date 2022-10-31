package com.example.Logger_Application.Controllers;

import com.example.Logger_Application.Model.Client;
import com.example.Logger_Application.Model.Log;
import com.example.Logger_Application.Repository.LogRepository;
import com.example.Logger_Application.Services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
public class LogController {
    public static final String DATE_PATTERN = "yyyy/MM/dd";
    LogService logService;
    @Autowired
    LogController(LogRepository logRepository, LogService logService){
        this.logService = logService;
    }

    @PostMapping("/api/logs/create")
    public ResponseEntity createLog(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody Log log) {
        if(this.logService.createLog(token,log)){
            return ResponseEntity.status(HttpStatus.OK).body("Log created.");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token!");
    }
    @GetMapping("/api/logs/search")
    public ResponseEntity<?> findAllFiltered(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestParam(required = false)@DateTimeFormat(pattern = DATE_PATTERN) Date dateFrom,
            @RequestParam(required = false)@DateTimeFormat(pattern = DATE_PATTERN) Date dateTo,
            @RequestParam(required = false)String message,
            @RequestParam(required = false)Integer logType) {

        System.out.println(token);
        if (logService.findClientByToken(token) != null) {
            Client client = logService.findClientByToken(token);
            return ResponseEntity.status(HttpStatus.OK).body(logService.getFiltered(client, dateFrom, dateTo, message, logType));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token!");
    }
}