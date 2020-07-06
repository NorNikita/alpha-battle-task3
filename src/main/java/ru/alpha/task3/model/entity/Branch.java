package ru.alpha.task3.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "branches")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Branch {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String address;
    private Double lat;
    private Double lon;
    private String title;

    @OneToMany(
            mappedBy = "branch",
            fetch = FetchType.EAGER,
            cascade = CascadeType.DETACH,
            orphanRemoval = true
    )
    private List<QueueLog> queueLogs = new ArrayList<>();
}