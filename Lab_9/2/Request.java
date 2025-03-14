import java.io.*;
import java.util.*;

public class Request implements Serializable {

   private String opcode, message;
   private int k;
   
   public Request() {
      opcode = "";
      message = "";
      k = 0;
   }

   public void setOpcode(String s){
      opcode = s;
   }

   public void setMessage(String s){
      message = s;
   }

   public void setK(int n){
      k = n;
   }

   public String getOpcode() {
      return opcode;
   }

   public String getMessage() {
      return message;
   }

   public int getK() {
      return k;
   }
}

