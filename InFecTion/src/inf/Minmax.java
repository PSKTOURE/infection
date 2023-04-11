package inf;

import java.util.Set;

/**
 * Algorithme MiniMax permettant de determiné le coup le plus avantageux
 * pour le joueur
 * @param State : l'etat actuel
 * @param d : profondeur de recherche de l'algo
 * @author toure215
 *
 */

public class Minmax {
	protected State state;
	protected int d;
	protected int visitedNode = 0;

	

	public Minmax(State state,  int d) {
		this.state = state;
		this.d = d;
	}

	/**
	 * algorithme reccurcif minimax permettant de calculer
	 * le meilleur coup possible en fonction de 
	 * l'adversaire
	 * @param state
	 * @param d
	 * @return la valeur du meilleur noeud 
	 */
	public float minMax(State state,int d) {
		visitedNode += 1;
		char player = this.state.getPlayer();
		this.setVisitedNode(visitedNode);
		float b = 0;
		Set<Move> setOfMoves = state.getMove(state.getPlayer());
		if (d == 0 || state.isOver() == true) {
			return state.getScore(player);
		}
		if (player == state.getPlayer()) {
			b = Float.NEGATIVE_INFINITY;
			for (Move move : setOfMoves) {
				State newState = state.play(move);
				float m = minMax(newState, d - 1);
				if (b < m) {
					b = m;
				}
			}
		} else {
			b = Float.POSITIVE_INFINITY;
			for (Move move : setOfMoves) {
				State newState = state.play(move);
				float m = minMax(newState, d - 1);
				if (b > m) {
					b = m;
				}
			}
		}
		return b;
	}
	
	/**
	 * Méthode determinant le meilleur coup possible
	 * @param state
	 * @param d
	 * @return bestMove
	 */
	protected Move getBestMove(State state, int d) {

		Move bestMove = null;
		float bestScore = -1000;
		Set<Move> setOfMoves = state.getMove(state.getPlayer());
		if (setOfMoves.isEmpty()) {
			return null;
		} else {
			for (Move move : setOfMoves) {
				State nextState = state.play(move);
				float value = minMax(nextState, d);
				if (value > bestScore) {
					bestScore = value;
					bestMove = move;
				}
			}
		}
		return bestMove;
	}
	
	// getters and setters
	public int getVisitedNode() {
		return visitedNode;
	}

	public void setVisitedNode(int visitedNode) {
		this.visitedNode = visitedNode;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public int getD() {
		return d;
	}

	public void setD(int d) {
		this.d = d;
	}

}
