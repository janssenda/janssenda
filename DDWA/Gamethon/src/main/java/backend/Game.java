package backend;

import java.math.BigDecimal;
import java.util.Random;

public class Game {

    public double roundNumber;
    public ArrayList<Case> cases;

    Random rng = new Random();

    public Game() {
        roundNumber = 1;
        cases = generateCases();
    }

    public double getRoundNumber() {
        return roundNumber;
    }


    public void setRoundNumber(double roundNumber) {
        this.roundNumber = roundNumber;

    }

    public ArrayList<Case> getCases() {
        return cases;
    }

    public void setCases(ArrayList<Case> cases) {
        this.cases = cases;
    }

    public BigDecimal makeOffer() throws Exception {

        throw new Exception("This doesn't work yet");

    }

    public ArrayList<Case> generateCases() {

        ArrayList<Case> toFill = new ArrayList<>();
        ArrayList<Integer> caseIDs = new ArrayList<>();

        for (int i = 1; i < 27; i++) {
            caseIDs.add(i);

        }


        for (CaseValue cv : CaseValue.values()) {

            int randomIndex = rng.nextInt(caseIDs.size());
            Case toAdd = new Case(caseIDs.get(randomIndex), cv);
            caseIDs.remove(randomIndex);
            toFill.add(toAdd);

        }

        return toFill;

    }


}
