package org.example;

import org.example.domain.Question;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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
    public String  processUserMessage(String message){

        String result = "";
        switch (message) {
            case "Привет" -> result += "Привет!";
            case "help" -> result += readFile("HelpMessage.txt");
            default -> result += "Я не знаю такой команды(";
        }
        return result;
    }
    public static List<Question> questions (String data){
        List<String> ques = new ArrayList<>();
        ques = List.of(data.split("#"));
        List<Question> questions = new ArrayList<>();
        for(String q : ques){
//            question[i] = new ArrayList<String>();
            String[] a = q.split("\\*"); //TODO грамотно обозвать
            String questionPart = a[0];
            List<String> variables = Arrays.stream(a).skip(1).toList();
            String answer = Arrays.stream(a).filter(el -> el.contains("+")).findFirst().orElseThrow();

            questions.add(new Question(questionPart, variables, answer));
        }

        return questions;
    }

}

