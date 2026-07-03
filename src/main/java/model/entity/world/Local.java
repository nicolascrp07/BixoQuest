package main.java.model.entity.world;

import main.java.model.entity.character.Jogador;
import main.java.model.exception.SaldoInsuficienteException;

import java.util.ArrayList; // Importação necessária para a lista de conexões

// Classe abstrata para todos os locais do jogo
public abstract class Local {

    protected String nome;      // Nome do local
    protected String descricao; // Descrição do local
    private boolean interagiu;  // Controle de interação para evitar abusos no mesmo turno
    private ArrayList<Local> conexoes;   // Lista de adjacência (Grafo) que define para onde o jogador pode ir a partir daqui

    // Constrói o local
    public Local(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.interagiu = false;
        this.conexoes = new ArrayList<>();
    }

    // Interagir com um local consome energia e reposiciona o jogador
    public void interagir(Jogador jogador) {
        jogador.setEnergia(jogador.getEnergia() - 5);
        jogador.setLocalAtual(this);
    }

    // Define a interação específica de cada local sobre o jogador
    public abstract void acaoEspecifica(Jogador jogador) throws SaldoInsuficienteException;

    // Retorna o nome do local
    public String getNome() { return nome; }

    // Retorna se o jogador já utilizou a interação específica deste local na semana atual
    public boolean isInteragiu() {
        return interagiu;
    }

    // Atualiza o status de interação do local
    public void setInteragiu(boolean interagiu) {
        this.interagiu = interagiu;
    }

    // Retorna a lista de locais vizinhos
    public ArrayList<Local> getConexoes() {
        return conexoes;
    }

    // Adiciona um destino acessível a partir deste local
    public void addConexao(Local destino) {
        if (!this.conexoes.contains(destino)) {
            this.conexoes.add(destino);
        }
    }
}