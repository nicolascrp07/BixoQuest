package main.java.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import main.java.controller.BixoController;
import main.java.controller.GerenciadorCenas;
import main.java.model.entity.academic.Disciplina;
import main.java.model.entity.world.AmbienteAula;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// Controla a renderização e a lógica de validação do minigame de provas
public class MinigameController implements TelaControlavel {

    @FXML private ImageView imagemDeFundo;
    @FXML private Label labelMateria;
    @FXML private Label labelPergunta;
    @FXML private VBox caixaAlternativas;

    private BixoController bixoController;
    private Disciplina disciplinaAtual;
    private Map<String, String[]> bancoDePerguntas;
    private int acertos = 0;

    // Prepara o dicionário de questões, identifica a matéria local e exibe a prova
    @Override
    public void inicializarTela(BixoController bc) {
        this.bixoController = bc;
        this.acertos = 0;

        inicializarBancoDePerguntas();

        if (bc.getLocalAtual() instanceof AmbienteAula sala) {
            this.disciplinaAtual = sala.getDisciplinaAtual();
            labelMateria.setText("PROVA DE " + disciplinaAtual.getNome().toUpperCase());
            trocarImagemDeFundo(sala.getNome());
            carregarPerguntaDaMateria();
        } else {
            GerenciadorCenas.getInstance().navegarParaLocalAtual();
        }
    }

    // Instancia os arrays de perguntas, alternativas e gabarito em memória
    private void inicializarBancoDePerguntas() {
        bancoDePerguntas = new HashMap<>();

        // Exatas
        bancoDePerguntas.put("Pré-Cálculo", new String[]{"Qual a raiz quadrada de 144?", "10", "12", "14", "16", "12"});
        bancoDePerguntas.put("Cálculo I", new String[]{"Qual a derivada de e^x?", "1", "x", "e^x", "0", "e^x"});
        bancoDePerguntas.put("Estruturas Discretas", new String[]{"Qual a negação de (A e B) por De Morgan?", "~A e ~B", "~A ou ~B", "A ou B", "A e B", "~A ou ~B"});
        bancoDePerguntas.put("Álgebra Vetorial e Geometria Analítica", new String[]{"O produto escalar de dois vetores perpendiculares é:", "1", "0", "-1", "Infinito", "0"});
        bancoDePerguntas.put("Cálculo II", new String[]{"O que calcula a integral dupla de uma função constante 1?", "Volume", "Comprimento", "Área", "Densidade", "Área"});
        bancoDePerguntas.put("Equações Diferenciais I", new String[]{"Qual a ordem da equação y'' + y = 0?", "Primeira", "Segunda", "Terceira", "Zero", "Segunda"});
        bancoDePerguntas.put("Álgebra Linear", new String[]{"Uma matriz possui inversa se o seu determinante for:", "Zero", "Positivo", "Diferente de zero", "Negativo", "Diferente de zero"});
        bancoDePerguntas.put("Probabilidade e Estatística", new String[]{"Qual a probabilidade de cair 'Cara' em uma moeda justa?", "25%", "33%", "50%", "100%", "50%"});

        // Algoritmos
        bancoDePerguntas.put("Algoritmos e Programação I", new String[]{"O que é uma variável na programação?", "Um laço de repetição", "Um espaço reservado na memória", "Um comando de saída", "Uma classe", "Um espaço reservado na memória"});
        bancoDePerguntas.put("Estrutura de Dados", new String[]{"Qual estrutura usa a lógica LIFO (Último a entrar, Primeiro a sair)?", "Fila", "Grafo", "Pilha", "Árvore", "Pilha"});
        bancoDePerguntas.put("Algoritmos e Programação II", new String[]{"São pilares da Orientação a Objetos:", "Classe, Método e Retorno", "Herança, Polimorfismo e Encapsulamento", "If, Else e While", "Pilha, Fila e Lista", "Herança, Polimorfismo e Encapsulamento"});
        bancoDePerguntas.put("Engenharia de Software", new String[]{"Qual metodologia ágil usa o conceito de 'Sprints'?", "Cascata", "Scrum", "Espiral", "Prototipação", "Scrum"});
        bancoDePerguntas.put("Banco de Dados", new String[]{"Qual comando SQL é utilizado para extrair dados de uma tabela?", "SELECT", "EXTRACT", "PULL", "GET", "SELECT"});
        bancoDePerguntas.put("Análise e Projeto de Algoritmos", new String[]{"Qual a complexidade de tempo de uma Busca Binária ideal?", "O(1)", "O(n)", "O(log n)", "O(n²)", "O(log n)"});
        bancoDePerguntas.put("Linguagens Formais e Compiladores", new String[]{"O que um Autômato Finito Determinístico (AFD) NÃO possui?", "Estados finais", "Estado inicial", "Transições vazias (épsilon)", "Alfabeto", "Transições vazias (épsilon)"});
        bancoDePerguntas.put("Computação Gráfica", new String[]{"O que é um Pixel?", "Um polígono 3D", "A menor unidade de uma imagem digital", "Um vetor matemático", "Uma textura", "A menor unidade de uma imagem digital"});

        // Hardware
        bancoDePerguntas.put("Circuitos Digitais", new String[]{"Qual porta lógica retorna 1 APENAS se as entradas forem diferentes?", "AND", "OR", "XOR", "NOT", "XOR"});
        bancoDePerguntas.put("Arquitetura de Computadores", new String[]{"Qual a função da ULA no processador?", "Armazenar arquivos", "Realizar operações lógicas e aritméticas", "Resfriar a CPU", "Gerar energia", "Realizar operações lógicas e aritméticas"});
        bancoDePerguntas.put("Sistemas Operacionais", new String[]{"O que previne o impasse (deadlock) de recursos?", "Algoritmo do Banqueiro", "Memória Virtual", "Paginação", "Escalonamento Round Robin", "Algoritmo do Banqueiro"});
        bancoDePerguntas.put("Redes de Computadores", new String[]{"Qual protocolo atribui endereços IP dinamicamente?", "DNS", "HTTP", "DHCP", "FTP", "DHCP"});
        bancoDePerguntas.put("Circuitos Elétricos", new String[]{"Pela 1ª Lei de Ohm, a Tensão (V) é igual a:", "I / R", "R * I", "R + I", "P * I", "R * I"});
        bancoDePerguntas.put("Eletrônica Geral", new String[]{"Qual componente permite a passagem de corrente em apenas um sentido?", "Capacitor", "Indutor", "Resistor", "Diodo", "Diodo"});
        bancoDePerguntas.put("Sinais e Sistemas", new String[]{"A Transformada de Fourier converte um sinal do domínio do tempo para o domínio da:", "Frequência", "Fase", "Amplitude", "Potência", "Frequência"});
        bancoDePerguntas.put("Processamento Digital de Sinais", new String[]{"O Teorema de Nyquist dita que a taxa de amostragem deve ser o dobro da:", "Tensão máxima", "Frequência máxima", "Amplitude", "Largura de banda", "Frequência máxima"});
    }

