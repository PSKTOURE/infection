package inf;

import java.util.Set;

/**
 * Meme principe que minimax mais écrit différemment
 * @author toure215
 *
 */

public class NegaMax extends Minmax {

	public NegaMax(State state, int d) {
		super(state, d);
	}

	public float negaMax(State state, int d) {
		char player = super.state.getPlayer();
		float m = Float.NEGATIVE_INFINITY;
		Set<Move> setOfMoves = state.getMove(state.getPlayer());
		if (d == 0 || state.isOver() == true) {
			return state.getScore(player);
		} else {
			for (Move move : setOfMoves) {
				State newState = state.play(move);
				m = Math.max(m, -negaMax(newState,d - 1));
			}
		}
		return m;
	}
	
	@Override
	public Move getBestMove(State state, int d) {

		Move bestMove = null;
		float bestScore = -1000;
		Set<Move> setOfMoves = state.getMove(state.getPlayer());
		if (setOfMoves.isEmpty()) {
			return null;
		} else {
			for (Move move : setOfMoves) {
				State nextState = state.play(move);
				float value = negaMax(nextState, d);
				if (value > bestScore) {
					bestScore = value;
					bestMove = move;
				}
			}
		}
		return bestMove;
	}
}