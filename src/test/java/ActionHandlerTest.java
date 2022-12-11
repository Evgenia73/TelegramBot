import junit.framework.TestCase;
import org.example.ActionsHandler;
import org.example.TestClasses.QuestionStorageTest;
import org.example.domain.AnswerMessage;
import org.example.domain.Question;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


public class ActionHandlerTest extends TestCase{

    public ActionsHandler actionsHandler = new ActionsHandler(new QuestionStorageTest());

    @Test
    public void testHelpMessage(){
        String result = actionsHandler.readFile("HelpMessage.txt");
        assertEquals(result, actionsHandler.processUserMessage("help", "1").getStringResponse());
    }
    public void testHelloMessage(){
        assertEquals("Привет!", actionsHandler.processUserMessage("Привет", "1").getStringResponse());
    }

    public void testDefaultMessage(){
        assertEquals("Я не знаю такой команды(", actionsHandler.processUserMessage("wwr", "1").getStringResponse());
    }

    public void testChoosingTheme(){
        List<String> themes = new ArrayList<>();
        themes.add("JS");
        themes.add("История");
        themes.add("Математика");
        themes.add("Музыка");
        AnswerMessage answerMessage = actionsHandler.processUserMessage("Выбрать тему", "1");
        assertEquals("Выберите понравившуюся тему", answerMessage.getStringResponse());
        assertEquals(themes, answerMessage.getResponseList());
    }

    public void testQuestionMessage(){
        Question question = new Question("Вопрос", List.of("1", "2", "3"), "3");
        AnswerMessage answerMessage = actionsHandler.processUserMessage("test", "1");
        assertEquals(question.getQuestionPart(), answerMessage.getStringResponse());
        assertEquals(question.getResponseOptions(), answerMessage.getResponseList());
    }

    public void testCorrectTheme(){
        actionsHandler.processUserMessage("Выбрать тему", "1");
        assertEquals("Вы выбрали тему История! Чтобы начать введите команду test",
                actionsHandler.processUserMessage("История", "1").getStringResponse());
        actionsHandler.processUserMessage("test", "1");
        assertEquals("Верно", actionsHandler.processUserMessage("3","1").getStringResponse());
    }
}
