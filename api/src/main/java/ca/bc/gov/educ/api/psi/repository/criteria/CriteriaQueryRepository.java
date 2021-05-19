package ca.bc.gov.educ.api.psi.repository.criteria;

import java.util.List;

public interface CriteriaQueryRepository<T> {

	List<T> findByCriteria(CriteriaHelper criteriaHelper, Class<T> type);
}
