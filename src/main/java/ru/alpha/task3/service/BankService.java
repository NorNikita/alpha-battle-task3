package ru.alpha.task3.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alpha.task3.model.Bank;
import ru.alpha.task3.model.BranchDto;
import ru.alpha.task3.model.DistBranchDto;
import ru.alpha.task3.repository.BankRepo;

import java.util.Optional;

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


    @Override
    public DistBranchDto findNearParam(Double lat, Double lon) {
        final double r = 6371;
        Position first = bankRepo.findAll()
                .stream()
                .map(dto -> {
                    double sin1 = Math.sin((lat - dto.getLat()) / 2);
                    double sin2 = Math.sin((lon - dto.getLon()) / 2);

                    double d = 2 * r * Math.sqrt(sin1 * sin1 + Math.cos(lat) * Math.cos(dto.getLat()) * sin2 * sin2);

                    return Position.builder()
                            .id(dto.getId())
                            .metrica(d)
                            .build();
                })
                .sorted()
                .findFirst()
                .orElseThrow(() -> new NullPointerException());

        Bank bank = bankRepo.findById(first.getId()).orElseThrow(() -> new NullPointerException());

        return DistBranchDto.builder()
                .id(bank.getId())
                .address(bank.getAddress())
                .lat(bank.getLat())
                .lon(bank.getLon())
                .title(bank.getTitle())
                .distance(Math.round(first.metrica))
                .build();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    private static class Position implements Comparable<Position> {
        private Long id;
        private Double metrica;

        @Override
        public int compareTo(Position o) {
            return this.metrica.compareTo(o.metrica);
        }
    }
}
