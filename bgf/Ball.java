import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Ball extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        class CurrentXY {
            private final int maxX;
            private final int maxY;

            private int x = 0;

            private int y = 0;

            public int getX() {
                return x;
            }

            public int getY() {
                return y;
            }

            CurrentXY(int maxX, int maxY) {
                this.maxX = maxX;
                this.maxY = maxY;
            }

            public boolean Up() {
                if (y != 0) {
                    y--;
                    return true;
                }
                return false;
            }

            public boolean Right() {
                if (x != maxX-1) {
                    x++;
                    return true;
                }
                return false;
            }

            public boolean Left() {
                if (x != 0) {
                    x--;
                    return true;
                }
                return false;
            }

            public boolean Down() {
                if (y != maxY-1) {
                    y++;
                }
                return false;
            }
        }

        CurrentXY xy = new CurrentXY(10, 10);

        GridPane root = new GridPane();

        GridPane buttons = new GridPane();
        Button up = new Button("Up");
        Button Down = new Button("Down");
        Button Left = new Button("Left");
        Button Right = new Button("Right");

        buttons.setHgap(10);
        buttons.setVgap(10);

        buttons.add(up, 1, 0);
        buttons.add(Left, 0, 1);
        buttons.add(Right, 2, 1);
        buttons.add(Down, 1, 2);
//        buttons.setGridLinesVisible(true);

        HBox hBox = new HBox(buttons);
        hBox.setAlignment(Pos.CENTER);
        GridPane table = new GridPane();
//        table.setGridLinesVisible(true);
        table.setPadding(new Insets(5));

        ArrayList<ArrayList<Circle>> lists = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            lists.add(new ArrayList<Circle>(10));
            for (int j = 0; j < 10; j++) {
                Circle circle = new Circle(25);
                HBox box = new HBox(3, circle);
                box.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.MEDIUM)));
                lists.get(i).add(circle);
                if (j == 0 && i == 0) {
                    circle.setFill(Color.BLACK);
                } else {
                    circle.setFill(Color.BLACK);
                    circle.setVisible(false);
                }
                table.add(box, i, j, 1, 1);
            }
        }

        root.add(table, 0, 0);
        root.add(hBox, 0, 1);

        up.setOnAction(event -> {
            lists.get(xy.getX()).get(xy.getY()).setVisible(false);
            xy.Up();
            lists.get(xy.getX()).get(xy.getY()).setVisible(true);
        });
        Down.setOnAction(event -> {
            lists.get(xy.getX()).get(xy.getY()).setVisible(false);
            xy.Down();
            lists.get(xy.getX()).get(xy.getY()).setVisible(true);
        });
        Left.setOnAction(event -> {
            lists.get(xy.getX()).get(xy.getY()).setVisible(false);
            xy.Left();
            lists.get(xy.getX()).get(xy.getY()).setVisible(true);
        });
        Right.setOnAction(event -> {
            lists.get(xy.getX()).get(xy.getY()).setVisible(false);
            xy.Right();
            lists.get(xy.getX()).get(xy.getY()).setVisible(true);
        });
//        root.setGridLinesVisible(true);

        Scene scene = new Scene(root, Screen.getPrimary().getBounds().getMaxX(), Screen.getPrimary().getBounds().getMaxY());
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().getCode()== 27) {
                    primaryStage.close();
                }
                if (event.getCode().getCode()== 38) {
                    up.fire();
                }
                if (event.getCode().getCode()== 40) {
                    Down.fire();
                }
                if (event.getCode().getCode()== 39) {
                    Right.fire();
                }
                if (event.getCode().getCode()== 37) {
                    Left.fire();
                }
            }
        });
        primaryStage.setTitle("Ball Table");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setFullScreen(true);
        up.setMinSize(Down.getWidth(),Down.getHeight());
    }


    public static int exe() {
        launch();
		return 0;
    }
}
