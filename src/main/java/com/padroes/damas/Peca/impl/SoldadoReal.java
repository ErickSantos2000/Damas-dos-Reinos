package com.padroes.damas.Peca.impl;

import com.padroes.damas.Peca.Cor;
import com.padroes.damas.Peca.TipoPeca;
import com.padroes.damas.tabuleiro.Casa;
import com.padroes.damas.tabuleiro.Tabuleiro;

public class SoldadoReal extends Soldado {

    public SoldadoReal(int y, int x, Cor cor) {
        super(y, x, cor);
    }

    public SoldadoReal() {
        super();
    }

    @Override
    public TipoPeca getTipo() {
        return TipoPeca.SOLDADO_REAL;
    }

    @Override
    public boolean podeMover(Casa origem, Casa destino, Tabuleiro tabuleiro) {
        int dx = destino.getX() - origem.getX();
        int dy = destino.getY() - origem.getY();

        return  Math.abs(dx) == 1
                && Math.abs(dy) == 1
                && destino.getPeca() == null;

    }

    @Override
    public boolean podeCapturar(Casa origem, Casa destino, Tabuleiro tabuleiro) {
        int dx = destino.getX() - origem.getX();
        int dy = destino.getY() - origem.getY();

        if (Math.abs(dx) != 2 || Math.abs(dy) != 2 || destino.getPeca() != null) {
            return false;
        }

        Casa meio = tabuleiro.getCasa(
                origem.getX() + dx / 2,
                origem.getY() + dy / 2
        );

        return meio.possuiPecaInimiga(getCor());

    }
}
