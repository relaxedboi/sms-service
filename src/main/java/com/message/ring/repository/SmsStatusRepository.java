package com.message.ring.repository;

import com.message.ring.models.SmsStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsStatusRepository extends JpaRepository<SmsStatus, Long> {
}
