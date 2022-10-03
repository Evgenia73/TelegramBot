package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ActionsHandler {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String readFile(String filename){
        String fileContent="";
        try {
            Scanner scanner = new Scanner(new File((String.format("src%1$smain%1$sresources%1$s%2$s", File.separator,filename))));
            scanner.useDelimiter("\\Z");
            fileContent+= scanner.next();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileContent;

    }
    public static String  processUserMessage(String message){

        String result = "";
        switch (message) {
            case "Привет" -> result += "Привет!";
            case "help" -> result += readFile("HelpMessage.txt");
            default -> result += "Я не знаю такой команды(";
        }
        return result;
    }

}

