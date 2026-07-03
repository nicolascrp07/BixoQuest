package main.java.model.repository;

import main.java.model.entity.academic.Disciplina;
import main.java.model.entity.character.Animal;
import main.java.model.entity.character.Colega;
import main.java.model.entity.world.*;

import java.util.ArrayList;

// Repositório responsável por armazenar e gerenciar a universidade do jogo
public class UniversidadeRepository {

    private ArrayList<Universidade> ub = new ArrayList<>(); // Base de dados das universidades

    // Salva a universidade se ela ainda não estiver cadastrada | Retorna null caso contrário
    public void salvar(Universidade uni) {
        if (!ub.contains(uni)) {
            ub.add(uni);
        }
    }

    // Retorna uma cópia da lista com todas as universidades cadastradas
    public ArrayList<Universidade> buscarTodos() {
        return new ArrayList<>(ub);
    }

    // Busca uma universidade pelo nome
    public Universidade buscarPorNome(String nome) {
        for (Universidade u : ub) {
            if (u.getNome().equalsIgnoreCase(nome)) {
                return u;
            }
        }
        return null; // Nenhuma universidade encontrada
    }

    // Remove a universidade do repositório e retorna true se a operação for realizada
    public boolean deletar(Universidade uni) {
        return ub.remove(uni);
    }

