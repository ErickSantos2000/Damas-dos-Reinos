package Jogador;

public class Jogador {
    private String nome;
    private int qtdPecasComidasDoOponente;

    public Jogador() {
    }

    public Jogador(String nome, int qtdPecasComidasDoOponente) {
        this.nome = nome;
        this.qtdPecasComidasDoOponente = qtdPecasComidasDoOponente;
    }

    public int contarQtdPecasComidasDoOponente() {
        return qtdPecasComidasDoOponente++;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQtdPecasComidasDoOponente() {
        return qtdPecasComidasDoOponente;
    }

    public void setQtdPecasComidasDoOponente(int qtdPecasComidasDoOponente) {
        this.qtdPecasComidasDoOponente = qtdPecasComidasDoOponente;
    }
}
