package ar.com.tandilweb.byo.backend.Model.repository;

import org.springframework.data.repository.CrudRepository;

import ar.com.tandilweb.byo.backend.Model.domain.Users;

public interface UserRepository extends CrudRepository<Users, Integer>{
	Users findBylinkedinId(String linkedin_id);
}
 