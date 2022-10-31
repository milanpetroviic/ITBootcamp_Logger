package com.example.Logger_Application.Services;

import com.example.Logger_Application.Model.Client;
import com.example.Logger_Application.Model.Log;
import com.example.Logger_Application.Repository.ClientRepository;
import com.example.Logger_Application.Repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;
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
        if(clientRepository.findByMyToken(token).isPresent()){
            log.setClient(clientRepository.findByMyToken(token).get());
            logRepository.save(log);
            return true;
        }
        return false;
    }
    public List<Log> findAllForClient(String token) {
        if (clientRepository.findByMyToken(token).isPresent()) {
            Client client = clientRepository.findByMyToken(token).get();
            return logRepository.findByClient(client);
        }
        return null;
    }
    public Client findClientByToken(String token){
        if(clientRepository.findByMyToken(token).isPresent()) {
            return clientRepository.findByMyToken(token).get();
        }
        return null;
    }
    public Object getFiltered(Client client, Date dateFrom, Date dateTo, String message, Integer logType) {
        return null;
    }
}
