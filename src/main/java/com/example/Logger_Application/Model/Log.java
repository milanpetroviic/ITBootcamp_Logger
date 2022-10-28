package com.example.Logger_Application.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
@Data
@NoArgsConstructor
@Entity
@Table(name = "log")
public class Log {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long logId;
    private String message;
    private LogType logType;
    private Date createdDate;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "ClientId")
    private Client client;
    @PrePersist
    private void setLogDateTime(){
        this.setCreatedDate(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
    }
}