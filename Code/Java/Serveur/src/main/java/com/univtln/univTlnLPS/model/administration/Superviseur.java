package com.univtln.univTlnLPS.model.administration;

import com.univtln.univTlnLPS.model.scan.ScanData;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.java.Log;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Set;

/**
 * Classe Superviseur du modele
 */
@Log
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
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
                        " where (superviseur.email=:email) and (superviseur.passwordHash=:passwordhash)")})

@Entity
public class Superviseur extends Utilisateur implements Principal {
    @XmlElement
    @NotNull
    private String email;

    @XmlElement
    @NotNull
    private byte[] passwordHash;

    @XmlElement
    @NotNull
    private byte[] salt = new byte[16];

    @XmlElement(name = "scan")
    @XmlElementWrapper(name = "scans")
    @OneToMany(mappedBy="superviseur")
    private Set<ScanData> scanList;

    @Builder.Default
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

    /**
     * Check the password
     *
     * @param password the password
     * @return true if the password is correct
     */
    @SneakyThrows
    public boolean checkPassword(String password) {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] submittedPasswordHash = factory.generateSecret(spec).getEncoded();
        return Arrays.equals(passwordHash, submittedPasswordHash);
    }

    /**
     * @return supervisor's email
     */
    @Override
    public String getName() {
        return email;
    }


}
