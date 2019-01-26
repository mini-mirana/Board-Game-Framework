import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
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
     * is game started or not
     */
    public static volatile boolean gameStarted = false;

    /**
     *
     */
    public static volatile String moveQueue = "";
    /**
     * if true c++ has to pay attention
     */
    public static volatile boolean queueTriggered = true;
    /**
     * c++ answer
     */
    public static volatile boolean answerQueue = true;

    ManageBoard() {

    }


    /**
     * @param x old place row
     * @param y old place column
     * @param i new place row
     * @param j new place column
     */
    public void movePiece(int x, int y, int i, int j) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("reached hereeeeeeeeeeee");
                HBox piece1Temp = Ball.lists.get(y - 1).get(x - 2);
                String text = ((Label) (piece1Temp.getChildren().get(0))).getText();
                piece1Temp.getChildren().remove(0);
                switch (Ball.cellShape) {
                    case 1:
                        Rectangle rectangle = new Rectangle(58, 58, Color.GREY);
                        piece1Temp.getChildren().add(rectangle);
                        break;
                    case 0:
                        Circle circle = new Circle(30, Color.GREY);
                        piece1Temp.getChildren().add(circle);
                        break;
                }

                HBox piece2Temp = Ball.lists.get(j-1).get(i-2);
                piece2Temp.setAlignment(Pos.CENTER);
                piece2Temp.getChildren().remove(0);
                Label label = new Label(text);
                label.setFont(new Font(30));
                piece2Temp.getChildren().add(label);
            }
        });
    }

    /**
     * puts a move on the queue to be validate by the engine
     * @param x
     * @param y
     * @param i
     * @param j
     */
    public void isPieceMoveValid(int x, int y, int i, int j) {
        ManageBoard.queueTriggered = true;
        moveQueue = String.format("%d%d%d%d", y+2, x+1 , j+2, i+1);
        //moveQueue = String.format("%d%d%d%d", y, x , j, i);
    }
}

public class Ball extends Application {

    public static int nCelly = 8;
    public static int nCellx = 8;
    public static int nPlayer = 2;
    public static int nPiece = 1;

    /**
     * A string that lets show messages about game stage
     */
    public static String gameMsg = "Message from engine";
    public static HBox engineMsgBox;

    /**
     * start/end game button
     */
    public static HBox startButtonBox;

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
     * put "" if there is no lockedCell
     */
    //public static String lockedCell = "1,1-2,2";
    public static String lockedCell = "";

    /**
     * choose whether dice is needed or not by putting diceNeeded 1 or 0 respectively
     */
    public static int diceNeeded = 0;
    private static int diceNum = 0;

    /**
     * unicode representation containing desired number of pieces
     * Example: "xxx" "ooo"
     * put "" to show there is no piece
     * put \n to go to next line
     */
    public static String pieceA = "";
    public static String pieceB = "";
    public static String pieceC = "";
    public static String pieceD = "";


    public static ArrayList<ArrayList<HBox>> lists;
    public static ArrayList<HBox> pieceB_array;
    public static ArrayList<HBox> pieceA_array;
    public static ArrayList<HBox> pieceC_array;
    public static ArrayList<HBox> pieceD_array;
    public static ArrayList<HBox> pieces_array;

    // arrange pieces
    private boolean isPieceClicked = false;
    private boolean isClickedPieceOuter = false;
    private HBox clickedPiece = null;
    public ManageBoard mB;

