package ru.alpha.task3.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueueLogDto {

    private LocalDate dayWeek;
    private LocalTime startWaiting;
    private LocalTime endWaiting;
}
