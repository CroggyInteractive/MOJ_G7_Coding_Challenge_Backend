package uk.gov.hmcts.reform.dev.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import uk.gov.hmcts.reform.dev.models.CaseDetails;

@Repository
public interface CaseRepository extends JpaRepository<CaseDetails, Long> {
}
