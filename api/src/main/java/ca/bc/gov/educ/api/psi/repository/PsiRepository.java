package ca.bc.gov.educ.api.psi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.psi.model.entity.PsiEntity;

@Repository
public interface PsiRepository extends JpaRepository<PsiEntity, String> {

    List<PsiEntity> findAll();

    @Query(value="SELECT si.* FROM TAB_POSTSEC si where "
			+ "(:psiName is null or si.PSI_NAME like %:psiName%) and ROWNUM <= 50",nativeQuery = true)	
	List<PsiEntity> searchForPSI(String psiName);

}
