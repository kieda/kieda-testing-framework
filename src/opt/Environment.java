/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package opt;

import funs.Assert;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

/**
 *
 * @author kieda
 */
public class Environment {
    public static boolean IS_RECORDING;
    
    public static final String DEFAULT_SAVE_NAME= "out";
    public static final String DEFAULT_SAVE_EXT = "bro";
    
    public static String CURRENT_SAVE_NAME= DEFAULT_SAVE_NAME;
    public static String CURRENT_SAVE_EXT = DEFAULT_SAVE_EXT;
    
    public static final String ARROW_SPACE      = " => ";
    public static final String COMMA_SPACE      = ", ";
    public static final String SINGLE_SPACE     = " ";
    public static final String NEW_LINE         = "\n";
    
    public static final String BRACKET_OPEN     = "[";
    public static final String BRACKET_CLOSE    = "]";
    
    public static final String BRACE_OPEN       = "{";
    public static final String BRACE_CLOSE      = "}";
    
    public static final int VERBOSE             = 0xA00;
    public static final int COMPACT             = 0xA01;
    
    
    public static String CONNECTIVE_NEXT        = SINGLE_SPACE;
    public static String CONNECTIVE_LIST        = SINGLE_SPACE;
    
    public static String LIST_1_BEGIN           = BRACE_OPEN;
    public static String LIST_1_END             = BRACE_CLOSE;
    public static String LIST_2_BEGIN           = BRACKET_OPEN;
    public static String LIST_2_END             = BRACKET_CLOSE;
    
    public static int write_opt                 = COMPACT;
    public static int read_opt                  = COMPACT;
    
    public static final boolean DEFAULT_MOUSE_MOVEMENT = false;
    public static final boolean DEFAULT_MOUSE_PRESS    = true;
    public static final boolean DEFAULT_MOUSE_RELEASE  = true;
    public static final boolean DEFAULT_KEY_PRESS      = true;
    public static final boolean DEFAULT_KEY_RELEASE    = true;
    
    public static boolean record_mouse_movement        = DEFAULT_MOUSE_MOVEMENT;
    public static boolean record_mouse_press           = DEFAULT_MOUSE_PRESS;
    public static boolean record_mouse_release         = DEFAULT_MOUSE_RELEASE;
    public static boolean record_key_press             = DEFAULT_KEY_PRESS;
    public static boolean record_key_release           = DEFAULT_KEY_RELEASE;
    
    public static final int PER_PIXEL_TRANSP_VAL  = 0xABCD;
    public static final int PER_PIXEL_TRANSLU_VAL = 0xBCDA;
    public static final int TRANSLUCENT_VAL       = 0xABBA;
    
    public static final boolean PER_PIXEL_TRANSP;
    public static final boolean PER_PIXEL_TRANSLU;
    public static final boolean TRANSLUCENT;
    
    public static final int CHOSEN_OPTION;
    
    static {
        //wow look at the length of that line...
        PER_PIXEL_TRANSP = GraphicsEnvironment.getLocalGraphicsEnvironment()
                           .getDefaultScreenDevice()
                           .isWindowTranslucencySupported(
                                GraphicsDevice.WindowTranslucency
                                .PERPIXEL_TRANSPARENT
                            );
        TRANSLUCENT = GraphicsEnvironment.getLocalGraphicsEnvironment()
                           .getDefaultScreenDevice()
                           .isWindowTranslucencySupported(
                                GraphicsDevice.WindowTranslucency
                                .TRANSLUCENT
                            );
        PER_PIXEL_TRANSLU = GraphicsEnvironment.getLocalGraphicsEnvironment()
                           .getDefaultScreenDevice()
                           .isWindowTranslucencySupported(
                                GraphicsDevice.WindowTranslucency
                                .PERPIXEL_TRANSLUCENT
                            );
        if(IS_RECORDING)
            Assert.Assert(PER_PIXEL_TRANSLU||TRANSLUCENT||PER_PIXEL_TRANSP, "no transparency supported on this system.");
        CHOSEN_OPTION = PER_PIXEL_TRANSP?PER_PIXEL_TRANSP_VAL
                      :PER_PIXEL_TRANSLU?PER_PIXEL_TRANSLU_VAL
                            :TRANSLUCENT?TRANSLUCENT_VAL
                                      :-1;//will never happen due to assertion
    }
    
//    public static boolean NON_STOP_MODE = false;
    public static Integer NON_STOP_KEY  = null;
//    public static final long BEGIN_TIME = System.currentTimeMillis();
    public static int RECORD_OPTION;
    /**
     * regular, non-nonstop.
     */
    public static int RECORD_OPTION_DEFAULT       = 0xDEADBEEF;
    public static int RECORD_OPTION_EXIT_ON_MOUSE = 0xDEADBEF0;
    public static int RECORD_OPTION_ON_KEY        = 0xDEADBEF1;
    
}
