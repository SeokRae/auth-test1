package com.example.auth.member.infrastructure;

import com.example.auth.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByUsername(String username);
	
	Optional<Member> findByEmail(String email);
}
