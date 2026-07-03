package main.java.view.controller;

import main.java.controller.BixoController;

// Interface de contrato para garantir a padronização e injeção do BixoController em todas as cenas
public interface TelaControlavel {
    void inicializarTela(BixoController bc);
}