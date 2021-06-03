package ca.bc.gov.educ.api.psi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import ca.bc.gov.educ.api.psi.model.dto.GradCountry;
import ca.bc.gov.educ.api.psi.model.dto.GradProvince;
import ca.bc.gov.educ.api.psi.model.dto.Psi;
import ca.bc.gov.educ.api.psi.model.entity.PsiEntity;
import ca.bc.gov.educ.api.psi.repository.PsiCriteriaQueryRepository;
import ca.bc.gov.educ.api.psi.repository.PsiRepository;
import ca.bc.gov.educ.api.psi.repository.criteria.CriteriaHelper;
import ca.bc.gov.educ.api.psi.util.EducPsiApiConstants;
import reactor.core.publisher.Mono;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PsiServiceTest {

    @Autowired
    EducPsiApiConstants educPsiApiConstants;

    @Autowired
    private PsiService psiService;

    @MockBean
    private PsiRepository psiRepository;

    @MockBean
    private PsiCriteriaQueryRepository psiCriteriaQueryRepository;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersMock;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriMock;
    @Mock
    private WebClient.RequestBodySpec requestBodyMock;
    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriMock;
    @Mock
    private WebClient.ResponseSpec responseMock;
    
    @Mock
    private Mono<GradCountry> monoCountryResponse;
    
    @Mock
    private Mono<GradProvince> monoProvinceResponse;
	

    @Before
    public void setUp() {
        openMocks(this);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testGetPSIList() {
        // psi data
    	List<PsiEntity> gradPSIList = new ArrayList<>();
		PsiEntity obj = new PsiEntity();
		obj.setPsiCode("AB");
		obj.setPsiName("Autobody");
		obj.setAddress1("ABC");
		obj.setAddress2("DEF");
		obj.setAddress3("FGF");
		obj.setAttentionName("XZA");
		obj.setCity("Abbotsford");
		obj.setCountryCode("CDD");
		obj.setCslCode("SW@");
		obj.setFax("23432234234");
		obj.setOpenFlag("Y");
		obj.setPhone1("123213");
		obj.setPostal("V3T1C4");
		gradPSIList.add(obj);
		obj = new PsiEntity();
		obj.setPsiCode("AB");
		obj.setPsiName("Autobody");
		obj.setAddress1("ABC");
		obj.setAddress2("DEF");
		obj.setAddress3("FGF");
		obj.setAttentionName("XZA");
		obj.setCity("Abbotsford");
		obj.setCountryCode("CDD");
		obj.setCslCode("SW@");
		obj.setFax("23432234234");
		obj.setOpenFlag("Y");
		obj.setPhone1("123213");
		obj.setPostal("V3T1C4");
		gradPSIList.add(obj);
		

        when(psiRepository.findAll()).thenReturn(gradPSIList);
        List<Psi> results = psiService.getPSIList();

        assertThat(results).isNotNull();
        assertThat(results.size()).isEqualTo(2);
    }

    @Test
    public void testGetPsiDetails() {
        // School
    	final PsiEntity obj = new PsiEntity();
		obj.setPsiCode("AB");
		obj.setPsiName("Autobody");
		obj.setAddress1("ABC");
		obj.setAddress2("DEF");
		obj.setAddress3("FGF");
		obj.setAttentionName("XZA");
		obj.setCity("Abbotsford");
		obj.setCountryCode("CDD");
		obj.setCslCode("SW@");
		obj.setFax("23432234234");
		obj.setOpenFlag("Y");
		obj.setPhone1("123213");
		obj.setPostal("V3T1C4");

        // Country
        final GradCountry country = new GradCountry();
        country.setCountryCode("CA");
        country.setCountryName("Canada");

        // Provice
        final GradProvince province = new GradProvince();
        province.setCountryCode("CA");
        province.setProvCode("BC");
        province.setProvName("British Columbia");

        when(psiRepository.findById("AB")).thenReturn(Optional.of(obj));

        when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
   	 	when(this.requestHeadersUriMock.uri(String.format(educPsiApiConstants.getCountryByCountryCodeUrl(), obj.getCountryCode()))).thenReturn(this.requestHeadersMock);
       when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
       when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
       when(this.responseMock.bodyToMono(GradCountry.class)).thenReturn(monoCountryResponse);
       when(this.monoCountryResponse.block()).thenReturn(country); 

       when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
  	 	when(this.requestHeadersUriMock.uri(String.format(educPsiApiConstants.getProvinceByProvinceCodeUrl(), obj.getProvinceCode()))).thenReturn(this.requestHeadersMock);
      when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
      when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
      when(this.responseMock.bodyToMono(GradProvince.class)).thenReturn(monoProvinceResponse);
      when(this.monoProvinceResponse.block()).thenReturn(province); 

        var result = psiService.getPSIDetails("AB","accessToken");

        assertThat(result).isNotNull();
        assertThat(result.getPsiCode()).isEqualTo("AB");
        assertThat(result.getPsiName()).isEqualTo("Autobody");
    }

    @Test
    public void testGetPSIsByParams() {
        // School
    	PsiEntity obj = new PsiEntity();
		obj.setPsiCode("AB");
		obj.setPsiName("Autobody");
		obj.setAddress1("ABC ");
		obj.setAddress2("DEF ");
		obj.setAddress3("FGF ");
		obj.setAttentionName("XZA");
		obj.setCity("Abbotsford");
		obj.setCountryCode("CDD");
		obj.setProvinceCode("BC");
		obj.setCslCode("SW@");
		obj.setFax("23432234234");
		obj.setOpenFlag("Y");
		obj.setPhone1("123213 ");
		obj.setPostal("V3T1C4 ");
		List<PsiEntity> list = new ArrayList<PsiEntity>();
		list.add(obj);
        // Country
        GradCountry country = new GradCountry();
        country.setCountryCode("CA");
        country.setCountryName("Canada");

        // Provice
        GradProvince province = new GradProvince();
        province.setCountryCode("CA");
        province.setProvCode("BC");
        province.setProvName("British Columbia");

        when(psiCriteriaQueryRepository.findByCriteria(any(CriteriaHelper.class), eq(PsiEntity.class))).thenReturn(list);

        when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
   	 	when(this.requestHeadersUriMock.uri(String.format(educPsiApiConstants.getCountryByCountryCodeUrl(), obj.getCountryCode()))).thenReturn(this.requestHeadersMock);
       when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
       when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
       when(this.responseMock.bodyToMono(GradCountry.class)).thenReturn(monoCountryResponse);
       when(this.monoCountryResponse.block()).thenReturn(country); 

       when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
  	 	when(this.requestHeadersUriMock.uri(String.format(educPsiApiConstants.getProvinceByProvinceCodeUrl(), obj.getProvinceCode()))).thenReturn(this.requestHeadersMock);
      when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
      when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
      when(this.responseMock.bodyToMono(GradProvince.class)).thenReturn(monoProvinceResponse);
      when(this.monoProvinceResponse.block()).thenReturn(province);

        var result = psiService.getPSIByParams("Autobody", "AB", null,null,"accessToken");
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getPsiCode()).isEqualTo("AB");
        assertThat(result.get(0).getPsiName()).isEqualTo("Autobody");
    }
    
    @Test
    public void testGetPSIsByParamsStar() {
        // School
    	PsiEntity obj = new PsiEntity();
		obj.setPsiCode("AB");
		obj.setPsiName("Autobody");
		obj.setAddress1("ABC ");
		obj.setAddress2("DEF ");
		obj.setAddress3("FGF ");
		obj.setAttentionName("XZA");
		obj.setCity("Abbotsford");
		obj.setCountryCode("CDD");
		obj.setProvinceCode("BC");
		obj.setCslCode("SW@");
		obj.setFax("23432234234");
		obj.setOpenFlag("Y");
		obj.setPhone1("123213 ");
		obj.setPostal("V3T1C4 ");
		List<PsiEntity> list = new ArrayList<PsiEntity>();
		list.add(obj);
        // Country
        GradCountry country = new GradCountry();
        country.setCountryCode("CA");
        country.setCountryName("Canada");

        // Provice
        GradProvince province = new GradProvince();
        province.setCountryCode("CA");
        province.setProvCode("BC");
        province.setProvName("British Columbia");

        when(psiCriteriaQueryRepository.findByCriteria(any(CriteriaHelper.class), eq(PsiEntity.class))).thenReturn(list);

        when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
   	 	when(this.requestHeadersUriMock.uri(String.format(educPsiApiConstants.getCountryByCountryCodeUrl(), obj.getCountryCode()))).thenReturn(this.requestHeadersMock);
       when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
       when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
       when(this.responseMock.bodyToMono(GradCountry.class)).thenReturn(monoCountryResponse);
       when(this.monoCountryResponse.block()).thenReturn(country); 

       when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
  	 	when(this.requestHeadersUriMock.uri(String.format(educPsiApiConstants.getProvinceByProvinceCodeUrl(), obj.getProvinceCode()))).thenReturn(this.requestHeadersMock);
      when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
      when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
      when(this.responseMock.bodyToMono(GradProvince.class)).thenReturn(monoProvinceResponse);
      when(this.monoProvinceResponse.block()).thenReturn(province);

        var result = psiService.getPSIByParams("Autobody", "AB*", null,null,"accessToken");
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getPsiCode()).isEqualTo("AB");
        assertThat(result.get(0).getPsiName()).isEqualTo("Autobody");
    }
}
