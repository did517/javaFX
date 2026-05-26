import java.io.File;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.sound.sampled.*;

public class Countdown extends Application {
    private Timeline timeline;
    private int seconds;

    public void start(Stage stage) {

        TextField input = new TextField();
        input.setPromptText("Enter seconds");
        Label display = new Label("0");
        HBox root = new HBox(10, input, display);

        input.setOnAction(e -> {
            try {
                seconds = Integer.parseInt(input.getText());
                display.setText(String.valueOf(seconds));

                if (timeline != null) {
                    timeline.stop();
                }

                timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1), ev -> {
                        seconds--;
                        display.setText(String.valueOf(seconds));

                        if (seconds <= 0) {
                            timeline.stop();
                            display.setText("Time's up!");
                            playSound("C:\\Users\\nkdxd\\OneDrive\\Desktop\\didi_File\\HelloFX\\Lyra.wav");

                        }
                    })
                );

                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.play();

            } catch (NumberFormatException ex) {
                display.setText("Invalid");
            }
        });

        stage.setScene(new Scene(root, 300, 50));
        stage.setTitle("Countdown");
        stage.show();
    }

    public static void playSound(String lyra) {
    try {
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("Lyra.wav"));
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.loop(Clip.LOOP_CONTINUOUSLY); 
        clip.start();
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
        e.printStackTrace();
    }
}


    public static void main(String[] args) {
        launch();
    }
}


