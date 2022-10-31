package com.example.Logger_Application.Repository;

import com.example.Logger_Application.Model.Client;
import com.example.Logger_Application.Model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface LogRepository extends JpaRepository<Log,Integer>, JpaSpecificationExecutor<Log> {
    List<Log> findAll();
    List<Log> findByClient(Client client);
}
