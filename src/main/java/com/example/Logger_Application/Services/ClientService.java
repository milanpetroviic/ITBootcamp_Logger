package com.example.Logger_Application.Services;

import antlr.StringUtils;
import com.example.Logger_Application.Model.Client;
import com.example.Logger_Application.Model.ClientRole;
import com.example.Logger_Application.Repository.ClientRepository;
import com.example.Logger_Application.Repository.LogRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
@Transactional
public class ClientService {
    ClientRepository clientRepository;
    LogRepository logRepository;
    public void save(Client client){
        client.setRole(ClientRole.USER);
        clientRepository.save(client);
    }
    public String login(String account, String password) {
        Optional<Client> client = clientRepository.getClientLogin(account, password);
        if (client.isPresent()) {
            clientRepository.updateClientToken(UUID.randomUUID().toString(),client.get().getClientId());
            return clientRepository.getClientToken(client.get().getClientId());
        }
        return "";
    }
    public ClientRole getRolefromToken(String token){
        if(clientRepository.findByMyToken(token).isPresent()) {
            return clientRepository.findByMyToken(token).get().getRole();
        }
        return null;
    }
    public void setRoleToAdmin(Client client){
        clientRepository.setAdminRole(client.getClientId());
    }
}
