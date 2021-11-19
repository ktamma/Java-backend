package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Embeddable
@Table(name = "AUTHORITIES")
public class Authority {
    @NotNull
    private String authority;

}

//    username  VARCHAR(50) NOT NULL,
//    authority VARCHAR(50) NOT NULL,
//    FOREIGN KEY (username) REFERENCES USERS
//    ON DELETE CASCADE
