package org.example.question;

import org.example.QuestionType;
import org.example.domain.Question;

import java.sql.SQLException;
import java.util.List;

public interface QuestionsStorage {
    public Question getRandomQuestionByTheme(QuestionType questionType) throws SQLException;

    public void addQuestion(Question question);

    public List<Question> getAllQuestion();
}
