package com.br.recycle.api.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.br.CPF;

import com.br.recycle.api.payload.RoleName;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity

@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"})
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class User implements Serializable {

    private static final long serialVersionUID = 7422560922720116772L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    @NotEmpty
    private String name;

    //private String username;

    @NaturalId
    @NotEmpty(message = "{validation.mail.notEmpty}")
    @Email(regexp = ".*@.*\\..*", message = "Email inválido!")
    private String email;

    //@JsonIgnore
    @Transient
    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleName role;

    @NotEmpty
    private String password;

    @Column(name = "cell_phone")
    private String cellPhone;

    @CPF(message = "CPF inválido")
    @Column(name = "cpf_cnpj")
    private String cpfCnpj;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Flow flowIndicator;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    //@OneToMany(mappedBy = "user")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    @JsonManagedReference
    //@NotEmpty
    private List<Address> address;
  
    
	private String token;
	
	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime tokenCreationDate;
//    @OneToOne(mappedBy = "user")
//    private Giver giver;
//    
//    @OneToOne(mappedBy = "user")
//    private Cooperative cooperative;

    public boolean isNew() {
        return getId() == null;
    }

    private Boolean active = Boolean.TRUE;

}