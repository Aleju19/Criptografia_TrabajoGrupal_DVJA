package com.appsecco.dvja.models;

import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import org.apache.commons.lang.StringUtils;
import com.appsecco.dvja.util.EncryptionUtils;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String name;

    private String role;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="updated_at")
    private Date updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //Desencripta el correo electrónico con AES al leerlo desde la base de datos.
    public String getEmail() {
        return EncryptionUtils.decrypt(this.email);
    }

    //Encripta el correo electrónico con AES antes de persistirlo en la base de datos.
    public void setEmail(String email) {
        if (email != null) {
            // Si ya viene cifrado (por llamadas internas de JPA), no lo re-ciframos
            if (email.length() > 24 && !email.contains("@")) {
                this.email = email;
            } else {
                this.email = EncryptionUtils.encrypt(email);
            }
        } else {
            this.email = null;
        }
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return StringUtils.equals(getRole(), "admin");
    }

    @PrePersist
    @PreUpdate
    private void setTimestamps() {
        if(id == null)
            createdAt = new Date();
        updatedAt = new Date();
    }
}
