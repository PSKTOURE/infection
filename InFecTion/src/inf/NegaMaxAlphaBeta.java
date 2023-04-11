package inf;

import java.util.Set;
/**
 * Même principe que AlphaBeta mais ecrit
 * d'une autre manière
 * @author toure215
 *
 */
public class NegaMaxAlphaBeta extends AlphaBeta {

	public NegaMaxAlphaBeta(State state, int alpha, int beta, int d) {
		super(state, alpha, beta, d);
	}

	public float negaAlphaBeta(State state, float alpha, float beta, int d) {
		float m = Float.NEGATIVE_INFINITY;
		char player = super.state.getPlayer();
		Set<Move> setOfMoves = state.getMove(state.getPlayer());
		if (d == 0 || state.isOver() == true) {
			return state.getScore(player);
		} else {
			for (Move move : setOfMoves) {
				State newState = state.play(move);
				alpha = Math.max(m, -negaAlphaBeta(newState, -beta, -alpha, d - 1));
				if (alpha >= beta) {
					return alpha;
				}
			}
			return alpha;
		}
	}
}
