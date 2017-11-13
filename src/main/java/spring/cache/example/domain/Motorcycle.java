package spring.cache.example.domain;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Motorcycle implements Serializable {

    private String brand;
    private String model;

}
