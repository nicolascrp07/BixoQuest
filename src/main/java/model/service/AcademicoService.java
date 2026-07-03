package main.java.model.service;

import main.java.model.entity.academic.Avaliacao;
import main.java.model.entity.academic.Disciplina;
import main.java.model.entity.character.Jogador;
import main.java.model.entity.event.Consequencia;
import main.java.model.strategy.*;
import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;

// Service responsável por todas as regras de negócio acadêmicas do jogo
public class AcademicoService {

    // Impactos predefinidos aplicados ao jogador conforme a dificuldade da avaliação
    private static final Consequencia IMPACTO_DIFICIL = new Consequencia(-15, -10, 0, 20, -15);
    private static final Consequencia IMPACTO_MEDIO   = new Consequencia(-10, -5,  0, 0, -5);
    private static final Consequencia IMPACTO_FACIL   = new Consequencia(-5,  0,   0, 10, 0);

    // Constantes que identificam as áreas das disciplinas
    public static final String EXATAS     = "exatas";
    public static final String ALGORITMOS = "algoritmos";
    public static final String HARDWARE   = "hardware";

    // Mapa que associa cada área à sua respectiva estratégia de avaliação (STRATEGY)
    private Map<String, EstrategiaAvaliacao> motorDeProvas;

    public AcademicoService() {
        motorDeProvas = new HashMap<>();
        motorDeProvas.put(EXATAS, new ProvaExatasStrategy());
        motorDeProvas.put(ALGORITMOS, new ProvaAlgoritmosStrategy());
        motorDeProvas.put(HARDWARE, new ProvaHardwareStrategy());
    }

    // Calcula a dificuldade da avaliação, aplica o desgaste correspondente no jogador e delega o cálculo da nota à estratégia da área da disciplina
    public void aplicarProva(Jogador j, Disciplina d, int acertos, int totalPerguntas) {
        Avaliacao a = d.getAvaliacao();

        this.calcularDificuldade(j, a);

        if (a.getDificuldade() == 3)      IMPACTO_DIFICIL.aplicar(j);
        else if (a.getDificuldade() == 2) IMPACTO_MEDIO.aplicar(j);
        else                              IMPACTO_FACIL.aplicar(j);

        // Seleciona a estratégia da área da disciplina e calcula a nota cruzando o desempenho no minigame com os atributos do jogador
        EstrategiaAvaliacao estrategia = motorDeProvas.get(d.getArea());
        double notaFinal = estrategia.calcularNota(acertos, totalPerguntas, j, a.getDificuldade());

        a.setNota(notaFinal);
        d.setNotaFinal(notaFinal);
    }

    // Define a dificuldade da avaliação com base no nível de conhecimento atual do jogador
    public void calcularDificuldade(Jogador j, Avaliacao a) {
        if (j.getNivelConhecimento() <= 30)      a.setDificuldade(3); // Difícil
        else if (j.getNivelConhecimento() <= 70) a.setDificuldade(2); // Médio
        else                                     a.setDificuldade(1); // Fácil
    }

    // Verifica aprovação em todas as disciplinas ativas, move as aprovadas para o histórico e atualiza o score
    public ArrayList<Disciplina> fecharSemestre(Jogador j) {
        ArrayList<Disciplina> ativas = j.getDisciplinas();
        ArrayList<Disciplina> recemAprovadas = new ArrayList<>();

        for (Disciplina d : ativas) {
            d.verificarAprovacao();
            if (d.getStatusAprovacao()) {
                recemAprovadas.add(d);
            }
        }

        for (Disciplina aprovada : recemAprovadas) {
            j.removeDisciplina(aprovada);
            j.addDisciplinaHistorico(aprovada);
        }

        this.atualizarScore(j);
        return recemAprovadas;
    }

    // Recalcula o desempenho acadêmico do jogador com base na média das notas do histórico
    public void atualizarScore(Jogador j) {
        ArrayList<Disciplina> aprovadas = j.getHistoricoAprovadas();
        if (aprovadas.isEmpty()) {
            j.setDesempenhoAcademico(0.0);
            return;
        }

        double soma = 0;
        for (Disciplina a : aprovadas) {
            soma += a.getNotaFinal();
        }
        j.setDesempenhoAcademico(soma / aprovadas.size());
    }

    // Matricula o jogador em uma disciplina de cada área, respeitando pré-requisitos e histórico
    public void matricularNovoSemestre(Jogador j, ArrayList<Disciplina> catalogo) {

        // Verifica quais áreas ainda não possuem uma disciplina ativa na grade do jogador
        boolean faltaAlgoritmos = true;
        boolean faltaHardware   = true;
        boolean faltaExatas     = true;

        for (Disciplina d : j.getDisciplinas()) {
            if (d.getArea().equals(ALGORITMOS)) faltaAlgoritmos = false;
            if (d.getArea().equals(HARDWARE))   faltaHardware   = false;
            if (d.getArea().equals(EXATAS))     faltaExatas     = false;
        }

        for (Disciplina materiaDoCatalogo : catalogo) {

            // Matricula na primeira disciplina de algoritmos elegível encontrada
            if (faltaAlgoritmos && materiaDoCatalogo.getArea().equals(ALGORITMOS)) {
                boolean jaPassou       = j.getHistoricoAprovadas().contains(materiaDoCatalogo);
                boolean temPreRequisito = (materiaDoCatalogo.getPreRequisito() == null) ||
                        (j.getHistoricoAprovadas().contains(materiaDoCatalogo.getPreRequisito()));
                if (!jaPassou && temPreRequisito) {
                    j.addDisciplina(materiaDoCatalogo);
                    faltaAlgoritmos = false;
                }
            }

            // Matricula na primeira disciplina de hardware elegível encontrada
            if (faltaHardware && materiaDoCatalogo.getArea().equals(HARDWARE)) {
                boolean jaPassou       = j.getHistoricoAprovadas().contains(materiaDoCatalogo);
                boolean temPreRequisito = (materiaDoCatalogo.getPreRequisito() == null) ||
                        (j.getHistoricoAprovadas().contains(materiaDoCatalogo.getPreRequisito()));
                if (!jaPassou && temPreRequisito) {
                    j.addDisciplina(materiaDoCatalogo);
                    faltaHardware = false;
                }
            }

            // Matricula na primeira disciplina de exatas elegível encontrada
            if (faltaExatas && materiaDoCatalogo.getArea().equals(EXATAS)) {
                boolean jaPassou       = j.getHistoricoAprovadas().contains(materiaDoCatalogo);
                boolean temPreRequisito = (materiaDoCatalogo.getPreRequisito() == null) ||
                        (j.getHistoricoAprovadas().contains(materiaDoCatalogo.getPreRequisito()));
                if (!jaPassou && temPreRequisito) {
                    j.addDisciplina(materiaDoCatalogo);
                    faltaExatas = false;
                }
            }
        }
    }
}