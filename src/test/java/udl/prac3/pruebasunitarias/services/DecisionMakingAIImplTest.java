package udl.prac3.pruebasunitarias.services;

import org.junit.jupiter.api.*;
import udl.prac3.pruebasunitarias.data.*;
import udl.prac3.pruebasunitarias.services.exceptions.BadPromptException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DecisionMakingAIImplTest {

    private DecisionMakingAIImpl ai;

    @BeforeEach
    void setUp() {
        ai = new DecisionMakingAIImpl();
    }

    @Test
    void getSuggestions_withoutInit_throwsBadPrompt() {
        assertThrows(BadPromptException.class, () -> ai.getSuggestions("hola"));
    }

    @Test
    void getSuggestions_withInit_returnsNonEmpty() throws Exception {
        ai.initDecisionMakingAI();
        String ans = ai.getSuggestions("Suggest improvements");
        assertNotNull(ans);
        assertFalse(ans.isBlank());
    }

    @Test
    void parseSuggest_returnsThreeSuggestionsWithExpectedTypes() throws Exception {
        // aiAnswer actualmente se ignora en vuestra impl, pero igual lo probamos
        List<Suggestion> sugg = ai.parseSuggest("whatever");

        assertEquals(3, sugg.size());

        assertEquals(Suggestion.SuggestionType.INSERT, sugg.get(0).getType());
        assertNotNull(sugg.get(0).getProductID());
        assertNotNull(sugg.get(0).getGuidelines());
        assertTrue(sugg.get(0).getGuidelines().length >= 5);

        assertEquals(Suggestion.SuggestionType.MODIFY, sugg.get(1).getType());
        assertNotNull(sugg.get(1).getProductID());
        assertNotNull(sugg.get(1).getGuidelines());

        assertEquals(Suggestion.SuggestionType.ELIMINATE, sugg.get(2).getType());
        assertNotNull(sugg.get(2).getProductID());
    }
}
