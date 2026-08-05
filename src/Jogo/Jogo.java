package Jogo;

import Jogador.GerenciadorTurno;
import Jogador.Jogador;
import Peca.Cor;
import Peca.Peca;
import Peca.TipoPeca;
import Peca.impl.Cavaleiro;
import Peca.impl.Mago;
import Peca.impl.Soldado;
import Peca.impl.SoldadoReal;
import tabuleiro.Casa;
import tabuleiro.Tabuleiro;

import java.util.ArrayList;
import java.util.List;

public class Jogo {
    private final Tabuleiro tabuleiro;
    private final Jogador jogadorBranco;
    private final Jogador jogadorPreto;
    private final GerenciadorTurno gerenciadorTurno;
    private boolean capturaObrigatoria;

    public Jogo() {
        this.tabuleiro = new Tabuleiro(8, 8);
        this.jogadorBranco = new Jogador("Reino Branco", 0);
        this.jogadorPreto = new Jogador("Reino Preto", 0);
        this.gerenciadorTurno = new GerenciadorTurno(jogadorBranco, jogadorPreto, jogadorBranco);
        this.capturaObrigatoria = false;

        inicializarCasas();
        inicializarPecas();
    }

    private void inicializarCasas() {
        for (int linha = 0; linha < tabuleiro.getLinhas(); linha++) {
            for (int coluna = 0; coluna < tabuleiro.getColunas(); coluna++) {
                Casa casa = tabuleiro.getCasa(linha, coluna);
                casa.setX(linha);
                casa.setY(coluna);
            }
        }
    }

    private void inicializarPecas() {
        List<Peca> pecasBrancas = new ArrayList<>();
        List<Peca> pecasVermelhas = new ArrayList<>();

        // vermelhas
        pecasVermelhas.add(new Mago(0, 3, Cor.VERMELHA));
        pecasVermelhas.add(new Mago(1, 4, Cor.VERMELHA));
        pecasVermelhas.add(new Mago(2, 3, Cor.VERMELHA));
        pecasVermelhas.add(new Cavaleiro(0, 0, Cor.VERMELHA));
        pecasVermelhas.add(new Cavaleiro(1, 1, Cor.VERMELHA));
        pecasVermelhas.add(new Cavaleiro(2, 0, Cor.VERMELHA));
        pecasVermelhas.add(new Soldado(1, 7, Cor.VERMELHA));
        pecasVermelhas.add(new Soldado(2, 6, Cor.VERMELHA));
        pecasVermelhas.add(new Soldado(0, 6, Cor.VERMELHA));

        pecasBrancas.add(new Mago(6, 3, Cor.BRANCA));
        pecasBrancas.add(new Mago(5, 4, Cor.BRANCA));
        pecasBrancas.add(new Mago(7, 4, Cor.BRANCA));
        pecasBrancas.add(new Soldado(6, 6, Cor.BRANCA));
        pecasBrancas.add(new Soldado(5, 7, Cor.BRANCA));
        pecasBrancas.add(new Soldado(7, 7, Cor.BRANCA));
        pecasBrancas.add(new Cavaleiro(5, 1, Cor.BRANCA));
        pecasBrancas.add(new Cavaleiro(6, 0, Cor.BRANCA));
        pecasBrancas.add(new Cavaleiro(7, 1, Cor.BRANCA));

        posicionarPecas(pecasBrancas);
        posicionarPecas(pecasVermelhas);
    }

    private void posicionarPecas(List<Peca> pecas) {
        for (Peca peca : pecas) {
            vincularTabuleiro(peca);
            tabuleiro.getCasa(peca.getY(), peca.getX()).colocarPeca(peca);
        }
    }

    private void vincularTabuleiro(Peca peca) {
        peca.setTabuleiro(tabuleiro);
    }

    public boolean mover(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino) {
        Casa origem = tabuleiro.getCasa(linhaOrigem, colunaOrigem);
        Casa destino = tabuleiro.getCasa(linhaDestino, colunaDestino);

        if (origem == null || destino == null || origem.getPeca() == null) {
            return false;
        }

        Peca peca = origem.getPeca();
        Jogador vez = gerenciadorTurno.getVez();

        if (vez == jogadorBranco && peca.getCor() != Cor.BRANCA) {
            return false;
        }

        if (vez == jogadorPreto && peca.getCor() != Cor.VERMELHA) {
            return false;
        }

        if (destino.getPeca() != null && destino.getPeca().getCor() == peca.getCor()) {
            return false;
        }

        boolean destinoVazio = destino.getPeca() == null;
        boolean capturaPorSalto = destinoVazio && peca.podeCapturar(origem, destino);
        boolean movimentoValido = destinoVazio
                ? peca.podeMover(origem, destino) || capturaPorSalto
                : peca.podeCapturar(origem, destino);

        if (!movimentoValido) {
            return false;
        }

        if (capturaPorSalto) {
            removerPecaCapturadaNoSalto(origem, destino);
        }

        origem.removerPeca();
        destino.colocarPeca(peca);
        promoverSoldadoSeNecessario(destino);
        gerenciadorTurno.mudarTurno();
        verificarEstadoDoJogo();
        return true;
    }

    private void removerPecaCapturadaNoSalto(Casa origem, Casa destino) {
        int meioX = origem.getX() + (destino.getX() - origem.getX()) / 2;
        int meioY = origem.getY() + (destino.getY() - origem.getY()) / 2;
        tabuleiro.getCasa(meioX, meioY).removerPeca();
    }

    private void promoverSoldadoSeNecessario(Casa destino) {
        Peca peca = destino.getPeca();

        if (peca == null || peca.getTipo() != TipoPeca.SOLDADO) {
            return;
        }

        boolean chegouAoFim = (peca.getCor() == Cor.BRANCA && destino.getX() == 0)
                || (peca.getCor() == Cor.VERMELHA && destino.getX() == tabuleiro.getLinhas() - 1);

        if (chegouAoFim) {
            SoldadoReal soldadoReal = new SoldadoReal(destino.getY(), destino.getX(), peca.getCor());
            vincularTabuleiro(soldadoReal);
            destino.colocarPeca(soldadoReal);
        }
    }

    private void verificarEstadoDoJogo() {
        if (!existePecaDoTipo(Cor.BRANCA)) {
            return;
        }

        if (!existePecaDoTipo(Cor.VERMELHA)) {
            return;
        }
    }

    private boolean existePecaDoTipo(Cor cor) {
        for (int linha = 0; linha < tabuleiro.getLinhas(); linha++) {
            for (int coluna = 0; coluna < tabuleiro.getColunas(); coluna++) {
                Casa casa = tabuleiro.getCasa(linha, coluna);
                if (casa.getPeca() != null && casa.getPeca().getCor() == cor) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCapturaObrigatoria() {
        return capturaObrigatoria;
    }

    public void setCapturaObrigatoria(boolean capturaObrigatoria) {
        this.capturaObrigatoria = capturaObrigatoria;
    }

    public Jogador getJogadorBranco() {
        return jogadorBranco;
    }

    public Jogador getJogadorPreto() {
        return jogadorPreto;
    }

    public GerenciadorTurno getGerenciadorTurno() {
        return gerenciadorTurno;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public TipoPeca getTipoPeca(Casa casa) {
        if (casa == null || casa.getPeca() == null) {
            return null;
        }
        return casa.getPeca().getTipo();
    }
}
