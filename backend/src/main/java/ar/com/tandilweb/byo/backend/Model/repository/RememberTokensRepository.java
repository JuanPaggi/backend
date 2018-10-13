package ar.com.tandilweb.byo.backend.Model.repository;

import org.springframework.data.repository.CrudRepository;
import ar.com.tandilweb.byo.backend.Model.domain.RememberTokens;


public interface RememberTokensRepository extends CrudRepository<RememberTokens, Long> {
	RememberTokens findById(long id_user);
}
