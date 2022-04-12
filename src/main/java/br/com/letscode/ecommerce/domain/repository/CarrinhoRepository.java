package br.com.letscode.ecommerce.domain.repository;

import br.com.letscode.ecommerce.domain.model.entity.CarrinhoEntity;
import br.com.letscode.ecommerce.domain.model.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarrinhoRepository extends JpaRepository<CarrinhoEntity, Long> {
    Optional<CarrinhoEntity> findByUsuario(UsuarioEntity usuario);
}
