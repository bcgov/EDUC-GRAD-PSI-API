package ca.bc.gov.educ.api.psi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.bc.gov.educ.api.psi.model.dto.Psi;
import ca.bc.gov.educ.api.psi.service.PsiService;
import ca.bc.gov.educ.api.psi.util.EducPsiApiConstants;
import ca.bc.gov.educ.api.psi.util.GradValidation;
import ca.bc.gov.educ.api.psi.util.PermissionsContants;
import ca.bc.gov.educ.api.psi.util.ResponseHelper;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@CrossOrigin
@RestController
@RequestMapping(EducPsiApiConstants.GRAD_PSI_API_ROOT_MAPPING)
@EnableResourceServer
@OpenAPIDefinition(info = @Info(title = "API for PSI Data.", description = "This API is for PSI Tables.", version = "1"))
public class PsiController {

    private static Logger logger = LoggerFactory.getLogger(PsiController.class);

    @Autowired
    PsiService psiService;
    
    @Autowired
	GradValidation validation;
    
    @Autowired
	ResponseHelper response;

    @GetMapping
    @PreAuthorize(PermissionsContants.READ_PSI_INFO)
    public ResponseEntity<List<Psi>> getAllPSIs(
    		@RequestParam(value = "pageNo", required = false,defaultValue = "0") Integer pageNo, 
            @RequestParam(value = "pageSize", required = false,defaultValue = "150") Integer pageSize) { 
    	logger.debug("getAllPSIs : ");
        return response.GET(psiService.getPSIList(pageNo,pageSize));
    }
    
    @GetMapping(EducPsiApiConstants.GET_PSI_BY_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_PSI_INFO)
    public ResponseEntity<Psi> getPSIDetails(@PathVariable String psiCode) { 
    	logger.debug("getPSIDetails : ");
        return response.GET(psiService.getPSIDetails(psiCode));
    }
}
