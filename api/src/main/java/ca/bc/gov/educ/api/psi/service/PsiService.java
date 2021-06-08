package ca.bc.gov.educ.api.psi.service;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ca.bc.gov.educ.api.psi.model.dto.GradCountry;
import ca.bc.gov.educ.api.psi.model.dto.GradProvince;
import ca.bc.gov.educ.api.psi.model.dto.Psi;
import ca.bc.gov.educ.api.psi.model.entity.PsiEntity;
import ca.bc.gov.educ.api.psi.model.transformer.PsiTransformer;
import ca.bc.gov.educ.api.psi.repository.PsiCriteriaQueryRepository;
import ca.bc.gov.educ.api.psi.repository.PsiRepository;
import ca.bc.gov.educ.api.psi.util.criteria.CriteriaHelper;
import ca.bc.gov.educ.api.psi.util.criteria.GradCriteria.OperationEnum;
import ca.bc.gov.educ.api.psi.util.EducPsiApiConstants;

@Service
public class PsiService {

    @Autowired
    private PsiRepository psiRepository;  

    @Autowired
    private PsiTransformer psiTransformer;
    
    @Autowired
    private PsiCriteriaQueryRepository  psiCriteriaQueryRepository;
    
    @Autowired
    WebClient webClient;
    
    @Autowired
    EducPsiApiConstants educPsiApiConstants;

    @SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(PsiService.class);

     /**
     * Get all Schools in PSI DTO
     */
    public List<Psi> getPSIList() {
        return psiTransformer.transformToDTO(psiRepository.findAll());
    }

	public Psi getPSIDetails(String psiCode,String accessToken) {
		Psi psi =  psiTransformer.transformToDTO(psiRepository.findById(psiCode));
		if(psi != null) {
			if(StringUtils.isNotBlank(psi.getCountryCode())) {
				GradCountry country = webClient.get()
						.uri(String.format(educPsiApiConstants.getCountryByCountryCodeUrl(), psi.getCountryCode()))
						.headers(h -> h.setBearerAuth(accessToken))
						.retrieve()
						.bodyToMono(GradCountry.class).block();
		        if(country != null) {
		        	psi.setCountryName(country.getCountryName());
				}
			}
			if(StringUtils.isNotBlank(psi.getProvinceCode())) {
		        GradProvince province = webClient.get()
						.uri(String.format(educPsiApiConstants.getProvinceByProvinceCodeUrl(), psi.getProvinceCode()))
						.headers(h -> h.setBearerAuth(accessToken))
						.retrieve()
		        		.bodyToMono(GradProvince.class).block();
		        if(province != null) {
		        	psi.setProvinceName(province.getProvName());
				}
			}
		}
		return psi;
	}

	public List<Psi> getPSIByParams(String psiName, String psiCode, String cslCode, String transmissionMode,String accessToken) {
		CriteriaHelper criteria = new CriteriaHelper();
        getSearchCriteria("psiCode", psiCode,"psiCode", criteria);
        getSearchCriteria("psiName", psiName,"psiName", criteria);
        getSearchCriteria("cslCode", cslCode,"cslCode", criteria);
        getSearchCriteria("transmissionMode",transmissionMode,"transmissionMode", criteria);
        List<Psi> psiList = psiTransformer.transformToDTO(psiCriteriaQueryRepository.findByCriteria(criteria, PsiEntity.class));
        psiList.forEach(pL -> {
        	if(StringUtils.isNotBlank(pL.getCountryCode())) {
	    		GradCountry country = webClient.get()
						.uri(String.format(educPsiApiConstants.getCountryByCountryCodeUrl(), pL.getCountryCode()))
						.headers(h -> h.setBearerAuth(accessToken))
						.retrieve()
						.bodyToMono(GradCountry.class).block();
		        if(country != null) {
		        	pL.setCountryName(country.getCountryName());
				}
        	}
        	if(StringUtils.isNotBlank(pL.getProvinceCode())) {
		        GradProvince province = webClient.get()
						.uri(String.format(educPsiApiConstants.getProvinceByProvinceCodeUrl(), pL.getProvinceCode()))
						.headers(h -> h.setBearerAuth(accessToken))
						.retrieve()
		        		.bodyToMono(GradProvince.class).block();
		        if(province != null) {
		        	pL.setProvinceName(province.getProvName());
				}
        	}
    	});
		return psiList;
	}
	
	public CriteriaHelper getSearchCriteria(String roolElement, String value, String parameterType, CriteriaHelper criteria) {
        if(parameterType.equalsIgnoreCase("psiName")) {
        	if (StringUtils.isNotBlank(value)) {
                if (StringUtils.contains(value, "*")) {
                    criteria.add(roolElement, OperationEnum.LIKE, StringUtils.strip(value.toUpperCase(), "*"));
                } else {
                    criteria.add(roolElement, OperationEnum.EQUALS, value.toUpperCase());
                }
            }
        }else {
        	if (StringUtils.isNotBlank(value)) {
                if (StringUtils.contains(value, "*")) {
                    criteria.add(roolElement, OperationEnum.STARTS_WITH_IGNORE_CASE, StringUtils.strip(value.toUpperCase(), "*"));
                } else {
                    criteria.add(roolElement, OperationEnum.EQUALS, value.toUpperCase());
                }
            }
        }
        return criteria;
    }
}
