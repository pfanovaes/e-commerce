package br.com.letscode.ecommerce.domain.service;

import br.com.letscode.ecommerce.domain.model.entity.CarrinhoEntity;
import br.com.letscode.ecommerce.domain.model.entity.ProdutoEntity;
import br.com.letscode.ecommerce.domain.model.entity.UsuarioEntity;
import br.com.letscode.ecommerce.domain.repository.CarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarrinhoService {
    private final CarrinhoRepository carrinhoRepository;
    private final UsuarioService usuarioService;
    private final ProdutoService produtoService;

    public CarrinhoService(
            @Autowired CarrinhoRepository carrinhoRepository,
            @Autowired UsuarioService usuarioService,
            @Autowired ProdutoService produtoService) {
        this.carrinhoRepository = carrinhoRepository;
        this.usuarioService = usuarioService;
        this.produtoService = produtoService;
    }

    public CarrinhoEntity getCarrinho(Long id) {
        return carrinhoRepository.getById(id);
    }

    public CarrinhoEntity getCarrinhoDoUsuario(String nomeUsuario) {
        UsuarioEntity usuario = usuarioService.buscarPorNome(nomeUsuario);

        Optional<CarrinhoEntity> carrinhoEncontrado = carrinhoRepository.findByUsuario(usuario);

        CarrinhoEntity carrinho;
        if(carrinhoEncontrado.isEmpty()) {
            carrinho = carrinhoRepository.save(new CarrinhoEntity(usuario));
        } else {
            carrinho = carrinhoEncontrado.get();
        }

        return carrinho;
    }


    public CarrinhoEntity inserirItemCarrinho(Long idCarrinho, Long idProduto, Integer quantidade) {
        CarrinhoEntity carrinho = carrinhoRepository.findById(idCarrinho).orElseThrow(
                () -> new IllegalArgumentException(String.format("Carrinho ID '%s' não encontrado"))
        );

        ProdutoEntity produto = produtoService.buscarPorId(idProduto);
        carrinho.adicionarItem(produto, quantidade);

        carrinhoRepository.saveAndFlush(carrinho);

        return carrinho;
    }

    public CarrinhoEntity removerItemCarrinho(Long idCarrinho, Long idProduto, Integer quantidade) {
        CarrinhoEntity carrinho = carrinhoRepository.findById(idCarrinho).orElseThrow(
                () -> new IllegalArgumentException(String.format("Carrinho ID '%s' não encontrado"))
        );
        ProdutoEntity produto = produtoService.buscarPorId(idProduto);

        carrinho.removerItem(produto, quantidade);

        carrinhoRepository.saveAndFlush(carrinho);

        return carrinho;
    }
}