    // Constrói o mundo do jogo com locais, personagens e professores posicionados
    public void criarMundo(ArrayList<Disciplina> grade) {

        ArrayList<String> dialogosColegas = new ArrayList<>();
        dialogosColegas.add("Você entendeu o que é para fazer no MI de Programação? Eu li o edital três vezes e continuo em desespero...");
        dialogosColegas.add("A fila da cantina tá dando a volta no pavilhão. Desisti do salgado, vou almoçar apenas vento e ansiedade hoje.");
        dialogosColegas.add("Alguém pelo amor de Deus tem o resumo de Álgebra Linear? Se eu não passar nessa matéria, minha grade trava inteira!");
        dialogosColegas.add("Dormir pra quê, né? Fiquei acordado até as 4 da manhã caçando um 'NullPointerException' no meu código.");
        dialogosColegas.add("O ônibus da linha Uefs passou direto no ponto de novo, lotado. Vou ter que ir andando até o terminal...");
        dialogosColegas.add("Ouvi dizer que o professor de Estruturas de Dados vai aplicar a prova mais difícil do semestre amanhã. Adeus, final de semana.");
        dialogosColegas.add("Alguém tem cabo de celular para emprestar? Minha bateria está em 2% e eu preciso ver o PDF do tutorial.");
        dialogosColegas.add("Caiu exatamente a questão que a gente estudou no laboratório ontem! Acho que finalmente presenciei um milagre acadêmico!");

        ArrayList<String> dialogosScooby = new ArrayList<>();
        dialogosScooby.add("*Au au!* (O doguinho abana o rabo, feliz por não ter prazos de relatórios para cumprir)");
        dialogosScooby.add("(O cachorro caramelo do campus te olha com uma profunda e inabalável sabedoria acadêmica)");
        dialogosScooby.add("*Woof!* (O doguinho fareja a sua mochila em busca daquele salgado que você comprou na cantina)");

        ArrayList<String> dialogosFelicia = new ArrayList<>();
        dialogosFelicia.add("*Miau...* (A gatinha roça nas suas pernas, sugando todo o estresse do seu pré-prova)");
        dialogosFelicia.add("*Prrr...* (A gatinha ronrona no seu colo. Por um momento, você esquece da sua nota de Cálculo)");
        dialogosFelicia.add("(O gato pisca lentamente para você, te julgando em silêncio por não ter feito os testes de unidade)");

        Sala salaAlgoritmos = new Sala("Sala de Algoritmos", "Sala de aula de programação", grade.get(8));
        Sala salaExatas     = new Sala("Sala de Exatas",     "Sala de aula de matemática",  grade.get(0));
        LEDS leds           = new LEDS("LEDS",               "Laboratório de Eletrônica Digital e Sistemas", grade.get(16));
        Cantina cantina     = new Cantina("Cantina",         "Cantina universitária",        new ArrayList<>(), 0, 15.0);
        PontoDeOnibus ponto = new PontoDeOnibus("Ponto de Ônibus", "Ponto de ônibus do campus", "Linha 1");
        Colegiado colegiado = new Colegiado("Colegiado",     "Colegiado do curso");
        Modulo modulo3 = new Modulo("Módulo 3", "Prédio principal com as salas teóricas.");
        Modulo modulo5 = new Modulo("Módulo 5", "Prédio focado nos laboratórios práticos.");
        Modulo moduloAuxiliar = new Modulo("Módulo Auxiliar", "Prédio intermediário.");
        Modulo mapaPrincipal = new Modulo("Mapa Principal", "Módulo que reúne os demais no início do jogo.");

        Universidade uni = new Universidade("UEFS", "Universidade Estadual de Feira de Santana");

        uni.addConexao(mapaPrincipal);  mapaPrincipal.addConexao(uni);
        uni.addConexao(modulo3);     modulo3.addConexao(uni);
        uni.addConexao(modulo5);     modulo5.addConexao(uni);
        uni.addConexao(cantina);     cantina.addConexao(uni);
        uni.addConexao(colegiado);   colegiado.addConexao(uni);
        uni.addConexao(ponto);       ponto.addConexao(uni);
        uni.addConexao(moduloAuxiliar); moduloAuxiliar.addConexao(uni);
        uni.addConexao(salaAlgoritmos); salaAlgoritmos.addConexao(uni);
        uni.addConexao(salaExatas);     salaExatas.addConexao(uni);
        uni.addConexao(leds);           leds.addConexao(uni);


        mapaPrincipal.addConexao(modulo3);          modulo3.addConexao(mapaPrincipal);
        mapaPrincipal.addConexao(modulo5);          modulo5.addConexao(mapaPrincipal);
        mapaPrincipal.addConexao(moduloAuxiliar);   moduloAuxiliar.addConexao(mapaPrincipal);
        mapaPrincipal.addConexao(mapaPrincipal);

        modulo5.addConexao(salaAlgoritmos);   salaAlgoritmos.addConexao(modulo5);
        modulo5.addConexao(salaExatas);       salaExatas.addConexao(modulo5);

        modulo3.addConexao(colegiado);        colegiado.addConexao(modulo3);
        modulo3.addConexao(leds);             leds.addConexao(modulo3);

        moduloAuxiliar.addConexao(cantina);   cantina.addConexao(moduloAuxiliar);
        moduloAuxiliar.addConexao(ponto);     ponto.addConexao(moduloAuxiliar);

        uni.getLocais().add(mapaPrincipal);
        uni.getLocais().add(salaAlgoritmos);
        uni.getLocais().add(salaExatas);
        uni.getLocais().add(leds);
        uni.getLocais().add(cantina);
        uni.getLocais().add(colegiado);
        uni.getLocais().add(ponto);
        uni.getLocais().add(modulo3);
        uni.getLocais().add(modulo5);
        uni.getLocais().add(moduloAuxiliar);

        // Distribuição de Professores
        for (Disciplina d : grade) {
            switch (d.getArea()) {
                case DisciplinaRepository.ALGORITMOS -> d.getProfessor().setLocal(salaAlgoritmos);
                case DisciplinaRepository.HARDWARE -> d.getProfessor().setLocal(leds);
                case DisciplinaRepository.EXATAS -> d.getProfessor().setLocal(salaExatas);
            }
        }

        // Instanciação e Distribuição dos NPCs
        Animal gato      = new Animal("Felícia",          colegiado, dialogosFelicia);
        Animal cachorro  = new Animal("Scooby",           cantina,   dialogosScooby);
        Colega colega1   = new Colega("Arthur",      ponto,     dialogosColegas);
        Colega colega2   = new Colega("Bella", salaAlgoritmos, dialogosColegas);

        uni.getPersonagens().add(gato);
        uni.getPersonagens().add(cachorro);
        uni.getPersonagens().add(colega1);
        uni.getPersonagens().add(colega2);

        for (Disciplina d : grade) {
            if (!uni.getPersonagens().contains(d.getProfessor())) {
                uni.getPersonagens().add(d.getProfessor());
            }
        }

        this.salvar(uni);
    }
}