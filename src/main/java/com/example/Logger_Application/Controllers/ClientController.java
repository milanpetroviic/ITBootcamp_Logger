package com.example.Logger_Application.Controllers;

import com.example.Logger_Application.Model.Client;
import com.example.Logger_Application.Model.ClientRole;
import com.example.Logger_Application.Repository.ClientRepository;
import com.example.Logger_Application.Services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
@RestController
public class ClientController {
    ClientRepository clientRepository;
    ClientService clientService;

    @Autowired
    ClientController(ClientRepository clientRepository, ClientService clientService){
        this.clientRepository = clientRepository;
        this.clientService= clientService;
    }

    @GetMapping("/api/clients")
    public ResponseEntity<?> allClients(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        ClientRole role = clientService.getRolefromToken(token);

        if(role == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid token.");
        }
        if(role!= ClientRole.ADMIN){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correct token, but not admin");
        }
        return ResponseEntity.status(HttpStatus.OK).body(clientService.allClients());
    }

    @PostMapping("/api/clients/register")
    public ResponseEntity registerUser(@RequestBody Client client) {
        if(clientRepository.existsClientByEmail(client.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists.");
        }
        if(clientRepository.existsClientByUsername(client.getUsername())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists.");
        }

        clientService.save(client);
        return ResponseEntity.status(HttpStatus.OK).body("Registered!");
    }
}