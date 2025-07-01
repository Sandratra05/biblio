package com.springjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.springjpa.entity.QuotaTypePret;
import com.springjpa.entity.QuotaTypePretId;

@Repository
public interface QuotaTypePretRepository extends JpaRepository<QuotaTypePret, QuotaTypePretId> {
    @Query(value = "SELECT quota FROM quota_type_pret WHERE id_profil = :idProfil AND id_type_pret = :idTypePret", nativeQuery = true)
    Integer findQuota(@Param("idProfil") Integer idProfil, @Param("idTypePret") Integer idTypePret);

    // QuotaTypePret findByProfilIdProfilAndTypePretIdTypePret(Integer idProfil, Integer idTypePret);
}
