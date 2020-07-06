package ru.alpha.task3.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alpha.task3.model.dto.BranchDto;
import ru.alpha.task3.service.IBankService;

@RestController
@RequestMapping("/branches")
@AllArgsConstructor
public class Controller {

    private IBankService bankService;

    @GetMapping("/{id}")
    public ResponseEntity<BranchDto> getBranches(@PathVariable Long id) {
        return ResponseEntity.ok(bankService.findBranchesById(id));
    }

    @GetMapping
    public ResponseEntity<BranchDto> getNearBranch(@RequestParam Double lat,
                                                   @RequestParam Double lon) {
        return ResponseEntity.ok(bankService.findNearestBranch(lat, lon));
    }

    @GetMapping("/{id}/predict")
    public ResponseEntity<BranchDto> getPredict(@PathVariable Long id,
                                                @RequestParam Long dayOfWeek,
                                                @RequestParam Long hourOfDay) {
        return ResponseEntity.ok(bankService.predictWaiting(id, dayOfWeek, hourOfDay));
    }
}
