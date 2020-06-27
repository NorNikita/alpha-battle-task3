package ru.alpha.task3.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alpha.task3.model.Bank;
import ru.alpha.task3.model.BranchDto;
import ru.alpha.task3.repository.BankRepo;

@Service
@AllArgsConstructor
public class BankService implements IBankService {

    private BankRepo bankRepo;

    @Override
    public BranchDto findBranchesById(Long id) {
        Bank bank = bankRepo.findById(id).orElseThrow(() -> new NullPointerException());

        return BranchDto.builder()
                .id(bank.getId())
                .address(bank.getAddress())
                .lat(bank.getLat())
                .lon(bank.getLon())
                .title(bank.getTitle())
                .build();
    }
}
