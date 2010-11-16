package dk.tdc.sandbox.akka;

public class Util {


    public static String filterOutNumbers(final String s) {
        StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0; i < s.length() ; i++) {
            c = s.charAt(i);

            if (Character.isLetter(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
