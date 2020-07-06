package ru.alpha.task3.service.impl;

import lombok.AllArgsConstructor;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.springframework.stereotype.Service;
import ru.alpha.task3.model.dto.BranchDto;
import ru.alpha.task3.model.dto.QueueLogDto;
import ru.alpha.task3.model.entity.Branch;
import ru.alpha.task3.model.exception.AlphaTaskException;
import ru.alpha.task3.model.exception.ExceptionMessage;
import ru.alpha.task3.repository.BranchRepo;
import ru.alpha.task3.service.IBankService;

import java.time.Duration;
import java.time.LocalTime;

@Service
@AllArgsConstructor
public class BankService implements IBankService {

    private BranchRepo branchRepo;

    @Override
    public BranchDto findBranchesById(Long id) {
        Branch bank = branchRepo.findById(id).orElseThrow(() -> new AlphaTaskException(ExceptionMessage.BRANCH_NOT_FOUND));

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

        return branchRepo.findAll()
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

    @Override
    public BranchDto predictWaiting(Long id, Long dayOfWeek, Long hourOfDay) {
        Branch branch = branchRepo.findById(id).orElseThrow(() -> new AlphaTaskException(ExceptionMessage.BRANCH_NOT_FOUND));

        final DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();

        branch.getQueueLogs()
                .stream()
                .filter(entity -> entity.getData().getDayOfWeek().getValue() == dayOfWeek && entity.getStartTimeOfWait().getHour()  == hourOfDay)
                .map(entity -> QueueLogDto.builder()
                        .dayWeek(entity.getData())
                        .startWaiting(entity.getStartTimeOfWait())
                        .endWaiting(entity.getEndTimeOfWait())
                        .build()
                )
                .peek(dto -> {
                    LocalTime startWaiting = dto.getStartWaiting();
                    LocalTime endWaiting = dto.getEndWaiting();
                    long startHour = startWaiting.getHour();
                    long endHour = endWaiting.getHour();

                    if (startHour == endHour) {
                        double seconds = Duration.between(startWaiting, endWaiting).getSeconds();
                        descriptiveStatistics.addValue(seconds);
                    } else {
                        double seconds = Duration.between(startWaiting, getLocalTime(startHour + 1)).getSeconds();
                        descriptiveStatistics.addValue(seconds);
                    }
                })
                .count();

        double percentile50 = descriptiveStatistics.getPercentile(50);

        return BranchDto.builder()
                .id(branch.getId())
                .address(branch.getAddress())
                .lat(branch.getLat())
                .lon(branch.getLon())
                .title(branch.getTitle())
                .dayOfWeak(dayOfWeek)
                .hourOfDay(hourOfDay)
                .predicting(Math.round(percentile50))
                .build();
    }

    private LocalTime getLocalTime(long hour) {
        if (hour / 10 != 0) return LocalTime.parse(hour + ":00:00");
        return LocalTime.parse("0" + hour + ":00:00");
    }
}
