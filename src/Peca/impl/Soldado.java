package Peca.impl;

import Peca.Peca;
import Peca.Cor;
import tabuleiro.Casa;
import Peca.TipoPeca;
import tabuleiro.Tabuleiro;

public class Soldado implements Peca {

    private int y, x;
    private Cor cor;
    private Tabuleiro tabuleiro;

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
    public boolean podeCapturar(Casa origem, Casa destino){
        int dx = destino.getX() - origem.getX();
        int dy = destino.getY() - origem.getY();

        boolean avancarParaFrente = (cor == Cor.BRANCA && dy == 2) ||
                                    (cor == Cor.VERMELHA && dy == -2);


        int meioX = origem.getX() + (dx / 2);
        int meioY = origem.getY() + (dy / 2);

        Casa casaMeio = tabuleiro.getCasa(meioX, meioY);
        boolean haInimigoNoMeio = casaMeio.possuiPecaInimiga(this.getCor());

        return Math.abs(dx) == 2 && Math.abs(dy) == 2 && avancarParaFrente && haInimigoNoMeio && destino.getPeca() == null;
    }

    @Override
    public boolean podeMover(Casa origem, Casa destino) {
        int dx = destino.getX() - origem.getX();
        int dy = destino.getY() - origem.getY();

        boolean avancarParaFrente =
                (cor == Cor.BRANCA && dy == 1) ||
                        (cor == Cor.VERMELHA && dy == -1);

        return Math.abs(dx) == 1 && Math.abs(dy) == 1 && avancarParaFrente && destino.getPeca() == null;
    }

    public int getY() {
        return y;
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
