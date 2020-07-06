package ru.alpha.task3.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "queue_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QueueLog {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "data")
    private LocalDate data;

    @Column(name = "start_time_of_wait")
    private LocalTime startTimeOfWait;

    @Column(name = "end_time_of_wait")
    private LocalTime endTimeOfWait;

    @Column(name = "end_time_of_service")
    private LocalTime endTimeOfService;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branches_id", nullable = false)
    private Branch branch;

}
