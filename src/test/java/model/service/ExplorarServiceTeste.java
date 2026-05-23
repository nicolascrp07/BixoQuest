package test.java.model.service;

import main.java.model.entity.academic.Avaliacao;
import main.java.model.entity.academic.Disciplina;
import main.java.model.entity.character.*;
import main.java.model.entity.world.*;
import main.java.model.repository.QuestRepository;
import main.java.model.service.AcademicoService;
import main.java.model.service.ExplorarService;
import main.java.model.service.QuestService;
import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;

import static org.junit.Assert.*;

// Testa o service de explorar e as suas regras de negócio
public class ExplorarServiceTeste {
    private ExplorarService explorarService;
    private AcademicoService academicoService;
    private Universidade universidade;
    private Cantina cantina;
    private PontoDeOnibus pontoDeOnibus;
    private Sala salaAlgoritmos;
    private Sala salaExatas;
    private LEDS leds;
    private Colegiado colegiado;
    private Jogador jogador;
    private Animal cachorro;
    private Animal gato;
    private Professor profExatas;
    private Professor profAlgoritmos;
    private Professor profHardware;
    private Colega colega1;
    private Colega colega2;
    private Disciplina dAlg1;
    private Disciplina dExt1;
    private Disciplina dHw1;
    private Disciplina dAlg2;
    private Disciplina dExt2;
    private Disciplina dHw2;
    private ArrayList<Disciplina> grade;

    // Constrói o sistema de teste dos locais e dos npcs antes de cada teste
    @Before
    public void setUp() {
        Avaliacao av = new Avaliacao("Av", 0, 0.0);
        dAlg1 = new Disciplina("Alg1", AcademicoService.ALGORITMOS, null, null, 0.0, 60, false, av);
        dExt1 = new Disciplina("Ext1", AcademicoService.EXATAS, null, null, 0.0, 60, false, av);
        dHw1  = new Disciplina("Hw1", AcademicoService.HARDWARE, null, null, 0.0, 60, false, av);
        dAlg2 = new Disciplina("Alg1", AcademicoService.ALGORITMOS, null, dAlg1, 0.0, 60, false, av);
        dExt2 = new Disciplina("Ext1", AcademicoService.EXATAS, null, dExt1, 0.0, 60, false, av);
        dHw2  = new Disciplina("Hw1", AcademicoService.HARDWARE, null, dHw1, 0.0, 60, false, av);

        grade = new ArrayList<>();
        grade.add(dAlg1);
        grade.add(dExt1);
        grade.add(dHw1);
        grade.add(dAlg2);
        grade.add(dExt2);
        grade.add(dHw2);

        salaAlgoritmos = new Sala("Sala Algoritmos", "Sala de algoritmos", dAlg1);
        salaExatas     = new Sala("Sala Exatas", "Sala de exatas", dExt1);
        leds           = new LEDS("LEDS", "Laboratório", dHw1);
        cantina        = new Cantina("Cantina", "Cantina", new ArrayList<>(), 0, 5.0);
        colegiado      = new Colegiado("Colegiado", "Colegiado");
        pontoDeOnibus  = new PontoDeOnibus("Ponto", "Ponto de ônibus", "Linha 087");

        ArrayList<String> falas = new ArrayList<>();
        falas.add("Fala");

        profAlgoritmos = new Professor("Prof Algoritmos", salaAlgoritmos, falas);
        profExatas     = new Professor("Prof Exatas", salaExatas, falas);
        profHardware   = new Professor("Prof Hardware", leds, falas);
        cachorro       = new Animal("Scooby", cantina, falas);
        gato           = new Animal("Felícia", colegiado, falas);
        colega1        = new Colega("Colega1", pontoDeOnibus, falas);
        colega2        = new Colega("Colega2", leds, falas);

        universidade = new Universidade("UEFS", "Universidade Estadual de Feira de Santana");
        universidade.getLocais().add(salaAlgoritmos);
        universidade.getLocais().add(salaExatas);
        universidade.getLocais().add(leds);
        universidade.getLocais().add(cantina);
        universidade.getLocais().add(colegiado);
        universidade.getLocais().add(pontoDeOnibus);
        universidade.getPersonagens().add(profAlgoritmos);
        universidade.getPersonagens().add(profExatas);
        universidade.getPersonagens().add(profHardware);
        universidade.getPersonagens().add(cachorro);
        universidade.getPersonagens().add(gato);
        universidade.getPersonagens().add(colega1);
        universidade.getPersonagens().add(colega2);

        jogador = new Jogador("Nicolas", 100, 50, 50, 100, 50.0, 0.0, universidade);
        QuestService qs = new QuestService(new QuestRepository());
        academicoService = new AcademicoService();
        explorarService = new ExplorarService(academicoService, qs);
    }

