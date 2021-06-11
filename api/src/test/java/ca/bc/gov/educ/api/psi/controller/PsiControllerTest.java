package ca.bc.gov.educ.api.psi.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import ca.bc.gov.educ.api.psi.model.dto.Psi;
import ca.bc.gov.educ.api.psi.service.PsiService;
import ca.bc.gov.educ.api.psi.util.ResponseHelper;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class PsiControllerTest {

    @Mock
    private PsiService psiService;

    @Mock
    ResponseHelper responseHelper;

    @InjectMocks
    private PsiController psiController;

    @Test
    public void testGetAllPsis() {
        List<Psi> gradPsiList = new ArrayList<>();
        Psi obj = new Psi();
        obj.setPsiCode("1234567");
        obj.setPsiName("Test1 Psi");
        gradPsiList.add(obj);
        obj = new Psi();
        obj.setPsiCode("7654321");
        obj.setPsiName("Test2 Psi");
        gradPsiList.add(obj);

        Mockito.when(psiService.getPSIList()).thenReturn(gradPsiList);
        psiController.getAllPSIs();
        Mockito.verify(psiService).getPSIList();
    }

    @Test
    public void testGetPsiDetails() {
        Psi school = new Psi();
        school.setPsiCode("1234567");
        school.setPsiName("Test Psi");

        Authentication authentication = Mockito.mock(Authentication.class);
        OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
        // Mockito.whens() for your authorization object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getDetails()).thenReturn(details);
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(psiService.getPSIDetails("1234567", null)).thenReturn(school);
        psiController.getPSIDetails("1234567");
        Mockito.verify(psiService).getPSIDetails("1234567", null);

    }

    @Test
    public void testGetPsisByParams() {
        Psi school = new Psi();
        school.setPsiCode("1234567");
        school.setPsiName("Test Psi");

        Authentication authentication = Mockito.mock(Authentication.class);
        OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
        // Mockito.whens() for your authorization object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getDetails()).thenReturn(details);
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(psiService.getPSIByParams("Test Psi", "1234567", null,null)).thenReturn(Arrays.asList(school));
        psiController.getPSIByParams("Test Psi","1234567", null, null);
        Mockito.verify(psiService).getPSIByParams("Test Psi", "1234567", null,null);
    }
}
