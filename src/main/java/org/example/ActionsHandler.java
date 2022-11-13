package org.example;
import org.example.domain.Question;
import org.example.question.QuestionDAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ActionsHandler {
    private String message;
    private final HashMap<String, User> users;
    private final QuestionDAO questionDAO;

    public ActionsHandler() {
        users = new HashMap<>();
        questionDAO = new QuestionDAO(readFile("test_all.txt"));
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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
    public String processUserMessage(String message, String userId){
        if (!users.containsKey(userId)) users.put(userId, new User(userId));
        users.get(userId).setCurrentMessage(message);
        String result = "";
        switch (message) {
            case "Привет" -> result += "Привет!";
            case "help" -> result += readFile("HelpMessage.txt");
            default -> result += "Я не знаю такой команды(";
        }
        return result;
    }

    public Optional<Question> processKeyBoard(String chatId, String message) {
        Question currentQuestion = questionDAO.getRandomQuestion();
        users.get(chatId).setCurrentQuestion(currentQuestion);
        if (message.equals("test_all")) {
            return Optional.of(getUsers().get(chatId).getCurrentQuestion());
        }
        else if (message.equals("test_random")) {
            return Optional.of(getUsers().get(chatId).getCurrentQuestion());
        }
        else return Optional.empty();
    }

    public HashMap<String, User> getUsers() {
        return users;
    }
}

