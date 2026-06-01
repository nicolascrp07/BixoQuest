package main.java.view.factory;

public class MenuFactoryBase implements MenuFactory {

    @Override
    public DisplayScene construirMenuInicial() {
        return new MenuInicial();
    }

    @Override
    public DisplayScene construirMenuIniciarJogo() {
        return new MenuIniciarJogo();
    }

    @Override
    public DisplayScene construirMenuCarregarJogo() {
        return new MenuCarregarJogo();
    }

    @Override
    public DisplayScene construirMenuConfiguracoes() {
        return new MenuConfiguracoes();
    }
}