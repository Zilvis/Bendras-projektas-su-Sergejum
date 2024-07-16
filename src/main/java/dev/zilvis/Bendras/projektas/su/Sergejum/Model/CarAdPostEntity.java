package dev.zilvis.Bendras.projektas.su.Sergejum.Model;

import dev.zilvis.Bendras.projektas.su.Sergejum.Enums.FuleType;
import dev.zilvis.Bendras.projektas.su.Sergejum.Enums.Make;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Table(name = "AdPost")
public class CarAdPostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO Galima palikti string
    @Enumerated(EnumType.STRING)
    @Column(name = "make", nullable = false)
    private Make make;

    // Savininko email (Unikalus)
    @Column(name = "user_entity_email", nullable = true)
    private Long userEntityEmail;

    // TODO Perdaryti i enum ?
    @Column(name = "model", nullable = true)
    private String model;

    @Column(name = "year", nullable = true)
    private LocalDate year;

    @Column(name = "millage", nullable = true)
    private int millage;

    @Column(name = "price", nullable = true)
    private float price;

    @Column(name = "fuel_type", nullable = true)
    private FuleType fuleType;

    @Column(name = "description", length = 500, nullable = true)
    private String description;

    @Lob
    private byte[] image;

}
