import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Grid extends JPanel implements MouseListener {
    private static final long serialVersionUID = 1L;
    private BufferedImage gridImage;
    private Object[][] array;
    private static final int X_ORIGIN = 54; // X coordinate of the top left
    private static final int Y_ORIGIN = 56; // Y coordinate of the top left
    private static final int TILE_SIZE = 47; // Size of the tile spaces
    private static final int BORDER_SIZE = 5; // size of the border between spaces
    private volatile boolean isTurn;
    private boolean state;
    boolean com = false;
    private int boardSize;
    /*
     * Constructor that takes an array
     */
    Grid(Object[][] arr, int boar) {
        this(arr, "gridLabels.png" , boar);
    }

    /*
     * constructor that takes an array and a file path.
     */
    private Grid(Object[][] arr, String path , int boardSize) {
        this.boardSize = boardSize-1;
        array = arr;
        isTurn = true;
        state = false;
        // makes the background white and sets the size
        setBackground(Color.white);
        setPreferredSize(new Dimension((X_ORIGIN + arr.length + 1 + ((TILE_SIZE + BORDER_SIZE) * array.length)),
                Y_ORIGIN + arr.length + 1 + ((TILE_SIZE + BORDER_SIZE) * array.length)));
        setSize(getPreferredSize());
        setLocation(0, 0);

        try {
            gridImage = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println("Failed to load image");
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // draws the grid
        g2.drawImage(gridImage, 0, 0, this);

        // loops through all spots in the grid
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                // checks if there is a 1 or a ShipPiece that has not been
                // destroyed
                if (array[i][j].equals(1) || ((array[i][j]).getClass().getName().equals("ShipPiece")
                        && !((ShipPiece) array[i][j]).isDestroy())) {
                    // covers the spot on the grid with a gray box
                    g2.setColor(Color.gray);
                    g2.fillRect(X_ORIGIN + i + 1 + ((TILE_SIZE + BORDER_SIZE) * i), Y_ORIGIN + j + 1 + ((TILE_SIZE + BORDER_SIZE) * j),
                            TILE_SIZE + (BORDER_SIZE / 2) - 1, TILE_SIZE + (BORDER_SIZE / 2) - 1);
                    // if there is a ship piece at the position that is
                    // destroyed
                } else if ((array[i][j]).getClass().getName().equals("ShipPiece")) {
                    // draw the image associated with the ship piece
                    g2.drawImage(((ShipPiece) array[i][j]).getShipImage(),
                            X_ORIGIN + i + ((TILE_SIZE + BORDER_SIZE) * i) + BORDER_SIZE / 2,
                            Y_ORIGIN + j + ((TILE_SIZE + BORDER_SIZE) * j) + BORDER_SIZE / 2, this);
                }
            }
        }

    }
    void bot() {
        int counter1 = 0;
        int counter2 = 0;
        int time = 0;
        ArrayList<Point> a1;
        while (isTurn && time < 10000) {
            time++;
            a1 = new ArrayList<>();
            System.out.println("poel govna");
            paintAll();
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    if (!array[j][i].equals(0) && !array[j][i].equals(1)) {
                        if (((ShipPiece) array[j][i]).getShipImage().equals(new ImageIcon("Player1Hit.png").getImage())) {
                            a1.add(new Point(j, i));
                        }
                    }
                }
            }
            System.out.println(a1);
            System.out.println(counter1 + "        " + counter2);
            if (a1.size() == 0) {
                System.out.println("1");
                counter1 = new Random().nextInt(boardSize+1);
                counter2 = new Random().nextInt(boardSize+1);
            } else if (a1.size() == 1) {
                System.out.println("2");
                System.out.println(counter1 + "        " + counter2);
                do {
                    System.out.println("sdfsdf");
                    counter1 = (int) a1.get(0).getX();
                    counter2 = (int) a1.get(0).getY();
                    System.out.println(counter1 + "        " + counter2);
                    if (new Random().nextInt(2) == 1) {
                        if (counter1 != 0 && counter1 != boardSize) {
                            if (new Random().nextInt(2) == 1) {
                                counter1++;
                            } else
                                counter1--;
                        } else if (counter1 == 0) {
                            counter1++;
                        } else
                            counter1--;
                    } else {
                        if (counter2 != 0 && counter2 != boardSize) {
                            if (new Random().nextInt(2) == 1) {
                                counter2++;
                            } else
                                counter2--;
                        } else if (counter2 == 0) {
                            counter2++;
                        } else
                            counter2--;
                    }
                    System.out.println(counter1 + "        " + counter2);
                    System.out.println(array[counter1][counter2]);
                } while (array[counter1][counter2].equals(0));
                System.out.println(counter1 + "        " + counter2);
            } else {
                System.out.println("3");
                if (a1.get(0).getX() == a1.get(1).getX()) {
                    System.out.println("4");
                    System.out.println((int) a1.get(0).getX());
                    counter1 = (int) a1.get(0).getX();
                    TreeSet<Integer> test = new TreeSet<>();
                    for (Point b : a1) {
                        test.add((int) b.getY());
                    }
                    ArrayList<Integer> bb = new ArrayList<>(test);
                    if (bb.get(0) == 0) {
                        counter2 = bb.get(bb.size() - 1) + 1;
                    } else {
                        counter2 = bb.get(0) - 1;
                        if (array[counter1][counter2].equals(0)) {
                            counter2 = bb.get(bb.size() - 1) + 1;
                        }
                    }
                    if (array[counter1][counter2].equals(0)) {
                        isTurn = false;
                    }
                    System.out.println(counter1 + "        " + counter2);
                } else {
                    counter2 = (int) a1.get(0).getY();
                    TreeSet<Integer> test = new TreeSet<>();
                    for (Point b : a1) {
                        test.add((int) b.getX());
                    }
                    ArrayList<Integer> bb = new ArrayList<>(test);
                    if (bb.get(0) == 0) {
                        counter1 = bb.get(bb.size() - 1) + 1;
                    } else {
                        counter1 = bb.get(0) - 1;
                        if (array[counter1][counter2].equals(0)) {
                            counter1 = bb.get(bb.size() - 1) + 1;
                        }
                    }
                    if (counter1 > -1 && counter1 < boardSize && counter2 > -1 && counter2 < boardSize)
                        if (array[counter1][counter2].equals(0)) {
                            isTurn = false;
                        }
                }
            }
            paintAll();
            repaint();
            state = true;
            if (array[counter1][counter2].equals(1)) {
                // set the object at the coordinate to 0 (an empty
                // space)
                array[counter1][counter2] = 0;
                repaint();
                // end the turn
                isTurn = false;
                // if the object at the coordinate is a ShipPiece that
                // is not destroyed
            } else if ((array[counter1][counter2]).getClass().getName().equals("ShipPiece")
                    && !((ShipPiece) array[counter1][counter2]).isDestroy()) {
                // destroy the ship piece
                System.out.println("popal");
                ((ShipPiece) array[counter1][counter2]).destroy();
                repaint();
                // end the turn
//                        isTurn = false;
            }
            state = false;
        }

        paintAll();
    }

    private void paintAll() {
        for (int i = 0; i <boardSize+1 ; i++) {
            for (int j = 0; j < boardSize+1; j++) {
                if ((array[j][i]).getClass().getName().equals("ShipPiece")) {
                    if (((ShipPiece) array[j][i]).getShipImage().equals(new ImageIcon("dead.png").getImage())) {
                        if (j != 0 && i != 0 && j != boardSize && i != boardSize) {
                            if (array[j - 1][i - 1].equals(1))
                                array[j - 1][i - 1] = 0;
                            if (array[j + 1][i + 1].equals(1))
                                array[j + 1][i + 1] = 0;
                            if (array[j - 1][i + 1].equals(1))
                                array[j - 1][i + 1] = 0;
                            if (array[j + 1][i - 1].equals(1))
                                array[j + 1][i - 1] = 0;
                            if (array[j][i + 1].equals(1))
                                array[j][i + 1] = 0;
                            if (array[j][i - 1].equals(1))
                                array[j][i - 1] = 0;
                            if (array[j - 1][i].equals(1))
                                array[j - 1][i] = 0;
                            if (array[j + 1][i].equals(1))
                                array[j + 1][i] = 0;
                        } else if (j == 0 && i == 0) {
                            if (array[j][i + 1].equals(1))
                                array[j][i + 1] = 0;
                            if (array[j + 1][i].equals(1))
                                array[j + 1][i] = 0;
                            if (array[j + 1][i + 1].equals(1))
                                array[j + 1][i + 1] = 0;
                        } else if (j == boardSize && i == boardSize) {
                            if (array[j][i - 1].equals(1))
                                array[j][i - 1] = 0;
                            if (array[j - 1][i].equals(1))
                                array[j - 1][i] = 0;
                            if (array[j - 1][i - 1].equals(1))
                                array[j - 1][i - 1] = 0;
                        } else if (j == 0 && i == boardSize) {
                            if (array[j][i - 1].equals(1))
                                array[j][i - 1] = 0;
                            if (array[j + 1][i].equals(1))
                                array[j + 1][i] = 0;
                            if (array[j + 1][i - 1].equals(1))
                                array[j + 1][i - 1] = 0;
                        } else if (j == boardSize && i == 0) {
                            if (array[j][i + 1].equals(1))
                                array[j][i + 1] = 0;
                            if (array[j - 1][i].equals(1))
                                array[j - 1][i] = 0;
                            if (array[j - 1][i + 1].equals(1))
                                array[j - 1][i + 1] = 0;
                        } else if (j == 0) {
                            if (array[j + 1][i + 1].equals(1))
                                array[j + 1][i + 1] = 0;
                            if (array[j + 1][i - 1].equals(1))
                                array[j + 1][i - 1] = 0;
                            if (array[j][i + 1].equals(1))
                                array[j][i + 1] = 0;
                            if (array[j][i - 1].equals(1))
                                array[j][i - 1] = 0;
                            if (array[j + 1][i].equals(1))
                                array[j + 1][i] = 0;
                        } else if (i == 0) {
                            if (array[j + 1][i + 1].equals(1))
                                array[j + 1][i + 1] = 0;
                            if (array[j - 1][i + 1].equals(1))
                                array[j - 1][i + 1] = 0;
                            if (array[j][i + 1].equals(1))
                                array[j][i + 1] = 0;
                            if (array[j - 1][i].equals(1))
                                array[j - 1][i] = 0;
                            if (array[j + 1][i].equals(1))
                                array[j + 1][i] = 0;
                        } else if (j == boardSize) {
                            if (array[j - 1][i - 1].equals(1))
                                array[j - 1][i - 1] = 0;
                            if (array[j - 1][i + 1].equals(1))
                                array[j - 1][i + 1] = 0;
                            if (array[j][i + 1].equals(1))
                                array[j][i + 1] = 0;
                            if (array[j][i - 1].equals(1))
                                array[j][i - 1] = 0;
                            if (array[j - 1][i].equals(1))
                                array[j - 1][i] = 0;
                        } else {
                            if (array[j - 1][i - 1].equals(1))
                                array[j - 1][i - 1] = 0;
                            if (array[j + 1][i - 1].equals(1))
                                array[j + 1][i - 1] = 0;
                            if (array[j][i - 1].equals(1))
                                array[j][i - 1] = 0;
                            if (array[j - 1][i].equals(1))
                                array[j - 1][i] = 0;
                            if (array[j + 1][i].equals(1))
                                array[j + 1][i] = 0;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // left click
        if (isTurn && e.getButton() == MouseEvent.BUTTON1) {
            // turns the x coordinate of the mouse into an x coordinate in the
            // grid array using MATH
            int value = e.getX();
            int counter1 = 0;
            while (X_ORIGIN + ((TILE_SIZE + BORDER_SIZE) * counter1) + BORDER_SIZE < value) {
                counter1++;
            }
            counter1--;
            // turns the y coordinate of the mouse into a y coordinate in the
            // grid array using MATH
            int value2 = e.getY() - (TILE_SIZE / 2);
            int counter2 = 0;
            while (Y_ORIGIN + ((TILE_SIZE + BORDER_SIZE) * counter2) + BORDER_SIZE < value2) {
                counter2++;
            }
            counter2--;
            // if (counter1,counter2) is a valid position in the array
            if (counter1 < array.length && counter1 >= 0) {
                if (counter2 < array[0].length && counter2 >= 0) {
                    // if the object at the coordinate is 1
                    state = true;
                    if (array[counter1][counter2].equals(1)) {

                        // set the object at the coordinate to 0 (an empty
                        // space)
                        array[counter1][counter2] = 0;
                        repaint();
                        // end the turn
                        isTurn = false;
                        // if the object at the coordinate is a ShipPiece that
                        // is not destroyed
                    } else if ((array[counter1][counter2]).getClass().getName().equals("ShipPiece")
                            && !((ShipPiece) array[counter1][counter2]).isDestroy()) {
                        // destroy the ship piece
                        ((ShipPiece) array[counter1][counter2]).destroy();
                        paintAll();
                        repaint();
                        // end the turn
//                        isTurn = false;
                    }
                    state = false;
                }
            }
        } else if (!isTurn && e.getButton() == MouseEvent.BUTTON1) {
            System.out.println("tu lox");
            state = true;
        }
        paintAll();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    /*
     * Returns isTurn
     */
    boolean isTurn() {
        return isTurn;
    }

    /*
     * Sets the turn to the parameter
     */
    void setTurn(boolean t) {
        isTurn = t;
    }

    /*
     * Returns the grid array
     */
    Object[][] getArray() {
        return array;
    }

    /*
     * Sets the grid array to the parameter
     */
    void setArray(Object[][] arr, boolean com) {
        array = arr;
        this.com = com;
    }
}

