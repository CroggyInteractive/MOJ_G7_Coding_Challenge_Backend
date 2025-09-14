package uk.gov.hmcts.reform.dev.services;

import uk.gov.hmcts.reform.dev.dto.CaseDto;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.dev.models.CaseDetails;
import uk.gov.hmcts.reform.dev.repository.CaseRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CaseService {

    private final CaseRepository caseRepository;

    public CaseService(CaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }

    public List<CaseDto> getAllCases() {
        List<CaseDetails> cases = caseRepository.findAll();

        return cases.stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    public Optional<CaseDto> getCaseById(Long caseId) {
        Optional<CaseDetails> caseOptional = caseRepository.findById(caseId);
        return caseOptional.map(this::mapToDTO);
    }

    public CaseDto saveCase(CaseDto caseDto) {
        CaseDetails caseToSave = mapDtoToModel(caseDto);

        CaseDetails savedCase = caseRepository.save(caseToSave);

        return mapToDTO(savedCase);
    }

    public void deleteCaseByCaseNumber(Long caseNumber) {
        caseRepository.deleteById(caseNumber);
    }

    public CaseDto updateCase(CaseDto caseDto) {
        CaseDetails caseToUpdate = mapDtoToModel(caseDto);
        CaseDetails updatedCase = caseRepository.save(caseToUpdate);
        return mapToDTO(updatedCase);
    }

    private CaseDto mapToDTO(CaseDetails caseDetails){
        return CaseDto.builder()
            .id(caseDetails.getId())
            .caseNumber(caseDetails.getCaseNumber())
            .title(caseDetails.getTitle())
            .description(caseDetails.getDescription())
            .createdDate(caseDetails.getCreatedDate())
            .dueDate(caseDetails.getDueDateTime())
            .build();
    }

    private CaseDetails mapDtoToModel(CaseDto caseDto){
        return CaseDetails.builder()
            .caseNumber(caseDto.getCaseNumber())
            .title(caseDto.getTitle())
            .description(caseDto.getDescription())
            .status(caseDto.getStatus())
            .dueDateTime(caseDto.getDueDate())
            .build();

    }
}
