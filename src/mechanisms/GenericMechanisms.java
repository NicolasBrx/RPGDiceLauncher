/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mechanisms;

import java.util.Random;
import tools.RPGDLException;

/**
 *
 * @author nicolas
 */
public class GenericMechanisms implements GameMechanisms {
  
  /**
   * 
   * @param exp
   * @return
   * @throws RPGDLException 
   */
  @Override
  public String launchDices(String exp) throws RPGDLException{
    String toReturn = "";
    Random rand = new Random();
    String[] parts = exp.split("D|d");
    if(parts.length != 2){
      throw new RPGDLException("Invalid Dice Expression.");
    }
    else{
      for(int i = 0 ; i < Integer.parseInt(parts[0]) ; i++){
        toReturn += String.valueOf(rand.nextInt(Integer.parseInt(parts[1]))) + 1;
        if(i < Integer.parseInt(parts[0]) - 1){
          toReturn += " - ";
        }
      }
    }
    return toReturn;
  }
    
  /**
   * 
   * @return 
   */
  @Override
  public String mechanismQuickDescription(){
    return "A generic dice launcher. Expression must follow the format "
            + "\"X\"D\"Y\" which launches \"X\" dices with \"Y\" faces.";
  }
}
