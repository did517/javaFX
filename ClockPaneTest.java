import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class ClockPaneTest extends Application {
    public void start(Stage primaryStage){
        int hour = (int)(Math.random()*12);
        int minute =Math.random()<0.5 ? 0:30;
        ClockPane clock = new ClockPane(hour, minute, 0);
        clock.setSecondHandVisible(false);
        Scene scene = new Scene(clock, 250,250);
        primaryStage.setTitle("ClockPane");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String [] args){
        launch(args);
    }
    
}
