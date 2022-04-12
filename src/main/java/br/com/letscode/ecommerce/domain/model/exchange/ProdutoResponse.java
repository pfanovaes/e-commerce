package br.com.letscode.ecommerce.domain.model.exchange;

import br.com.letscode.ecommerce.domain.model.entity.ProdutoEntity;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProdutoResponse {
    private String nome;
    private BigDecimal valor;

    public ProdutoResponse(ProdutoEntity produto) {
        this.nome = produto.getNome();
        this.valor = produto.getValor();
    }
}
