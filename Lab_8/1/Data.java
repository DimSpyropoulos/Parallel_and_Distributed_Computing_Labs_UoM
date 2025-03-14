public class Data {
    private String op, message;
    private int k;

    public Data() {
        op = "";
        message = "";
        k = 0;
    }

    public void set_op(String op) {
        this.op = op;
    }

    public String get_op(){
        return op;
    }

    public void set_message(String m) {
        message = m;
    }
    
    public String get_message() {
        return message;
    }

    public void set_k(String kString) {
        k = Integer.parseInt(kString);
    }
    
    public int get_k() {
        return k;
    }


    public String decode(){
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

    public String encode(){
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

    public String opperation() {
        String result = "";

        switch (this.op) {
            case "lower": result = message.toLowerCase();                
                break;
            case "upper": result = message.toUpperCase();
                break;
            case "encode": result = encode();
                break;
            case "decode": result = decode();
                break;        
            default: result = "Unknown opperation";
                break;
        }
        
        return result;
    }
    
}
