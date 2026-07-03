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

        if (!pb.isEmpty()) return;

        // Falas para Professores de Algoritmos
        ArrayList<String> falasAlgoritmos = new ArrayList<>();
        falasAlgoritmos.add("Vocês já leram o problema dessa semana? O tutorial não se resolve sozinho e a fase 1 já está no fim!");
        falasAlgoritmos.add("Lembrem-se: a arquitetura MVC não é uma sugestão carinhosa, é um requisito básico para a manutenibilidade.");
        falasAlgoritmos.add("O código compila, mas não faz o que o edital pede. Engenharia não é tentativa e erro, é planejamento.");
        falasAlgoritmos.add("Quem implementou essa persistência de dados em arquivos sem tratar as exceções? Zero para a dupla.");
        falasAlgoritmos.add("Ponteiros em C são como a vida universitária: um passo em falso para o lugar errado e você tem um 'Segmentation Fault'.");
        falasAlgoritmos.add("Muito bem, a lógica desse Service está coesa. Mas cadê a cobertura de testes de unidade dessa classe?");
        falasAlgoritmos.add("Não deixem para escrever o relatório do formato SBC no domingo à noite. A formatação vai devorar a alma de vocês.");

        // Falas para Professores de Hardware
        ArrayList<String> falasHardware = new ArrayList<>();
        falasHardware.add("Vocês acham que a CPU faz mágica? Tudo se resume a transistores e portas lógicas. Revisem o mapa de Karnaugh!");
        falasHardware.add("Se vocês ligarem o VCC no GND novamente na protoboard, eu vou reprovar a bancada inteira por tentativa de incêndio!");
        falasHardware.add("A arquitetura de Von Neumann não vai perdoar esse gargalo de memória que vocês criaram nesse projeto.");
        falasHardware.add("Lembrem-se do Teorema de Nyquist! Não adianta amostrar o sinal de qualquer jeito e esperar que o áudio não fique distorcido.");
        falasHardware.add("O osciloscópio não mente. Se a onda está quadrada quando deveria ser senoidal, o seu circuito está chorando.");
        falasHardware.add("Cuidado com a estática. Uma mão desavisada e aquele CI que custou caro vai direto pro lixo.");
        falasHardware.add("Alguém mediu a corrente antes de ligar a fonte? O cheiro de componente queimado me diz que não...");

        // Falas para Professores de Exatas
        ArrayList<String> falasExatas = new ArrayList<>();
        falasExatas.add("A prova surpresa de hoje vai separar os que sabem integrar por partes dos que choram no limite.");
        falasExatas.add("Se o determinante dessa matriz der zero, o sistema não tem solução única, e a esperança de vocês também não.");
        falasExatas.add("Lógica proposicional é a base de tudo. Se (A e B) é falso, pelo amor de De Morgan, revisem a tabela verdade!");
        falasExatas.add("Na probabilidade, a chance de vocês passarem sem estudar é um evento mutuamente excludente com a realidade.");
        falasExatas.add("Quero ver quem consegue resolver essa Equação Diferencial de segunda ordem homogênea sem olhar no apêndice do livro.");
        falasExatas.add("Não esqueçam a constante 'C' no final da integral indefinida, ou eu vou descontar meio ponto de cada um!");
        falasExatas.add("Isso é geometria analítica básica! O produto vetorial não comuta, parem de inverter o sinal!");

        // Professores de Algoritmos
        this.salvar(new Professor("Claudênia Plinda",            null, falasAlgoritmos));
        this.salvar(new Professor("Pamelinda Cortizona",         null, falasAlgoritmos));
        this.salvar(new Professor("Biancarlota Santalinda",      null, falasAlgoritmos));
        this.salvar(new Professor("Gabriela Peixolinda",         null, falasAlgoritmos));

        // Professores de Hardware
        this.salvar(new Professor("Anfransérgio Diastronho",     null, falasHardware));
        this.salvar(new Professor("Joãoberto Boscolino",         null, falasHardware));
        this.salvar(new Professor("Delmarvilho Brogliovski",     null, falasHardware));
        this.salvar(new Professor("Ângelo Duartênis",            null, falasHardware));

        // Professores de Exatas
        this.salvar(new Professor("Jaquelândia Sintrônica",      null, falasExatas));
        this.salvar(new Professor("Geraldoncio Assislânio",      null, falasExatas));
        this.salvar(new Professor("Cristianópolis Mascarenhudo", null, falasExatas));
        this.salvar(new Professor("Ademaksonildo Araujástico",   null, falasExatas));
    }
}