import java.io.Serializable;
import java.util.Random;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		JogoBatalhaNaval jogo = new JogoBatalhaNaval();
		jogo.iniciarOuCarregarJogo();

		LeitorEscritorArquivos jogoSalvar = new LeitorEscritorArquivos(jogo);
		// Para salvar o jogo
		jogoSalvar.gravarJogo(jogo.getTabuleiroJogador(), jogo.getTabuleiroComputador());

		// Para carregar o jogo
		jogoSalvar.carregarJogo();
	}
}

class JogoBatalhaNaval {
	private Tabuleiro tabuleiroJogador;
	private Tabuleiro tabuleiroComputador;
	private LeitorEscritorArquivos salvarJogo;
	private Scanner entrada;

	public JogoBatalhaNaval() {
		tabuleiroJogador = new Tabuleiro(10, 10);
		tabuleiroComputador = new Tabuleiro(10, 10);
		salvarJogo = new LeitorEscritorArquivos(this);
		entrada = new Scanner(System.in);
	}

	public void iniciarOuCarregarJogo() {
		System.out.println("Deseja iniciar um novo jogo ou carregar um jogo salvo?");
		System.out.println("1 - Novo Jogo");
		System.out.println("2 - Carregar Jogo");

		int opcao = entrada.nextInt();
		if (opcao == 2) {
			carregarJogo();
		} else {
			iniciarJogo();
		}
	}

	public void iniciarJogo() {
		inicializarEmbarcacoes(tabuleiroJogador);
		inicializarEmbarcacoes(tabuleiroComputador);

		System.out.println("\nTABULEIRO DO JOGADOR\n");
		tabuleiroJogador.mostrarTabuleiroDoJogador();
		System.out.println("\n________________________________________________________\n");

		while (!terminouJogo()) {
			System.out.println("\nTURNO DO JOGADOR\n");
			realizarTurnoJogador();

			if (terminouJogo()) break;

			System.out.println("\nTabuleiro do Computador após o ataque do Jogador:\n");
			tabuleiroComputador.mostrarTabuleiro();
			System.out.println("\n________________________________________________________\n");

			System.out.println("\nTURNO DO COMPUTADOR");
			realizarTurnoComputador();

			System.out.println("\nTabuleiro do Jogador após o ataque do Computador:\n");
			tabuleiroJogador.mostrarTabuleiroDoJogador();
			System.out.println("\n________________________________________________________\n");
		}
		System.out.println("\n________________________________________________________\n");
		if (tabuleiroJogador.todosNaviosDestruidos()) {
			System.out.println("O COMPUTADOR GANHOU!");
		} else {
			System.out.println("VOCÊ GANHOU!");
		}
		System.out.println("\n________________________________________________________\n");

		System.out.println("\nDeseja salvar o jogo?");
		System.out.println("1 - Sim");
		System.out.println("2 - Não");

		int opcao = entrada.nextInt();
		if (opcao == 1) {
			gravarJogo();
		}
	}

	private void inicializarEmbarcacoes(Tabuleiro tabuleiro) {
		Random random = new Random();  

		for (int i = 0; i < 3; i++) {
			int linha, coluna;
			do {
				linha = random.nextInt(10) + 1;
				coluna = random.nextInt(8) + 1;
			} while (!tabuleiro.validarPosicao(linha, coluna, 3)); // Verifica posição para IntermediateShip

			IntermediateShip intermediateShip = new IntermediateShip(linha, coluna, coluna + 1, coluna + 2, 0);
			tabuleiro.posicionarEmbarcacao(intermediateShip);
		}

		for (int i = 0; i < 2; i++) {
			int linha, coluna;
			do {
				linha = random.nextInt(10) + 1;
				coluna = random.nextInt(7) + 1;
			} while (!tabuleiro.validarPosicao(linha, coluna, 4)); // Verifica posição para BigShip

			BigShip bigShip = new BigShip(linha, coluna, coluna + 1, coluna + 2, coluna + 3);
			tabuleiro.posicionarEmbarcacao(bigShip);
		}

		for (int i = 0; i < 4; i++) {
			int linha, coluna;
			do {
				linha = random.nextInt(10) + 1;
				coluna = random.nextInt(9) + 1;
			} while (!tabuleiro.validarPosicao(linha, coluna, 2)); // Verifica posição para Ship

			Ship ship = new Ship(linha, coluna, coluna + 1, 0, 0);
			tabuleiro.posicionarEmbarcacao(ship);
		}

		for (int i = 0; i < 5; i++) {
			int linha, coluna;
			do {
				linha = random.nextInt(10) + 1;
				coluna = random.nextInt(10) + 1;
			} while (!tabuleiro.validarPosicao(linha, coluna, 1)); // Verifica posição para TinyShip

			TinyShip tinyShip = new TinyShip(linha, coluna, 0, 0, 0);
			tabuleiro.posicionarEmbarcacao(tinyShip);
		}
	}

