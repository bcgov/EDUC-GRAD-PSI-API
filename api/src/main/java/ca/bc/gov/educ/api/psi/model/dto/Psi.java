package ca.bc.gov.educ.api.psi.model.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class Psi {

	private String psiCode;
    private String psiName;    
    private String address1;    
	private String address2;	
	private String address3;
	private String city;	
	private String provinceCode;	
	private String countryCode;    
    private String postal;    
    private String cslCode;    
    private String attentionName; 
    private String openFlag;   
    private String fax;    
    private String phone1;    
    private String transmissionMode;  
    private String psisCode;    
    private String psiUrl;
    private String psiGrouping;
    
	@Override
	public String toString() {
		return "Psi [psiCode=" + psiCode + ", psiName=" + psiName + ", address1=" + address1 + ", address2=" + address2
				+ ", address3=" + address3 + ", city=" + city + ", provinceCode=" + provinceCode + ", countryCode="
				+ countryCode + ", postal=" + postal + ", cslCode=" + cslCode + ", attentionName=" + attentionName
				+ ", openFlag=" + openFlag + ", fax=" + fax + ", phone1=" + phone1 + ", transmissionMode="
				+ transmissionMode + ", psisCode=" + psisCode + ", psiUrl=" + psiUrl + ", psiGrouping=" + psiGrouping
				+ "]";
	} 
    
    
    
}