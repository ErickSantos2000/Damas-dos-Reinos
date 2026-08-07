import com.padroes.damas.Jogador.GerenciadorTurno;
import com.padroes.damas.Jogo.Jogo;
import com.padroes.damas.Peca.Cor;
import com.padroes.damas.Peca.Peca;
import com.padroes.damas.Peca.TipoPeca;
import com.padroes.damas.Peca.impl.Cavaleiro;
import com.padroes.damas.Peca.impl.Mago;
import com.padroes.damas.Peca.impl.Soldado;
import com.padroes.damas.Peca.impl.SoldadoReal;
import com.padroes.damas.tabuleiro.Casa;
import com.padroes.damas.tabuleiro.Tabuleiro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTeste {

    private Jogo jogo;
    private Tabuleiro tabuleiro;

    @BeforeEach
    void setUp() {
        jogo = new Jogo(false);
        tabuleiro = jogo.getTabuleiro();
        limparTabuleiro();
    }

    // ---------- Testes de Movimento ----------

    @Test
    void soldadoMoveUmaCasaDiagonalParaFrente() {
        posicionarPeca(5, 0, new Soldado(5, 0, Cor.BRANCA));
        assertTrue(jogo.mover(5, 0, 4, 1));
        Casa destino = tabuleiro.getCasa(4, 1);
        assertTrue(destino.temPeca());
        assertEquals(TipoPeca.SOLDADO, destino.getPeca().getTipo());
        assertEquals(Cor.BRANCA, destino.getPeca().getCor());
        assertFalse(tabuleiro.getCasa(5, 0).temPeca());
    }

    @Test
    void soldadoNaoMoveParaTras() {
        posicionarPeca(2, 1, new Soldado(2, 1, Cor.PRETA));
        assertFalse(jogo.mover(2, 1, 1, 0));
        assertTrue(tabuleiro.getCasa(2, 1).temPeca());
    }

    @Test
    void soldadoNaoMoveMaisDeUmaCasa() {
        posicionarPeca(5, 0, new Soldado(5, 0, Cor.BRANCA));
        assertFalse(jogo.mover(5, 0, 3, 2));
        assertTrue(tabuleiro.getCasa(5, 0).temPeca());
    }

    @Test
    void cavaleiroMoveEmL() {
        posicionarPeca(4, 0, new Cavaleiro(4, 0, Cor.BRANCA));
        assertTrue(jogo.mover(4, 0, 2, 1));
        assertTrue(tabuleiro.getCasa(2, 1).temPeca());
        assertFalse(tabuleiro.getCasa(4, 0).temPeca());
    }

    @Test
    void cavaleiroNaoMoveEmDiagonal() {
        posicionarPeca(4, 0, new Cavaleiro(4, 0, Cor.BRANCA));
        assertFalse(jogo.mover(4, 0, 3, 1));
        assertTrue(tabuleiro.getCasa(4, 0).temPeca());
    }

    @Test
    void magoMoveQuantasCasasQuiserDiagonal() {
        posicionarPeca(5, 0, new Mago(5, 0, Cor.BRANCA));
        assertTrue(jogo.mover(5, 0, 2, 3));
        assertTrue(tabuleiro.getCasa(2, 3).temPeca());
        assertFalse(tabuleiro.getCasa(5, 0).temPeca());
    }

    @Test
    void magoNaoPulaPeca() {
        posicionarPeca(5, 0, new Mago(5, 0, Cor.BRANCA));
        posicionarPeca(4, 1, new Soldado(4, 1, Cor.PRETA));
        assertFalse(jogo.mover(5, 0, 3, 2));
        assertTrue(tabuleiro.getCasa(5, 0).temPeca());
    }

    @Test
    void soldadoRealMoveEmQualquerDiagonal() {
        posicionarPeca(4, 0, new SoldadoReal(4, 0, Cor.BRANCA));
        assertTrue(jogo.mover(4, 0, 5, 1));
        assertTrue(tabuleiro.getCasa(5, 1).temPeca());
        assertFalse(tabuleiro.getCasa(4, 0).temPeca());
    }

    // ---------- Testes de Captura ----------

    @Test
    void soldadoCapturaPorSalto() {
        posicionarPeca(4, 0, new Soldado(4, 0, Cor.BRANCA));
        posicionarPeca(3, 1, new Soldado(3, 1, Cor.PRETA));
        assertTrue(jogo.mover(4, 0, 2, 2));
        assertFalse(tabuleiro.getCasa(3, 1).temPeca());
        assertTrue(tabuleiro.getCasa(2, 2).temPeca());
        assertEquals(Cor.BRANCA, tabuleiro.getCasa(2, 2).getPeca().getCor());
    }

    @Test
    void soldadoNaoCapturaSemPecaNoMeio() {
        posicionarPeca(4, 0, new Soldado(4, 0, Cor.BRANCA));
        assertFalse(jogo.mover(4, 0, 2, 2));
        assertTrue(tabuleiro.getCasa(4, 0).temPeca());
    }

    @Test
    void cavaleiroCapturaPorOcupacao() {
        posicionarPeca(4, 0, new Cavaleiro(4, 0, Cor.BRANCA));
        posicionarPeca(2, 1, new Soldado(2, 1, Cor.PRETA));
        assertTrue(jogo.mover(4, 0, 2, 1));
        assertTrue(tabuleiro.getCasa(2, 1).temPeca());
        assertEquals(Cor.BRANCA, tabuleiro.getCasa(2, 1).getPeca().getCor());
        assertEquals(TipoPeca.CAVALEIRO, tabuleiro.getCasa(2, 1).getPeca().getTipo());
    }

    @Test
    void cavaleiroNaoPodeMoverParaCasaOcupadaPorAliado() {
        posicionarPeca(4, 0, new Cavaleiro(4, 0, Cor.BRANCA));
        posicionarPeca(2, 1, new Soldado(2, 1, Cor.BRANCA));
        assertFalse(jogo.mover(4, 0, 2, 1));
        assertTrue(tabuleiro.getCasa(4, 0).temPeca());
        assertTrue(tabuleiro.getCasa(2, 1).temPeca());
    }

    // ANALISAR O MÉTODO ABAIXO:
    @Test
    void magoCapturaADistancia() {
        posicionarPeca(5, 0, new Mago(5, 0, Cor.BRANCA));
        posicionarPeca(3, 2, new Soldado(3, 2, Cor.PRETA));
        assertTrue(jogo.mover(5, 0, 3, 2));
        assertFalse(tabuleiro.getCasa(3, 2).temPeca());
        assertTrue(tabuleiro.getCasa(5, 0).temPeca());
        assertEquals(TipoPeca.MAGO, tabuleiro.getCasa(5, 0).getPeca().getTipo());
    }

    @Test
    void magoNaoCapturaSeHouverPecaPropriaNoCaminho() {
        posicionarPeca(5, 0, new Mago(5, 0, Cor.BRANCA));
        posicionarPeca(4, 1, new Soldado(4, 1, Cor.BRANCA));
        posicionarPeca(3, 2, new Soldado(3, 2, Cor.PRETA));
        assertFalse(jogo.mover(5, 0, 3, 2));
        assertTrue(tabuleiro.getCasa(5, 0).temPeca());
        assertTrue(tabuleiro.getCasa(3, 2).temPeca());
    }

    @Test
    void soldadoRealCapturaEmQualquerDirecao() {
        posicionarPeca(4, 0, new SoldadoReal(4, 0, Cor.BRANCA));
        posicionarPeca(3, 1, new Soldado(3, 1, Cor.PRETA));
        assertTrue(jogo.mover(4, 0, 2, 2));
        assertFalse(tabuleiro.getCasa(3, 1).temPeca());
        assertTrue(tabuleiro.getCasa(2, 2).temPeca());
        assertEquals(Cor.BRANCA, tabuleiro.getCasa(2, 2).getPeca().getCor());
    }

    // ---------- Teste de Promoção ----------

    @Test
    void soldadoPromoveAoAlcançarUltimaFileira() {
        posicionarPeca(1, 0, new Soldado(1, 0, Cor.BRANCA));
        assertTrue(jogo.mover(1, 0, 0, 1));
        Casa casaDestino = tabuleiro.getCasa(0, 1);
        assertTrue(casaDestino.temPeca());
        assertEquals(TipoPeca.SOLDADO_REAL, casaDestino.getPeca().getTipo());
        assertEquals(Cor.BRANCA, casaDestino.getPeca().getCor());
    }

    @Test
    void soldadoPretoPromoveAoAlcançarUltimaFileira() {
        posicionarPeca(6, 1, new Soldado(6, 1, Cor.PRETA));
        jogo.getGerenciadorTurno().setVezAtual(jogo.getGerenciadorTurno().getJogador2());
        assertTrue(jogo.mover(6, 1, 7, 0));
        Casa destino = tabuleiro.getCasa(7, 0);
        assertTrue(destino.temPeca());
        assertEquals(TipoPeca.SOLDADO_REAL, destino.getPeca().getTipo());
        assertEquals(Cor.PRETA, destino.getPeca().getCor());
    }

    // ---------- Testes de Fim de Jogo ----------

    @Test
    void fimDeJogoPorEliminacao() {
        limparTabuleiro();
        posicionarPeca(4, 1, new Soldado(4, 1, Cor.PRETA));
        posicionarPeca(5, 2, new Soldado(5, 2, Cor.BRANCA));
        GerenciadorTurno gerenciador = jogo.getGerenciadorTurno();
        gerenciador.setVezAtual(gerenciador.getJogador2());
        assertTrue(jogo.mover(4, 1, 6, 3));
        assertFalse(tabuleiro.getCasa(5, 2).temPeca());
        assertTrue(jogo.isEncerrado());
        assertEquals("Reino Negro", jogo.getVencedor().getNome());
        assertEquals("todas as peças adversárias foram eliminadas", jogo.getMotivoEncerramento());
    }

    @Test
    void fimDeJogoPorAfogamento() {
        limparTabuleiro();
        posicionarPeca(1, 0, new Soldado(1, 0, Cor.BRANCA));
        posicionarPeca(2, 3, new Mago(2, 3, Cor.PRETA));
        GerenciadorTurno gerenciador = jogo.getGerenciadorTurno();
        gerenciador.setVezAtual(gerenciador.getJogador2()); // Define a vez do Reino Negro
        assertTrue(jogo.mover(2, 3, 0, 1));
        assertTrue(jogo.isEncerrado());
        assertEquals("Reino Negro", jogo.getVencedor().getNome());
        assertEquals("o adversário não possui movimentos válidos", jogo.getMotivoEncerramento());
    }

    // ---------- Teste de Captura Obrigatória ----------

    @Test
    void capturaObrigatoriaImpedeMovimentoSimples() {
        Jogo jogoObrigatorio = new Jogo(true);
        Tabuleiro tab = jogoObrigatorio.getTabuleiro();
        posicionarPeca(tab, 4, 0, new Soldado(4, 0, Cor.BRANCA));
        posicionarPeca(tab, 3, 1, new Soldado(3, 1, Cor.PRETA));
        posicionarPeca(tab, 5, 2, new Soldado(5, 2, Cor.BRANCA));
        assertFalse(jogoObrigatorio.mover(5, 2, 4, 3));
        assertTrue(tab.getCasa(5, 2).temPeca());
        assertTrue(jogoObrigatorio.mover(4, 0, 2, 2));
        assertFalse(tab.getCasa(3, 1).temPeca());
        assertTrue(tab.getCasa(2, 2).temPeca());
    }

    // ---------- Métodos auxiliares ----------

    private void posicionarPeca(int linha, int coluna, Peca peca) {
        posicionarPeca(tabuleiro, linha, coluna, peca);
    }

    private void posicionarPeca(Tabuleiro tab, int linha, int coluna, Peca peca) {
        Casa casa = tab.getCasa(linha, coluna);
        casa.colocarPeca(peca);
    }

    private void limparTabuleiro() {
        for (int l = 0; l < tabuleiro.getLinhas(); l++) {
            for (int c = 0; c < tabuleiro.getColunas(); c++) {
                tabuleiro.getCasa(l, c).removerPeca();
            }
        }
    }
}
