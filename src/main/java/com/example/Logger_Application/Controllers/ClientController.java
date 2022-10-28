package com.example.Logger_Application.Controllers;

import com.example.Logger_Application.Model.Client;
import com.example.Logger_Application.Repository.ClientRepository;
import com.example.Logger_Application.Services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/api/clients/all")
    public List<Client> allClients() {
        return clientRepository.findAll();
    }

    @PostMapping("/api/clients/register")
    public ResponseEntity registerUser(@RequestBody Client client) {
        if(clientRepository.existsClientByEmail(client.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists.");
        }
        if(clientRepository.existsClientByUsername(client.getUsername())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists.");
        }
        clientRepository.save(client);
        return ResponseEntity.status(HttpStatus.OK).body("Registered!");
    }

    @PostMapping("/api/clients/login")
    public ResponseEntity loginUser(@RequestBody Map<String,String> requestParams) throws Exception{
        String account=requestParams.get("Account");
        String password=requestParams.get("Password");
        if(!clientService.login(account,password).isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body("Token : " + clientService.login(account,password));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email or username or password incorrect");
    }
}