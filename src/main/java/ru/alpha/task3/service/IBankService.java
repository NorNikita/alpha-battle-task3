package ru.alpha.task3.service;

import ru.alpha.task3.model.BranchDto;

public interface IBankService {
    BranchDto findBranchesById(Long id);
}
