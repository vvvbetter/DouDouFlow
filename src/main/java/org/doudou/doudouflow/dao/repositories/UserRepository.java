package org.doudou.doudouflow.dao.repositories;

import org.doudou.doudouflow.dao.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
