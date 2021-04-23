package com.univtln.univTlnLPS.Carte;

import lombok.*;

import java.awt.Image;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Campus {
    private Image plan;
    private List<Batiment> batimentList;
}
