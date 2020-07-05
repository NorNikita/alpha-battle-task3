package ru.alpha.task3.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.alpha.task3.model.dto.BranchDto;
import ru.alpha.task3.model.dto.DistBranchDto;
import ru.alpha.task3.service.IBankService;

@RestController
@AllArgsConstructor
public class Controller {

    private IBankService bankService;

    @GetMapping("/branches/{id}")
    public ResponseEntity<BranchDto> getBranches(@PathVariable Long id) {
        return ResponseEntity.ok(bankService.findBranchesById(id));
    }

    @GetMapping("/branches")
    public ResponseEntity<DistBranchDto> getNearBranch(@RequestParam Double lat,
                                                       @RequestParam Double lon) {
        return ResponseEntity.ok(bankService.findNearParam(lat, lon));
    }
}
