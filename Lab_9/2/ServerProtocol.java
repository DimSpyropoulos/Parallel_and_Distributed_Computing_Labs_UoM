import java.net.*;
import java.io.*;

public class ServerProtocol {
    
    

	public Reply processRequest(Request r) {
		Reply rep = new Reply();
		System.out.println("Received message from client: " + r.getMessage() + " op: " + r.getOpcode());
		
        if (!r.getOpcode().equals("CLOSE")){
            rep.setMessage(operation(r.getOpcode(), r.getMessage(),r.getK()));
        }
		System.out.println("Send message to client: " + rep.getMessage());
		return rep;
	}

    public String decode(String message, int k){
        StringBuilder result = new StringBuilder();
		for (char character : message.toCharArray()) {
 			if (character != ' ') {
 				int originalAlphabetPosition = character - 'a';
 				int newAlphabetPosition = (originalAlphabetPosition - k+ 26) % 26;
 				char newCharacter = (char) ('a' + newAlphabetPosition);
 				result.append(newCharacter);
 			} else {
 			result.append(character);
 			}
		}
		return result.toString();
    }

    public String encode(String message, int k){
		StringBuilder result = new StringBuilder();
		for (char character : message.toCharArray()) {
 			if (character != ' ') {
 				int originalAlphabetPosition = character - 'a';
 				int newAlphabetPosition = (originalAlphabetPosition + k) % 26;
 				char newCharacter = (char) ('a' + newAlphabetPosition);
 				result.append(newCharacter);
 			} else {
 			result.append(character);
 			}
		}
		return result.toString();
	}

    public String operation(String op, String message, int k) {
        String result = "";

        switch (op) {
            case "lower": result = message.toLowerCase();                
                break;
            case "upper": result = message.toUpperCase();
                break;
            case "encode": result = encode(message,k);
                break;
            case "decode": result = decode(message,k);
                break;        
            default: result = "Unknown opperation";
                break;
        }
        
        return result;
    }
}