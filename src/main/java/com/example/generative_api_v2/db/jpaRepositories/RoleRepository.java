package com.example.generative_api_v2.db.jpaRepositories;

import com.example.generative_api_v2.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Role findByAuthority(String authority);
}
