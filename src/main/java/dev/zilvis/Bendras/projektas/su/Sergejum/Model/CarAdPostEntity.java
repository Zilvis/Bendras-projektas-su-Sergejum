package dev.zilvis.Bendras.projektas.su.Sergejum.Model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.zilvis.Bendras.projektas.su.Sergejum.Enums.FuleType;
import dev.zilvis.Bendras.projektas.su.Sergejum.Enums.Make;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.engine.jdbc.env.internal.LobTypes;
import org.springframework.core.codec.ByteArrayDecoder;

import java.sql.Blob;
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

    @Column(length = 50)
    private String title;

    @Column(name = "make", nullable = true)
    private String make;

    // Savininko email (Unikalus)
    @Column(name = "user_entity_email", nullable = true)
    private Long userEntityEmail;

    @Column(name = "model", nullable = true)
    private String model;

    @Column(name = "year", nullable = true)
    private LocalDate year;

    @Column(name = "millage", nullable = true)
    private Long millage;

    @Column(name = "price", nullable = true)
    private Float price;

    @Column(name = "fuel_type", nullable = true)
    private String fuelType;

    @Column(name = "description")
    private String description;

    @Column(name = "image", columnDefinition="LONGBLOB")
    @Lob
    private byte[] image;

}
