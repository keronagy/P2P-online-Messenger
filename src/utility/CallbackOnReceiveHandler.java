/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

/**
 *
 * @author fadia
 */
import java.util.ArrayList;
public interface CallbackOnReceiveHandler {
    public void handleReceivedData(ArrayList<String> msg);
}
