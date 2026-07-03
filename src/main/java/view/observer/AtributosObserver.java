package main.java.view.observer;

// Interface do padrão Observer: define o contrato para telas que exibem os atributos do jogador (HUD)
public interface AtributosObserver {
    void atualizarHUD(int energia, int conhecimento, int motivacao, int saude, double dinheiro, double desempenho, int semana, int semestre, int qtdMateriasAtuais, int disciplinasPassadas, String caminhoIconeAvatar);
}