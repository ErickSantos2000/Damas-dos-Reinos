package Peca.impl;

import Peca.Cor;
import Peca.TipoPeca;
import tabuleiro.Casa;

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
    public boolean podeMover(Casa origem, Casa destino) {
        int dx = destino.getX() - origem.getX();
        int dy = destino.getY() - origem.getY();

        return  Math.abs(dx) == 1
                && Math.abs(dy) == 1
                && destino.getPeca() == null;

    }

    @Override
    public boolean podeCapturar(Casa origem, Casa destino) {
        int dx = destino.getX() - origem.getX();
        int dy = destino.getY() - origem.getY();

        if (Math.abs(dx) != 2 || Math.abs(dy) != 2 || destino.getPeca() != null) {
            return false;
        }

        Casa meio = super.casaMeio(
                origem.getX() + dx / 2,
                origem.getY() + dy / 2
        );

        return meio.possuiPecaInimiga(getCor());

    }
}
