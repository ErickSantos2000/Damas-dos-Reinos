package Jogo;

import Jogador.GerenciadorTurno;
import Jogador.Jogador;
import Peca.Cor;
import Peca.Peca;
import Peca.TipoPeca;
import Peca.impl.SoldadoReal;
import tabuleiro.Casa;
import tabuleiro.Tabuleiro;

public class Jogo {
    private final Tabuleiro tabuleiro;
    private final Jogador jogadorBranco;
    private final Jogador jogadorVermelho;
    private final GerenciadorTurno gerenciadorTurno;
    private boolean capturaObrigatoria;

    public Jogo() {
        this.tabuleiro = new Tabuleiro(8, 8);
        this.jogadorBranco = new Jogador("Reino Branco", 0);
        this.jogadorVermelho = new Jogador("Reino Preto", 0);
        this.gerenciadorTurno = new GerenciadorTurno(jogadorBranco, jogadorVermelho, jogadorBranco);
        this.capturaObrigatoria = false;

    }

    public boolean mover(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino) {
        Casa origem = tabuleiro.getCasa(linhaOrigem, colunaOrigem);
        Casa destino = tabuleiro.getCasa(linhaDestino, colunaDestino);

        if (origem.getPeca() == null) {
            return false;
        }

        Peca peca = origem.getPeca();
        Jogador vez = gerenciadorTurno.getVez();

        if (vez == jogadorBranco && peca.getCor() != Cor.BRANCA) {
            return false;
        }

        if (vez == jogadorVermelho && peca.getCor() != Cor.VERMELHA) {
            return false;
        }

        if (destino.temPeca() && destino.getPeca().getCor() == peca.getCor()) {
            return false;
        }

        boolean destinoVazio = !destino.temPeca();
        boolean capturaPorSalto = destinoVazio && peca.podeCapturar(origem, destino, tabuleiro);
        boolean movimentoValido = destinoVazio
                ? peca.podeMover(origem, destino, tabuleiro) || capturaPorSalto
                : peca.podeCapturar(origem, destino, tabuleiro);

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
        peca.promover(destino, tabuleiro);
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

    public GerenciadorTurno getGerenciadorTurno() {
        return gerenciadorTurno;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }
}
