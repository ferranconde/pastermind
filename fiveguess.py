from itertools import product
import operator


def evalc(code, guess):
    # returns a set of pegs (answer)

    # zip("ABCD", "AABB") = parelles AA, BA, CB, DB. ++black si coincideixen
    black = len([code_peg for code_peg, guess_peg in zip(code, guess) if code_peg == guess_peg])
    # "ABCD" i "AABB" hauria de donar (1, 1). Fent aixo evitem pegs no correspostes
    white = sum([min(code.count(i), guess.count(i)) for i in "12345678"]) - black

    return black, white


def sieve(guess, pegs, S):
    # "If the answer were S[i], would I have gotten the response I got with _guess_?"
    # returns modified S (S[i] : evalc(S[i], guess) == pegs)

    print pegs

    S = [i for i in S if evalc(i, guess) == pegs]

    print "longitud: ", len(S)
    return S


def minimax(S, possible):
    # per cada codi que encara no hem provat (no cal q estigue a S necessariament), "guess":
    # fem una passada per S, avaluant evalc(guess, S[i]), i mantenint comptadors per cada possible resultat

    # --- ie: 	guess "2345", retorna 100 coincidencies "BW", 50 "WW" i 300 "W" (rand)
    # --- 	  	llavors, tindriem los comptadors <50, WW>, <100, BW> i <300, W>.

    # la SCORE d'un guess es el "minim nombre d'eliminats" = len(S) - max(comptadors)
    # ---		en este cas, posem len(S) = 1200 -> 	score(2345) = 1200 - 300 = 900

    # Agafem lo conjunt dels MAXIMS PUNTUADORS, i triem un (prioritzem que sigue a S), sera NEXT GUESS
    # --- 		si cap dels maxims es a S, significa q no ho resoldrem a la propera tirada.

    scores = {key: 0 for key in possible}

    for guess in possible:
        ranking = {key: 0 for key in results}
        for code in S:
            ranking[evalc(guess, code)] += 1
        scores[guess] = len(S) - max(ranking.values())

    nlist = sorted(scores.items(), key=operator.itemgetter(1))[::-1]
    next_candidates = [i for i in nlist if i[1] == max(nlist, key=operator.itemgetter(1))[1]]
    pref_candidates = sorted([i[0] for i in next_candidates if i[0] in S])
    non_pref_candidates = sorted([i[0] for i in next_candidates if i[0] not in S])

    if len(pref_candidates) > 0:
        next_guess = pref_candidates[0]
        S.remove(next_guess)
    else:
        next_guess = non_pref_candidates[0]

    # possible.remove(next_guess)
    return next_guess


possible = [''.join(p) for p in product("12345678", repeat=6)]
results = [(right, wrong) for right in range(7) for wrong in range(7 - right) if not (right == 5 and wrong == 1)]
S = possible

print "Five guess"
print "======================="
my_guess = '112233'

while True:
    rb = int(raw_input("Blacks? "))
    rw = int(raw_input("Whites? "))
    response = (rb, rw)
    if response == (6, 0):
        print "won"
        break
    S = sieve(my_guess, response, S)
    my_guess = minimax(S, possible)
    print "trying ", my_guess, "..."
