package udl.prac3.pruebasunitarias.services;

import udl.prac3.pruebasunitarias.services.exceptions.*;
import java.util.List;

public interface DecisionMakingAI {

    void initDecisionMakingAI() throws AIException;

    String getSuggestions(String prompt) throws BadPromptException;

    List<Suggestion> parseSuggest(String aiAnswer);
}