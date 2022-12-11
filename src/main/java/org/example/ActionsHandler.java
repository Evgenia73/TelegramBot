package org.example;
import JDBC.MyConnection;
import org.example.domain.AnswerMessage;
import org.example.domain.Question;
import org.example.domain.UserContext;
import org.example.question.QuestionStorageImpl;
import org.example.question.QuestionsStorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.*;


public class ActionsHandler {
    private String message;
    private final HashMap<String, User> users;
    private final QuestionsStorage storage;

    private List<String> themes;


    public ActionsHandler() {
        users = new HashMap<>();
        storage = new QuestionStorageImpl(MyConnection.getMySQLConnection());
        themes = Arrays.stream(QuestionType.values()).map(Enum::toString).toList();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Чтение данных из файла
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
     * Обрабатывает сообщение от пользователя и возращает ответ, в зависимости от контекста сообщения данного пользователя
     * @param message
     * @param userId
     */
    public AnswerMessage processUserMessage(String message, String userId){
        User user;
        if (!users.containsKey(userId)) {
            user = new User(userId);
            users.put(userId, user);
        }
        else
            user = users.get(userId);

        user.setCurrentMessage(message);
        AnswerMessage result = new AnswerMessage();

        switch (message) {
            case "Привет":
                    result.setStringResponse("Привет!");
                    break;
            case "help":
                result.setStringResponse(readFile("HelpMessage.txt"));
                break;
            case "Выбрать тему": {
                result.setStringResponse("Выберите понравившуюся тему");
                result.setResponseList(themes);
                user.setContext(UserContext.ChoosingTheme);
                break;
            }
            case "test":
                user.setContext(UserContext.AnsweringQuestion);
                try {
                    Question q = storage.getRandomQuestionByTheme(user.getQuestionType());
                    user.setCurrentQuestion(q);
                    result.setResponseList(q.getResponseOptions());
                    result.setStringResponse(q.getQuestionPart());
                } catch (SQLException e) {
                    result.setStringResponse("Ошибка");
                }
                break;
            default:
                    if (user.getContext() == UserContext.ChoosingTheme) {
                        // TODO проверка коректности темы
                        user.setQuestionType(QuestionType.valueFromString(message));
                        user.setContext(UserContext.AnsweringQuestion);
                        result.setStringResponse(String.format("Вы выбрали тему %s! Чтобы начать введите команду test",
                                message));
                    }
                    else if (user.getContext() == UserContext.AnsweringQuestion) {
                        if (user.getCurrentQuestion().checkAnswer(message))
                            result.setStringResponse("Верно");
                        else
                            result.setStringResponse("Неверно");
                    } else
                        result.setStringResponse("Я не знаю такой команды(");
                    break;
        }
        return result;
    }
    public HashMap<String, User> getUsers() {
        return users;
    }
}

