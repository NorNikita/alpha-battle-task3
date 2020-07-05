package ru.alpha.task3.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MetricaDto implements Comparable<MetricaDto> {

    private Long id;
    private Long distance;

    @Override
    public int compareTo(MetricaDto o) {
        return this.distance.compareTo(o.getDistance());
    }
}
