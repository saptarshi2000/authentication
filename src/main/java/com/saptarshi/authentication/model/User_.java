package com.saptarshi.authentication.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User_ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column
    @NotBlank(message = "name required")
    private String name;

    @Column
    @NotBlank(message = "email required")
    private String email;
    @Column
    @NotBlank(message = "password required")
//    @Size(min = 5,max = 10,message = "password length must be between 5 and 10")
    private String password;

    @Column
    @NotBlank(message = "phone required")
    private String phone;

    @Column
    private Boolean active;


    @Column(name = "refresh_token")
    private String refreshToken;
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)

    /*

    @JsonFormat
      (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss"

     */
    @Column
    @JsonFormat( pattern = "dd-MM-yyyy")
    private LocalDate createdOn;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role",
        joinColumns = {
                @JoinColumn(name = "user_id")
        },
        inverseJoinColumns = {
                @JoinColumn(name = "role_id")
        }
    )
    private Set<Role> roles;


}
