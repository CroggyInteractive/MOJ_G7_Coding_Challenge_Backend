package uk.gov.hmcts.reform.dev.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.gov.hmcts.reform.dev.dto.CaseDto;
import uk.gov.hmcts.reform.dev.services.CaseService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CaseControllerTest {

    @Mock
    private CaseService caseService;

    @InjectMocks
    private CaseController caseController;

    private CaseDto caseDto;

    @BeforeEach
    void setUp() {
        caseDto = CaseDto.builder()
                .id(1L)
                .caseNumber("CASE-123")
                .title("Test Case")
                .status("OPEN")
                .createdDate(LocalDateTime.now())
                .build();
    }

    @Test
    void whenCreateCase_shouldReturnOkAndSavedDto() {
        when(caseService.saveCase(any(CaseDto.class))).thenReturn(caseDto);

        ResponseEntity<CaseDto> response = caseController.createCase(new CaseDto());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getCaseNumber()).isEqualTo("CASE-123");
    }

    @Test
    void whenGetCase_withExistingId_shouldReturnOkAndDto() {
        when(caseService.getCaseById(1L)).thenReturn(Optional.of(caseDto));

        ResponseEntity<CaseDto> response = caseController.getCase(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
    }

    @Test
    void whenGetCase_withNonExistentId_shouldReturnNotFound() {
        when(caseService.getCaseById(99L)).thenReturn(Optional.empty());

        ResponseEntity<CaseDto> response = caseController.getCase(99L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}