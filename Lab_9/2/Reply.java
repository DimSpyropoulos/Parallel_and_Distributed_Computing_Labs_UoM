import java.io.*;
import java.util.*;

public class Reply implements Serializable {

   private String message;

   public Reply() {
	    message = "";
   }

   public void setMessage(String s) {
      message = s;
   }

   public String getMessage() {
     return message;
   }
   

}