	private void realizarTurnoJogador() {
		int linha, coluna;
		do {
			linha = lerLinha();
		} while (linha == -1);

		do {
			coluna = lerColuna();
		} while (coluna == -1);

		atirar(tabuleiroComputador, linha, coluna, true); // Passando true para indicar que é o jogador atirando
	}

	private void realizarTurnoComputador() {
		int linha = sortearNumero();
		int coluna = sortearNumero();
		atirar(tabuleiroJogador, linha, coluna, false); // Passando false para indicar que é o computador atirando
	}

	private int lerLinha() {
		while (true) {
			System.out.print("\nDigite a linha (1-10): ");
			String linhaInput = entrada.next();

			if (linhaInput.matches("[1-9]|10")) {
				int linha = Integer.parseInt(linhaInput);
				if (linha >= 1 && linha <= 10) {
					return linha;
				} else {
					System.out.println("\nEntrada inválida! O número deve estar entre 1 e 10.\n");
				}
			} else {
				System.out.println("\nEntrada inválida! O número deve estar entre 1 e 10.\n");
			}

			System.out.println("Deseja continuar?\n1 - Sim\n2 - Não");

			String opcao = entrada.next();
			if (opcao.equals("2")) {
				System.out.println("\nDeseja salvar o jogo atual antes de sair?");
				System.out.println("1 - Sim");
				System.out.println("2 - Não");

				opcao = entrada.next();
				if (opcao.equals("1")) {
					gravarJogo();
					System.out.println("\nJogo salvo com sucesso!");
					System.out.println("\nObrigado(a) por jogar!\n");
					System.exit(0); // Encerra o jogo
				} else {
					System.out.println("\nObrigado(a) por jogar!\n");
					System.exit(0); // Encerra o jogo
				}
			}
		}
	}

	private int lerColuna() {
		while (true) {
			System.out.print("Digite a coluna (1-10): ");
			String colunaInput = entrada.next();

			if (colunaInput.matches("[1-9]|10")) {
				int coluna = Integer.parseInt(colunaInput);
				if (coluna >= 1 && coluna <= 10) {
					return coluna;
				} else {
					System.out.println("\nEntrada inválida! O número deve estar entre 1 e 10.\n");
				}
			} else {
				System.out.println("\nEntrada inválida! O número deve estar entre 1 e 10.\n");
			}

			System.out.println("Deseja continuar?\n1 - Sim\n2 - Não");

			String opcao = entrada.next();
			if (opcao.equals("2")) {
				System.out.println("\nObrigado por jogar!");
				System.exit(0);
			}
		}
	}

	private int sortearNumero() {
		return new Random().nextInt(10) + 1;
	}

	private void atirar(Tabuleiro tabuleiro, int linha, int coluna, boolean jogador) {
		tabuleiro.atirar(linha, coluna, jogador);
	}

	private boolean terminouJogo() {
		return tabuleiroJogador.todosNaviosDestruidos() || tabuleiroComputador.todosNaviosDestruidos();
	}
	
	public void setTabuleiros(Tabuleiro tabuleiroJogador, Tabuleiro tabuleiroComputador) {
        this.tabuleiroJogador = tabuleiroJogador;
        this.tabuleiroComputador = tabuleiroComputador;
    }

	public Tabuleiro getTabuleiroJogador() {
		return tabuleiroJogador;
	}

	public Tabuleiro getTabuleiroComputador() {
		return tabuleiroComputador;
	}

	public void gravarJogo() {
		salvarJogo.gravarJogo(tabuleiroJogador, tabuleiroComputador);
	}

