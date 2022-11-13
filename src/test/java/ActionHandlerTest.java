import org.example.ActionsHandler;
import org.example.User;
import org.example.domain.Question;
import org.junit.jupiter.api.Test;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ActionHandlerTest extends TestCase{

    public ActionsHandler actionsHandler = new ActionsHandler();

    @Test
    public void testHelpMessage(){
        String result = actionsHandler.readFile("HelpMessage.txt");
        assertEquals(result, actionsHandler.processUserMessage("help", null));
    }
    public void testHelloMessage(){
        assertEquals("Привет!", actionsHandler.processUserMessage("Привет", null));
    }

    public void testDefaultMessage(){
        assertEquals("Я не знаю такой команды(", actionsHandler.processUserMessage("wwr", null));
    }

    public void testProcessKeyBoard(){
        User user = new User("1");
        actionsHandler.getUsers().put("1", user);
        Question question = new Question();
        question.setAnswer("б) три: for, while и do…while.\r");
        question.setQuestionPart("Какие конструкции для циклов есть в javascript:\r");
        List<String> responseOptions = new ArrayList<>();
        responseOptions.add("а) только одна: for\r");
        responseOptions.add("б) три: for, while и do…while.\r");
        responseOptions.add("в) только две: for и while.\r\n");
        question.setResponseOptions(responseOptions);
        user.setCurrentQuestion(question);
        Optional<Question> optionalQuestion = actionsHandler.processKeyBoard("1", "test_all");
        assertEquals(user.getCurrentQuestion().getQuestionPart(), optionalQuestion.get().getQuestionPart());
        assertEquals(user.getCurrentQuestion().getResponseOptions(), optionalQuestion.get().getResponseOptions());
        assertEquals(user.getCurrentQuestion().getAnswer(), optionalQuestion.get().getAnswer());

    }
}
