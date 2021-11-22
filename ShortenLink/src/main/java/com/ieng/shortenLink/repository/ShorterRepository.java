package com.ieng.shortenLink.repository;

import com.ieng.shortenLink.entity.ShorterLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShorterRepository extends JpaRepository<ShorterLink, Long> {
    Optional<ShorterLink> findByHash(String hash);
}
