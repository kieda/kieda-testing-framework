/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.nio.ByteBuffer;

/**
 *
 * @author kieda
 */
public interface Encryptable {
    public <T> ByteBuffer algoEn(ByteBuffer b, T key);
    public <T> ByteBuffer algoDe(ByteBuffer b, T key);
}
