package com.padroes.damas.Peca.impl;

import com.padroes.damas.Peca.Peca;
import com.padroes.damas.Peca.Cor;
import com.padroes.damas.tabuleiro.Casa;
import com.padroes.damas.Peca.TipoPeca;
import com.padroes.damas.tabuleiro.Tabuleiro;

public class Soldado implements Peca {

    private int y, x;
    private Cor cor;

    public Soldado(int y, int x, Cor cor) {
        this.y = y;
        this.x = x;
        this.cor = cor;
    }

    public Soldado() {
    }

    @Override
    public Cor getCor() {
        return cor;
    }

    @Override
    public TipoPeca getTipo() {
        return TipoPeca.SOLDADO;
    }

    @Override
    public boolean podeCapturar(Casa origem, Casa destino, Tabuleiro tabuleiro){
        int dx = destino.getX() - origem.getX();
        int dy = destino.getY() - origem.getY();

        boolean avancarParaFrente = (cor == Cor.BRANCA && dx == -2) ||
                                    (cor == Cor.PRETA && dx == 2);


        int meioX = origem.getX() + (dx / 2);
        int meioY = origem.getY() + (dy / 2);

        Casa casaMeio = tabuleiro.getCasa(meioX, meioY);
        boolean haInimigoNoMeio = casaMeio.possuiPecaInimiga(this.getCor());

        return Math.abs(dx) == 2 && Math.abs(dy) == 2 && avancarParaFrente && haInimigoNoMeio && destino.getPeca() == null;
    }

    @Override
    public boolean podeMover(Casa origem, Casa destino, Tabuleiro tabuleiro) {
        int dx = destino.getX() - origem.getX();
        int dy = destino.getY() - origem.getY();

        boolean avancarParaFrente =
                (cor == Cor.BRANCA && dx == -1) ||
                        (cor == Cor.PRETA && dx == 1);

        return Math.abs(dx) == 1 && Math.abs(dy) == 1 && avancarParaFrente && destino.getPeca() == null;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean promover(Casa destino, Tabuleiro tabuleiro){
        Peca peca = destino.getPeca();

        if (peca == null || peca.getTipo() != TipoPeca.SOLDADO) {
            return false;
        }

        boolean chegouAoFim = (peca.getCor() == Cor.BRANCA && destino.getX() == 0)
                || (peca.getCor() == Cor.PRETA && destino.getX() == tabuleiro.getLinhas() - 1);

        if (chegouAoFim) {
            destino.colocarPeca(new SoldadoReal(destino.getY(), destino.getX(), peca.getCor()));
            return true;
        }

        return false;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

}
