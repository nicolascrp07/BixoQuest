package main.java.model.repository;

import main.java.model.entity.academic.Avaliacao;
import main.java.model.entity.academic.Disciplina;
import main.java.model.entity.character.Professor;

import java.util.ArrayList;

// Repositório responsável por armazenar e gerenciar as disciplinas do jogo
public class DisciplinaRepository {

    // Constantes que identificam as áreas das disciplinas
    public static final String EXATAS     = "exatas";
    public static final String ALGORITMOS = "algoritmos";
    public static final String HARDWARE   = "hardware";

    private ArrayList<Disciplina> db = new ArrayList<>(); // Base de dados das disciplinas

    // Salva a disciplina se ela ainda não estiver cadastrada | Retorna null caso contrário
    public Disciplina salvar(Disciplina disciplina) {
        if (!db.contains(disciplina)) {
            db.add(disciplina);
            return disciplina;
        }
        return null;
    }

    // Retorna uma cópia da lista com todas as disciplinas cadastradas
    public ArrayList<Disciplina> buscarTodas() {
        return new ArrayList<>(db);
    }

    // Busca uma disciplina pelo nome
    public Disciplina buscarPorNome(String nome) {
        for (Disciplina d : db) {
            if (d.getNome().equalsIgnoreCase(nome)) {
                return d;
            }
        }
        return null; // Nenhuma disciplina encontrada
    }

    // Remove a disciplina do repositório e retorna true se a operação for realizada
    public boolean deletar(Disciplina disciplina) {
        return db.remove(disciplina);
    }

