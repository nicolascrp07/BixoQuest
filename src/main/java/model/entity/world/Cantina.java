package main.java.model.entity.world;

import main.java.model.entity.character.Jogador;
import main.java.model.exception.SaldoInsuficienteException;

import java.util.ArrayList;

// Cantina da universidade
public class Cantina extends Local {

    private ArrayList<String> cardapio; // Lanches ofertados
    private int tamanhoFila;            // Quantidade de pessoas da fila
    private double valorLanche;         // Preço cobrado por um lanche

    // Constrói a cantina
    public Cantina(String nome, String descricao, ArrayList<String> cardapio, int tamanhoFila, double valorLanche) {
        super(nome, descricao);
        this.cardapio = cardapio;
        this.tamanhoFila = tamanhoFila;
        this.valorLanche = valorLanche;
    }

    private void comprarLanche(Jogador jogador) throws SaldoInsuficienteException {
        if (jogador.getDinheiro() >= valorLanche) {
            jogador.setDinheiro(jogador.getDinheiro() - valorLanche);
            jogador.setMotivacao(jogador.getMotivacao() + 15);
        } else {
            throw new SaldoInsuficienteException("Você não tem dinheiro para esse lanche!");
        }
    }

    @Override
    public void acaoEspecifica(Jogador j) throws SaldoInsuficienteException {
        this.comprarLanche(j);
    }
}