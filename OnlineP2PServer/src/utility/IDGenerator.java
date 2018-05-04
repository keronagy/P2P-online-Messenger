/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;
import java.util.Random;
/**
 *
 * @author fadia
 */
public class IDGenerator {

    private static int clients = 0;
    private static int rooms = 0;

    public static String generateClientID() {
        return Constants.isAClient + "-" + clients++;
    }

    public static String generateRoomID() {
        return Constants.isARoom + "-" + rooms++;
    }
    public static String generateServerID() {
        return Constants.isAServer + "-" +  new Random().nextInt(Integer.MAX_VALUE);
    }
}
