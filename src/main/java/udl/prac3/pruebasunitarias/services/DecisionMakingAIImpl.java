package udl.prac3.pruebasunitarias.services;

import udl.prac3.pruebasunitarias.data.ProductID;
import udl.prac3.pruebasunitarias.data.exceptions.ProductIDException;
import udl.prac3.pruebasunitarias.services.exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class DecisionMakingAIImpl implements DecisionMakingAI {

    private boolean initialized = false;

    @Override
    public void initDecisionMakingAI() throws AIException {
        initialized = true;
        System.out.println("DecisionMakingAI inicializada correctamente.");
    }

    @Override
    public String getSuggestions(String prompt) throws BadPromptException {

        if (!initialized) {
            throw new BadPromptException("La IA no ha sido inicializada");
        }

        if (prompt == null || prompt.trim().isEmpty()) {
            throw new BadPromptException("Prompt inválido o vacío");
        }

        /*
         * Texto simulado devuelto por la IA
         */
        return "INSERT; MODIFY; ELIMINATE";
    }

    @Override
    public List<Suggestion> parseSuggest(String aiAnswer) {

        List<Suggestion> suggestions = new ArrayList<>();

        if (aiAnswer == null || aiAnswer.isEmpty()) {
            return suggestions;
        }

        try {
            // INSERT
            ProductID insertID = new ProductID("000000000001");
            String[] insertGuidelines = {
                    "Tomar 1 comprimido al día",
                    "Preferiblemente por la mañana"
            };

            suggestions.add(new Suggestion(
                    Suggestion.SuggestionType.INSERT,
                    insertID,
                    insertGuidelines
            ));

            // MODIFY
            ProductID modifyID = new ProductID("000000000002");
            String[] modifyGuidelines = {
                    "Tomar 1 comprimido cada 8 horas"
            };

            suggestions.add(new Suggestion(
                    Suggestion.SuggestionType.MODIFY,
                    modifyID,
                    modifyGuidelines
            ));

            // ELIMINATE
            ProductID eliminateID = new ProductID("000000000003");
            suggestions.add(new Suggestion(eliminateID));

        } catch (ProductIDException e) {
            // En prácticas basta con ignorar o registrar
            System.err.println("Error creando ProductID en DecisionMakingAI: " + e.getMessage());
        }

        return suggestions;
    }
}
