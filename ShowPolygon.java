import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Polygon;

public class ShowPolygon extends Application {
   
    public void start(Stage primaryStage) {

        Scene scene = new Scene(new MyPolygon(), 400, 400);
        primaryStage.setTitle("ShowPolygon"); 
        primaryStage.setScene(scene); 
        primaryStage.show(); 
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class MyPolygon extends Pane {
    private void paint() {

        Polygon polygon = new Polygon();
        polygon.setFill(Color.RED); 
        polygon.setStroke(Color.WHITE); 
        polygon.setStrokeWidth(4); 
        
        
        ObservableList<Double> list = polygon.getPoints();
        
        double centerX = getWidth() / 2, centerY = getHeight() / 2;
        double radius = Math.min(getWidth(), getHeight()) * 0.4;
        

        int sides = 8;
        for (int i = 0; i < sides; i++) {
            list.add(centerX + radius * Math.cos(2 * i * Math.PI / sides));
            list.add(centerY - radius * Math.sin(2 * i * Math.PI / sides));
        }
        getChildren().clear();
        getChildren().add(polygon);
        
        Text stopText = new Text(centerX - 70, centerY + 20, "STOP");
        stopText.setFont(Font.font(60)); 
        stopText.setFill(Color.WHITE); 
        getChildren().add(stopText);
    }

   
    public void setWidth(double width) {
        super.setWidth(width);
        paint(); 
    }

    public void setHeight(double height) {
        super.setHeight(height);
        paint(); 
    }
}