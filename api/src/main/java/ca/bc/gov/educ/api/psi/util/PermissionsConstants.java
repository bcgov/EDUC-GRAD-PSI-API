package ca.bc.gov.educ.api.psi.util;

public interface PermissionsConstants {
	String _PREFIX = "#oauth2.hasAnyScope('";
	String _SUFFIX = "')";

	String READ_PSI_INFO = _PREFIX + "READ_GRAD_PSI_DATA" + _SUFFIX;
	
}
