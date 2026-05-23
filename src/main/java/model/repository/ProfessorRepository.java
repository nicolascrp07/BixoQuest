package main.java.model.repository;

import main.java.model.entity.character.Professor;

import java.util.ArrayList;

// Repositório responsável por armazenar e gerenciar os professores do jogo
public class ProfessorRepository {

    private ArrayList<Professor> pb = new ArrayList<>(); // Base de dados dos professores

    // Salva o professor se ele ainda não estiver cadastrado | Retorna null caso contrário
    public Professor salvar(Professor professor) {
        if (!pb.contains(professor)) {
            pb.add(professor);
            return professor;
        }
        return null;
    }

    // Retorna uma cópia da lista com todos os professores cadastrados
    public ArrayList<Professor> buscarTodos() {
        return new ArrayList<>(pb);
    }

    // Busca um professor pelo nome
    public Professor buscarPorNome(String nome) {
        for (Professor d : pb) {
            if (d.getNome().equalsIgnoreCase(nome)) {
                return d;
            }
        }
        return null; // Nenhum professor encontrado
    }

    // Remove o professor do repositório e retorna true se a operação for realizada
    public boolean deletar(Professor professor) {
        return pb.remove(professor);
    }

    // Instancia e salva todos os professores do jogo
    public void criarProfessores() {

        // Instancia a lista de falas dos professores no modo clássico
        ArrayList<String> falas = new ArrayList<>();
        falas.add("Vocês já leram o problema dessa semana? O tutorial não se resolve sozinho e a fase 1 já está no fim!");
        falas.add("Lembrem-se: a arquitetura MVC não é uma sugestão carinhosa, é um requisito básico para a manutenibilidade do seu software.");
        falas.add("O código compila, mas não faz o que o edital pede. Engenharia não é tentativa e erro, é planejamento.");
        falas.add("Quem implementou essa persistência de dados em arquivos .txt sem tratar as exceções de I/O? Zero para a dupla.");
        falas.add("Ponteiros em C são como a vida universitária: um passo em falso apontando para o lugar errado e você tem um 'Segmentation Fault'.");
        falas.add("A prova surpresa de hoje vai separar os algoritmos otimizados dos laços infinitos. Guardem os celulares.");
        falas.add("Muito bem, a lógica desse Service está coesa. Mas cadê a cobertura de testes de unidade dessa classe?");
        falas.add("Não deixem para escrever o relatório do formato SBC no domingo à noite. A formatação vai devorar a alma de vocês.");

        // Professoras de Algoritmos
        this.salvar(new Professor("Claudênia Plinda",            null, falas));
        this.salvar(new Professor("Pamelinda Cortizona",         null, falas));
        this.salvar(new Professor("Biancarlota Santalinda",      null, falas));
        this.salvar(new Professor("Gabriela Peixolinda",         null, falas));

        // Professores de Hardware
        this.salvar(new Professor("Anfransérgio Diastronho",     null, falas));
        this.salvar(new Professor("Joãoberto Boscolino",         null, falas));
        this.salvar(new Professor("Delmarvilho Brogliovski",     null, falas));
        this.salvar(new Professor("Ângelo Duartênis",            null, falas));

        // Professores de Exatas
        this.salvar(new Professor("Jaquelândia Sintrônica",      null, falas));
        this.salvar(new Professor("Geraldoncio Assislânio",      null, falas));
        this.salvar(new Professor("Cristianópolis Mascarenhudo", null, falas));
        this.salvar(new Professor("Ademaksonildo Araujástico",   null, falas));
    }
}