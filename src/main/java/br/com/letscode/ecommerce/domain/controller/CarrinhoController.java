package br.com.letscode.ecommerce.domain.controller;

import br.com.letscode.ecommerce.domain.model.entity.CarrinhoEntity;
import br.com.letscode.ecommerce.domain.model.exchange.CarrinhoResponse;
import br.com.letscode.ecommerce.domain.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("carrinho")
public class CarrinhoController {
    private final CarrinhoService service;

    public CarrinhoController(@Autowired CarrinhoService service) {
        this.service = service;
    }

    @GetMapping("{idCarrinho}")
    public ResponseEntity<CarrinhoResponse> getCarrinho(@PathVariable("idCarrinho") Long id) {
        return ResponseEntity.ok(new CarrinhoResponse(service.getCarrinho(id)));
    }

    @GetMapping
    public ResponseEntity<CarrinhoResponse> getCarrinho(@RequestParam(name = "nomeUsuario") String nomeUsuario) {
        return ResponseEntity.ok(new CarrinhoResponse(service.getCarrinhoDoUsuario(nomeUsuario)));
    }

    @PutMapping("{idCarrinho}/addItem/{idProduto}")
    public ResponseEntity<CarrinhoResponse> adicionarItem(
            @PathVariable("idCarrinho") Long idCarrinho,
            @PathVariable("idProduto") Long idProduto,
            @RequestBody Integer quantidade) {
        return ResponseEntity.ok(new CarrinhoResponse(service.inserirItemCarrinho(idCarrinho, idProduto, quantidade)));
    }

    @PutMapping("{idCarrinho}/delItem/{idProduto}")
    public ResponseEntity<CarrinhoResponse> getCarrinho(
            @PathVariable("idCarrinho") Long idCarrinho,
            @PathVariable("idProduto") Long idProduto,
            @RequestBody Integer quantidade) {
        return ResponseEntity.ok(new CarrinhoResponse(service.removerItemCarrinho(idCarrinho, idProduto, quantidade)));
    }
}
