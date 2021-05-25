package com.sigsauer.univ.abc.repository;

import com.sigsauer.univ.abc.models.clients.LegalClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegalClientRepository extends JpaRepository<LegalClient, Long> {

    boolean existsByEdrpou(String edrpou);

    boolean existsByIdAndActiveFalse(Long id);


}
