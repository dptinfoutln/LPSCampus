package com.univtln.univTlnLPS.model.administration;

import lombok.*;

/**
 * Classe du couple email mot de passe
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class EmailPass {
    /**
     * The Email.
     */
    String email;
    /**
     * The Password.
     */
    String password;
}
