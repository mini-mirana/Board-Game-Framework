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


/**
 * this can move a piece
 * replace a piece with square or circle
 * change used piece
 */
class ManageBoard {
    /**
     *
     */
    public static String moveQueue;
    /**
     * if true c++ has to pay attention
     */
    public static boolean queueTriggered;
    /**
     * c++ answer
     */
    public static boolean answerQueue;

    ManageBoard(){

    }

    /**
     *
     * @param cat 1: Player A, 2: Player B, 3: Player C, 4: Player D
     * @param y which piece
     * @param i new place row
     * @param j new place column
     */
    public void moveOutter(int cat, int y,int i,int j){
        switch(cat) {
            case 1:
                HBox pieceTemp = Ball.pieceA_array.get(y);
                String text = ((Label) (pieceTemp.getChildren().get(0))).getText();
                pieceTemp.setVisible(false);

                HBox listTemp = Ball.lists.get(i).get(j);
                listTemp.getChildren().remove(0);
                Label label = new Label(text);
                listTemp.getChildren().add(label);
                break;
            case 2:
                HBox pieceTemp2 = Ball.pieceA_array.get(y);
                String text2 = ((Label) (pieceTemp2.getChildren().get(0))).getText();
                pieceTemp2.setVisible(false);

                HBox listTemp2 = Ball.lists.get(i).get(j);
                listTemp2.getChildren().remove(0);
                Label label2 = new Label(text2);
                listTemp2.getChildren().add(label2);
                break;
            case 3:
                HBox pieceTemp3 = Ball.pieceA_array.get(y);
                String text3 = ((Label) (pieceTemp3.getChildren().get(0))).getText();
                pieceTemp3.setVisible(false);

                HBox listTemp3 = Ball.lists.get(i).get(j);
                listTemp3.getChildren().remove(0);
                Label label3 = new Label(text3);
                listTemp3.getChildren().add(label3);
                break;
            case 4:
                HBox pieceTemp4 = Ball.pieceA_array.get(y);
                String text4 = ((Label) (pieceTemp4.getChildren().get(0))).getText();
                pieceTemp4.setVisible(false);

                HBox listTemp4 = Ball.lists.get(i).get(j);
                listTemp4.getChildren().remove(0);
                Label label4 = new Label(text4);
                listTemp4.getChildren().add(label4);
                break;
        }
    }
    public void moveInner(int x, int y,int i,int j){

    }
    public void isMoveValidOutter(int cat, int y,int i,int j){

    }
    public void isMoveValidInner(int cat, int y,int i,int j){

    }
}


public class Ball extends Application {

    public static int nCell = 3;
    public static int nPlayer = 4;
    public static int nPiece = 1;
    /**
     * A string that lets show messages about game stage
     */
    public static String gameMsg = "Message from engine";
    public static HBox engineMsgBox;
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

    public static ArrayList<ArrayList<HBox>> lists;
    public static ArrayList<HBox> pieceB_array;
    public static ArrayList<HBox> pieceA_array;
    public static ArrayList<HBox> pieceC_array;
    public static ArrayList<HBox> pieceD_array;

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

        Label engineMsg = new Label();
        engineMsgBox = new HBox(engineMsg);
        engineMsgBox.setAlignment(Pos.CENTER);
        root.add(engineMsgBox,0,0);
        showMsg(gameMsg);

        for (int i = 0; i < nCell; i++) {
            for (int j = 0; j < nCell; j++) {
                switch(cellShape){
                    case 0:
                        Circle circle = new Circle(30, Color.GREY);
                        HBox hbox = new HBox(circle);
                        hbox.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,CornerRadii.EMPTY,BorderStroke.THIN)));
                        table.add(hbox,i,j);
                        lists.get(i).add(hbox);

                        addClickHandle(hbox);

                        break;
                    case 1:
                        Rectangle rectangle = new Rectangle(60,60,Color.GREY);
                        HBox hbox2 = new HBox(rectangle);
                        hbox2.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,CornerRadii.EMPTY,BorderStroke.THIN)));
                        table.add(hbox2,i,j);
                        lists.get(i).add(hbox2);
                        addClickHandle(hbox2);
                        break;
                }
            }
        }


        disableCell("-",lockedCell,lists);



        int y = foundY(pieceA);
        switch (nPlayer){
            case 2:
                for (int l = 0; l < y ; l++) {
                    for (int i = 0; i < pieceA.length(); i++) {
                        if(arrangePieces(piece_top, i, l, pieceA, pieceA_array, true)) break;
                        arrangePieces(piece_bottom, i, l, pieceB, pieceB_array, true);
                    }
                }
                break;
            case 4:
                for (int l = 0;l < y ;l++) {
                    for (int i = 0; i < pieceA.length(); i++) {
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

    public static void showMsg(String gameMsg) {
        ((Label)(engineMsgBox.getChildren().get(0))).setText(gameMsg);
    }

    /**
     * permanently disables a cell base on a formatted string
     * @param delimiter
     * @param lockedCell
     * @param HBoxlists
     */
    private void disableCell(String delimiter, String lockedCell, ArrayList<ArrayList<HBox>> HBoxlists) {
        Scanner scanner = new Scanner(lockedCell);
        scanner.useDelimiter(delimiter);
        while(scanner.hasNext()){
            String s = scanner.next();
            HBox hBox = HBoxlists.get(Integer.parseInt(Character.toString(s.charAt(0)))).get(Integer.parseInt(Character.toString(s.charAt(2))));
            hBox.setVisible(false);
        }
    }

    private void addClickHandle(HBox hbox) {
        hbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (isPieceClicked) {
                    ((HBox) event.getSource()).getChildren().remove(0);
                    Text text = new Text(((Label) (clickedPiece.getChildren().get(0))).getText());
                    text.setFont(new Font(30));
                    ((HBox) event.getSource()).setAlignment(Pos.CENTER);
                    ((HBox) event.getSource()).getChildren().add(text);
                    clickedPiece.setVisible(false);
                    isPieceClicked = false;
                    clickedPiece = null;
                }
            }
        });
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
