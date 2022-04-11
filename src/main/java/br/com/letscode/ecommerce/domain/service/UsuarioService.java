package br.com.letscode.ecommerce.domain.service;


import br.com.letscode.ecommerce.domain.model.entity.UsuarioEntity;
import br.com.letscode.ecommerce.domain.model.exchange.UsuarioFiltrosRequest;
import br.com.letscode.ecommerce.domain.model.exchange.UsuarioRequest;
import br.com.letscode.ecommerce.domain.model.pagination.OffsetLimitPageable;
import br.com.letscode.ecommerce.domain.repository.UsuarioRepository;
import br.com.letscode.ecommerce.domain.repository.UsuarioRepositorySecurity;
import br.com.letscode.ecommerce.domain.specification.UsuarioSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Optional;


import static org.springframework.data.jpa.domain.Specification.where;
@Service
public class UsuarioService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioRepositorySecurity usuarioRepositorySecurity;

    private EntityManager entityManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepositorySecurity.findUsuarioByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));

    }

    public Page<UsuarioEntity> buscarTodos(Integer offset,
                                           Integer limit,
                                           UsuarioFiltrosRequest filtros) {

        Pageable pageable = new OffsetLimitPageable(offset, limit);

        return usuarioRepository.findAll(
                where(UsuarioSpecifications.nomeContem(filtros.getNome())),
                pageable
        );
    }
    public UsuarioEntity buscarPorId(Long id) {
        return usuarioRepository.findById(id).get();//TODO adicionar tratativa para optional empty
    }

   /* public UsuarioEntity buscarPorCodigoBarra(String codigoBarra){
        return usuarioRepository.findByCodigoBarra(codigoBarra);
    }*/
   public UsuarioEntity criar(UsuarioRequest usuarioRequest) {

       //valida descricao, sanitizacao... retira HTML, scripts de campos texto. pode ser na view.
       //Optional<FabricanteEntity> fabricanteEntity = fabricanteRepository.findById(produtoRequest.getIdFabricante());
       //TODO implementar exception para o sistema
       UsuarioEntity usuarioEntity = toEntity(usuarioRequest);

       return usuarioRepository.save(usuarioEntity);
   }

    public UsuarioEntity atualizar(Long id, UsuarioRequest usuarioRequest) {
        Optional<UsuarioEntity> usuario = usuarioRepository.findById(id);
        //UsuarioEntity usuarioEntity = toEntity(usuarioRequest);
        /*UsuarioEntity usuarioParaAtualizar = UsuarioEntity(
                id = usuario.getId(),
                nome = usuario.getNome(),
                dataNascimento = usuario.getDataNascimento()*/
        //);
        UsuarioEntity usuarioEntity = usuario.get();
        usuarioEntity.setNome(usuarioRequest.getNome());
        usuarioEntity.setDataNascimento(usuarioRequest.getDataNascimento());
        return usuarioRepository.save(usuarioEntity);
    }

    private UsuarioEntity toEntity(UsuarioRequest usuarioRequest) {
        return new UsuarioEntity(
                usuarioRequest.getNome(),
                usuarioRequest.getDataNascimento()

        );

    }

    public UsuarioEntity buscarPorNome(String nome) {
       return usuarioRepository.findByName(nome);
    }

    public void apagarPorId(Long id) {
       usuarioRepository.delete(buscarPorId(id));
    }
}