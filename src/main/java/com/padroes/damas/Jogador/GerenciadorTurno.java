package com.padroes.damas.Jogador;

public class GerenciadorTurno {
    private Jogador jogador1;
    private Jogador jogador2;
    private Jogador vezAtual;

    public GerenciadorTurno() {
    }

    public GerenciadorTurno(Jogador jogador1, Jogador jogador2, Jogador vezAtual) {
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        this.vezAtual = vezAtual;
    }

    public Jogador getVez() {
        if (vezAtual == jogador1) return jogador1;
        return jogador2;
    }

    public void mudarTurno() {
        if (vezAtual == jogador1) {
            vezAtual = jogador2;
        } else {
            vezAtual = jogador1;
        }
    }

    public Jogador getJogador1() {
        return jogador1;
    }

    public void setJogador1(Jogador jogador1) {
        this.jogador1 = jogador1;
    }

    public Jogador getJogador2() {
        return jogador2;
    }

    public void setJogador2(Jogador jogador2) {
        this.jogador2 = jogador2;
    }

    public Jogador getVezAtual() {
        return vezAtual;
    }

    public void setVezAtual(Jogador vezAtual) {
        this.vezAtual = vezAtual;
    }
}
