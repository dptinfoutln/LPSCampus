package com.univtln.univTlnLPS.model.administration;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.*;
import lombok.experimental.SuperBuilder;

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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
        @NamedQuery(name = "superviseur.findByEmail",
                query = "select superviseur from Superviseur superviseur" +
                        " where superviseur.email=:email"),
        @NamedQuery(name = "superviseur.findByAccount",
                query = "select superviseur from Superviseur superviseur" +
                        " where (superviseur.email=:email) and (superviseur.passwordHash=:passwordhash)"),
        @NamedQuery(name = "superviseur.update",
                query = "update Superviseur super" +
                        " set super.email=:email," +
                        " super.passwordHash=:pWH," +
                        " super.caracteristiquesMachine=:caract," +
                        " super.scan=:scan" +
                        " where super.id=:id"),
        @NamedQuery(name = "superviseur.delete", query = "delete from Superviseur superviseur where superviseur.id=:id")})

@Entity
@Inheritance(strategy = InheritanceType.JOINED)

public class Superviseur extends Utilisateur {
    @XmlElement
    @NotNull
    private String email;

    @XmlElement
    @NotNull
    private String passwordHash;

    @XmlElement
    @NotNull
    byte[] salt = new byte[16];

    SecureRandom random = new SecureRandom();

    public void setPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        this.passwordHash = new String(factory.generateSecret(spec).getEncoded());
    }

    @SneakyThrows
    public boolean checkPassword(String password) {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] submittedPasswordHash = factory.generateSecret(spec).getEncoded();
        return Arrays.equals(passwordHash.getBytes(), submittedPasswordHash);
    }
}
