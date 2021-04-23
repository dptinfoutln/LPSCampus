package com.univtln.univTlnLPS.Scan;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ScanData {
    private String infoScan;

    List<WifiData> wifiList;
}
