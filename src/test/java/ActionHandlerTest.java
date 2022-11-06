import org.example.ActionsHandler;
import org.junit.jupiter.api.Test;
import junit.framework.TestCase;
import static org.example.ActionsHandler.*;

public class ActionHandlerTest extends TestCase{

    public ActionsHandler handler = new ActionsHandler();

    @Test
    public void testHelpMessage(){
        String result = readFile("HelpMessage.txt");
        assertEquals(result,handler.processUserMessage("help"));
    }
    public void testHelloMessage(){
        assertEquals("Привет!", handler.processUserMessage("Привет"));
    }

    public void testDefaultMessage(){
        assertEquals("Я не знаю такой команды(", handler.processUserMessage("wwr"));
    }
}
