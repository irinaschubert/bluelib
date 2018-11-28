package hilfsklassen;

public class IntHelfer {
public static Boolean istInteger(String input) {
	Boolean r = false;
	try {
	     Integer.parseInt(input);
	     r = true;
	}
	catch (NumberFormatException e) {
		r = false;
	}
	
	return r;
}

public static int stringZuInt(String input) {
	     return Integer.parseInt(input);

}

public static boolean istDecimal(String input) {
	Boolean r = false;

    if(input.matches("^[0-9]*\\.?[0-9]*$")){
    	r = true;
    }
    
    return r;
    	
}

}
