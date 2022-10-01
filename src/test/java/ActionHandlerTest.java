import org.example.ActionsHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActionHandlerTest {

    @Test
    void testHelpMessage(){
        String result = ActionsHandler.readFile("HelpMessage.txt");
        assertEquals(result,ActionsHandler.processUserMessage("help"));
    }
}
