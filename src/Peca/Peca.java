package Peca;

public interface Peca {
    Cor getCor();
    Peca getTipo();
    void mover(int linha, int coluna);
}
