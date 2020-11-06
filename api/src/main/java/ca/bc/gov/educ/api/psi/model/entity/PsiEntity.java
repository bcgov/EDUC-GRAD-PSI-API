package ca.bc.gov.educ.api.psi.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Data;

@Data
@Immutable
@Entity
@Table(name = "TAB_POSTSEC")
public class PsiEntity {
   
	@Id
	@Column(name = "PSI_CODE", nullable = false)
    private String psiCode;   

    @Column(name = "PSI_NAME", nullable = true)
    private String psiName;  
    
    @Column(name = "ADDRESS1", nullable = true)
    private String address1;
    
	@Column(name = "ADDRESS2", nullable = true)
	private String address2;
	
	@Column(name = "ADDRESS3", nullable = true)
	private String address3; 
	
	@Column(name = "CITY", nullable = true)
	private String city; 
	
	@Column(name = "PROV_CODE", nullable = true)
	private String provinceCode; 
	
	@Column(name = "CNTRY_CODE", nullable = true)
	private String countryCode; 
    
    @Column(name = "PSI_POSTAL", nullable = true)
    private String postal; 
    
    @Column(name = "PSI_CSL_CODE", nullable = true)
    private String cslCode; 
    
    @Column(name = "PSI_ATTN_NAME", nullable = true)
    private String attentionName; 
    
    @Column(name = "OPEN_FLAG", nullable = true)
    private String openFlag;
    
    @Column(name = "FAX", nullable = true)
    private String fax; 
    
    @Column(name = "PHONE1", nullable = true)
    private String phone1; 
    
    @Column(name = "TRANSMISSION_MODE", nullable = true)
    private String transmissionMode; 
    
    @Column(name = "PSIS_CODE", nullable = true)
    private String psisCode; 
    
    @Column(name = "PSI_URL", nullable = true)
    private String psiUrl; 
    
    @Column(name = "PSI_GROUPING", nullable = true)
    private String psiGrouping; 
}
