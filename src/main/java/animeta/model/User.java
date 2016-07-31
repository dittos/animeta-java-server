package animeta.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "auth_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1318613383;

    private Integer   id;
    private String    username;
    private String    firstName;
    private String    lastName;
    private String    email;
    private String    password;
    private Boolean   isStaff;
    private Boolean   isActive;
    private Boolean   isSuperuser;
    private Timestamp lastLogin;
    private Timestamp dateJoined;

    public User() {}

    public User(User value) {
        this.id = value.id;
        this.username = value.username;
        this.firstName = value.firstName;
        this.lastName = value.lastName;
        this.email = value.email;
        this.password = value.password;
        this.isStaff = value.isStaff;
        this.isActive = value.isActive;
        this.isSuperuser = value.isSuperuser;
        this.lastLogin = value.lastLogin;
        this.dateJoined = value.dateJoined;
    }

    public User(
        Integer   id,
        String    username,
        String    firstName,
        String    lastName,
        String    email,
        String    password,
        Boolean   isStaff,
        Boolean   isActive,
        Boolean   isSuperuser,
        Timestamp lastLogin,
        Timestamp dateJoined
    ) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isStaff = isStaff;
        this.isActive = isActive;
        this.isSuperuser = isSuperuser;
        this.lastLogin = lastLogin;
        this.dateJoined = dateJoined;
    }

    @Id
    @Column(name = "id", nullable = false, precision = 32)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "username", nullable = false, length = 30)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "first_name", nullable = false, length = 30)
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false, length = 30)
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "email", nullable = false, length = 254)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password", nullable = false, length = 128)
    @JsonIgnore
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "is_staff", nullable = false)
    public Boolean getIsStaff() {
        return this.isStaff;
    }

    public void setIsStaff(Boolean isStaff) {
        this.isStaff = isStaff;
    }

    @Column(name = "is_active", nullable = false)
    public Boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Column(name = "is_superuser", nullable = false)
    public Boolean getIsSuperuser() {
        return this.isSuperuser;
    }

    public void setIsSuperuser(Boolean isSuperuser) {
        this.isSuperuser = isSuperuser;
    }

    @Column(name = "last_login")
    public Timestamp getLastLogin() {
        return this.lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Column(name = "date_joined", nullable = false)
    public Timestamp getDateJoined() {
        return this.dateJoined;
    }

    public void setDateJoined(Timestamp dateJoined) {
        this.dateJoined = dateJoined;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("User (");

        sb.append(id);
        sb.append(", ").append(username);
        sb.append(", ").append(firstName);
        sb.append(", ").append(lastName);
        sb.append(", ").append(email);
        sb.append(", ").append(password);
        sb.append(", ").append(isStaff);
        sb.append(", ").append(isActive);
        sb.append(", ").append(isSuperuser);
        sb.append(", ").append(lastLogin);
        sb.append(", ").append(dateJoined);

        sb.append(")");
        return sb.toString();
    }
}
