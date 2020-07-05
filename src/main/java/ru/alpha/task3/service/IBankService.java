package ru.alpha.task3.service;

import ru.alpha.task3.model.dto.BranchDto;
import ru.alpha.task3.model.dto.DistBranchDto;

public interface IBankService {
    BranchDto findBranchesById(Long id);

    DistBranchDto findNearParam(Double lat, Double lon);
}