	public void carregarJogo() {
	    Tabuleiro[] tabuleiros = salvarJogo.loadGame();
	    if (tabuleiros != null) {
	        System.out.println("Jogo carregado com sucesso!");
	        tabuleiroJogador = tabuleiros[0];
	        tabuleiroComputador = tabuleiros[1];
	        iniciarOuContinuarJogo();
	    } else {
	        System.out.println("Não foi possível carregar o jogo. Iniciando um novo jogo...");
	        iniciarJogo();
	    }
	}
	
	public void reiniciarJogo(Tabuleiro tabuleiroJogador, Tabuleiro tabuleiroComputador) {
	    if (tabuleiroJogador != null && tabuleiroComputador != null) {
	        setTabuleiros(tabuleiroJogador, tabuleiroComputador);
	        iniciarOuContinuarJogo();
	    } else {
	        System.out.println("Erro: Tabuleiros não foram fornecidos corretamente para reiniciar o jogo.");
	    }
	}

	void iniciarOuContinuarJogo() {
		if (tabuleiroJogador != null && tabuleiroComputador != null && oJogoNaoTerminou()) {
			iniciarJogo(); // Se os tabuleiros foram carregados e o jogo não terminou, continua o jogo
		} else {
			System.out.println("O jogo já foi concluído ou os tabuleiros não foram carregados. Iniciando um novo jogo...");
			iniciarJogo();
		}
	}

	private boolean oJogoNaoTerminou() {
		return !tabuleiroJogador.todosNaviosDestruidos() && !tabuleiroComputador.todosNaviosDestruidos();
	}
	
	public void reiniciarEstadoDoJogo(Tabuleiro tabuleiroJogador, Tabuleiro tabuleiroComputador) {
        if (tabuleiroJogador != null && tabuleiroComputador != null) {
            this.tabuleiroJogador = tabuleiroJogador;
            this.tabuleiroComputador = tabuleiroComputador;
            System.out.println("Estado do jogo reiniciado com os tabuleiros carregados.");
        } else {
            System.out.println("Erro ao reiniciar o estado do jogo: tabuleiros inválidos.");
        }
    }
}

