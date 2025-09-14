package uk.gov.hmcts.reform.dev.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CaseDto {

    private long id;

    private String caseNumber;

    private String title;

    private String description;

    private String status;

    private LocalDateTime createdDate;

    private LocalDateTime dueDate;
}
