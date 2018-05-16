package Domain.Classes;

import java.io.Serializable;
import java.util.*;

/**
 * A class that implements a custom made Genetic Algorithm to find out the correct answer to a Mastermind game
 */
public class GeneticAlgorithm implements IBreakerLogic, Serializable
{
    /**
     * A list of Pairs containing a Guess and its GuessResult based on the input of the player
     */
    private List<Pair<Guess, GuessResult>> playsHistory;

    /**
     * A Random instance used to generate different random numbers within the algorithm
     */
    private Random rand;

    /**
     * Maximum number of generations that will be generated in the algorithm to compute the response
     */
    private int maxGen;

    /**
     * Maximum size of eligible candidate codes set from which a new code proposal will be made
     */
    private int maxSize;

    /**
     *  It references the Game linked to this strategy instance
     */
    private Game currentGame;

    /**
     * Creates a new empty GeneticAlgorithm instance without any parameter initialized
     */
    public GeneticAlgorithm() {
    }

    /**
     * Initializes the current GeneticAlghorithm instance with the Game passed as the parameter
     * @param game A Game that contains all the fields necessary for the GeneticAlgorithm to be correctly initialized
     */
    public void initialize(Game game) {
        currentGame = game;
        playsHistory = new ArrayList<>();
        rand = new Random();

        if (Math.pow(currentGame.getNumHoles(), currentGame.getNumColors()) >= 60) {
            maxGen = 100;
            maxSize = 60;
            maxGen += (10*(currentGame.getNumColors() - 6) + 10*(currentGame.getNumHoles() - 4));
            maxSize += (5*(currentGame.getNumColors() - 6) + 10*(currentGame.getNumHoles() - 4));
            if (maxGen < 100) maxGen = 100;
            if (maxSize < 60) maxSize = 60;
        }
        else {
            maxGen = 100;
            maxSize = (int)(Math.pow(currentGame.getNumHoles(), currentGame.getNumColors())/2);
        }

    }

    /**
     * Evaluates the fitness (score) of a Guess
     * @param trial Guess to evaluate
     * @return Fitness score for that Guess
     */
    private int fitness(Guess trial) {
        int diffBlack = 0;
        int diffWhite = 0;
        for (Pair<Guess, GuessResult> oldGuess : playsHistory) {
            Pair<Integer, Integer> evalPeg = evaluateCode(trial, oldGuess.x, currentGame.getColors()).toPair();
            diffBlack += Math.abs(evalPeg.x - oldGuess.y.toPair().x);
            diffWhite += Math.abs(evalPeg.y - oldGuess.y.toPair().y);
        }
        return diffBlack + diffWhite;
    }

    /**
     * Evaluates the fitness (score) of a Guess in case none of the previous ones where valid
     * @param trial Guess to evaluate
     * @return Fitness score for that Guess
     */
    private int fitnessBad(Guess trial) {
        int diffBlack = 0;
        int diffWhite = 0;
        for (Pair<Guess, GuessResult> oldGuess : playsHistory) {
            Pair<Integer, Integer> evalPeg = evaluateCode(trial, oldGuess.x, currentGame.getColors()).toPair();
            diffBlack += Math.abs(evalPeg.x - oldGuess.y.toPair().x);
            diffWhite += Math.abs(evalPeg.y - oldGuess.y.toPair().y);
        }
        return 2*diffBlack + diffWhite + 2*(currentGame.getNumHoles()*(playsHistory.size()-1));
    }

