package ar.com.tandilweb.byo.backend.Model.repository;

import org.springframework.data.repository.CrudRepository;
import ar.com.tandilweb.byo.backend.Model.domain.GpsData;

public interface GpsDataRepository extends CrudRepository<GpsData, Long> {
	GpsData findById(long id_gps);
}
