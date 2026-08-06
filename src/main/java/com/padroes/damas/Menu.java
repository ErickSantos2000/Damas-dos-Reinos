package com.padroes.damas;

import com.padroes.damas.Jogo.Jogo;
import com.padroes.damas.tabuleiro.ExibirTabuleiro;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner;
    private final ExibirTabuleiro tela;

    public Menu(Scanner scanner, ExibirTabuleiro tela) {
        this.scanner = scanner;
        this.tela = tela;
    }

    public void iniciar(Jogo jogo) {
        while (!jogo.isEncerrado()) {
            tela.exibirTabuleiro(jogo.getTabuleiro());

            System.out.println("\nVez de: " + jogo.getGerenciadorTurno().getVez().getNome());
            System.out.println("Informe as coordenadas passo a passo");

            boolean movimentoValido = executarMovimento(jogo);
            if (!movimentoValido) {
                System.out.println("Movimento inválido.");
            }
        }

        if (jogo.getVencedor() != null) {
            tela.exibirTabuleiro(jogo.getTabuleiro());
            System.out.println("Vitória de " + jogo.getVencedor().getNome()
                    + ": " + jogo.getMotivoEncerramento() + ".");
        } else {
            System.out.println("main.java.com.padroes.damas.Jogo encerrado.");
        }
    }

    private boolean executarMovimento(Jogo jogo) {
        System.out.print("Linha de origem: ");
        int linhaOrigem = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Coluna de origem: ");
        int colunaOrigem = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Linha de destino: ");
        int linhaDestino = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Coluna de destino: ");
        int colunaDestino = Integer.parseInt(scanner.nextLine().trim());

        return jogo.mover(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino);
    }
}
