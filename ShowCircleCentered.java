import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ShowCircleCentered extends Application {

   
    public void start(Stage primaryStage) {
        Pane pane = new Pane();

        Circle circle = new Circle();

        circle.setCenterX(100);
        circle.setCenterY(100);
        circle.setRadius(50);
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.ALICEBLUE);

        pane.getChildren().add(circle);

        Scene scene= new Scene( pane , 200, 200);
        primaryStage.setTitle("ShowCircleCentered");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}