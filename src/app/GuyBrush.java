/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Steven
 */
public class GuyBrush extends Application {
    
    private static final Image[] sprites = new Image[]{
        new Image(GuyBrush.class.getResourceAsStream("/resources/stand.png")),
        new Image(GuyBrush.class.getResourceAsStream("/resources/walk1.png")),
        new Image(GuyBrush.class.getResourceAsStream("/resources/walk2.png")),
        new Image(GuyBrush.class.getResourceAsStream("/resources/walk3.png")),
        new Image(GuyBrush.class.getResourceAsStream("/resources/walk4.png")),
        new Image(GuyBrush.class.getResourceAsStream("/resources/walk5.png")),
        new Image(GuyBrush.class.getResourceAsStream("/resources/walk6.png"))
    };
    private int currentSprite = 0;
    private ImageView guyBrush;
    private static final int SPEED = 200; 
    private TranslateTransition trans;
    private Timeline spriteAnim;
    @Override
    public void start(Stage primaryStage) 
    {
        Group root = new Group();
        Scene scene = new Scene(root,600,400);
        Label info = new Label("Click a point in the scene to walk there");
        info.setFont(Font.font("Arial", FontWeight.BOLD,16));
        info.setTranslateX(10);
        info.setTranslateY(10);
        
        
        guyBrush = new ImageView(sprites[currentSprite]);
        guyBrush.setTranslateX(100);
        guyBrush.setTranslateY(100);
        
        
        
        root.getChildren().addAll(info,guyBrush);
        spriteAnim = new Timeline();
        spriteAnim.setCycleCount(Animation.INDEFINITE);
        spriteAnim.getKeyFrames().add(new KeyFrame(Duration.millis(125),new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                currentSprite++;
                if(currentSprite==sprites.length)
                {
                    currentSprite = 0;
                }
                guyBrush.setImage(sprites[currentSprite]);
            }
        }));
        
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) 
            {
                double targetX = t.getSceneX() - 54;
                double targetY = t.getSceneY() - 150;
                
                double distX = targetX - guyBrush.getTranslateX();
                double distY = targetY - guyBrush.getTranslateY();
                
                double dist = Math.sqrt(distX*distX+distY*distY);
                
                if(trans != null)
                {
                    trans.stop();
                }
                trans = new TranslateTransition();
                trans.setNode(guyBrush);
                trans.setFromX(guyBrush.getTranslateX());
                trans.setFromY(guyBrush.getTranslateY());
                
                trans.setToX(targetX);
                trans.setToY(targetY);
                trans.setDuration(Duration.seconds(dist/SPEED));
                trans.setOnFinished(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent t) {
                   
                        spriteAnim.stop();
                        currentSprite = 0;
                        guyBrush.setImage(sprites[currentSprite]);
                    }
                });
               
                trans.play();
                spriteAnim.play();
            
            }
        });
        primaryStage.setScene(scene);
        primaryStage.setTitle("GuyBrush");
        primaryStage.show();
        
    }

 
    public static void main(String[] args) {
        launch(args);
    }
}