    /**
     * Performs a crossover (explained in the Documentation of the Genetic Algorithm) between two Guesses
     * @param firstGuess First Guess
     * @param secondGuess Second Guess
     * @return Crossover of both Guesses
     */
    private Pair<Guess, Guess> crossover(Guess firstGuess, Guess secondGuess) {
        Guess sonA;
        Guess sonB;
        if (rand.nextInt(100) <= 50) {
            // 1-point crossover
            int i = rand.nextInt(firstGuess.getPegsList().size());     // random cut point
            List<Peg> subListA = firstGuess.getPegsList().subList(0, i+1);
            subListA.addAll(secondGuess.getPegsList().subList(i+1, secondGuess.getPegsList().size()));
            sonA = new Guess(subListA);
            List<Peg> subListB = secondGuess.getPegsList().subList(0, i+1);
            subListB.addAll(firstGuess.getPegsList().subList(i+1, firstGuess.getPegsList().size()));
            sonB = new Guess(subListB);
        }

        else {
            int i = rand.nextInt(firstGuess.getPegsList().size());
            int j = rand.nextInt(firstGuess.getPegsList().size());
            if (j < i+1) {
                int swap = i;
                i = j;
                j = swap;
            }
            List<Peg> subListA = firstGuess.getPegsList().subList(0, i+1);
            subListA.addAll(secondGuess.getPegsList().subList(i+1, j+1));
            subListA.addAll(firstGuess.getPegsList().subList(j+1, firstGuess.getPegsList().size()));
            sonA = new Guess(subListA);

            List<Peg> subListB = secondGuess.getPegsList().subList(0, i+1);
            subListB.addAll(firstGuess.getPegsList().subList(i+1, j+1));
            subListB.addAll(secondGuess.getPegsList().subList(j+1, secondGuess.getPegsList().size()));
            sonB = new Guess(subListB);
        }
        return new Pair<>(sonA, sonB);
    }

    /**
     * Mutates a Guess with random genes
     * @param guess Guess to mutate
     * @return Guess with some color mutated
     */
    private Guess mutate(Guess guess) {
        int i = rand.nextInt(guess.getPegsList().size());
        Peg.Color col = currentGame.getColors().get(rand.nextInt(currentGame.getNumColors()));
        List<Peg> newList = guess.getPegsList();
        newList.set(i, new Peg(col));
        return new Guess(newList);
    }

    /**
     * Permutes a Guess with itself
     * @param guess Guess to permute
     * @return Guess permuted with itself
     */
    private Guess permute(Guess guess) {
        int i = rand.nextInt(guess.getPegsList().size());
        int j = rand.nextInt(guess.getPegsList().size());
        List<Peg> newList = guess.getPegsList();
        Peg iPeg = newList.get(i);
        Peg jPeg = newList.get(j);
        newList.set(i, jPeg);
        newList.set(j, iPeg);
        return new Guess(newList);
    }

    /**
     * Inverts a Guess with itself
     * @param guess Guess to invert
     * @return Guess inverted with itself
     */
    private Guess invert(Guess guess) {
        int i = rand.nextInt(guess.getPegsList().size());
        int j = rand.nextInt(guess.getPegsList().size());
        if (j < i+1) {
            int swap = i;
            i = j;
            j = swap;
        }
        List<Peg> transposed = guess.getPegsList().subList(i, j);
        Collections.reverse(transposed);
        List<Peg> newList = guess.getPegsList().subList(0, i);
        newList.addAll(transposed);
        newList.addAll(guess.getPegsList().subList(j, guess.getPegsList().size()));
        return new Guess(newList);
    }


