/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extra_favionataren_lab2_22141210;

/**
 *
 * @author berli
 */
public class AccountAlreadyExist extends Exception {
        AccountAlreadyExist(int num){
        super("Cuenta "+num+ " ya esta agregada en el sitema");

    }
}
