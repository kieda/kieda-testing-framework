/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.mouse;

import data.Action;
import java.io.BufferedWriter;
import java.io.IOException;
import static funs.ArrayFuns.make;
/**
 *
 * @author kieda
 */
public abstract class MouseAction extends Action{
    final int button,  x_pos, y_pos;
    public MouseAction(int mouse_action, int x_pos, int y_pos, int delay_time, final byte MOUSE_ACTION) {
        super(delay_time, MOUSE_ACTION);
        this.button = mouse_action;
        this.x_pos = x_pos;
        this.y_pos = y_pos;
    }
//    @Override public void write(BufferedWriter out) throws IOException{
//        out.write("[");
//        out.write(mouse_action+" "+x_pos+" "+y_pos+" "+delay_time);
//        out.write("]");
//        if(next != null){
//            out.write(" => ");
//            next.write(out);
//        }
//    }
    @Override protected int[] important() {
        return new int []{x_pos, y_pos};
    }
    @Override protected int id() {
        return button;
    }
}
