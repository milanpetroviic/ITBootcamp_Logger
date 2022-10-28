package com.example.Logger_Application.Services;

import com.example.Logger_Application.Model.Client;
import com.example.Logger_Application.Model.Log;
import com.example.Logger_Application.Repository.ClientRepository;
import com.example.Logger_Application.Repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class LogService {
    LogRepository logRepository;
    ClientRepository clientRepository;
    @Autowired
    LogService(LogRepository logRepository, ClientRepository clientRepository){
        this.logRepository = logRepository;
        this.clientRepository = clientRepository;
    }
    public boolean createLog(String token, Log log){
        if(clientRepository.findBy(token).isPresent()){
            log.setClient(clientRepository.findBy(token).get());
            logRepository.save(log);
            return true;
        }
        return false;
    }
    public List<Log> findAllForClient(String token) {
        if (clientRepository.findBy(token).isPresent()) {
            Client client = clientRepository.findBy(token).get();
            return logRepository.findByClient(client);
        }
        return null;
    }
    public Client findClientByToken(String token){
        if(clientRepository.findBy(token).isPresent()) {
            return clientRepository.findBy(token).get();
        }
        return null;
    }
}
