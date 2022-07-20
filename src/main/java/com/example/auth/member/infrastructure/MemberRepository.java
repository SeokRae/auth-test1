package com.example.auth.member.infrastructure;

import com.example.auth.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByUsername(String username);
	
	@Query("select u from Member u join fetch u.group g left join fetch g.permissions gp join fetch gp.permission where u.email = :email")
	Optional<Member> findByEmail(String email);
	
}
