package br.com.letscode.ecommerce.domain.model.exchange;

import br.com.letscode.ecommerce.domain.model.entity.ItemCarrinhoEntity;
import lombok.Getter;

@Getter
public class ItemCarrinhoResponse {
    private ProdutoResponse produtoResponse;
    private Integer quantidade;
    private Double valor;

    public ItemCarrinhoResponse(ItemCarrinhoEntity entity) {
        this.produtoResponse = new ProdutoResponse(entity.getProduto());
        this.quantidade = entity.getQuantidade();
        this.valor = entity.getValor().doubleValue();
    }
}
