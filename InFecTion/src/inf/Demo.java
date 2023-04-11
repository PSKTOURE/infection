package inf;

import java.util.ArrayList;
import java.util.Set;

public class Demo {

	public static void main(String[] args) {
		ArrayList<State> listOfState = new ArrayList<>();
		int n = 0;
		State state = new State();

		while (!state.isOver()) {
			listOfState.add(state);
			Minmax minmaxB = new Minmax(state, Integer.parseInt(args[0]));
			Move moveB = minmaxB.getBestMove(state, Integer.parseInt(args[0]));
			if(args[3] == "true") {
				AlphaBeta minmaxAB = new AlphaBeta(state,-100,100,Integer.parseInt(args[0]));
				Move moveAB = minmaxAB.getBestMove(state, -100,100,Integer.parseInt(args[0]));
				minmaxB = minmaxAB;
				moveB = moveAB;
			}
			n += minmaxB.getVisitedNode();
			System.out.println("Tour :" + state.getTurn());
			System.out.println("Player : " + state.getPlayer());
			System.out.println("Nombre de noeuds visites : " + minmaxB.getVisitedNode());
			System.out.println("Nombre total de noeuds visités " + n);
			System.out.println("Move Bleu : " + moveB.toString());
			//play move of player B
			state = state.play(moveB);
			System.out.println(state.toString());
			System.out.println("nombreRouge : " + state.getnR() + ", " + "nombreBleu : " + state.getnB());
			System.out.println("scoreRouge : " + state.getScore('R') + ", " + "scoreBleu : " + state.getScore('B'));
			System.out.println("Next Player = " + state.getPlayer());
			System.out.println("-------------------------------------------------");
			if (listOfState.contains(state)) {
				System.out.println("State already visited");
				break;
			}
			if (state.isOver() && state.getScore('R') > state.getScore('B')) {
				System.out.println("Joueur Rouge vainqueur!");
			}
			if (state.isOver() && state.getScore('R') < state.getScore('B')) {
				System.out.println("Joueur bleu vainqueur!");
			}
			
			//AlphaBeta minmaxR = new AlphaBeta(state,-100,100, 3);
			//Move moveR = minmaxR.getBestMove(state,-100,100,3);
			//Set<Move> setOfMovesB = state.getMove('R');
			//Move moveR = state.getRandomMove(setOfMovesB);
			Minmax minmaxR = new Minmax(state,Integer.parseInt(args[1]));
			Move moveR = minmaxR.getBestMove(state, Integer.parseInt(args[1]));
			if(args[3] == "true") {
				AlphaBeta minmaxABR = new AlphaBeta(state,-100,100,Integer.parseInt(args[1]));
				Move moveABR = minmaxABR.getBestMove(state, -100,100,Integer.parseInt(args[1]));
				minmaxR = minmaxABR;
				moveR = moveABR;
			}
			System.out.println("Tour :" + state.getTurn());
			System.out.println("Player : " + state.getPlayer());
			//System.out.println("Nombre de noeuds visites : " + minmaxR.getVisitedNode());
			System.out.println("Move Rouge : " + moveR.toString());
			//play move of player R
			state = state.play(moveR);
			System.out.println(state.toString());
			System.out.println("nombreRouge : " + state.getnR() + ", " + "nombreBleu : " + state.getnB());
			System.out.println("scoreRouge : " + state.getScore('R') + ", " + "scoreBleu : " + state.getScore('B'));
			System.out.println("Next Player = " + state.getPlayer());
			System.out.println("-------------------------------------------------");
			if (listOfState.contains(state)) {
				System.out.println("State already visited");
				break;
			}
			if (state.isOver() && state.getScore('R') > state.getScore('B')) {
				System.out.println("Joueur Rouge vainqueur!");
			}
			if (state.isOver() && state.getScore('R') < state.getScore('B')) {
				System.out.println("Joueur bleu vainqueur!");
			}
		}

	}

}