    /**
     * Performs the Genetic Algorithm to get the best possible Guess
     * @return Guess to try to solve the Game
     */
    private Guess genetic() {
        Set<Guess> population = new HashSet<>();
        int initialSize =
                Math.pow(currentGame.getNumHoles(), currentGame.getNumColors()) < 150
                ? (int)(Math.pow(currentGame.getNumHoles(), currentGame.getNumColors())/2.0)
                : 150;
        while (population.size() < initialSize) {
            population.add(Guess.generateCode(currentGame.getNumHoles(), currentGame.getColors()));
        }

        int h = 1;
        ArrayList<Guess> elite = new ArrayList<>();
        while (elite.size() < maxSize && h < maxGen) {
            ArrayList<Guess> parents = new ArrayList<>();
            for (Guess chromosome : population) {      // select only eligibles to be parents
                if (fitness(chromosome) == 0) {
                    parents.add(chromosome);
                }
            }

            // fallback (perfecte)
            if (parents.size() <= 1) {
                ArrayList<Pair<Guess, Integer>> failedParents = new ArrayList<>();
                for (Guess chromosome : population) {
                    failedParents.add(new Pair<>(chromosome, fitnessBad(chromosome)));
                }
                failedParents.sort((o1, o2) -> o1.y.compareTo(o2.y));
                int maxScore = failedParents.get(0).y;
                for (Pair<Guess, Integer> elem : failedParents) {
                    if (elem.y > maxScore) maxScore = elem.y;
                }
                ArrayList<Pair<Guess, Float>> probs = new ArrayList<>();
                for (Pair<Guess, Integer> elem : failedParents) {
                    probs.add(new Pair<>(elem.x, (elem.y/(float)maxScore)));
                }
                for (Pair<Guess, Float> elem : probs) {
                    if (rand.nextFloat() > elem.y) {
                        parents.add(elem.x);
                    }
                }
            }

            Set<Guess> sons = new HashSet<>();
            for (int i = 0; i < parents.size()-1; ++i) {
                Pair<Guess, Guess> baby = crossover(parents.get(i), parents.get(i+1));
                Guess sonA = baby.x;
                Guess sonB = baby.y;
                if (rand.nextInt(100) <= 3) {
                    sonA = mutate(sonA);
                    sonB = mutate(sonB);
                }
                if (rand.nextInt(100) <= 3) {
                    sonA = permute(sonA);
                    sonB = permute(sonB);
                }
                if (rand.nextInt(100) <= 2) {
                    sonA = invert(sonA);
                    sonB = invert(sonB);
                }

                // if already in sons, add new random-generated sons
                while (!sons.add(sonA)) {
                    if (sons.size() >= Math.pow(currentGame.getNumHoles(), currentGame.getNumColors()))
                        break;
                    sonA = Guess.generateCode(currentGame.getNumHoles(), currentGame.getColors());
                }
                while (!sons.add(sonB)) {
                    if (sons.size() >= Math.pow(currentGame.getNumHoles(), currentGame.getNumColors()))
                        break;
                    sonB = Guess.generateCode(currentGame.getNumHoles(), currentGame.getColors());
                }
            }

            // select sons which are eligible (fitness = 0)
            for (Guess s : sons) {
                if (fitness(s) == 0) {
                    elite.add(s);
                    if (elite.size() >= maxSize) {
                        break;
                    }
                }
            }


            population.clear();
            population.addAll(elite);
            while (population.size() < maxSize) {
                population.add(Guess.generateCode(currentGame.getNumHoles(), currentGame.getColors()));
            }

            ++h;

        }   // end while



        ArrayList<Guess> S = new ArrayList<>(elite);

        Map<Guess, Integer> scores = new HashMap<>();

        for (Guess c : elite) {
            int countc = 0;         // remaining elements
            for (Guess cc : S) {
                Pair<Integer, Integer> result = evaluateCode(c, cc, currentGame.getColors()).toPair();
                for (Guess x : elite) {
                    if (x.equals(c) || x.equals(cc)) continue;
                    if (result.equals(evaluateCode(x, cc, currentGame.getColors()).toPair())) {
                        ++countc;
                    }
                }
            }
            scores.put(c, countc);
        }

        // convert scores to a list, sort by ascending, pick first element (least remaining)
        ArrayList<Pair<Guess, Integer>> nList = new ArrayList<>();
        for (Guess key : scores.keySet()) {
            Integer value = scores.get(key);
            nList.add(new Pair<>(key, value));
        }
        nList.sort((o1, o2) -> o1.y.compareTo(o2.y));


        return (nList.size() > 0 ? nList.get(0).x : null);
    }

    /**
     * Plays a turn
     * @param result Play done by the CodeMaker
     * @return Guess to try as the possible answer
     */
    @Override
    public Guess playTurn(GuessResult result) {
        Guess guess = currentGame.getLastGuess();

        playsHistory.add(new Pair<>(guess, result));
        Guess output = genetic();
        while (output == null) {
            output = genetic();
        }
        return output;
    }

}
