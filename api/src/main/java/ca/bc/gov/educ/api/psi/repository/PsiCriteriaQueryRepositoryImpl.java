package ca.bc.gov.educ.api.psi.repository;

import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.psi.model.entity.PsiEntity;
import ca.bc.gov.educ.api.psi.repository.criteria.CriteriaQueryRepositoryImpl;

@Repository
public class PsiCriteriaQueryRepositoryImpl extends CriteriaQueryRepositoryImpl<PsiEntity> implements PsiCriteriaQueryRepository {

}
