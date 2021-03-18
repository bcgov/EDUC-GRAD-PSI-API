package ca.bc.gov.educ.api.psi.service;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ca.bc.gov.educ.api.psi.model.dto.Psi;
import ca.bc.gov.educ.api.psi.model.entity.PsiEntity;
import ca.bc.gov.educ.api.psi.model.transformer.PsiTransformer;
import ca.bc.gov.educ.api.psi.repository.PsiRepository;

@Service
public class PsiService {

    @Autowired
    private PsiRepository psiRepository;  

    @Autowired
    private PsiTransformer psiTransformer;

    private static Logger logger = LoggerFactory.getLogger(PsiService.class);

     /**
     * Get all Schools in School DTO
     * @param pageSize 
     * @param pageNo 
     *
     * @return Course 
     * @throws java.lang.Exception
     */
    public List<Psi> getPSIList(Integer pageNo, Integer pageSize) {
        List<Psi> schoolList  = new ArrayList<Psi>();

        try {
        	Pageable paging = PageRequest.of(pageNo, pageSize);        	 
            Page<PsiEntity> pagedResult = psiRepository.findAll(paging);
        	schoolList = psiTransformer.transformToDTO(pagedResult.getContent());            
        } catch (Exception e) {
            logger.debug("Exception:" + e);
        }

        return schoolList;
    }

	public Psi getPSIDetails(String psiCode) {
		return psiTransformer.transformToDTO(psiRepository.findById(psiCode));
	}

	public List<Psi> getPSIByParams(String psiName, Integer pageNo, Integer pageSize, String accessToken) {
		return psiTransformer.transformToDTO(psiRepository.searchForPSI(StringUtils.toRootUpperCase(StringUtils.strip(psiName, "*"))));
	}
}
