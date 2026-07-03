package main.java.model.repository;

import java.util.ArrayList;

// DTO (Data Transfer Object) da partida para persistência de dados
public class PartidaDTO {
    private String id;
    private String nomeJogador;
    private int energia;
    private int nivelConhecimento;
    private int motivacao;
    private int saude;
    private double dinheiro;
    private double desempenhoAcademico;
    private int semanaAtual;
    private int semestreAtual;
    private String nomeLocalAtual;
    private ArrayList<String> historicoAprovadasNomes;
    private ArrayList<String> disciplinasAtuaisNomes;
    private ArrayList<String> locaisInteragidosNomes;
    private ArrayList<String> questsAtivasNomes;
    private String caminhoAvatar;
    private String caminhoIconeAvatar;

    // Constrói o DTO da partida
    public PartidaDTO(String id, String nomeJogador, int energia, int nivelConhecimento, int motivacao, int saude, double dinheiro, double desempenhoAcademico, int semanaAtual, int semestreAtual, String nomeLocalAtual, ArrayList<String> historicoAprovadasNomes, ArrayList<String> disciplinasAtuaisNomes, ArrayList<String> locaisInteragidosNomes, ArrayList<String> questsAtivasNomes, String caminhoAvatar, String caminhoIconeAvatar) {
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
        this.locaisInteragidosNomes = locaisInteragidosNomes;
        this.questsAtivasNomes = questsAtivasNomes;
        this.caminhoAvatar = caminhoAvatar;
        this.caminhoIconeAvatar = caminhoIconeAvatar;
    }

    // Getters: expõem os campos do DTO para leitura na conversão de/para Partida
    public String getId() { return id; }
    public String getNomeJogador() { return nomeJogador; }
    public int getEnergia() { return energia; }
    public int getNivelConhecimento() { return nivelConhecimento; }
    public int getMotivacao() { return motivacao; }
    public int getSaude() { return saude; }
    public double getDinheiro() { return dinheiro; }
    public double getDesempenhoAcademico() { return desempenhoAcademico; }
    public int getSemanaAtual() { return semanaAtual; }
    public int getSemestreAtual() { return semestreAtual; }
    public String getNomeLocalAtual() { return nomeLocalAtual; }
    public ArrayList<String> getHistoricoAprovadasNomes() { return historicoAprovadasNomes; }
    public ArrayList<String> getDisciplinasAtuaisNomes() { return disciplinasAtuaisNomes; }
    public ArrayList<String> getLocaisInteragidosNomes() { return locaisInteragidosNomes; }
    public ArrayList<String> getQuestsAtivasNomes() { return questsAtivasNomes; }
    public String getCaminhoAvatar() { return caminhoAvatar; }
    public String getCaminhoIconeAvatar() { return caminhoIconeAvatar; }
}