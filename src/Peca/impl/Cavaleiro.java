package Peca.impl;

import Peca.Peca;
import Peca.Cor;
import tabuleiro.Casa;
import Peca.TipoPeca;
import tabuleiro.Tabuleiro;

public class Cavaleiro implements Peca {
    private int y, x;
    private Cor cor;

    public Cavaleiro(int y, int x, Cor cor) {
        this.y = y;
        this.x = x;
        this.cor = cor;
    }

    public Cavaleiro() {
    }

    @Override
    public Cor getCor() {
        return cor;
    }

    @Override
    public TipoPeca getTipo() {
        return TipoPeca.CAVALEIRO;
    }

    @Override
    public boolean podeCapturar(Casa origem, Casa destino, Tabuleiro tabuleiro){
        int dx = Math.abs(destino.getX() - origem.getX());
        int dy = Math.abs(destino.getY() - origem.getY());

        boolean movimentoL = (dx == 2 && dy == 1) || (dx == 1 && dy == 2);

        return movimentoL && destino.getPeca() != null && destino.getPeca().getCor() != this.getCor();
    }

    @Override
    public boolean podeMover(Casa origem, Casa destino, Tabuleiro tabuleiro) {
        int dx = Math.abs(destino.getX() - origem.getX());
        int dy = Math.abs(destino.getY() - origem.getY());

        boolean movimentoL = (dx == 2 && dy == 1) || (dx == 1 && dy == 2);

        return movimentoL && destino.getPeca() == null;
    }


    public int getY() {
        return y;
    }

    @Override
    public boolean promover(Casa destino, Tabuleiro tabuleiro){
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
