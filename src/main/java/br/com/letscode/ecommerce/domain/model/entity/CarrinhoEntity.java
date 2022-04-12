package br.com.letscode.ecommerce.domain.model.entity;

import br.com.letscode.ecommerce.domain.model.exchange.ItemCarrinhoResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "CARRINHO")
public class CarrinhoEntity {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "ID_USUARIO")
    private UsuarioEntity usuario;

    @Column(name = "DATA_CRIACAO")
    private LocalDateTime dataCriacao;

    @Column(name = "DATA_ATUALIZACAO")
    private LocalDateTime dataAtualizacao;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ID_CARRINHO")
    private Set<ItemCarrinhoEntity> itens = new HashSet<>();

    public CarrinhoEntity(UsuarioEntity usuario) {
        this.usuario = usuario;
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    public void adicionarItem(ProdutoEntity produto, Integer quantidade) {
        Optional<ItemCarrinhoEntity> itemEncontrado = itens.stream().filter(it -> it
                .getProduto()
                .getId()
                .equals(produto.getId()))
                .findAny();

        ItemCarrinhoEntity item;
        if(itemEncontrado.isEmpty()) {
            item = new ItemCarrinhoEntity(produto, 0);
            itens.add(item);
        } else {
            item = itemEncontrado.get();
        }

        item.setQuantidade(item.getQuantidade() + quantidade);

        this.dataAtualizacao = LocalDateTime.now();
    }

    public void removerItem(ProdutoEntity produto, Integer quantidade) {
        ItemCarrinhoEntity item = itens.stream().filter(it ->
                it.getProduto().getId().equals(produto.getId())
        ).findAny().orElseThrow(() -> new IllegalArgumentException("Produto não existe no carrinho"));

        int qtdeFinal = item.getQuantidade() - quantidade;
        if(qtdeFinal < 0) {
            qtdeFinal = 0;
        }

        if(qtdeFinal == 0){
            itens.remove(item);
        } else {
            item.setQuantidade(qtdeFinal);
        }

        this.dataAtualizacao = LocalDateTime.now();
    }

    public BigDecimal getTotal() {
        double valor = itens.stream().map(item -> item.getValor().doubleValue()).reduce(0.0, (acc, item) -> item.doubleValue() + acc);
        return new BigDecimal(valor);

    }

}