    // Aplica a imagem de fundo correspondente ao tipo de sala em que o jogador está
    private void trocarImagemDeFundo(String nomeLocal) {
        String caminho = nomeLocal.equalsIgnoreCase("LEDS") ? "/imagens/LEDSPROVA.jpeg" : "/imagens/PROVAEXATAS.jpeg";
        try {
            Image novaImagem = new Image(Objects.requireNonNull(getClass().getResourceAsStream(caminho)));
            imagemDeFundo.setImage(novaImagem);
        } catch (Exception e) {
            System.err.println("Imagem de fundo não encontrada: " + caminho);
        }
    }

    // Extrai os dados da questão no banco e injeta nos botões visuais
    private void carregarPerguntaDaMateria() {
        caixaAlternativas.getChildren().clear();
        String[] dadosPergunta = bancoDePerguntas.getOrDefault(disciplinaAtual.getNome(),
                new String[]{"Pergunta secreta para " + disciplinaAtual.getNome() + "?", "A", "B", "C", "D", "A"});

        labelPergunta.setText("Questão Única: " + dadosPergunta[0]);
        String respostaCorreta = dadosPergunta[5];

        for (int i = 1; i <= 4; i++) {
            String alternativa = dadosPergunta[i];
            Button btnAlternativa = new Button(alternativa);
            btnAlternativa.setPrefWidth(400);
            btnAlternativa.setPrefHeight(50);
            btnAlternativa.setStyle("-fx-font-size: 16px; -fx-cursor: hand;");

            btnAlternativa.setOnAction(e -> verificarResposta(alternativa, respostaCorreta));
            caixaAlternativas.getChildren().add(btnAlternativa);
        }
    }

    // Analisa a escolha do jogador e aciona a computação da nota
    private void verificarResposta(String escolhida, String correta) {
        if (escolhida.equals(correta)) acertos++;
        finalizarProva();
    }

    // Finaliza o processo da matéria e bloqueia novas provas no mesmo turno
    private void finalizarProva() {
        bixoController.finalizarMinigameProva(disciplinaAtual, acertos, 1);
        double notaCalculada = disciplinaAtual.getNotaFinal();
        String mensagem = String.format("A prova acabou!\nCom os seus acertos e atributos, sua nota final foi: %.1f", notaCalculada);

        mostrarAlerta(Alert.AlertType.INFORMATION, "Fim da Prova!", mensagem);
        GerenciadorCenas.getInstance().navegarParaLocalAtual();
    }

    // Exibe caixas de aviso para o usuário
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}