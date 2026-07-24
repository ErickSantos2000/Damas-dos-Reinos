package tabuleiro;

import Peca.Peca;

public class Casa {
    private String cor;
    private String coordenada;
    private Peca peca;

    public Casa(String cor, String coordenada, Peca peca) {
        this.cor = cor;
        this.coordenada = coordenada;
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

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(String coordenada) {
        this.coordenada = coordenada;
    }

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }
}
