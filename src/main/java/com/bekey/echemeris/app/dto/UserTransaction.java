package com.bekey.echemeris.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTransaction {

    private Integer id;
    private String name;
    private Double amount;
}
