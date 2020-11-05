package ca.bc.gov.educ.api.psi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.psi.model.entity.PsiEntity;

@Repository
public interface PsiRepository extends JpaRepository<PsiEntity, String> {

    List<PsiEntity> findAll();

}
