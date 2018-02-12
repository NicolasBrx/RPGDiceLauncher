package mechanisms;

import java.util.Random;
import tools.RPGDLException;

/**
 * This class handles die mechanism for the RPG called Feng Shui. In this game
 * 2 six face dices are launched for each test, one positive and one negative.
 * Results are then added and used for game resolution.
 * 
 * @author Nicolas Brax
 */
public class FSMechanisms implements GameMechanisms {
  
  /**
   * The positive launched dice.
   */
  private int positiveDice;
  
  /**
   * The negative launched dice.
   */
  private int negativeDice;
  
  /**
   * This method is an indicator to the fact that the mechanism is static or not, 
   * meaning that each test is resolved using the same launch or that each
   * launch can use different dice according to contexts. In Feng Shui, it is 
   * always the same, 2D6.
   * 
   * @return True since for Feng Shui, 2D6 are always launched.
   */
  @Override
  public boolean staticMechanism(){
    return true;
  }
  
  /**
   * Analyse the expression to resolve the dice launch. For Feng Shui, the three
   * dices are launched and then the scores are analysed to give the result.
   * 
   * The result is computed as follows: 
   *  - positiveDice - negativeDice
   * 
   * @param exp the expression to analyse, MUST BE "2D6".
   * @return a String containing the dice scores and the results.
   * @throws RPGDLException This expression is thrown if the expression is not
   *                         understandable by this engine.
   */
  @Override
  public String launchDices(String exp) throws RPGDLException{
    String toReturn = "";
    Random rand = new Random();
    this.positiveDice = 0;
    while(this.positiveDice == 0 || (this.positiveDice % 6) == 0){
      this.positiveDice += (rand.nextInt(6) + 1);
    }
    this.negativeDice = 0;
    while(this.negativeDice == 0 || (this.negativeDice % 6) == 0){
      this.negativeDice += (rand.nextInt(6) + 1);
    }
    toReturn += "[" + this.positiveDice + "+, " + this.negativeDice +"-] --result " 
             + (this.positiveDice - this.negativeDice) ;
    
    return toReturn;
  }
  
  /**
   * The expression that MUST be used for the game Feng Shui.
   * 
   * @return the expression to use.
   */
  @Override
  public String helpExpression(){
    return "2D6 (static)";
  }
  
  /**
   * This method gives a quick description of how the die mechanism for the 
   * game Feng Shui works.
   * 
   * @return a description of the die mechanism.
   */
  @Override
  public String mechanismQuickDescription(){
    return "In Feng Shui, it launches two six faces dices, one positive and one "
            + "negative and add their values. The value of related attribute or "
            + "skill can be added using the --addX option where X is the value.";
  }
  
}
