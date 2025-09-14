package uk.gov.hmcts.reform.dev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.hmcts.reform.dev.dto.CaseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.dev.services.CaseService;
import uk.gov.hmcts.reform.dev.models.ExampleCase;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
public class CaseController {

    CaseService caseService;

    @Autowired
    public CaseController(CaseService caseService) {
        this.caseService = caseService;
    }

    @GetMapping(value = "/get-example-case", produces = "application/json")
    public ResponseEntity<ExampleCase> getExampleCase() {
        return ok(new ExampleCase(1, "ABC12345", "Case Title",
                                  "Case Description", "Case Status", LocalDateTime.now()
        ));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<CaseDto> createCase(@RequestBody CaseDto caseDetails) {
        log.info("Adding new case with case details {}", caseDetails);

        return ok(caseService.saveCase(caseDetails));
    }

    @GetMapping(value = "/get-case/{id}")
    public ResponseEntity<CaseDto> getCase(@PathVariable long id) {
        log.info("Getting case with id {}", id);
        Optional<CaseDto> caseDtoResponse;

        try {
            caseDtoResponse = caseService.getCaseById(id);
            log.info("Found case with id {}", id);
        } catch (Exception ex) {
            // I would normally writee a custom exception here
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(caseDtoResponse.get());
    }
}
