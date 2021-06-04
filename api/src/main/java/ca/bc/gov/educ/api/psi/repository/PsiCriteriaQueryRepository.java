package ca.bc.gov.educ.api.psi.repository;

import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.psi.model.entity.PsiEntity;
import ca.bc.gov.educ.api.psi.util.criteria.CriteriaQueryRepository;

@Repository
public interface PsiCriteriaQueryRepository extends CriteriaQueryRepository<PsiEntity> {

}
