package ru.alpha.task3.service;

import ru.alpha.task3.model.dto.BranchDto;

public interface IBankService {

    double Radius = 6_371_000;

    BranchDto findBranchesById(Long id);

    BranchDto findNearestBranch(Double lat, Double lon);

    BranchDto predictWaiting(Long id, Long dayOfWeek, Long hourOfDay);
}
