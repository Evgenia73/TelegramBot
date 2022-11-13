package org.example.question;

import org.example.ActionsHandler;
import org.example.domain.Question;

import java.util.*;

public class QuestionDAO {
    private int currentQuestion;
    private List<Question> questions;
    private ArrayList<String> answers = null;

    public List<Question> createQuestions(String data) {
        List<String> ques = new ArrayList<>();
        ques = List.of(data.split("#"));
        List<Question> questions = new ArrayList<>();
        for (String q : ques) {
            String[] partsList = q.split("\n\\*\r\n");
            String questionPart = partsList[0];
            List<String> variables = new ArrayList<>(Arrays.stream(partsList).skip(1).toList());
            String answer = Arrays.stream(partsList).filter(el -> el.contains("+")).findFirst().orElseThrow();
            int index = variables.indexOf(answer);
            variables.set(index, answer.replaceAll(".$", ""));
            questions.add(new Question(questionPart, variables, answer.replaceAll(".$", "")));
        }
        return questions;
    }

    public QuestionDAO(){}
    public QuestionDAO(String data) {
        this.currentQuestion = 0;
        this.questions = createQuestions(data);
        answers = new ArrayList<String>();
    }
    public int getCurrentQuestion() {
        return currentQuestion;
    }


    public Question getRandomQuestion() {
        final Random random = new Random();
        int numberOfQuestion = random.nextInt(questions.size());
        return questions.get(numberOfQuestion);
    }

    public Question getNextQuestion() {
        return questions.get(currentQuestion++ % questions.size());
    }
}
