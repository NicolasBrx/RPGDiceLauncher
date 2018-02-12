package mechanisms;

import tools.RPGDLException;

/**
 * This interface is made to ensure that every game mechanisms implemented
 * might be used into the RPG Software Suit. The methods are as follow:
 *  - indication of tests unicity;
 *  - launching dice according to an expression;
 *  - an method to help on the expression
 *  - a method to describe the mechanisms.
 * 
 * @author Nicolas Brax
 */
public interface GameMechanisms {
  
  /**
   * Indicate if the game use always the same launch or if it can vary
   * from one launch to another.
   * 
   * @return True if it does not change in the game, false otherwise.
   */
  public boolean staticMechanism();
  
  /**
   * Launch the dice and analyse the results according to the expression
   * given in parameters.
   * 
   * @param expression The expression that will be analysed, according to the game.
   * @return The result of the dice launch.
   * @throws RPGDLException An exception is thrown if the expression is not 
   *                         understood by the engine.
   */
  public String launchDices(String expression) throws RPGDLException;
  
  /**
   * A pattern to follow to give the right expresison to the method launchDices(exp).
   * 
   * @return The right pattern according to the game played.
   */
  public String helpExpression();
  
  /**
   * A quick description of the mechanisms involved into the game played.
   * 
   * @return A quick description.
   */
  public String mechanismQuickDescription();
  
}
