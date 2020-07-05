package ru.alpha.task3.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchDto implements Comparable<BranchDto> {

    private Long id;
    private String address;
    private Double lat;
    private Double lon;
    private String title;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long distance;

    @Override
    public int compareTo(BranchDto anotherBranch) {
        return this.distance.compareTo(anotherBranch.getDistance());
    }
}