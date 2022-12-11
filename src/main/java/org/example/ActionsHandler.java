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

    /**
     * Чтение файла
     * @param filename - имя считываемого файла
     * @return
     */
    public String readFile(String filename){
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

    /**
     * Реакция на шаблонные сообщения, в том числе на неизвестные команды
     * @param message
     * @return
     */
    public String  processUserMessage(String message){

        String result = "";
        switch (message) {
            case "Привет" -> result += "Привет!";
            case "help" -> result += readFile("HelpMessage.txt");
            default -> result += "Я не знаю такой команды(";
        }
        return result;
    }

    /**
     * Формат, в котором будут выводиться варианты ответа для тестирования пользователя и реакция на них
     * @param data
     */
    public List<Question> questions (String data){
        List<String> ques = new ArrayList<>();
        ques = List.of(data.split("#"));
        List<Question> questions = new ArrayList<>();
        for(String q : ques){
//            question[i] = new ArrayList<String>();
            String[] partsList = q.split("\n\\*\r\n");
            String questionPart = partsList[0];
            List<String> variables = new ArrayList<>(Arrays.stream(partsList).skip(1).toList());
            String answer = Arrays.stream(partsList).filter(el -> el.contains("+")).findFirst().orElseThrow();
            int index = variables.indexOf(answer);
            variables.set(index, answer.replaceAll(".$", ""));
            questions.add(new Question(questionPart, variables, answer.replaceAll(".$", "")));
        }

        return questions;
    }

}

