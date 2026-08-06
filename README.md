# Damas-dos-Reinos

## Regra de captura

Por padrão, as capturas são opcionais. Para usar a variação com captura obrigatória, crie o jogo com o seguinte código:

```java
Jogo jogo = new Jogo(true);
jogo.setCapturaObrigatoria(true);
```

Quando essa opção está ativa, qualquer captura disponível deve ser escolhida no turno.

Escolha de design: foi adotada a captura opcional como padrão para preservar mais alternativas estratégicas em cada turno e valorizar os diferentes estilos de movimento das peças.

## Comando de execução

```bash
mvn exec:java
```
