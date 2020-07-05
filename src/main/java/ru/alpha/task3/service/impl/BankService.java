package ru.alpha.task3.service.impl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alpha.task3.model.dto.BranchDto;
import ru.alpha.task3.model.dto.MetricaDto;
import ru.alpha.task3.model.entity.Branch;
import ru.alpha.task3.model.exception.AlphaTaskException;
import ru.alpha.task3.model.exception.ExceptionMessage;
import ru.alpha.task3.repository.BankRepo;
import ru.alpha.task3.service.IBankService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BankService implements IBankService {

    private BankRepo bankRepo;

    @Override
    public BranchDto findBranchesById(Long id) {
        Branch bank = bankRepo.findById(id).orElseThrow(() -> new AlphaTaskException(ExceptionMessage.BRANCH_NOT_FOUND));

        return BranchDto.builder()
                .id(bank.getId())
                .address(bank.getAddress())
                .lat(bank.getLat())
                .lon(bank.getLon())
                .title(bank.getTitle())
                .build();
    }


    @Override
    public BranchDto findNearestBranch(Double lat, Double lon) {

        final double latR = (Math.PI * lat) / 180;
        final double lonR = (Math.PI * lon) / 180;

        return bankRepo.findAll()
                .stream()
                .map(branch -> {
                    double latR_dto = (Math.PI * branch.getLat()) / 180;
                    double lonR_dto = (Math.PI * branch.getLon()) / 180;

                    double sin1 = Math.sin((latR - latR_dto) / 2);
                    double sin2 = Math.sin((lonR - lonR_dto) / 2);

                    double phi = Math.sqrt(sin1 * sin1 + Math.cos(latR) * Math.cos(latR_dto) * sin2 * sin2);
                    double d = 2 * IBankService.Radius * Math.asin(phi);

                    return BranchDto.builder()
                            .id(branch.getId())
                            .address(branch.getAddress())
                            .lat(branch.getLat())
                            .lon(branch.getLon())
                            .title(branch.getTitle())
                            .distance(Math.round(d))
                            .build();
                })
                .sorted()
                .findFirst()
                .orElseThrow(() -> new AlphaTaskException(ExceptionMessage.BRANCH_NOT_FOUND));

    }
}