    // Cria e salva toda a grade curricular do curso
    public void criarGrade(ArrayList<Professor> professores) {

        if (!db.isEmpty()) return;

        // Disciplinas de Exatas
        Disciplina preCalculo  = new Disciplina("Pré-Cálculo",                            EXATAS,     professores.get(8),  null,        0.0, 60, false, new Avaliacao("Avaliação Pré-Cálculo",                  0, 0.0));
        Disciplina calc1       = new Disciplina("Cálculo I",                               EXATAS,     professores.get(8),  preCalculo,  0.0, 60, false, new Avaliacao("Avaliação Cálculo I",                    0, 0.0));
        Disciplina discretas   = new Disciplina("Estruturas Discretas",                    EXATAS,     professores.get(9),  calc1,       0.0, 60, false, new Avaliacao("Avaliação Estruturas Discretas",         0, 0.0));
        Disciplina algVetorial = new Disciplina("Álgebra Vetorial e Geometria Analítica",  EXATAS,     professores.get(9),  discretas,   0.0, 60, false, new Avaliacao("Avaliação Álgebra Vetorial",             0, 0.0));
        Disciplina calc2       = new Disciplina("Cálculo II",                              EXATAS,     professores.get(10), algVetorial, 0.0, 60, false, new Avaliacao("Avaliação Cálculo II",                   0, 0.0));
        Disciplina eqDif       = new Disciplina("Equações Diferenciais I",                 EXATAS,     professores.get(10), calc2,       0.0, 60, false, new Avaliacao("Avaliação Equações Diferenciais I",      0, 0.0));
        Disciplina algLinear   = new Disciplina("Álgebra Linear",                          EXATAS,     professores.get(11), eqDif,       0.0, 60, false, new Avaliacao("Avaliação Álgebra Linear",               0, 0.0));
        Disciplina probEstat   = new Disciplina("Probabilidade e Estatística",             EXATAS,     professores.get(11), algLinear,   0.0, 60, false, new Avaliacao("Avaliação Probabilidade e Estatística",  0, 0.0));

        // Disciplinas de Algoritmos
        Disciplina alg1        = new Disciplina("Algoritmos e Programação I",              ALGORITMOS, professores.get(0),  null,        0.0, 60, false, new Avaliacao("Avaliação Algoritmos I",                 0, 0.0));
        Disciplina estDados    = new Disciplina("Estrutura de Dados",                      ALGORITMOS, professores.get(0),  alg1,        0.0, 60, false, new Avaliacao("Avaliação Estrutura de Dados",           0, 0.0));
        Disciplina alg2        = new Disciplina("Algoritmos e Programação II",             ALGORITMOS, professores.get(1),  estDados,    0.0, 60, false, new Avaliacao("Avaliação Algoritmos II",                0, 0.0));
        Disciplina engSoft     = new Disciplina("Engenharia de Software",                  ALGORITMOS, professores.get(1),  alg2,        0.0, 60, false, new Avaliacao("Avaliação Engenharia de Software",       0, 0.0));
        Disciplina bd          = new Disciplina("Banco de Dados",                          ALGORITMOS, professores.get(2),  engSoft,     0.0, 60, false, new Avaliacao("Avaliação Banco de Dados",               0, 0.0));
        Disciplina analProj    = new Disciplina("Análise e Projeto de Algoritmos",         ALGORITMOS, professores.get(2),  bd,          0.0, 60, false, new Avaliacao("Avaliação Análise e Projeto",            0, 0.0));
        Disciplina lingFormais = new Disciplina("Linguagens Formais e Compiladores",       ALGORITMOS, professores.get(3),  analProj,    0.0, 60, false, new Avaliacao("Avaliação Linguagens Formais",           0, 0.0));
        Disciplina compGrafica = new Disciplina("Computação Gráfica",                      ALGORITMOS, professores.get(3),  lingFormais, 0.0, 60, false, new Avaliacao("Avaliação Computação Gráfica",           0, 0.0));

        // Disciplinas de Hardware
        Disciplina circDig     = new Disciplina("Circuitos Digitais",                      HARDWARE,   professores.get(4),  null,        0.0, 60, false, new Avaliacao("Avaliação Circuitos Digitais",           0, 0.0));
        Disciplina arquComp    = new Disciplina("Arquitetura de Computadores",             HARDWARE,   professores.get(4),  circDig,     0.0, 60, false, new Avaliacao("Avaliação Arquitetura de Computadores",  0, 0.0));
        Disciplina so          = new Disciplina("Sistemas Operacionais",                   HARDWARE,   professores.get(5),  arquComp,    0.0, 60, false, new Avaliacao("Avaliação Sistemas Operacionais",        0, 0.0));
        Disciplina redes       = new Disciplina("Redes de Computadores",                   HARDWARE,   professores.get(5),  so,          0.0, 60, false, new Avaliacao("Avaliação Redes de Computadores",        0, 0.0));
        Disciplina circElet    = new Disciplina("Circuitos Elétricos",                     HARDWARE,   professores.get(6),  redes,       0.0, 60, false, new Avaliacao("Avaliação Circuitos Elétricos",          0, 0.0));
        Disciplina eletGeral   = new Disciplina("Eletrônica Geral",                        HARDWARE,   professores.get(6),  circElet,    0.0, 60, false, new Avaliacao("Avaliação Eletrônica Geral",             0, 0.0));
        Disciplina sinais      = new Disciplina("Sinais e Sistemas",                       HARDWARE,   professores.get(7),  eletGeral,   0.0, 60, false, new Avaliacao("Avaliação Sinais e Sistemas",            0, 0.0));
        Disciplina pds         = new Disciplina("Processamento Digital de Sinais",         HARDWARE,   professores.get(7),  sinais,      0.0, 60, false, new Avaliacao("Avaliação PDS",                          0, 0.0));

        // Salva todas as disciplinas
        this.salvar(preCalculo);  this.salvar(calc1);       this.salvar(discretas);
        this.salvar(algVetorial); this.salvar(calc2);       this.salvar(eqDif);
        this.salvar(algLinear);   this.salvar(probEstat);

        this.salvar(alg1);        this.salvar(estDados);    this.salvar(alg2);
        this.salvar(engSoft);     this.salvar(bd);          this.salvar(analProj);
        this.salvar(lingFormais); this.salvar(compGrafica);

        this.salvar(circDig);     this.salvar(arquComp);    this.salvar(so);
        this.salvar(redes);       this.salvar(circElet);    this.salvar(eletGeral);
        this.salvar(sinais);      this.salvar(pds);
    }
}