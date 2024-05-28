package org.jp.repository;

import org.jp.entity.UserClientEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserClientEntity,Integer> {
    Optional<UserClientEntity> findByUsername(String username);
}
