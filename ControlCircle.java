
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;                                 
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ControlCircle extends Application {
    CirclePane circlePane = new CirclePane();

    public void start(Stage primaryStage){
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        Button btLeft = new Button("Left");
        Button btRight = new Button("Right");
        Button btUp = new Button("Up");
        Button btDown = new Button("Down");
        hBox.getChildren().add(btLeft);
        hBox.getChildren().add(btRight);
        hBox.getChildren().add(btUp);
        hBox.getChildren().add(btDown);
        btRight.setOnAction(e -> circlePane.Right());
        btLeft.setOnAction(e -> circlePane.Left());
        btUp.setOnAction(e-> circlePane.Up());
        btDown.setOnAction(e -> circlePane.Down());
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(circlePane);
        borderPane.setBottom(hBox);
        BorderPane.setAlignment(hBox,Pos.CENTER);
        Scene scene = new Scene(borderPane, 200, 150);
        primaryStage.setTitle("ControlCircle");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String [] args){
        launch(args);
    }
    
    class CirclePane extends StackPane{
        private Circle circle = new Circle(30);
        public CirclePane(){
            getChildren().add(circle);
            circle.setStroke(Color.BLACK);
            circle.setFill(Color.WHITESMOKE);
        }
        public void Left(){
            circle.setTranslateX(circle.getTranslateX() + 20);
          
            
        }
        public void Right(){
            circle.setTranslateX(circle.getTranslateX() + 20 );
        }
        
        public void Up(){
            circle.setTranslateY(circle.getTranslateY() -20);
        }
        public void Down(){
            circle.setTranslateY(circle.getTranslateY() +20);
        }
    }
    
}