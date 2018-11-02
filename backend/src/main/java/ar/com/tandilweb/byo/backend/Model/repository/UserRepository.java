package ar.com.tandilweb.byo.backend.Model.repository;

import java.util.List;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import ar.com.tandilweb.byo.backend.Model.domain.Users;

public interface UserRepository extends CrudRepository<Users, Long>{
	Users findBylinkedinId(String linkedin_id);
	Users findByemail(String email);
	
	@Transactional
    @Query("SELECT u FROM User u JOIN FETCH u.gps_datas")
    List<Users> findAllUsersWithGps();
}
 