package mechanisms;

import java.util.Random;
import tools.RPGDLException;

/**
 * This class handles die mechanism for the RPG called Call of Cthulhu. In this 
 * game, 1 hundred faces die is launched for each test. To succeed, the score
 * must be below a given threshold.
 * 
 * @author Nicolas Brax
 */
public class ACMechanisms  implements GameMechanisms {
  
  /**
   * The scores hit by the die after a launch.
   */
  private int score;
  
  /**
   * This method is an indicator to the fact that the mechanism is static or not, 
   * meaning that each test is resolved using the same launch or that each
   * launch can use different dice according to contexts. In Call of Cthulhu, it is 
   * always the same, 1D100 with a difficulty and a treshold.
   * 
   * @return True since for Call of Cthulhu, it is always the same launch.
   */
  @Override
  public boolean staticMechanism(){
    return true;
  }
  
  /**
   * Analyse the expression to resolve the dice launch. For Call of Cthulhu, the
   * die is launched and then the score is analysed to give the result.
   * 
   * The result is computed as follows: 
   *  - Below the threshold is a success, above is a failure;
   *  - 1 is a critical success;
   *  - 100 or 96+ is a critical failure according to the threshold (<50 or >50).
   * 
   * @param exp the expression to analyse, MUST BE "1D100 --thresholdT --difficultyD".
   * @return a String containing the dice scores and the results.
   * @throws RPGDLException This expression is thrown if the expression is not
   *                         understandable by this engine.
   */
  @Override
  public String launchDices(String exp) throws RPGDLException{
    String toReturn = "";
    Random rand = new Random();
    score = rand.nextInt(100) + 1;
    try{
       int threshold = Integer.parseInt(exp.split("--")[1].split("hold")[1].trim());
       int difficulty = Integer.parseInt(exp.split("--difficulty")[1].replace("(static)", "").trim());
       int realThreshold = threshold / ((difficulty == 1) ? 1 : ((difficulty == 2) ? 2 : 10));  // modify the threshold according
                                                                                                // to the difficulty
       toReturn += "[" + score + "]"
               + " --result "             
               + ((score > realThreshold)                                       // if result > threshold
               ? (((threshold > 50 && score == 100)                             // if 100 or >= 96
                 ||(threshold < 50 && score >= 96))                             // according to the threshold
                 ? "critical-failure" : "failure")                              // this is a critical failure, a failure otherwise
               : ((score == 1) ? "critical-success" : "success"))               // else it is a success or a critical one if scores 1.
               + " --threshold(" + threshold +")"
               + " --difficulty(" + difficulty +")";
    }
    catch(Exception e){
      throw new RPGDLException("Can't analyse the dice expression for Call of Cthulhu.");
    }
    return toReturn;
  }
  
  /**
   * The expression that MUST be used for the game Call of Cthulhu.
   * 
   * @return the expression to use.
   */
  @Override
  public String helpExpression(){
    return "1D100 --thresholdX --difficultyT (static)";
  }
    
  /**
   * This method gives a quick description of how the die mechanism for the 
   * game Call of Cthulhu works.
   * 
   * @return a description of the die mechanism.
   */
  @Override
  public String mechanismQuickDescription(){
    return "In Call of Cthulhu, besides launches for damage and other computation, "
            + "tests are made using a 100 faces die. The score is then "
            + "compared to a threshold modified according to a difficulty level. "
            + "There are three levels of difficulty --normal, difficult, extreme-- "
            + "that respectively modifies the value NOT to exceed --threshold, "
            + "threshold/2,threshold/10--. Moreover, critical success occurs on "
            + "a score of 1 and critical failure occurs on a score of 100 if threshold "
            + "is above 50 and on 96-100 if threshold is below 50.";
  }
}
