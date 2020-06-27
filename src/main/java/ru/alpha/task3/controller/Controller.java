package ru.alpha.task3.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.alpha.task3.model.BranchDto;
import ru.alpha.task3.service.IBankService;

@RestController
@AllArgsConstructor
public class Controller {

    private IBankService bankService;

    @GetMapping("/branches/{id}")
    ResponseEntity<BranchDto> getBranches(@PathVariable Long id) {
        return ResponseEntity.ok(bankService.findBranchesById(id));
    }
}
