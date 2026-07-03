package main.java.model.entity.event;

import main.java.model.entity.character.Jogador;

// Efeitos que uma escolha causa sobre os atributos do jogador
public class Consequencia {

    private int efeitoEnergia;          // Efeito na energia do jogador
    private int efeitoMotivacao;        // Efeito na motivação do jogador
    private double efeitoDinheiro;      // Efeito no saldo do jogador
    private int efeitoConhecimento;     // Efeito no nível de conhecimento do jogador
    private int efeitoSaude;            // Efeito na saúde do jogador

    // Constrói a consequência
    public Consequencia(int efeitoEnergia, int efeitoMotivacao, double efeitoDinheiro,
                        int efeitoConhecimento, int efeitoSaude) {
        this.efeitoEnergia = efeitoEnergia;
        this.efeitoMotivacao = efeitoMotivacao;
        this.efeitoDinheiro = efeitoDinheiro;
        this.efeitoConhecimento = efeitoConhecimento;
        this.efeitoSaude = efeitoSaude;
    }

    // Aplica todos os efeitos desta consequência sobre o jogador
    public void aplicar(Jogador jogador) {
        jogador.setEnergia(jogador.getEnergia() + efeitoEnergia);
        jogador.setMotivacao(jogador.getMotivacao() + efeitoMotivacao);
        jogador.setDinheiro(jogador.getDinheiro() + efeitoDinheiro);
        jogador.setNivelConhecimento(jogador.getNivelConhecimento() + efeitoConhecimento);
        jogador.setSaude(jogador.getSaude() + efeitoSaude);
    }
}