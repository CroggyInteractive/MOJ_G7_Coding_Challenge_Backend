package uk.gov.hmcts.reform.dev.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.reform.dev.dto.CaseDto;
import uk.gov.hmcts.reform.dev.models.CaseDetails;
import uk.gov.hmcts.reform.dev.repository.CaseRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CaseServiceTest {

    @Mock
    private CaseRepository caseRepository;

    @InjectMocks
    private CaseService caseService;

    private CaseDetails caseDetails;
    private CaseDto caseDto;

    @BeforeEach
    void setUp() {
        caseDetails = CaseDetails.builder()
                .id(1L)
                .caseNumber("CASE-001")
                .title("Test Title")
                .status("OPEN")
                .description("A test case")
                .createdDate(LocalDateTime.now())
                .dueDateTime(LocalDateTime.now().plusDays(10))
                .build();

        caseDto = CaseDto.builder()
                .id(1L)
                .caseNumber("CASE-001")
                .title("Test Title")
                .status("OPEN")
                .description("A test case")
                .build();
    }

    @Test
    void whenSaveCase_shouldReturnSavedDto() {

        when(caseRepository.save(any(CaseDetails.class))).thenReturn(caseDetails);

        CaseDto savedDto = caseService.saveCase(caseDto);

        assertThat(savedDto).isNotNull();
        assertThat(savedDto.getId()).isEqualTo(1L);
        assertThat(savedDto.getTitle()).isEqualTo("Test Title");

        verify(caseRepository, times(1)).save(any(CaseDetails.class));
    }

    @Test
    void whenGetCaseById_whenFound_shouldReturnCaseDto() {
        when(caseRepository.findById(1L)).thenReturn(Optional.of(caseDetails));

        Optional<CaseDto> foundDto = caseService.getCaseById(1L);

        assertThat(foundDto).isPresent();
        assertThat(foundDto.get().getId()).isEqualTo(1L);
        verify(caseRepository, times(1)).findById(1L);
    }

    @Test
    void whenGetCaseById_whenNotFound_shouldReturnEmptyOptional() {
        when(caseRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<CaseDto> foundDto = caseService.getCaseById(99L);

        assertThat(foundDto).isNotPresent();
        verify(caseRepository, times(1)).findById(99L);
    }

    @Test
    void whenGetAllCases_shouldReturnDtoList() {
        when(caseRepository.findAll()).thenReturn(Collections.singletonList(caseDetails));

        List<CaseDto> allCases = caseService.getAllCases();

        assertThat(allCases).isNotNull();
        assertThat(allCases.size()).isEqualTo(1);
        assertThat(allCases.getFirst().getCaseNumber()).isEqualTo("CASE-001");
        verify(caseRepository, times(1)).findAll();
    }
}
