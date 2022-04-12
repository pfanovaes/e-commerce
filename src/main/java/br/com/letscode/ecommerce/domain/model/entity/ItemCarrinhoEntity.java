package br.com.letscode.ecommerce.domain.model.entity;

import lombok.*;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "ITEM_CARRINHO")
public class ItemCarrinhoEntity {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_PRODUTO")
    private ProdutoEntity produto;

    @Column(name = "QUANTIDADE")
    private Integer quantidade;

    public ItemCarrinhoEntity(ProdutoEntity produto, Integer quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public BigDecimal getValor() {
        if(produto != null && produto.getValor() != null && quantidade != null) {
            return new BigDecimal(produto.getValor().doubleValue() * quantidade);
        } else {
            return BigDecimal.ZERO;
        }
    }
}
