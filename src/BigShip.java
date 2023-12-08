import java.io.Serializable;

class BigShip extends Embarcacao implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BigShip(int linha, int coluna1, int coluna2, int coluna3, int coluna4) {
        super(linha, coluna1, coluna2, coluna3, coluna4);
    }

    @Override
    public char getInicial() {
        return 'B'; // 'B' representa o BigShip
    }
}
