package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @NotNull
    @Column(name = "username")
    private String userName;
    @NotNull
    @Column(name = "password")
    private String passWord;
    @NotNull
    private boolean enabled;
    @NotNull
    @Column(name = "first_name")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "AUTHORITIES",
            joinColumns=@JoinColumn(name = "username",
                    referencedColumnName = "username")
    )
    private List<Role> authorities;
}
//    username   VARCHAR(255) NOT NULL PRIMARY KEY,
//    password   VARCHAR(255) NOT NULL,
//    enabled    BOOLEAN      NOT NULL,
//    first_name VARCHAR(255) NOT NULL