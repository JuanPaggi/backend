package ar.com.tandilweb.byo.backend.Model.repository;

import org.springframework.data.repository.CrudRepository;

import ar.com.tandilweb.byo.backend.Model.domain.Usuarios;

public interface UsuariosRepository extends CrudRepository<Usuarios, Integer> {

}
