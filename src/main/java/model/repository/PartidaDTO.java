package main.java.model.repository;

import java.util.ArrayList;

// DTO (Data Transfer Object) da partida para persistência de dados
public class PartidaDTO {
    private String id;                                // Identificador da partida
    private String nomeJogador;                     // Nome do jogador
    private int energia;                            // Nível de energia do jogador
    private int nivelConhecimento;                  // Nível de conhecimento do jogador
    private int motivacao;                          // Nível de motivação do jogador
    private int saude;                              // Nível de saúde do jogador
    private double dinheiro;                        // Quantidade de dinheiro do jogador
    private double desempenhoAcademico;             // Desempenho acadêmico
    private int semanaAtual;                        // Semana atual da partida
    private int semestreAtual;                      // Semestre atual da partida
    private String nomeLocalAtual;                  // Nome do local onde o jogador está
    private ArrayList<String> historicoAprovadasNomes;   // Disciplinas já aprovadas (nomes)
    private ArrayList<String> disciplinasAtuaisNomes;    // Disciplinas atuais (nomes)

    // Constrói o DTO da partida
    public PartidaDTO(String id, String nomeJogador, int energia, int nivelConhecimento, int motivacao, int saude, double dinheiro, double desempenhoAcademico, int semanaAtual, int semestreAtual, String nomeLocalAtual, ArrayList<String> historicoAprovadasNomes, ArrayList<String> disciplinasAtuaisNomes) {
        this.id = id;
        this.nomeJogador = nomeJogador;
        this.energia = energia;
        this.nivelConhecimento = nivelConhecimento;
        this.motivacao = motivacao;
        this.saude = saude;
        this.dinheiro = dinheiro;
        this.desempenhoAcademico = desempenhoAcademico;
        this.semanaAtual = semanaAtual;
        this.semestreAtual = semestreAtual;
        this.nomeLocalAtual = nomeLocalAtual;
        this.historicoAprovadasNomes = historicoAprovadasNomes;
        this.disciplinasAtuaisNomes = disciplinasAtuaisNomes;
    }

    // Retorna o identificador da partida
    public String getId() { return id; }

    // Retorna o nome do jogador
    public String getNomeJogador() { return nomeJogador; }

    // Retorna a energia do jogador
    public int getEnergia() { return energia; }

    // Retorna o nível de conhecimento
    public int getNivelConhecimento() { return nivelConhecimento; }

    // Retorna a motivação do jogador
    public int getMotivacao() { return motivacao; }

    // Retorna a saúde do jogador
    public int getSaude() { return saude; }

    // Retorna o dinheiro do jogador
    public double getDinheiro() { return dinheiro; }

    // Retorna o desempenho acadêmico
    public double getDesempenhoAcademico() { return desempenhoAcademico; }

    // Retorna a semana atual
    public int getSemanaAtual() { return semanaAtual; }

    // Retorna o semestre atual
    public int getSemestreAtual() { return semestreAtual; }

    // Retorna o nome do local atual
    public String getNomeLocalAtual() { return nomeLocalAtual; }

    // Retorna o histórico de disciplinas aprovadas
    public ArrayList<String> getHistoricoAprovadasNomes() { return historicoAprovadasNomes; }

    // Retorna as disciplinas atuais
    public ArrayList<String> getDisciplinasAtuaisNomes() { return disciplinasAtuaisNomes; }
}