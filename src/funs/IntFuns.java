/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package funs;

/**
 *
 * @author kieda
 */
public class IntFuns {
      public static byte[] split(int i){
        return new byte[]{
            split1(i),
            split2(i),
            split3(i),
            split4(i)
        };
    }
      public static byte split1(int i){
        return (byte)((i>>24)&0xFF);
    } public static byte split2(int i){
        return (byte)((i>>16)&0xFF);
    } public static byte split3(int i){
        return (byte)((i>>8)&0xFF);
    } public static byte split4(int i){
        return (byte)(i&0xFF);
    }
    
    public static int join(byte b1, byte b2, byte b3, byte b4){
        return (b1<<24)&0xFF000000
            |  (b2<<16)&0xFF0000
            |  (b3<<8)&0xFF00
            |  (b4)&0xFF;
    }
    public static String bin(int in){
        char[] buf = new char[32+3];
        for(int i = 0; i < 32+3; i++){
            
            buf[31+3-i] = (i <8 )? ((in>>i&1)==1)?'1':'0' :
                          (i==8 )? ' ' : 
                          (i <17)? ((in>>(i-1)&1)==1)?'1':'0' :
                          (i==17)? ' ' :
                          (i <26)? ((in>>(i-2)&1)==1)?'1':'0' :
                          (i==26)? ' ' :
                          ((in>>(i-3)&1)==1)?'1':'0';
                    //(i%9==0)?' ' : ((in>>i&1)==1)?'1':'0';
        }
        return new String(buf);
    }
}
