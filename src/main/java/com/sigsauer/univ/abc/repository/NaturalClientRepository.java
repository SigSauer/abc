package com.sigsauer.univ.abc.repository;

import com.sigsauer.univ.abc.models.clients.NaturalClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NaturalClientRepository extends JpaRepository<NaturalClient, Long> {

    boolean existsByMobile(String mobile);

    boolean existsByTin(String mobile);

    boolean existsByDocNumber(String mobile);

    boolean existsByIdAndActiveFalse(Long id);


}
