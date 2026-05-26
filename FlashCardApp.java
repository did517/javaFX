import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FlashCardApp extends Application {

    private String firstLanguage = "";
    private String learningLanguage = "";
    private String level = "";
    private Timeline timer;
    private Label timerLabel = new Label();
    private int timeLeft;
    private int timeLimit = 30;
    private int currentIndex = 0;

    String[] English = {"Hello", "Thank you", "Water", "Sun", "House", "Bread", "Cloud", "Book", "Sea", "School"};
    String[] French  = {"Bonjour", "Merci", "Eau", "Soleil", "Maison", "Pain", "Nuage", "Livre", "Mer", "Ecole"};
    String[] Spanish = {"Hola", "Gracias", "Agua", "Sol", "Casa", "Pan", "Nube", "Libro", "Mar", "Escuela"};
    private String[] fromLang;
    private String[] toLang;
    private List<String> correctAnswers = new ArrayList<>();
    private List<String> wrongAnswers = new ArrayList<>();

    private Label questionLabel = new Label();
    private Label feedbackLabel = new Label();
    private TextField answerField = new TextField();
    private Button submitButton = new Button("Submit");
    private VBox levelBox; // ← IMPORTANT

    public void start(Stage stage) {

        // choice native lang
        Label message1 = new Label("Select your native language");
        message1.setFont(new Font(20));
        Button btEnglish = new Button("English");
        Button btFrench  = new Button("French");
        Button btSpanish = new Button("Spanish");
        VBox firstChoiceBox = new VBox(10, message1, btEnglish, btFrench, btSpanish);

        // choice of learning lang
        Label message2 = new Label("Select the language you are learning");
        message2.setFont(new Font(20));
        Button btnEnglish = new Button("English");
        Button btnFrench  = new Button("French");
        Button btnSpanish = new Button("Spanish");
        VBox secondChoiceBox = new VBox(10, message2, btnEnglish, btnFrench, btnSpanish);
        secondChoiceBox.setVisible(false);
        secondChoiceBox.setManaged(false);

        //lang level
        Label levelLabel = new Label("Select your level");
        levelLabel.setFont(new Font(20));
        Button btnBeginner     = new Button("Beginner (30s)");
        Button btnIntermediate = new Button("Intermediate (20s)");
        Button btnAdvanced     = new Button("Advanced (15s)");
        levelBox = new VBox(10, levelLabel, btnBeginner, btnIntermediate, btnAdvanced);
        levelBox.setVisible(false);
        levelBox.setManaged(false);

        // listenners for first choice
        btEnglish.setOnAction(e -> selectFirstLanguage("English", firstChoiceBox, secondChoiceBox));
        btFrench.setOnAction(e -> selectFirstLanguage("French", firstChoiceBox, secondChoiceBox));
        btSpanish.setOnAction(e -> selectFirstLanguage("Spanish", firstChoiceBox, secondChoiceBox));
        // listenners second choice
        btnEnglish.setOnAction(e -> startQuiz("English", secondChoiceBox));
        btnFrench.setOnAction(e -> startQuiz("French", secondChoiceBox));
        btnSpanish.setOnAction(e -> startQuiz("Spanish", secondChoiceBox));
        // listenners lang level
        btnBeginner.setOnAction(e -> startLevel("Beginner", 30));
        btnIntermediate.setOnAction(e -> startLevel("Intermediate", 20));
        btnAdvanced.setOnAction(e -> startLevel("Advanced", 15));
        //on masque les questions
        questionLabel.setVisible(false);
        answerField.setVisible(false);
        submitButton.setVisible(false);
        feedbackLabel.setVisible(false);
        timerLabel.setFont(new Font(18));
        timerLabel.setVisible(false);
        VBox root = new VBox(
                30,
                firstChoiceBox,
                secondChoiceBox,
                levelBox,
                timerLabel, 
                questionLabel,
                answerField,
                submitButton,
                feedbackLabel
        );

        stage.setScene(new Scene(root, 480, 450));
        stage.setTitle("FlashCard App");
        message1.setTextFill(Color.BLACK);
        message2.setTextFill(Color.BLACK);
        stage.show();
    }
   
    private void selectFirstLanguage(String language, VBox firstBox, VBox secondBox) {
        firstLanguage = language;
        firstBox.setVisible(false);
        firstBox.setManaged(false);
        secondBox.setVisible(true);
        secondBox.setManaged(true);
    }

    private void startQuiz(String language, VBox secondBox) {
        learningLanguage = language;

        if (firstLanguage.equals(learningLanguage)) {
            feedbackLabel.setText("Same language selected.");
            feedbackLabel.setVisible(true);
            return;
        }
        secondBox.setVisible(false);
        secondBox.setManaged(false);
        levelBox.setVisible(true);
        levelBox.setManaged(true);
    }

    private void startLevel(String chosenLevel, int seconds) {
        level = chosenLevel;
        timeLimit = seconds;
        levelBox.setVisible(false);
        levelBox.setManaged(false);
        setLanguages();
        showQuestion();
    }

    private void setLanguages() {
        fromLang = switch (firstLanguage) {
            case "French"  -> French;
            case "Spanish" -> Spanish;
            default        -> English;
        };
        toLang = switch (learningLanguage) {
            case "French"  -> French;
            case "Spanish" -> Spanish;
            default        -> English;
        };
    }

private void showQuestion() {
    // ici on affiche les questions
    questionLabel.setVisible(true);
    answerField.setVisible(true);
    submitButton.setVisible(true);
    feedbackLabel.setVisible(true);
    timerLabel.setVisible(true);

    questionLabel.setText("Translate: " + fromLang[currentIndex]);
    timeLeft = timeLimit;
    timerLabel.setText("Time left: " + timeLeft + "seconds");
    if (timer != null) timer.stop();
    timer = new Timeline(
        new KeyFrame(Duration.seconds(1), e -> {
            timeLeft--;
            timerLabel.setText("Time left: " + timeLeft + "seconds");
            if (timeLeft <= 0) {
                timer.stop();
                feedbackLabel.setText("Time's up");
                playSound("C:\\Users\\nkdxd\\OneDrive\\Desktop\\didi_File\\HelloFX\\perdu.wav");
                wrongAnswers.add(fromLang[currentIndex] + " → " + toLang[currentIndex]);
                currentIndex++;
                nextQuestion();
            }
        })
    );

    timer.setCycleCount(Timeline.INDEFINITE);
    timer.play();
    submitButton.setOnAction(e -> checkAnswer());
}

    private void checkAnswer() {
        if (timer != null) timer.stop();
        String userAnswer = answerField.getText().trim();
        String correct = toLang[currentIndex];
        if (userAnswer.equalsIgnoreCase(correct)) {
            correctAnswers.add(fromLang[currentIndex] + " → " + correct);
            feedbackLabel.setText("Correct!");
            playSound("C:\\Users\\nkdxd\\OneDrive\\Desktop\\didi_File\\HelloFX\\gagne.wav");
        } else {
            wrongAnswers.add(fromLang[currentIndex] + " → " + correct);
            feedbackLabel.setText("Wrong!!, Correct: " + correct);
            playSound("C:\\Users\\nkdxd\\OneDrive\\Desktop\\didi_File\\HelloFX\\perdu.wav");
        }
        answerField.clear();
        currentIndex++;
        nextQuestion();
    }


    private void nextQuestion() {
        if (currentIndex < fromLang.length) {
            showQuestion();
        } else {
            showResults();
        }
    }

    private void showResults() {
        questionLabel.setText("Quiz finished!");
        submitButton.setDisable(true);
        answerField.setDisable(true);
        StringBuilder result = new StringBuilder();
        result.append("Level: ").append(level).append("\n");
        result.append("Correct: ").append(correctAnswers.size()).append("\n");
        result.append("Wrong: ").append(wrongAnswers.size()).append("\n\n");
        for (String w : wrongAnswers) result.append(w).append("\n");
        feedbackLabel.setText(result.toString());
    }

    private void playSound(String path) {
        AudioClip sound = new AudioClip(new File(path).toURI().toString());
        sound.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


