package org.example.TestClasses;

import org.example.QuestionType;
import org.example.domain.Question;
import org.example.question.QuestionsStorage;

import java.sql.SQLException;
import java.util.List;

public class QuestionStorageTest implements QuestionsStorage {
    @Override
    public Question getRandomQuestionByTheme(QuestionType questionType) throws SQLException {
        return new Question("Вопрос", List.of("1", "2", "3"), "3");
    }

    @Override
    public void addQuestion(Question question) {

    }

    @Override
    public List<Question> getAllQuestion() {
        return null;
    }
}
