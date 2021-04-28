package com.univtln.univTlnLPS.Administration;

import com.univtln.univTlnLPS.Carte.Campus;
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
public class Administrateur extends Superviseur{
    private Campus campus;
}
