package inf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
/**
 * Classe representant l'etat actuel du plateau
 * @param player =  joueur actuel
 * @param turn = le numero du tour
 * @param nB, nR : le nombre des pions bleu et rouge respectivement
 * @param hasPassed : boolean indiquant si le joueur de l'état précédant a passé son tour ou non
 * @author toure215
 *
 */


public class State {

	private char[][] board;
	private char player;
	private int turn;
	private int nB;
	private int nR;
	private boolean hasPassed;
	
	//Constructeur
	public State(char[][] board, char player, int turn, int nB, int nR, boolean hasPassed) {

		this.board = board;
		this.player = player;
		this.turn = turn;
		this.hasPassed = hasPassed;
		this.nB = nB;
		this.nR = nR;
	}
	
	// Constructeur initialisant l'etat 0
	public State() {
		this.board = new char[7][7];
		this.player = 'B';
		this.turn = 1;
		this.nB = 2;
		this.nR = 2;
		this.hasPassed = false;
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				this.board[i][j] = 'V';
			}
		}
		this.board[0][0] = 'B';
		this.board[6][6] = 'B';
		this.board[0][6] = 'R';
		this.board[6][0] = 'R';
	}

	// Méthode retournant le score de chaque joueur
	
	public float getScore(char player) {
		float score = 0;
		if (player == 'B') {
			score = (float) getnB() / (getnB() + getnR());
		}
		if (player == 'R') {
			score = (float) getnR() / (getnR() + getnB());
		}
		return score;
	}
	/**
	 * Méthode calculant tous les moves possibles du joueur
	 * @param player
	 * @return Set of Move
	 */
	public Set<Move> getMove(char player) {
		Set<Move> setOfMoves = new HashSet<Move>();
		List<Move> list = new ArrayList<>();
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				if (board[i][j] == player) {
					for (int k = -1; k <= 1; k++) { // move possibles pour les cases directement adjacentes(les clonages possibles)
						for (int l = -1; l <= 1; l++) {
							if (i + k >= 0 && i + k < 7 && j + l >= 0 && j + l < 7 && board[i + k][j + l] == 'V') {
								Move moveClone = new Move(i, j, i + k, j + l, true);
								list.add(moveClone);
							}
						}
					}
					for (int k = -2; k <= 2; k += 2) { // move possibles pour les cases à une distance de 2(les sauts possibles)
						for (int l = -2; l <= 2; l += 2) {
							if (i + k >= 0 && i + k < 7 && j + l >= 0 && j + l < 7 && board[i + k][j + l] == 'V') {
								Move moveSaut = new Move(i, j, i + k, j + l, false);
								list.add(moveSaut);
							}
						}
					}
				}
			}
		}
		Collections.shuffle(list);
		setOfMoves.addAll(list);
		return setOfMoves;
	}

	/**
	 * Methode jouant un move
	 * Change le plateau fonction que le move est un clonage ou un saut
	 * @param move
	 * @return newState un nouvel etat
	 */
	public State play(Move move) {
		int xi = move.getXi();
		int yi = move.getYi();
		int xf = move.getXf();
		int yf = move.getYf();
		char[][] copyBoard = new char[7][7];
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				copyBoard[i][j] = board[i][j];
			}
		}
	

		State newState = new State(copyBoard, player, turn, nB, nR, false);
		
		if (player == 'R' && getMove('R').isEmpty()) {
			newState = new State(copyBoard, 'B', turn + 1, nB, nR, true);
			System.out.println("no move for R");
		}
		if (player == 'B' && getMove('B').isEmpty()) {
			newState = new State(copyBoard, 'R', turn + 1, nB, nR, true);
			System.out.println("no move for B");
		}

		if (move.isClone() == true) {
			copyBoard[xf][yf] = copyBoard[xi][yi];
		} else {
			copyBoard[xf][yf] = copyBoard[xi][yi];
			copyBoard[xi][yi] = 'V';
		}
		for (int k = -1; k <= 1; k++) {
			for (int l = -1; l <= 1; l++) {
				if (xf + k >= 0 && xf + k <= 6 && yf + l >= 0 && yf + l <= 6 && player == 'R'
						&& copyBoard[xf + k][yf + l] == 'B') {
					copyBoard[xf + k][yf + l] = 'R';

				}
				if (xf + k >= 0 && xf + k <= 6 && yf + l >= 0 && yf + l <= 6 && player == 'B'
						&& copyBoard[xf + k][yf + l] == 'R') {
					copyBoard[xf + k][yf + l] = 'B';
				}
			}
		}
		if (player == 'R') {
			newState = new State(copyBoard, 'B', turn + 1, nB - 1, nR + 1, false);
		}
		if (player == 'B') {
			newState = new State(copyBoard, 'R', turn + 1, nB + 1, nR - 1, false);
		}

		return newState;
	}
	
	/**
	 * Méthode indiquant si la partie est terminée
	 * @return un booleen
	 */

	public boolean isOver() {

		boolean ok = false;
		// Si le joueur n'a aucune possiblité de move
		if (player == 'R' && getMove('R').isEmpty()) { 
			ok = true;
		}
		if (player == 'B' && getMove('B').isEmpty()) {
			ok = true;
		}
		// Si le grille est remplie
		if (this.getnB() + this.getnR() == 49) {
			ok = true;
		}
		// Si l'un des joueurs n'a plus de pions
		if (this.getnB() == 0 || this.getnR() == 0) {
			ok = true;
		}
		// Si le joueur précedent a passé son tour et le suivant n'a aucun move possible
		if (hasPassed == true && getMove(player).isEmpty()) {
			ok = true;
		}
		return ok;

	}
	/**
	 * Methode pour l'affichage du board
	 */
	@Override
	public String toString() {
		String res = "";

		for (int j = 6; j >= 0; j--) {
			for (int i = 0; i < 7; i++) {
				res += this.board[i][j]+" ";
			}
			res += "\n";
		}
		return res;
	}
	
	// pour determiner si deux board sont identiques
	public boolean equals(State state) {
		boolean ok = true;
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				if (state.getBoard()[i][j] != this.board[i][j]) {
					ok = false;
				}
			}
		}
		return ok;
	}
	
	 @Override
	    public boolean equals(Object o) {
	        if (o == this) {
	            return true;
	        }
	        if (!(o instanceof State)) {
	            return false;
	        }
	        State c = (State) o;
	        return this.equals(c);
	    }
	 // Methode permettant de choisir un move random 
	 // parmis les moves possibles d'un joueur
	public Move getRandomMove(Set<Move> setOfMoves) {

		Move[] arrayOfMoves = setOfMoves.toArray(new Move[setOfMoves.size()]);
		Random rnd = new Random();
		if (setOfMoves.isEmpty()) {
			return null;
		}
		int i = rnd.nextInt(setOfMoves.size());
		return arrayOfMoves[i];
	}
	
	//Getters and Setters
	public char[][] getBoard() {
		return board;
	}

	/**
	 * @return the player
	 */
	public char getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(char player) {
		this.player = player;
	}

	/**
	 * @return the turn
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * @param turn the turn to set
	 */
	public void setTurn(int turn) {
		this.turn = turn;
	}

	/**
	 * @return the nB
	 */
	public int getnB() {
		int nB = 0;
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				if (this.board[i][j] == 'B') {
					nB++;
				}
			}
		}
		return nB;
	}

	/**
	 * @param nB the nB to set
	 */
	public void setnB(int nB) {
		this.nB = nB;
	}

	/**
	 * @return the nR
	 */
	public int getnR() {
		int nR = 0;
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				if (this.board[i][j] == 'R') {
					nR++;
				}
			}
		}
		return nR;
	}

	/**
	 * @param nR the nR to set
	 */
	public void setnR(int nR) {
		this.nR = nR;
	}

	/**
	 * @return the hasPassed
	 */
	public boolean isHasPassed() {
		return hasPassed;
	}

	/**
	 * @param hasPassed the hasPassed to set
	 */
	public void setHasPassed(boolean hasPassed) {
		this.hasPassed = hasPassed;
	}

	/**
	 * @param board the board to set
	 */
	public void setBoard(char[][] board) {
		this.board = board;
	}

}
