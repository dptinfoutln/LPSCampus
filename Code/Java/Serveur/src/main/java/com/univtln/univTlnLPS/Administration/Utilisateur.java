package com.univtln.univTlnLPS.Administration;

import com.univtln.univTlnLPS.Scan.ScanData;
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
public class Utilisateur {
    private String CaracteristiquesMachine;

    private ScanData scan = null;
}
