package Peca.impl;

import Peca.Peca;
import Peca.Cor;
import tabuleiro.Casa;
import Peca.TipoPeca;
import tabuleiro.Tabuleiro;

public class Mago implements Peca {
    private int y, x;
    private Cor cor;
    private Tabuleiro tabuleiro;

    public Mago(int y, int x, Cor cor) {
        this.y = y;
        this.x = x;
        this.cor = cor;
    }

    public Mago() {
    }

    @Override
    public Cor getCor() {
        return cor;
    }

    @Override
    public TipoPeca getTipo() {
        return TipoPeca.MAGO;
    }

    @Override
    public boolean podeCapturar(Casa origem, Casa destino) {
        if (origem == null || destino == null || origem.equals(destino)) {
            return false;
        }

        if (destino.getPeca() == null) {
            return false;
        }

        if (destino.getPeca().getCor() == this.getCor()) {
            return false;
        }

        int dx = destino.getX() - origem.getX();
        int dy = destino.getY() - origem.getY();

        if (Math.abs(dx) != Math.abs(dy) || dx == 0) {
            return false;
        }

        int passoX = Integer.compare(dx, 0);
        int passoY = Integer.compare(dy, 0);

        int x = origem.getX() + passoX;
        int y = origem.getY() + passoY;

        while (x != destino.getX() || y != destino.getY()) {
            Casa casaAtual = tabuleiro.getCasa(x, y);

            if (casaAtual.getPeca() != null) {
                return false;
            }

            x += passoX;
            y += passoY;
        }

        return true;
    }

    @Override
    public boolean podeMover(Casa origem, Casa destino) {
        if (origem == null || destino == null || origem.equals(destino)) {
            return false;
        }

        int dx = destino.getX() - origem.getX();
        int dy = destino.getY() - origem.getY();

        if (Math.abs(dx) != Math.abs(dy) || dx == 0) {
            return false;
        }

        int passoX = Integer.compare(dx, 0);
        int passoY = Integer.compare(dy, 0);

        int x = origem.getX() + passoX;
        int y = origem.getY() + passoY;

        while (x != destino.getX() || y != destino.getY()) {
            Casa casaAtual = tabuleiro.getCasa(x, y);

            if (casaAtual.getPeca() != null) {
                return false;
            }

            x += passoX;
            y += passoY;
        }

        return destino.getPeca() == null;
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
