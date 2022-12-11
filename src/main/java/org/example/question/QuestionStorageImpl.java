package org.example.question;

import org.example.QuestionType;
import org.example.domain.Question;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

/**
 * Класс который взаимодействует с postgreSQl
 */
public class QuestionStorageImpl implements QuestionsStorage {

    private Connection connection;

    public QuestionStorageImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * Достает рандомный вопрос из баззы данных по заданной теме
     * @param questionType
     * @throws SQLException
     */
    @Override
    public Question getRandomQuestionByTheme(QuestionType questionType) throws SQLException {
        String sql = String.format("select * from questions where theme = '%s' order by random() limit 1", questionType.toString());
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery(sql);
        Question question = null;
        if (set.next()) {
            var part = set.getString("question_part");
            Array responses = set.getArray("response_options");
            List<String> responseList = Arrays.stream(((String[]) responses.getArray())).toList();
            var answer = set.getString("answer");
            question = new Question(part, responseList, answer);
        }
        return question;
//            throw new NullPointerException(); // TODO придумать ошибку
    }

    @Override
    public void addQuestion(Question question) {

    }

    @Override
    public List<Question> getAllQuestion() {
        return null;
    }
}
