/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.util.HashMap;

/**
 *
 * @author fadia
 */
public interface CallbackOnReceiveHandler {

    public void handleReceivedData(HashMap<String, String> msg);
}
