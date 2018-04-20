/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlineP2PMessenger;
import private_chat.*;

public class Controller {

    public Controller() {
        listener pcl = new listener();
        Thread t = new Thread(pcl);
        
    }
    
}
