package by.yaromkin.user_manager.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Collection;
import java.util.Collections;

import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.GenerationType.TABLE;

@Entity
@Table(name = "t_user")
public class UserAccount implements UserDetails {

    @Id
    @GeneratedValue
    @Column(name="user_id")
    private Long id;
    @Column(unique = true)
    @Size(min=3, max=16, message = "more then 2 and less then 17 latin symbols")
    private String username;
    @Size(min=3, max=128)
    private String encryptedPassword;
    @Transient
    @Size(min=3, max=16, message = "more then 2 and less then 17 latin symbols or digits. Minimum 1 digit. " +
            "Minimum 1 symbol")
    private String initialPassword;
    @Size(min=1, max=16, message = "more then 0 and less then 17 latin symbols")
    private String firstName;
    @Size(min=1, max=16, message = "more then 0 and less then 17 latin symbols")
    private String lastName;
    @OneToOne(fetch = FetchType.EAGER)
    private Role role;
    private boolean active;
    @Column(columnDefinition="date DEFAULT CURRENT_DATE")
    private Date createdAt;

    public UserAccount() {
    }

    public UserAccount(Long id, String username, String encryptedPassword, String initialPassword, String firstName,
                       String lastName, Role role, boolean active, Date createdAt) {
        this.id = id;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.initialPassword = initialPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.active = active;
        this.createdAt = createdAt;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(getRole());
    }

    @Override
    public String getPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getInitialPassword() {
        return initialPassword;
    }

    public void setInitialPassword(String initialPassword) {
        this.initialPassword = initialPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount)) return false;

        UserAccount that = (UserAccount) o;

        if (active != that.active) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (encryptedPassword != null ? !encryptedPassword.equals(that.encryptedPassword) : that.encryptedPassword != null)
            return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (role != null ? !role.equals(that.role) : that.role != null) return false;
        return createdAt != null ? createdAt.equals(that.createdAt) : that.createdAt == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (encryptedPassword != null ? encryptedPassword.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", encryptedPassword='" + encryptedPassword + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                ", active=" + active +
                ", createdAt=" + createdAt +
                '}';
    }
}
