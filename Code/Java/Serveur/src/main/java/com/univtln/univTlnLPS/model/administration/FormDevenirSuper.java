package com.univtln.univTlnLPS.model.administration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.univtln.univTlnLPS.model.SimpleEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.*;
import lombok.extern.java.Log;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

/**
 * Classe Formulaire de demande a devenir Superviseur du modele
 */
@Log
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
@ToString
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity


@NamedQueries({
        @NamedQuery(name = "form.findByEmail",
                query = "select formDevenirSuper " +
                        "from FormDevenirSuper formDevenirSuper " +
                        "where formDevenirSuper.email=:email")})

public class FormDevenirSuper implements SimpleEntity {

    @XmlAttribute
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;

    @XmlElement
    @NotNull
    private String email;

    @XmlElement
    @NotNull
    private byte[] passwordHash;

    @XmlElement
    private byte[] salt = new byte[16];

    @Builder.Default
    @JsonIgnore
    private SecureRandom random = new SecureRandom();


    /**
     * Defini le hash du password
     *
     * @param password the password
     * @throws NoSuchAlgorithmException the no such algorithm exception
     * @throws InvalidKeySpecException  the invalid key spec exception
     */
    public void setPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if(random == null)
            random = new SecureRandom();
        if(salt == null)
            salt = new byte[16];

        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        this.passwordHash = factory.generateSecret(spec).getEncoded();
    }
}
