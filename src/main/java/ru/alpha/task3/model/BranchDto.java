package ru.alpha.task3.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchDto {

    private Long id;
    private String address;
    private Double lat;
    private Double lon;
    private String title;
}