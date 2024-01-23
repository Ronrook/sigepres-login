package com.sigepres.servicioweb.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "employee_id")
@Table(name = "employees")
public class Employee extends User implements Serializable, UserDetails {

    @Column(name = "password", nullable = false, length = 45)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Appointment> appointments; // Relación uno a muchos con Appointment

    @OneToMany(mappedBy = "employeeCreator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes; // Relación uno a muchos con Appointment



    public Employee(Integer userId, String firstName, String middleName, String lastName,
            String secondLastName, String dniNumber, LocalDate birthdate, String email,
            String phone, Integer userImage, boolean isActive, String password, Role role) {
        super(userId, firstName, middleName, lastName, secondLastName, dniNumber, birthdate, email, phone, userImage, isActive);
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((role.getName())));
    }

    @Override
    public String getUsername() {
        return this.getEmail();
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
}