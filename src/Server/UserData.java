package Server;

import java.io.*;
import java.util.Arrays;

public class UserData {
    private static File dir;
    static {
        dir = new File("Data");
        if(!dir.isDirectory()){
            dir.mkdir();
        }
    }

    public static Player loadUser(String user, String password){
        String[] ficheros = dir.list();
        if (ficheros == null){
            return null;
        }
        int n = Arrays.binarySearch(ficheros, user);
        String params[];
        if(n < 0)
            return null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dir.getPath() + "/" + ficheros[n]));
            params = reader.readLine().split(" ");
            reader.close();
        } catch (IOException e) {
            return null;
        }
        if(params[0].equals(password))
            return new Player(user, null, Integer.valueOf(params[1]));
        else
            return null;
    }

    public static void saveUser(Player player) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(dir.getPath() + "/" + player.getUser()));
        try {
            String[] params = reader.readLine().split(" ");
            reader.close();
            PrintWriter writer = new PrintWriter(dir.getPath() + "/" + player.getUser());
            writer.println(params[0] + " " +  player.getPuntuacion());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean saveNewUser(String user, String password) throws FileNotFoundException {
        String[] ficheros = dir.list();
        if(Arrays.binarySearch(ficheros, user) >= 0)
            return false;
        PrintWriter writer = new PrintWriter(dir.getPath() + "/" + user);
        writer.println(password + " 1500");
        writer.close();
        return true;
    }

    private static void display(){
        String[] ficheros = dir.list();

        if (ficheros == null) {
            System.out.println( "Either dir does not exist or is not a directory");
        } else {
            Arrays.stream(ficheros).forEach(System.out::println);
        }
    }

    public static void main(String[] args) {
        try {
            UserData.saveUser(new Player("ppe", null, 1000));
            UserData.saveUser(new Player("alfonso", null, 1003));
            UserData.saveUser(new Player("isabel", null, 1003));
            UserData.saveUser(new Player("leonardo", null, 1003));
            UserData.saveUser(new Player("curro", null, 1003));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        UserData.display();
    }

}
