package battletank.controls;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ActionKeyParserTest {

    @Test
    void createActionListenersFromDefault() {

        ActionKeyParser actionKeyParser = new ActionKeyParser();

        Map<Action, List<String>> myMap = actionKeyParser.getActionMapping();

        assertEquals("w_p",myMap.get(Action.MOVE_FORWARD).get(0));
    }
}