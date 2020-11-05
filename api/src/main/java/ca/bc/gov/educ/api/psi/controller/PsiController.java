package ca.bc.gov.educ.api.psi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.bc.gov.educ.api.psi.model.dto.Psi;
import ca.bc.gov.educ.api.psi.service.PsiService;
import ca.bc.gov.educ.api.psi.util.EducPsiApiConstants;

@CrossOrigin
@RestController
@RequestMapping(EducPsiApiConstants.GRAD_PSI_API_ROOT_MAPPING)
public class PsiController {

    private static Logger logger = LoggerFactory.getLogger(PsiController.class);

    @Autowired
    PsiService psiService;

    @GetMapping
    public List<Psi> getAllPSIs(
    		@RequestParam(value = "pageNo", required = false,defaultValue = "0") Integer pageNo, 
            @RequestParam(value = "pageSize", required = false,defaultValue = "150") Integer pageSize) { 
    	logger.debug("getAllPSIs : ");
        return psiService.getPSIList(pageNo,pageSize);
    }
    
    @GetMapping(EducPsiApiConstants.GET_PSI_BY_CODE_MAPPING)
    public Psi getPSIDetails(@PathVariable String psiCode) { 
    	logger.debug("getPSIDetails : ");
        return psiService.getPSIDetails(psiCode);
    }
}
