package io.github.akhabali.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DealerDto {
    private Long id;
    private String name;
    private Long tierLimit;

    public DealerDto(String name, Long tierLimit) {
        this.name = name;
        this.tierLimit = tierLimit;
    }
}
