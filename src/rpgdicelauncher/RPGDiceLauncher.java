package rpgdicelauncher;

import ihm.RPGDiceLauncherIHM;
import javax.swing.UnsupportedLookAndFeelException;
import tools.RPGDLException;

/**
 * This class allows to handle dice launches according to the game
 * managed. It also computes and displays results.
 * 
 * @author Nicolas Brax
 */
public class RPGDiceLauncher {
  
  /**
   * The IHM related to the plugin
   */
  private static RPGDiceLauncherIHM ihm;
  
  /**
   * The main method to launch the IHM and initialize the user interface
   * 
   * @param args The command line arguments
   * @throws tools.RPGDLException 
   */
  public static void main(String[] args) throws RPGDLException {
    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    }catch(ClassNotFoundException | InstantiationException 
         | IllegalAccessException | UnsupportedLookAndFeelException ex){
      throw new RPGDLException("Can't set the UI Look and Feel.");
    }
    ihm = new RPGDiceLauncherIHM();
    if(args.length != 0){
      ihm.setGame(args[0]);
    }
    else{
      ihm.setGame("Generic");
    }
    ihm.setVisible(true);
  }
  
  /**
   * Give an access to the IHM. Might be useful for integration in other
   * projects.
   * 
   * @return A link to the RPG Dice Launcher IHM.
   */
  public RPGDiceLauncherIHM getMainFrame(){
    return ihm;
  }
}
