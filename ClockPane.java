import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;

public class ClockPane extends Pane {
    private int hour;
    private int minute;
    private int second;
    private double radius;
    private double centerX;
    private double centerY;
    private BooleanProperty hourHandVisible = new SimpleBooleanProperty(true);
    private BooleanProperty minuteHandVisible = new SimpleBooleanProperty(true);
    private BooleanProperty secondHandVisisble = new SimpleBooleanProperty(true);

     public ClockPane(){
        setPrefSize(250,250);
        setTime((int)(Math.random()*12), (int)(Math.random()*60), (int)(Math.random()*60));
    }        
    public ClockPane(int hour, int minute, int second){
        setPrefSize(250,250);
        setTime(hour, minute, second);
    }
    public void setTime(int hour , int minute, int second){
       this.hour=hour;
      this.minute=minute;
      this.second=second;
      paintClock(); 
    }
    public int getHour(){
        return hour;
    }
    public int getMinute(){
        return minute;
    }
    public int getSecond(){
        return second;
    }
    public Boolean isHourHandVisible(){
        return hourHandVisible.get();
    }
    public void setHourHandVisible( Boolean visible){
        hourHandVisible.set(visible);
        paintClock();
    }
    public BooleanProperty hourHandVisibleProperty(){
        return hourHandVisible;
    }
    public Boolean isMinuteHandVisible(){
        return minuteHandVisible.get();
    }
    public void setMinuteHandVisible(Boolean visible){
        minuteHandVisible.set(visible);
        paintClock();
    }
    public BooleanProperty minuteHandVisibleProperty(){
        return minuteHandVisible;
    }
    public Boolean isSecondHandVisible(){
        return secondHandVisisble.get();
    }
    public void setSecondHandVisible(Boolean visible){
        secondHandVisisble.set(visible);
        paintClock();
    }
    public BooleanProperty secondHandVisibleProperty(){
        return secondHandVisisble;
    }
    protected void paintClock(){
        getChildren().clear();
        double width = getWidth() > 0 ? getWidth() : getPrefWidth();
        double height = getHeight() > 0 ? getHeight() : getPrefHeight();
        centerX = width/2;
        centerY = height/2;
        radius = Math.min(width, height)* 0.8/2;
        Circle circle = new Circle( centerX, centerY, radius);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        getChildren().add(circle);
        
        for(int i =1; i<=12; i++){
            double angle= Math.toRadians(( i-3)* 30);
            double x =centerX +(radius -20)*Math.cos(angle);
            double y = centerY +(radius -20)*Math.sin(angle) +5;
            javafx.scene.text.Text text =new javafx.scene.text.Text(x-6, y, String.valueOf(i));
            getChildren().add(text);
        }

        if(isHourHandVisible()){
            double hourLength = radius*0.5;
            double hourAngle =Math.toRadians((hour % 12 + minute/60)*30-90);
            Line hourHand=new Line (centerX, centerY , centerX + hourLength * Math.cosh(hourAngle), centerY + hourLength * Math.sin(hourAngle));
            hourHand.setStrokeWidth(5);
            getChildren().add(hourHand);
        }
        if(isMinuteHandVisible()){
            double minuteLength = radius*0.7;
            double minuteAngle = Math.toRadians((minute)*6-90);
            Line minuteHand = new Line(centerX, centerY, centerX + minuteLength*Math.cos(minuteAngle), centerY + minuteLength*Math.sin(minuteAngle));
            minuteHand.setStrokeWidth(3);
            minuteHand.setStroke(Color.BLUE);
            getChildren().add(minuteHand);
        }
        if(isSecondHandVisible()){
            double secondLength=radius *0.8;
            double secondAngle= Math.toRadians((second)*6-90);
            Line secondHand =new Line(centerX,centerY, centerX +secondLength*Math.cos(secondAngle),centerY + secondLength*Math.sin(secondAngle));
            secondHand.setStroke(Color.RED);
            getChildren().add(secondHand);
        }
    }
    protected void layoutChildren(){
        super.layoutChildren();
        paintClock();
    }
}