class Tabuleiro implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private char[][] posicoes;

	public Tabuleiro(int linhas, int colunas) {
		posicoes = new char[linhas][colunas];
		for (int i = 0; i < linhas; i++) {
			for (int j = 0; j < colunas; j++) {
				posicoes[i][j] = '~'; // "~" representa água
			}
		}
	}

	public void posicionarEmbarcacao(Embarcacao embarcacao) {
		int linha = embarcacao.getLinha() - 1;

		if (embarcacao instanceof IntermediateShip) {
			IntermediateShip intermediateShip = (IntermediateShip) embarcacao;
			int coluna1 = intermediateShip.getColuna1() - 1;
			int coluna2 = intermediateShip.getColuna2() - 1;
			int coluna3 = intermediateShip.getColuna3() - 1;
			posicoes[linha][coluna1] = intermediateShip.getInicial();
			posicoes[linha][coluna2] = intermediateShip.getInicial();
			posicoes[linha][coluna3] = intermediateShip.getInicial();

		} else if (embarcacao instanceof BigShip) {
			BigShip bigShip = (BigShip) embarcacao;
			int coluna1 = bigShip.getColuna1() - 1;
			int coluna2 = bigShip.getColuna2() - 1;
			int coluna3 = bigShip.getColuna3() - 1;
			int coluna4 = bigShip.getColuna4() - 1;
			posicoes[linha][coluna1] = bigShip.getInicial();
			posicoes[linha][coluna2] = bigShip.getInicial();
			posicoes[linha][coluna3] = bigShip.getInicial();
			posicoes[linha][coluna4] = bigShip.getInicial();

		} else if (embarcacao instanceof Ship) {
			Ship ship = (Ship) embarcacao;
			int coluna1 = ship.getColuna1() - 1;
			int coluna2 = ship.getColuna2() - 1;
			posicoes[linha][coluna1] = ship.getInicial();
			posicoes[linha][coluna2] = ship.getInicial();

		} else if (embarcacao instanceof TinyShip) {
			TinyShip tinyShip = (TinyShip) embarcacao;
			int coluna = tinyShip.getColuna1() - 1;
			posicoes[linha][coluna] = tinyShip.getInicial();
		}
	}

	public void atirar(int linha, int coluna, boolean jogador) {
		char posicao = posicoes[linha - 1][coluna - 1];
		if (posicao != '~' && posicao != 'O' && posicao != 'X') {
			if (jogador) {
				System.out.printf("\nVOCÊ ACERTOU UMA EMBARCAÇÃO: %c\n", posicao); // Exibe a inicial da embarcação atingida
				posicoes[linha - 1][coluna - 1] = '0'; // Marca a posição como atingida no tabuleiro do jogador 
			} else {
				System.out.printf("\nO COMPUTADOR ACERTOU UMA EMBARCAÇÃO: %c\n", posicao); // Exibe a inicial da embarcação atingida
				posicoes[linha - 1][coluna - 1] = '0'; // Marca a posição como atingida no tabuleiro do computador
			}
		} else {
			if (jogador) {
				System.out.println("\nÁGUA!\n");
				posicoes[linha - 1][coluna - 1] = 'X'; // Marca a posição como água atingida no tabuleiro do jogador
			} else {
				System.out.println("\nO COMPUTADOR ATIROU NA ÁGUA!\n"); // No tabuleiro do computador
				posicoes[linha - 1][coluna - 1] = 'X'; // Marca a posição como água atingida no tabuleiro do computador
			}
		}
	}

	public boolean todosNaviosDestruidos() {
		for (char[] linha : posicoes) {
			for (char posicao : linha) {
				if (posicao == 'I' || posicao == 'B' || posicao == 'S' || posicao == 'T') {
					return false; // Se ainda há um navio não destruído, retorna falso
				}
			}
		}
		return true; // Todos os navios foram destruídos
	}

	public void mostrarTabuleiroDoJogador() {
		// Números das colunas
		System.out.print("     ");
		for (int col = 1; col <= posicoes[0].length; col++) {
			System.out.printf("| %-3d", col);
		}
		System.out.println("|");

		// Linhas do tabuleiro com números
		for (int linha = 0; linha < posicoes.length; linha++) {
			System.out.printf("%-5d", (linha + 1)); // Número da linha
			for (char posicao : posicoes[linha]) {
				System.out.printf("| %-3s", posicao);
			}
			System.out.println("|");
		}
	}

	public void mostrarTabuleiro() {
		// Números das colunas
		System.out.print("     ");
		for (int col = 1; col <= posicoes[0].length; col++) {
			System.out.printf("| %-3d", col);
		}
		System.out.println("|");

		// Linhas do tabuleiro com números
		for (int linha = 0; linha < posicoes.length; linha++) {
			System.out.printf("%-5d", (linha + 1)); // Número da linha
			for (char posicao : posicoes[linha]) {
				if (posicao == 'I' || posicao == 'B' || posicao == 'S' || posicao == 'T') {
					System.out.printf("| %-3s", "~"); // Esconde os navios não atingidos
				} else {
					System.out.printf("| %-3s", posicao);
				}
			}
			System.out.println("|");}
	}


	public boolean validarPosicao(int linha, int coluna, int tamanho) {
		int linhaIndex = linha - 1;
		int colunaIndex = coluna - 1;

		if (linhaIndex < 0 || linhaIndex >= posicoes.length || colunaIndex < 0 || colunaIndex + tamanho > posicoes[0].length) {
			return false; // posição fora do tabuleiro
		}

		// Verifica se as posições para a embarcação têm espaços livres
		for (int i = 0; i < tamanho; i++) {
			if (posicoes[linhaIndex][colunaIndex + i] != '~') {
				return false; // posição ocupada
			}
		}
		return true; // posição válida para a embarcação
	}


	public static Tabuleiro fromString(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }

        String[] linhas = input.split("__");

        if (linhas.length != 10) {
            return null; // Número incorreto de linhas
        }

        Tabuleiro tabuleiro = new Tabuleiro(10, 10); // Criar um tabuleiro de 10x10
        for (int i = 0; i < 10; i++) {
            String linha = linhas[i].trim();
            if (linha.length() != 10) {
                return null; // Número incorreto de colunas
            }
            for (int j = 0; j < 10; j++) {
                tabuleiro.posicoes[i][j] = linha.charAt(j);
            }
        }

        return tabuleiro;
    }
}
