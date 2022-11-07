package org.example.question;

import org.example.domain.Question;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class QuestionDAO {
    private String pathToQuestions;
    private int currentQuestion;
    private int countQuestions;

    private List<Question> questions;

    private boolean isRandom;

    private ArrayList<String> answers = null;

    public String readFile(){
        String fileContent = "";

        try (Scanner scanner = new Scanner(new File((String.format("src%1$smain%1$sresources%1$s%2$s", File.separator, pathToQuestions))))) {
            scanner.useDelimiter("\\Z");
            fileContent += scanner.next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return fileContent;
    }

    private List<Question> readQuestions(String data) {
        List<String> ques = new ArrayList<String>();
        ques = List.of(data.split("#"));
        List<Question> questions = new ArrayList<>();
        for(String q : ques){
//            question[i] = new ArrayList<String>();
            String[] partsList = q.split("\n\\*\r\n");
            String questionPart = partsList[0];
            List<String> variables = new ArrayList<>(Arrays.stream(partsList).skip(1).toList());
            String answer = Arrays.stream(partsList).filter(el -> el.contains("+")).findFirst().orElseThrow();
            int index = variables.indexOf(answer);
            variables.set(index, answer.replaceAll(".$", ""));
            questions.add(new Question(questionPart, variables, answer.replaceAll(".$", "")));
        }

        countQuestions = questions.size();

        return questions;
    }

    public QuestionDAO(String pathToQuestions) {
        this.pathToQuestions = pathToQuestions;
        this.currentQuestion = 0;
        answers = new ArrayList<String>();
        questions = readQuestions(readFile());
    }

    public void setPathToQuestions(String pathToQuestions) {
        this.pathToQuestions = pathToQuestions;
        this.currentQuestion = 0;
        questions = readQuestions(readFile());
    }

    public void setIsRandom(boolean isRandom) {
        this.isRandom = isRandom;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public int getCountQuestions() {
        return countQuestions;
    }

    public boolean getIsRandom() {
        return isRandom;
    }

    public Question getRandomQuestion() {
        final Random random = new Random();

        int numberOfQuestion = random.nextInt() % questions.size();

        isRandom = true;

        return questions.get(numberOfQuestion);
    }

    public Question getNextQuestion() {
        return questions.get(currentQuestion++);
    }

    public void resetCurrentQuestion() {
        currentQuestion = 0;
    }

    public boolean can() {
        return currentQuestion < countQuestions;
    }
}
