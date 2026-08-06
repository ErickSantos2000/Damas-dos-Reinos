package com.padroes.damas;

import com.padroes.damas.Jogo.Jogo;
import com.padroes.damas.tabuleiro.ExibirTabuleiro;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Jogo jogo = new Jogo(false);
        ExibirTabuleiro tela = new ExibirTabuleiro();
        Scanner scanner = new Scanner(System.in);

        Menu menu = new Menu(scanner, tela);
        menu.iniciar(jogo);

        scanner.close();
    }
}