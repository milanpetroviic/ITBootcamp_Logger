package com.example.Logger_Application.Model;

import com.example.Logger_Application.Model.ClientRole;
import com.example.Logger_Application.Model.LogType;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    @Size(max = 1024, message = "Log must be less than 1024 characters long.")
    private String message;
    @Enumerated(value = EnumType.ORDINAL)
    private LogType logType;
    private Date createdDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "clientId")
    private Client client;

    @PrePersist
    private void setLogDateTime(){
        this.setCreatedDate(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
    }
}