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
        System.out.println("✓ DecisionMakingAI inicializada correctamente.");
    }

    @Override
    public String getSuggestions(String prompt) throws BadPromptException {

        if (!initialized) {
            throw new BadPromptException("La IA no ha sido inicializada");
        }

        if (prompt == null || prompt.trim().isEmpty()) {
            throw new BadPromptException("Prompt inválido o vacío");
        }

        // Simular respuesta de IA
        return "Sugerencias: Ajustar dosis, añadir suplemento, eliminar medicamento obsoleto";
    }

    @Override
    public List<Suggestion> parseSuggest(String aiAnswer) {

        List<Suggestion> suggestions = new ArrayList<>();

        if (aiAnswer == null || aiAnswer.isEmpty()) {
            return suggestions;
        }

        try {
            // INSERT - Formato correcto según el enunciado
            ProductID insertID = new ProductID("123456789001");
            String[] insertGuidelines = {
                    "BEFORELUNCH",      // dayMoment
                    "30",               // duration (días)
                    "2",                // dose
                    "1",                // frequency
                    "DAY",              // FqUnit
                    "Take with water"   // instructions
            };
            suggestions.add(new Suggestion(
                    Suggestion.SuggestionType.INSERT,
                    insertID,
                    insertGuidelines
            ));

            // MODIFY - Solo los campos que cambian
            ProductID modifyID = new ProductID("123456789002");
            String[] modifyGuidelines = {
                    "",     // dayMoment (sin cambios)
                    "",     // duration (sin cambios)
                    "3",    // dose (cambio a 3)
                    "",     // frequency (sin cambios)
                    "",     // FqUnit (sin cambios)
                    ""      // instructions (sin cambios)
            };
            suggestions.add(new Suggestion(
                    Suggestion.SuggestionType.MODIFY,
                    modifyID,
                    modifyGuidelines
            ));

            // ELIMINATE
            ProductID eliminateID = new ProductID("123456789003");
            suggestions.add(new Suggestion(eliminateID));

            System.out.println("✓ " + suggestions.size() + " sugerencias parseadas.");

        } catch (ProductIDException e) {
            System.err.println("Error creando ProductID: " + e.getMessage());
        }

        return suggestions;
    }
}