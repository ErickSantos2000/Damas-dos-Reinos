package com.padroes.damas.tabuleiro;

import com.padroes.damas.Peca.Peca;
import com.padroes.damas.Peca.Cor;

public class Casa {
    private String cor;
    private int x;
    private int y;
    private Peca peca;


    public Casa(String cor, int x, int y, Peca peca) {
        this.cor = cor;
        this.x = x;
        this.y = y;
        this.peca = peca;
    }

    public Casa() {
    }

    public void removerPeca() {
        this.peca = null;
    }

    public void colocarPeca(Peca peca) {
        this.peca = peca;
    }

    public boolean temPeca(){
        if (this.peca != null) return true;
        return false;
    }

    public boolean possuiPecaInimiga(Cor cor) {
        return temPeca() && peca.getCor() != cor;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }
}
