package inf;

import java.util.Set;

/**
 * Algorithme AlphaBeta meme objectif que minimax 
 * mais en visitant beaucoup de moins de noeuds
 * @author toure215
 *
 */

public class AlphaBeta extends Minmax {
	private float alpha;
	private float beta;
	private int visitedNode=0;

	
	//CONSTRUCTEUR
	public AlphaBeta(State state, int alpha, int beta, int d) {
		super(state,d);
		this.setAlpha(alpha);
		this.setBeta(beta);

	}
	/**
	 * Algorithme reccurcif permettant de calculer le meilleur coup
	 * en fonction de l'adversaire
	 * @param state
	 * @param alpha
	 * @param beta
	 * @param d
	 * @return la valeur du meilleur move faisable
	 */
	public float alphaBeta(State state, float alpha, float beta, int d) {
		visitedNode += 1;
		this.setVisitedNode(visitedNode);
		char player = super.state.getPlayer();
		Set<Move> setOfMoves = state.getMove(state.getPlayer());
		if (d == 0 || state.isOver() == true) {
			return state.getScore(player);
		}
		if (player == state.getPlayer()) {
			float bestVal = Float.NEGATIVE_INFINITY;
			for (Move move : setOfMoves) {
				State newState = state.play(move);
				bestVal = Math.max(bestVal, alphaBeta(newState, alpha, beta, d - 1));
				alpha = Math.max(alpha, bestVal);
				if (alpha >= beta) {
					break;
				}
			}
			return bestVal;
		} else {
			float bestVal = Float.POSITIVE_INFINITY;
			for (Move move : setOfMoves) {
				State newState = state.play(move);
				bestVal = Math.min(bestVal, alphaBeta(newState,alpha, beta, d - 1));
				beta = Math.min(beta,bestVal);
				if (alpha >= beta) {
					break;
				}
			}
			return bestVal;
		}
	}

	/**
	 * Methode jouant le meilleur coup en se basant sur le calcul de alphabeta
	 * @param state
	 * @param alpha
	 * @param beta
	 * @param d
	 * @return best possible move
	 */
	
	public Move getBestMove(State  state,float alpha, float beta, int d) {
		char player = super.state.getPlayer();
		Move bestMove = null;
		Set<Move> setOfMoves = state.getMove(state.getPlayer());
		if (d == 0 || state.isOver() == true) {
			return null;
		}
		if (player == state.getPlayer()) {
			float bestVal = Float.NEGATIVE_INFINITY;
			for (Move move : setOfMoves) {
				State newState = state.play(move);
				bestVal = Math.max(bestVal, alphaBeta(newState,alpha, beta, d - 1));
				if (bestVal > alpha) {
					alpha = bestVal;
					bestMove = move;
				}
				if(alpha >= beta) {
					return bestMove;
				}
			}
			return bestMove;
		} else {
			float bestVal = Float.POSITIVE_INFINITY;
			for (Move move : setOfMoves) {
				State newState = state.play(move);
				bestVal = Math.min(bestVal, alphaBeta(newState,alpha, beta, d - 1));
				if(bestVal < beta) {
					beta = bestVal;
					bestMove = move;
				}
				if (alpha >= beta) {
					return bestMove;
				}
			}
			return bestMove;
		}
	}
		
	// Getters and Setters
	public float getBeta() {
		return beta;
	}

	public void setBeta(float beta) {
		this.beta = beta;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}



}
