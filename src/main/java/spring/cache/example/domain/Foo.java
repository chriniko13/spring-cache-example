package spring.cache.example.domain;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@ToString
@EqualsAndHashCode

public class Foo implements Serializable {
    private String value;
}