    // Confirma que o jogador pode transitar entre locais adjacentes
    @Test
    public void jogadorPodeTransitar(){
        assertTrue(explorarService.podeTransitar(jogador, universidade));
        assertTrue(explorarService.podeTransitar(jogador, cantina));

        jogador.setLocalAtual(salaAlgoritmos);
        assertTrue(explorarService.podeTransitar(jogador, universidade));
    }

    // Confirma que o jogador não pode transitar diretamente entre dois locais sem passar pela universidade
    @Test
    public void jogadorNaoPodeTransitar(){
        jogador.setLocalAtual(salaAlgoritmos);
        assertFalse(explorarService.podeTransitar(jogador, leds));
    }

    // Interação com a cantina e efeitos nos atributos do jogador
    @Test
    public void interacaoCantina(){
        explorarService.moverPara(jogador, cantina);
        explorarService.executarAcao(jogador, jogador.getLocalAtual());
        assertEquals(45.0, jogador.getDinheiro(), 0.001);
        assertEquals(65, jogador.getMotivacao());
    }

    // Interação com salas de aula e efeitos nos atributos do jogador
    @Test
    public void interacaoSalas(){
        explorarService.moverPara(jogador, salaAlgoritmos);
        explorarService.executarAcao(jogador, jogador.getLocalAtual());
        assertEquals(85, jogador.getEnergia());
        assertEquals(60, jogador.getNivelConhecimento());

        explorarService.moverPara(jogador, universidade);
        explorarService.moverPara(jogador, salaExatas);
        explorarService.executarAcao(jogador, jogador.getLocalAtual());
        assertEquals(70, jogador.getEnergia());
        assertEquals(70, jogador.getNivelConhecimento());
    }

    // Interação com o LEDS e efeitos nos atributos do jogador
    @Test
    public void interacaoLEDS() {
        explorarService.moverPara(jogador, leds);
        explorarService.executarAcao(jogador, jogador.getLocalAtual());
        assertEquals(85, jogador.getEnergia());
        assertEquals(60, jogador.getNivelConhecimento());
    }

    // Interação com o colegiado e efeitos nos atributos do jogador
    @Test
    public void interacaoColegiado(){
        explorarService.moverPara(jogador, colegiado);
        explorarService.executarAcao(jogador, jogador.getLocalAtual());
        assertEquals(100, jogador.getEnergia());
        assertEquals(70, jogador.getMotivacao());
        assertEquals(55, jogador.getNivelConhecimento());
    }

    // Interação com o ponto de ônibus e efeitos nos atributos do jogador
    @Test
    public void interacaoPontodeOnibus(){
        explorarService.moverPara(jogador, pontoDeOnibus);
        explorarService.executarAcao(jogador, jogador.getLocalAtual());
        assertEquals(100, jogador.getEnergia());
    }

    // Confirma que a interação com o ponto de ônibus sinaliza ao controller para avançar a semana
    @Test
    public void gatilhoAvancarSemanaController(){
        explorarService.moverPara(jogador, pontoDeOnibus);
        assertTrue(explorarService.executarAcao(jogador, jogador.getLocalAtual()));
    }

    // Interação com animais e efeitos nos atributos do jogador
    @Test
    public void interacaoAnimais(){
        explorarService.moverPara(jogador, cantina);
        explorarService.interacaoNPC(jogador, cachorro);
        assertEquals(60, jogador.getMotivacao());

        explorarService.moverPara(jogador, universidade);
        explorarService.moverPara(jogador, colegiado);
        explorarService.interacaoNPC(jogador, gato);
        assertEquals(70, jogador.getMotivacao());
        assertEquals(90, jogador.getEnergia());
    }

