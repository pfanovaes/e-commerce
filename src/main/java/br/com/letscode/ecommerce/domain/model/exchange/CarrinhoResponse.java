package br.com.letscode.ecommerce.domain.model.exchange;

import br.com.letscode.ecommerce.domain.model.entity.CarrinhoEntity;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CarrinhoResponse {
    private Long id;
    private String nomeUsuario;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private List<ItemCarrinhoResponse> itens;
    private Double valorTotal;

    public CarrinhoResponse(CarrinhoEntity entity) {
        this.id = entity.getId();
        this.nomeUsuario = entity.getUsuario().getNome();
        this.itens = entity.getItens().stream().map(ItemCarrinhoResponse::new).collect(Collectors.toList());
        this.dataCriacao = entity.getDataCriacao();
        this.dataAtualizacao = entity.getDataAtualizacao();
        this.valorTotal = entity.getTotal().doubleValue();
    }
}
