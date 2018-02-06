package mechanisms;

import tools.RPGDLException;

/**
 *
 * @author Nicolas Brax
 */
public interface GameMechanisms {
  
  /**
   * 
   * @param expression
   * @return
   * @throws RPGDLException 
   */
  public String launchDices(String expression) throws RPGDLException;
  
  /**
   * 
   * @return 
   */
  public String mechanismQuickDescription();
  
}
