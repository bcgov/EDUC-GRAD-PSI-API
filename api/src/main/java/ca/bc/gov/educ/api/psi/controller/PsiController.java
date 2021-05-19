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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@CrossOrigin
@RestController
@RequestMapping(EducPsiApiConstants.GRAD_PSI_API_ROOT_MAPPING)
@EnableResourceServer
@OpenAPIDefinition(info = @Info(title = "API for PSI Data.", description = "This API is for PSI.", version = "1"))
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
    @Operation(summary = "Find All PSIs", description = "Get All PSIs", tags = { "PSI" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<Psi>> getAllPSIs() { 
    	logger.debug("getAllPSIs : ");
        return response.GET(psiService.getPSIList());
    }
    
    @GetMapping(EducPsiApiConstants.GET_PSI_BY_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_PSI_INFO)
    @Operation(summary = "Find a PSI by Code", description = "Get a PSI by Code", tags = { "PSI" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),@ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<Psi> getPSIDetails(@PathVariable String psiCode) { 
    	logger.debug("getPSIDetails : ");
        return response.GET(psiService.getPSIDetails(psiCode));
    }
    
    @GetMapping(EducPsiApiConstants.GET_PSI_SEARCH_MAPPING)
    @PreAuthorize(PermissionsContants.READ_PSI_INFO)
    @Operation(summary = "Search for PSIs", description = "Search For PSIs", tags = { "PSI" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<Psi>> getPSIByParams(
    		@RequestParam(value = "psiName", required = false) String psiName,
    		@RequestParam(value = "psiCode", required = false) String psiCode,
    		@RequestParam(value = "cslCode", required = false) String cslCode,
    		@RequestParam(value = "transmissionMode", required = false) String transmissionMode) {
		return response.GET(psiService.getPSIByParams(psiName,psiCode,cslCode,transmissionMode));
	}
}
