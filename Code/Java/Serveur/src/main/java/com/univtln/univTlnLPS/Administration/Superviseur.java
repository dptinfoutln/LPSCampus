package com.univtln.univTlnLPS.Administration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Superviseur extends Utilisateur{
    private String loginHash;
    private String passwordHash;
}
