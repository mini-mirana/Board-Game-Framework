import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Ball extends Application {
    
    public static int nCell = 3;
    public static int nPlayer = 4;
    public static int nPiece = 1;
    /**
     * if there is no limitation time put reactionTime = 0
     */
    public static int reactionTime = 0;
    /**
     * Cell shape:
     * for Circle put cellShape = 0
     * for Square put cellShape = 1
     */
    public static int cellShape = 1;
    /**
     * "3,4-5,8" the format of lockedCells
     */
    public static String lockedCell = "1,1-2,2";
    /**
     * choose whether dice is needed or not by putting diceNeeded 1 or 0 respectively
     */
    public static int diceNeeded = 0;
    /**
     * unicode representation containing desired number of pieces
     * Example: "xxx" "ooo"
     */
    public static String pieceA = "\u2654\u2655\u2656\u2657\n\u2662\u2663\u2664\u2665";
    public static String pieceB = "OOOO\nOOOO";
    public static String pieceC = "\u2658\u2659\u2660\u2661\n\u2666\u2667\u2668\u2669";
    public static String pieceD = "BBBB\nBBBB";

    private static int diceNum = 0;

    private static ArrayList<ArrayList<HBox>> lists;
    private static ArrayList<HBox> pieceA_array;
    private static ArrayList<HBox> pieceB_array;
    private static ArrayList<HBox> pieceC_array;
    private static ArrayList<HBox> pieceD_array;

    // arrange pieces
    private boolean isPieceClicked = false;
    private HBox clickedPiece = null;

    static {
        switch (nPlayer) {
            case 2:
                pieceA_array = new ArrayList<HBox>(20);
                pieceB_array = new ArrayList<HBox>(20);
                break;
            case 4:
                pieceA_array = new ArrayList<HBox>(20);
                pieceB_array = new ArrayList<HBox>(20);
                pieceC_array = new ArrayList<HBox>(20);
                pieceD_array = new ArrayList<HBox>(20);
                break;
        }
        switch (cellShape) {
            case 0:
                lists = new ArrayList<>();
                for (int i = 0; i < nCell; i++) {
                    ArrayList<HBox> temp = new ArrayList<>();
                    lists.add(temp);
                }
                break;
            case 1:
                lists = new ArrayList<>();
                for (int i = 0; i < nCell; i++) {
                    ArrayList<HBox> temp = new ArrayList<>();
                    lists.add(temp);
                }
                break;
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        GridPane table = new GridPane();
        GridPane piece_top = new GridPane();
        GridPane piece_bottom = new GridPane();
        GridPane piece_left = new GridPane();
        GridPane piece_right = new GridPane();
        GridPane root = new GridPane();

        root.add(piece_top, 1, 0);
        root.add(piece_left, 0, 1);
        root.add(table, 1, 1);
        root.add(piece_right, 2, 1);
        root.add(piece_bottom, 1, 2);

        Text diceNo = new Text(Integer.toString(diceNum));
        diceNo.minWidth(60);
        diceNo.minHeight(60);
        HBox dice = new HBox(diceNo);
        dice.setVisible(Boolean.parseBoolean(Integer.toString(diceNeeded)));
        dice.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.DASHED,CornerRadii.EMPTY,BorderStroke.THIN)));
        dice.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Random random = new Random(LocalDateTime.now().getSecond());
                int i = random.nextInt(6);
                diceNo.setText(Integer.toString(i));
                diceNum = i;
            }
        });
        dice.setAlignment(Pos.CENTER);
        root.add(dice, 2, 2);

        for (int i = 0; i < nCell; i++) {
            for (int j = 0; j < nCell; j++) {
                switch(cellShape){
                    case 0:
                        Circle circle = new Circle(30, Color.GREY);
                        HBox hbox = new HBox(circle);
                        hbox.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,CornerRadii.EMPTY,BorderStroke.THIN)));
                        table.add(hbox,i,j);
                        lists.get(i).add(hbox);

                        hbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if(isPieceClicked == true){
                                    ((HBox)event.getSource()).getChildren().remove(0);
                                    Text text = new Text(((Label)(clickedPiece.getChildren().get(0))).getText());
                                    text.setFont(new Font(30));
                                    ((HBox)event.getSource()).setAlignment(Pos.CENTER);
                                    ((HBox)event.getSource()).getChildren().add(text);
                                    clickedPiece.setVisible(false);
                                    isPieceClicked = false;
                                    clickedPiece = null;
                                }
                            }
                        });

                        break;
                    case 1:
                        Rectangle rectangle = new Rectangle(60,60,Color.GREY);
                        HBox hbox2 = new HBox(rectangle);
                        hbox2.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,CornerRadii.EMPTY,BorderStroke.THIN)));
                        table.add(hbox2,i,j);
                        lists.get(i).add(hbox2);
                        hbox2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if(isPieceClicked == true){
                                    ((HBox)event.getSource()).getChildren().remove(0);
                                    Text text = new Text(((Label)(clickedPiece.getChildren().get(0))).getText());
                                    text.setFont(new Font(30));
                                    ((HBox)event.getSource()).setAlignment(Pos.CENTER);
                                    ((HBox)event.getSource()).getChildren().add(text);
                                    clickedPiece.setVisible(false);
                                    isPieceClicked = false;
                                    clickedPiece = null;
                                }
                            }
                        });
                        break;
                }
            }
        }

        Scanner scanner = new Scanner(lockedCell);
        scanner.useDelimiter("-");
        while(scanner.hasNext()){
            String s = scanner.next();
            HBox hBox = lists.get(Integer.parseInt(Character.toString(s.charAt(0)))).get(Integer.parseInt(Character.toString(s.charAt(2))));
            hBox.setVisible(false);
        }

        int y = foundY(pieceA);
        switch (nPlayer){
            case 2:
                for (int l = 0; l < y ; l++) {
                    for (int i = 0; i < pieceA.length(); i++) {
                        //                    arrangeAB(piece_top, piece_bottom, i);
                        if(arrangePieces(piece_top, i, l, pieceA, pieceA_array, true)) break;
                        arrangePieces(piece_bottom, i, l, pieceB, pieceB_array, true);
                    }
                }
                break;
            case 4:
                for (int l = 0;l < y ;l++) {
                    for (int i = 0; i < pieceA.length(); i++) {
                        //                    arrangeAB(piece_top, piece_bottom, i);
                        if (arrangePieces(piece_top, i, l, pieceA, pieceA_array, true)) break;
                        arrangePieces(piece_bottom, i, l, pieceB, pieceB_array, true);
                        arrangePieces(piece_right, i, l, pieceC, pieceC_array, false);

                        arrangePieces(piece_left, i,l , pieceD, pieceD_array, false);
                    }
                }

                break;
        }



        Scene scene = new Scene(root, 300, 275);
        primaryStage.setTitle("Board");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private int foundY(String s) {
        int sum = 0;
        for (char c :
                s.toCharArray()) {
            if (c == '\n') sum++;
        }
        return sum+1;
    }

    private boolean arrangePieces(GridPane piece, int i, int j, String pieceC, ArrayList<HBox> pieceC_array, boolean horizontal) {
        char c = pieceC.charAt(i);
        if (c == '\n') return true;
        Label labelC = new Label(Character.toString(c));
        labelC.setFont(new Font(30));
        labelC.setAlignment(Pos.CENTER);
        labelC.setMinSize(30,30);
        HBox hBoxC = new HBox(labelC);
        hBoxC.setAlignment(Pos.CENTER);
        hBoxC.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,CornerRadii.EMPTY,BorderStroke.THIN)));
        pieceC_array.add(hBoxC);
        if (!horizontal)piece.add(hBoxC,j,i);
        else piece.add(hBoxC,i,j);
        hBoxC.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    ((HBox)event.getSource()).setBorder(new Border(new BorderStroke(Color.GOLD, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
                    isPieceClicked = true;
                    clickedPiece = (HBox) event.getSource();
                }
                if (event.getButton() == MouseButton.SECONDARY) {
                    ((HBox)event.getSource()).setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
                    isPieceClicked = false;
                    clickedPiece = null;
                }
            }
        });
        return false;
    }


    public static int exe(int nPlayer) {
		Ball.nPlayer = nPlayer;
        launch();
		return 0;
    }
}
