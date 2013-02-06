/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import org.kieda.data_structures.Queue;
import java.nio.ByteBuffer;

/**
 *
 * @author kieda
 */
public interface BufferPassable {
    public void pass(Queue<ByteBuffer> b);
}
