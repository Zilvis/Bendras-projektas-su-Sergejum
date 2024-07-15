package dev.zilvis.Bendras.projektas.su.Sergejum.Model;

import dev.zilvis.Bendras.projektas.su.Sergejum.Enums.FuleType;
import dev.zilvis.Bendras.projektas.su.Sergejum.Enums.Make;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
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
    @Column(name = "Model", nullable = false)
    private Make make;

    // TODO Perdaryti i enum ?
    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "year", nullable = false)
    private LocalDate year;

    @Column(name = "millage", nullable = false)
    private int millage;

    @Column(name = "price", nullable = false)
    private float price;

    @Column(name = "fuel_type", nullable = false)
    private FuleType fuleType;

    @Column(name = "description", length = 500, nullable = true)
    private String description;

    @Lob
    private byte[] image;

}
