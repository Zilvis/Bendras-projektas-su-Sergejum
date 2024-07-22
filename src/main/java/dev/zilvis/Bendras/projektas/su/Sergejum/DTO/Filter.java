package dev.zilvis.Bendras.projektas.su.Sergejum.DTO;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Filter {
    private String make;
    private String model;
    private long minPrice;
    private long maxPrice;
    private long millage;
    private LocalDate year;

}
