//package org.example.question;
//
//import org.example.ActionsHandler;
//import org.example.QuestionType;
//import org.example.domain.Question;
//
//import java.util.*;
//
//public class QuestionDAO {
//    private int currentQuestion;
//    private List<Question> questions;
//    private ArrayList<String> answers = null;
//
//    public List<Question> createQuestions(String data) {
////        List<String> ques = new ArrayList<>();
//        String [] ques = data.split("\r\n#");
//        List<Question> questions = new ArrayList<>();
//        String type = ques[0];
//        QuestionType questionType = QuestionType.valueOf(QuestionType.class, type);
//        List<String> listQuest = new ArrayList<>(Arrays.stream(ques).skip(1).toList());
//        for (String q : listQuest) {
//            String[] partsList = q.split("\n\\*\r\n");
//            String questionPart = partsList[0];
//            List<String> variables = new ArrayList<>(Arrays.stream(partsList).skip(1).toList());
//            String answer = Arrays.stream(partsList).filter(el -> el.contains("+")).findFirst().orElseThrow();
//            int index = variables.indexOf(answer);
//            variables.set(index, answer.replaceAll(".$", ""));
//            questions.add(new Question(questionPart, variables, answer.replaceAll(".$", ""), questionType));
//        }
//        return questions;
//    }
//
//    public QuestionDAO(){}
//    public QuestionDAO(String data) {
//        this.currentQuestion = 0;
//        this.questions = createQuestions(data);
//        answers = new ArrayList<String>();
//    }
//    public int getCurrentQuestion() {
//        return currentQuestion;
//    }
//
//
//    public Question getRandomQuestion() {
//        final Random random = new Random();
//        int numberOfQuestion = random.nextInt(questions.size());
//        return questions.get(numberOfQuestion);
//    }
//
//    public Question getNextQuestion() {
//        return questions.get(currentQuestion++ % questions.size());
//    }
//}
