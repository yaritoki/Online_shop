package org.jp.repository;

import org.jp.entity.Authority;
import org.jp.entity.UserClientEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuthorityRepository extends CrudRepository<Authority,Integer> {

    Optional<Authority> findByAuthority(String authority);
}