    static {
        switch (nPlayer) {
            case 2:
                pieceA_array = new ArrayList<HBox>(20);
                pieceB_array = new ArrayList<HBox>(20);
                pieces_array = new ArrayList<HBox>(40);
                break;
            case 4:
                pieceA_array = new ArrayList<HBox>(20);
                pieceB_array = new ArrayList<HBox>(20);
                pieceC_array = new ArrayList<HBox>(20);
                pieceD_array = new ArrayList<HBox>(20);
                pieces_array = new ArrayList<HBox>(80);
                break;
        }
        switch (cellShape) {
            case 0:
                lists = new ArrayList<>();
                for (int i = 0; i < nCelly; i++) {
                    ArrayList<HBox> temp = new ArrayList<>();
                    lists.add(temp);
                }
                break;
            case 1:
                lists = new ArrayList<>();
                for (int i = 0; i < nCelly; i++) {
                    ArrayList<HBox> temp = new ArrayList<>();
                    lists.add(temp);
                }
                break;
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        this.mB = new ManageBoard();
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

        Label diceNo = new Label(Integer.toString(diceNum));
        diceNo.setFont(new Font(30));
        HBox dice = new HBox(diceNo);
        dice.setVisible(diceNeeded == 1);
        dice.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderStroke.THIN)));
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
        dice.setPrefSize(60, 60);
        root.add(dice, 2, 2);

        Label engineMsg = new Label();
        engineMsgBox = new HBox(engineMsg);
        engineMsgBox.setAlignment(Pos.CENTER);
        engineMsgBox.setPrefSize(60, 60);
        root.add(engineMsgBox, 0, 0);
        engineMsgBox.setPadding(new Insets(4));
        showMsg(gameMsg);

        Button startButton = new Button("Start");
        startButtonBox = new HBox(startButton);
        startButtonBox.setAlignment(Pos.CENTER);
        startButtonBox.setPrefSize(60, 60);
        root.add(startButtonBox, 2, 0);
        startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for (int i = 0; i != pieces_array.size(); i++) {
                    pieces_array.get(i).setVisible(false);
                }
                ManageBoard.gameStarted = true;
            }
        });


        for (int i = 0; i < nCelly; i++) {
            for (int j = 0; j < nCellx; j++) {
                switch (cellShape) {
                    case 0:
                        Circle circle = new Circle(30, Color.GREY);
                        HBox hbox = new HBox(circle);
                        hbox.setPrefSize(60, 60);
                        hbox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
                        table.add(hbox, i, j);
                        lists.get(i).add(hbox);
                        addClickHandle(hbox);
                        break;
                    case 1:
                        Rectangle rectangle = new Rectangle(58, 58, Color.GREY);
                        HBox hbox2 = new HBox(rectangle);
                        hbox2.setPrefSize(60, 60);
                        hbox2.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
                        table.add(hbox2, i, j);
                        lists.get(i).add(hbox2);
                        addClickHandle(hbox2);
                        break;
                }
            }
        }

        disableCell("-", lockedCell, lists);

        int y = foundY(pieceA);
        switch (nPlayer) {
            case 2:
                for (int l = 0; l < y; l++) {
                    for (int i = 0; i < pieceA.length(); i++) { // length of pieceA & pieceB have to be same
                        if (arrangePieces(piece_top, i, l, pieceA, pieceA_array, true,Color.BLACK)) break;
                        arrangePieces(piece_bottom, i, l, pieceB, pieceB_array, true,Color.GOLD);
                    }
                }
                break;
            case 4:
                for (int l = 0; l < y; l++) {
                    for (int i = 0; i < pieceA.length(); i++) { // length of pieceA & pieceB have to be same
                        if (arrangePieces(piece_top, i, l, pieceA, pieceA_array, true,Color.BLACK)) break;
                        arrangePieces(piece_bottom, i, l, pieceB, pieceB_array, true,Color.GOLD);
                        arrangePieces(piece_right, i, l, pieceC, pieceC_array, false,Color.BLUE);
                        arrangePieces(piece_left, i, l, pieceD, pieceD_array, false,Color.GREEN);
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
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ((Label) (engineMsgBox.getChildren().get(0))).setText(gameMsg);
                ((Label) (engineMsgBox.getChildren().get(0))).setWrapText(true);
            }
        });
    }

    /**
     * permanently disables a cell base on a formatted string
     *
     * @param delimiter
     * @param lockedCell
     * @param HBoxlists
     */
    private void disableCell(String delimiter, String lockedCell, ArrayList<ArrayList<HBox>> HBoxlists) {
        Scanner scanner = new Scanner(lockedCell);
        scanner.useDelimiter(delimiter);
        while (scanner.hasNext()) {
            String s = scanner.next();
            HBox hBox = HBoxlists.get(Integer.parseInt(Character.toString(s.charAt(0)))).get(Integer.parseInt(Character.toString(s.charAt(2))));
            hBox.setVisible(false);
        }
    }

    private void addClickHandle(HBox hbox) {
        hbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (isPieceClicked && event.getButton().equals(MouseButton.PRIMARY) && clickedPiece != event.getSource()) {
                    if ((isClickedPieceOuter && !((HBox) event.getSource()).getChildren().get(0).getClass().getSimpleName().equals(Label.class.getSimpleName()))
                            || !isClickedPieceOuter) {
                        if (ManageBoard.gameStarted) {
                            String xy = findCoordinate(clickedPiece);
                            String ij = findCoordinate(((HBox) event.getSource()));
                            assert xy != null;

                            int x = Integer.parseInt(Character.toString(xy.charAt(0)));
                            int y = Integer.parseInt(Character.toString(xy.charAt(1)));
                            int i = Integer.parseInt(Character.toString(ij.charAt(0)));
                            int j = Integer.parseInt(Character.toString(ij.charAt(1)));
                            mB.isPieceMoveValid(x, y, i, j);
                            System.out.println("Wait for engine response");
                            while (ManageBoard.queueTriggered) {
                            }
                            System.out.println("got response from engine");
                            if (!ManageBoard.answerQueue) {
                                // TODO: unclick piece
                                clickedPiece.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
                                // TODO: handle isClickedPieceOuter, clickedPiece, and isPieceClicked
                                clickedPiece = null;
                                isPieceClicked = false;
                                isClickedPieceOuter = false;
                                return;
                            }
                        }
                        ((HBox) event.getSource()).getChildren().remove(0);
                        Label text = new Label(((Label) (clickedPiece.getChildren().get(0))).getText());
                        Paint textPaint = ((Label) (clickedPiece.getChildren().get(0))).getTextFill();
                        text.setTextFill(textPaint);
                        text.setFont(new Font(30));
                        ((HBox) event.getSource()).setAlignment(Pos.CENTER);
                        ((HBox) event.getSource()).setPrefSize(60, 60);
                        ((HBox) event.getSource()).getChildren().add(text);

                        clickedPiece.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
                        clickedPiece.getChildren().remove(0);
                        if (!isClickedPieceOuter) {
                            if (cellShape == 0) {
                                Circle circle = new Circle(30, Color.GREY);
                                clickedPiece.getChildren().add(circle);
                            } else if (cellShape == 1) {
                                Rectangle rectangle = new Rectangle(58, 58, Color.GREY);
                                clickedPiece.getChildren().add(rectangle);
                            }
                        } else {
                            clickedPiece.setVisible(false);
                            pieces_array.remove(clickedPiece);
                        }
                        clickedPiece = null;
                        isPieceClicked = false;
                        isClickedPieceOuter = false;
                    }
                } else {
                    if (event.getButton().equals(MouseButton.PRIMARY)) {
                        String simpleName = ((HBox) event.getSource()).getChildren().get(0).getClass().getSimpleName();
                        if (simpleName.equals(Label.class.getSimpleName())) {
                            ((HBox) event.getSource()).setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
                            clickedPiece = (HBox) event.getSource();
                            isPieceClicked = true;
                        }
                    } else if (event.getButton().equals(MouseButton.SECONDARY) && clickedPiece == event.getSource()) {
                        ((HBox) event.getSource()).setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
                        clickedPiece = null;
                        isPieceClicked = false;
                    }
                }
            }
        });
    }

    private String findCoordinate(HBox source) {
        for (int i = 0; i < Ball.lists.size(); i++) {
            for (int j = 0; j < Ball.lists.get(i).size(); j++) {
                if (source == Ball.lists.get(i).get(j)) {
                    return String.format("%d%d", i, j);
                }
            }
        }
        return null;
    }

    private int foundY(String s) {
        int sum = 0;
        for (char c :
                s.toCharArray()) {
            if (c == '\n') sum++;
        }
        return sum + 1;
    }

    private boolean arrangePieces(GridPane table, int i, int j, String pieceC, ArrayList<HBox> special_piece_array, boolean horizontal, Color textColor) {
        char c = pieceC.charAt(i);
        if (c == '\n') return true;

        Label labelC = new Label(Character.toString(c));
        labelC.setFont(new Font(30));
        labelC.setAlignment(Pos.CENTER);
        labelC.setTextFill(textColor);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setPrefSize(60, 60);
        hBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
        hBox.getChildren().add(labelC);

        special_piece_array.add(hBox);
        pieces_array.add(hBox);

        if (!horizontal) table.add(hBox, j, i);
        else table.add(hBox, i, j);

        hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!isPieceClicked && event.getButton().equals(MouseButton.PRIMARY)) {
                    ((HBox) event.getSource()).setBorder(new Border(new BorderStroke(Color.GOLD, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
                    clickedPiece = (HBox) event.getSource();
                    isPieceClicked = true;
                    isClickedPieceOuter = true;
                } else if (event.getButton().equals(MouseButton.SECONDARY) && clickedPiece == event.getSource()) {
                    ((HBox) event.getSource()).setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
                    clickedPiece = null;
                    isPieceClicked = false;
                    isClickedPieceOuter = false;
                }
            }
        });
        return false;
    }


    public static int exe(int nPlayer,int cellShape, int diceNeeded , int reactionTime , int nCellx,int nCelly,int nPiece,String lockedCell,
                          String pieceA, String pieceB, String pieceC, String pieceD) {
        Ball.nPlayer = nPlayer;
        Ball.cellShape = cellShape;
        Ball.diceNeeded = diceNeeded;
        Ball.reactionTime = reactionTime;
        Ball.lockedCell =lockedCell;
        Ball.nCellx = nCellx;
        Ball.nCelly= nCelly;
        Ball.nPiece = nPiece;
        Ball.pieceA = pieceA;
        Ball.pieceB = pieceB;
        Ball.pieceC = pieceC;
        Ball.pieceD = pieceD;
        launch();
        Platform.setImplicitExit(false);
        return 0;
    }
}
