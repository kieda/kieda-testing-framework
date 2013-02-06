/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package funs;

import java.nio.ByteBuffer;

/**
 *
 * @author kieda
 */
public interface Bijection<From, To>{
    public To         fun       (From in);
    public From       inverse   (To   out);
}
