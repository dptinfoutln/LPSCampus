package com.univtln.univTlnLPS.Carte;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Batiment {
    private List<Etage> etageList;
}
