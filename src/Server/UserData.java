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
        String params[] = null;
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
            return new Player(user, null, 0, null, Integer.valueOf(params[1]));
        else
            return null;
    }

    public static void saveUser(Player player) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(dir.getPath() + "/" + player.getUser()));
        String params[] = null;
        try {
            params = reader.readLine().split(" ");
            reader.close();
            PrintWriter writer = new PrintWriter(dir.getPath() + "/" + player.getUser());
            writer.println(params[1] + " " +  player.getPuntuacion());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveNewUser(String user, String password) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(dir.getPath() + "/" + user);
        writer.println(password + " 1500");
        writer.close();
    }

    public void display(){
        String[] children = dir.list();

        if (children == null) {
            System.out.println( "Either dir does not exist or is not a directory");
        } else {
            for (int i = 0; i< children.length; i++) {
                String filename = children[i];
                System.out.println(filename);
            }
        }
    }

    public static void main(String[] args) {
        UserData userData = new UserData();
        try {
            userData.saveUser(new Player("ppe", null, 0, null, 1000));
            userData.saveUser(new Player("alfonso", null, 0, null, 1003));
            userData.saveUser(new Player("isabel", null, 0, null, 1003));
            userData.saveUser(new Player("leonardo", null, 0, null, 1003));
            userData.saveUser(new Player("curro", null, 0, null, 1003));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        userData.display();
    }

}
