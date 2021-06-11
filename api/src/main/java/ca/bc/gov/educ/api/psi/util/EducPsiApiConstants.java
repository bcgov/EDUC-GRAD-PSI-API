package ca.bc.gov.educ.api.psi.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class EducPsiApiConstants {

    //API end-point Mapping constants
    public static final String API_ROOT_MAPPING = "";
    public static final String API_VERSION = "v1";
    public static final String GRAD_PSI_API_ROOT_MAPPING = "/api/" + API_VERSION + "/psi";
    public static final String GET_PSI_BY_CODE_MAPPING = "/{psiCode}";
    public static final String GET_PSI_SEARCH_MAPPING="/search";

    
    //Default Attribute value constants
    public static final String DEFAULT_CREATED_BY = "PSIAPI";
    public static final Date DEFAULT_CREATED_TIMESTAMP = new Date();
    public static final String DEFAULT_UPDATED_BY = "PSIAPI";
    public static final Date DEFAULT_UPDATED_TIMESTAMP = new Date();

    //Default Date format constants
    public static final String DEFAULT_DATE_FORMAT = "dd-MMM-yyyy";
    
    public static final String TRAX_DATE_FORMAT = "yyyyMM";
    
    @Value("${endpoint.code-api.country.country-by-country-code.url}")
    private String countryByCountryCodeUrl;
    @Value("${endpoint.code-api.province.province-by-prov-code.url}")
    private String provinceByProvinceCodeUrl;
}
