package br.com.letscode.ecommerce.domain.repository;

import br.com.letscode.ecommerce.domain.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,String> {

    Optional<Usuario> findUsuarioByUsername(String username);
}
