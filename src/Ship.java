import java.io.Serializable;

class Ship extends Embarcacao implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Ship(int linha, int coluna1, int coluna2, int coluna3, int coluna4) {
        super(linha, coluna1, coluna2, 0, 0);
    }

    @Override
    public char getInicial() {
        return 'S'; // 'S' representa o Ship
    }
}