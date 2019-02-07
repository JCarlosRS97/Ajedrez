package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RecursosWeb {
    private static String loginWeb;
    private static String errorLoginWeb;
    static {
        StringBuilder str = new StringBuilder();
        String line;
        try {
            loginWeb = loadFile("Resources/PWebLogin.html");
            errorLoginWeb = loadFile("Resources/PWebLoginError.html");

        } catch (IOException e) {
            System.out.println("No ha sido posible cargar los recursos.");
            System.exit(-1);
        }
    }

    public static String getLoginWeb() {
        return loginWeb;
    }

    public static String getErrorLoginWeb() {
        return errorLoginWeb;
    }

    private static String loadFile(String path) throws IOException{
        StringBuilder str = new StringBuilder();
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(path));
        while((line = reader.readLine()) != null){
            str.append(line).append('\n');
        }
        return str.toString();
    }
}
