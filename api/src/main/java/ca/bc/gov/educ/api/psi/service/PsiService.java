package ca.bc.gov.educ.api.psi.service;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.bc.gov.educ.api.psi.model.dto.Psi;
import ca.bc.gov.educ.api.psi.model.entity.PsiEntity;
import ca.bc.gov.educ.api.psi.model.transformer.PsiTransformer;
import ca.bc.gov.educ.api.psi.repository.PsiCriteriaQueryRepository;
import ca.bc.gov.educ.api.psi.repository.PsiRepository;
import ca.bc.gov.educ.api.psi.repository.criteria.CriteriaHelper;
import ca.bc.gov.educ.api.psi.repository.criteria.GradCriteria.OperationEnum;

@Service
public class PsiService {

    @Autowired
    private PsiRepository psiRepository;  

    @Autowired
    private PsiTransformer psiTransformer;
    
    @Autowired
    private PsiCriteriaQueryRepository  psiCriteriaQueryRepository;

    @SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(PsiService.class);

     /**
     * Get all Schools in PSI DTO
     */
    public List<Psi> getPSIList() {
        return psiTransformer.transformToDTO(psiRepository.findAll());
    }

	public Psi getPSIDetails(String psiCode) {
		return psiTransformer.transformToDTO(psiRepository.findById(psiCode));
	}

	public List<Psi> getPSIByParams(String psiName, String psiCode, String cslCode, String transmissionMode) {
		CriteriaHelper criteria = new CriteriaHelper();
        getSearchCriteria("psiCode", psiCode, criteria);
        getSearchCriteria("psiName", psiName, criteria);
        getSearchCriteria("cslCode", cslCode, criteria);
        getSearchCriteria("transmissionMode", transmissionMode, criteria);
		return psiTransformer.transformToDTO(psiCriteriaQueryRepository.findByCriteria(criteria, PsiEntity.class));
	}
	
	public CriteriaHelper getSearchCriteria(String roolElement, String value, CriteriaHelper criteria) {
        if (StringUtils.isNotBlank(value)) {
            if (StringUtils.contains(value, "*")) {
                criteria.add(roolElement, OperationEnum.STARTS_WITH_IGNORE_CASE, StringUtils.strip(value.toUpperCase(), "*"));
            } else {
                criteria.add(roolElement, OperationEnum.EQUALS, value.toUpperCase());
            }
        }
        return criteria;
    }
}