    // Interação com colegas e efeitos nos atributos do jogador
    @Test
    public void interacaoColegas(){
        explorarService.moverPara(jogador, pontoDeOnibus);
        explorarService.interacaoNPC(jogador, colega1);
        assertEquals(55, jogador.getMotivacao());

        explorarService.moverPara(jogador, universidade);
        explorarService.moverPara(jogador, leds);
        explorarService.interacaoNPC(jogador, colega2);
        assertEquals(60, jogador.getMotivacao());
        assertEquals(90, jogador.getEnergia());
    }

    // Interação com professor na sala e efeitos nos atributos do jogador
    @Test
    public void interacaoProfessor(){
        explorarService.moverPara(jogador, salaAlgoritmos);
        explorarService.interacaoNPC(jogador, profAlgoritmos);
        assertEquals(60, jogador.getNivelConhecimento());
    }

    // Interação com NPC fora do local dele não produz efeito nos atributos do jogador
    @Test
    public void interacaoNPCForaDoLocal(){
        explorarService.moverPara(jogador, salaExatas);
        explorarService.interacaoNPC(jogador, cachorro);
        assertNotEquals(60, jogador.getMotivacao());
    }

    // Aprovação em Algoritmos atualiza a disciplina da sala de algoritmos
    @Test
    public void atualizarMateriaSalaAlgoritmos() {
        dAlg1.setNotaFinal(10.0);
        dAlg1.verificarAprovacao();

        jogador.addDisciplina(dAlg1);
        ArrayList<Disciplina> aprovadas = academicoService.fecharSemestre(jogador);

        explorarService.atualizarSalas(aprovadas, universidade, grade);

        assertEquals(dAlg2, salaAlgoritmos.getDisciplinaAtual());
    }

    // Aprovação em Exatas atualiza a disciplina da sala de exatas
    @Test
    public void atualizarMateriaSalaExatas() {
        dExt1.setNotaFinal(10.0);
        dExt1.verificarAprovacao();

        jogador.addDisciplina(dExt1);
        ArrayList<Disciplina> aprovadas = academicoService.fecharSemestre(jogador);

        explorarService.atualizarSalas(aprovadas, universidade, grade);

        assertEquals(dExt2, salaExatas.getDisciplinaAtual());
    }

    // Aprovação em Hardware atualiza a disciplina do LEDS
    @Test
    public void atualizarMateriaLEDS() {
        dHw1.setNotaFinal(10.0);
        dHw1.verificarAprovacao();

        jogador.addDisciplina(dHw1);
        ArrayList<Disciplina> aprovadas = academicoService.fecharSemestre(jogador);

        explorarService.atualizarSalas(aprovadas, universidade, grade);

        assertEquals(dHw2, leds.getDisciplinaAtual());
    }

    // Reprovação mantém a disciplina atual
    @Test
    public void disciplinaReprovadaPermanece(){
        dHw1.setNotaFinal(3.0);
        dHw1.verificarAprovacao();

        jogador.addDisciplina(dHw1);
        ArrayList<Disciplina> aprovadas = academicoService.fecharSemestre(jogador);

        explorarService.atualizarSalas(aprovadas, universidade, grade);

        assertEquals(dHw1, leds.getDisciplinaAtual());
    }

    // Após 100 redistribuições, confirma que cada NPC está sempre em um local que pode acessar e que professores permanecem fixos nas suas salas
    @Test
    public void validadeDistribuicaoNPCS() {
        for (int i = 0; i < 100; i++){
            explorarService.atualizarLocal(universidade);
        }

        assertTrue(cachorro.podeAcessar(cachorro.getLocalAtual()));
        assertTrue(gato.podeAcessar(gato.getLocalAtual()));
        assertTrue(colega1.podeAcessar(colega1.getLocalAtual()));
        assertTrue(colega2.podeAcessar(colega2.getLocalAtual()));

        assertEquals(profAlgoritmos.getLocalAtual(), salaAlgoritmos);
        assertEquals(profExatas.getLocalAtual(), salaExatas);
        assertEquals(profHardware.getLocalAtual(), leds);
    }
}