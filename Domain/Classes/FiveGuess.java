package Domain.Classes;

import java.io.Serializable;
import java.util.*;

/**
 * A class that implements Donald Knuth's "FiveGuess" algorithm for the game MasterMind
 */
public class FiveGuess implements IBreakerLogic, Serializable
{

    /**
     * It holds a list of the results obtained for each possibleGuesses guess
     */
    private List<GuessResult> results = new ArrayList<>();

    /**
     * It holds a list of all the possibleGuesses guesses to be the correct answer
     */
    private List<Guess> possibleGuesses;

    /**
     * It holds a list of all the possibleGuesses available guesses
     */
    private List<Guess> availableGuesses;

    /**
     *  It references the Game linked to this strategy instance
     */
    private Game currentGame;


    /**
     * Creates a new empty FiveGuess instance without any parameter initialized
     */
    public FiveGuess() {
    }

    /**
     * Initializes the current FiveGuess instance with the Game passed as the parameter
     * @param game A Game that contains all the fields necessary for the FiveGuess to be correctly initialized
     */
    public void initialize(Game game) {
        currentGame = game;
        // initialize results
        for (int i = 0; i <= currentGame.getNumHoles(); ++i) {
            for (int j = 0; j <= (currentGame.getNumHoles()-i); ++j) {
                if (!(i == (currentGame.getNumHoles()-1) && j == 1)) {
                    results.add(new GuessResult(i, j, currentGame.getNumHoles()-i-j));
                }
            }
        }

        // initialize possibleGuesses, availableGuesses
        possibleGuesses = new ArrayList<>();
        availableGuesses = new ArrayList<>();
        List<Peg> data = new ArrayList<>(
                Collections.nCopies(currentGame.getNumHoles(), new Peg(Peg.Color.EMPTY)));
        generatePossibles(data, 0);
        availableGuesses.addAll(possibleGuesses);
    }

    /**
     * Generates all the Possible Guesses (Backtracking) for a specific range and saves them to the list of
     * Possible Guesses
     * @param data List that holds each temporal guess to be added later
     * @param i Current Hole
     */
    private void generatePossibles(List<Peg> data, int i) {
        if (i == currentGame.getNumHoles()) {
            possibleGuesses.add(new Guess(data));
        }

        else {
            for (int j = 0; j < currentGame.getNumColors(); ++j) {
                data.set(i, new Peg(currentGame.getColors().get(j)));
                generatePossibles(data, i+1);
            }
        }
    }


    /**
     * Modifies the Available Guesses with only the ones that match the GuessResult gotten by the Player
     * @param guess Guess tried by the computer
     * @param guessResult GuessResult obtained by the Player
     */
    private void sieve(Guess guess, GuessResult guessResult) {
        // if the answer were availableGuesses[i], would I have gotten the response I got with "guess"?
	    // returns modified availableGuesses (availableGuesses[i] : evaluateCode(availableGuesses[i], guess) == guessResult)

        ArrayList<Guess> modified_S = new ArrayList<>();
        for (Guess i : availableGuesses) {
            GuessResult evalPair = evaluateCode(i, guess, currentGame.getColors());
            if (evalPair.equals(guessResult)){
                modified_S.add(i);
            }
        }

        availableGuesses = modified_S;
    }

    /**
     * Plays a turn using the FiveGuess algorithm
     * @param result Play done by the CodeMaker
     * @return Play / Guess done by the computer
     */
    @Override
    public Guess playTurn(GuessResult result) {

        /* per cada codi que encara no hem provat (no cal q estigue a availableGuesses necessariament), "guess":
	       fem una passada per availableGuesses, avaluant evaluateCode(guess, availableGuesses[i]), i mantenint comptadors per cada possibleGuesses resultat
	       --- ie: 	guess "2345", retorna 100 coincidencies "BW", 50 "WW" i 300 "W" (rand)
	       --- 	  	llavors, tindriem los comptadors <50, WW>, <100, BW> i <300, W>.
	       la SCORE d'un guess es el "minim nombre d'eliminats" = len(availableGuesses) - max(comptadors)
	       ---		en este cas, posem len(availableGuesses) = 1200 -> 	score(2345) = 1200 - 300 = 900
	       Agafem lo conjunt dels MAXIMS PUNTUADORS, i triem un (prioritzem que sigue a availableGuesses), sera NEXT GUESS
	       --- 		si cap dels maxims es a availableGuesses, significa q no ho resoldrem a la propera tirada. */


        Guess guess = currentGame.getLastGuess();
        result = result.normalize();
        sieve(guess, result);

        // scores = {key: 0 for key in possibleGuesses}
        Map<Guess, Integer> scores = new HashMap<>();
        for (Guess key : possibleGuesses) {
            scores.put(key, 0);
        }

        for (Guess g : possibleGuesses) {
            Map<GuessResult, Integer> hitCount = new HashMap<>();
            for (GuessResult key : results) {
                hitCount.put(key, 0);
            }

            for (Guess code : availableGuesses) {
                GuessResult evalKey = evaluateCode(g, code, currentGame.getColors());
                hitCount.put(evalKey, hitCount.get(evalKey) + 1);
            }
            scores.put(g, availableGuesses.size() - Collections.max(hitCount.values()));
        }

        ArrayList<Pair<Guess, Integer>> nList = new ArrayList<>();

        for (Guess key : scores.keySet()) {
            Integer value = scores.get(key);
            nList.add(new Pair<>(key, value));
        }

        nList.sort(Comparator.comparing(o -> o.y));
        Collections.reverse(nList);

        Pair<Guess, Integer> nListMax = nList.get(0);

        ArrayList<Pair<Guess, Integer>> nextCandidates = new ArrayList<>();
        for (Pair<Guess, Integer> i : nList) {
            if (i.y.equals(nListMax.y)) {
                nextCandidates.add(i);
            }
        }

        // construct prefCandidates and nonPrefCandidates
        ArrayList<Guess> prefCandidates = new ArrayList<>();
        ArrayList<Guess> nonPrefCandidates = new ArrayList<>();
        for (Pair<Guess, Integer> i : nextCandidates) {
            if (availableGuesses.contains(i.x)) {
                prefCandidates.add(i.x);
            }
            else {
                nonPrefCandidates.add(i.x);
            }
        }

        prefCandidates.sort(this::compareGuess);
        nonPrefCandidates.sort(this::compareGuess);

        Guess nextGuess;
        if (prefCandidates.size() > 0) {
            nextGuess = prefCandidates.get(0);
            prefCandidates.remove(0);
        }
        else {
            nextGuess = nonPrefCandidates.get(0);
        }

        return nextGuess;
    }

}