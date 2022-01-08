package project;

import project.trees.RedBlackTree.RBNode;
import project.trees.RedBlackTree.RBTree;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class panel extends JPanel implements Serializable {

    private static final long serialVersionUID = 1250L;

    private RBTree<?> tree; /**red-black tree with undetermined type of data*/
    private int rad = 20; /**radius of the circles*/
    private int yOffset = 50;/**adjust the vertical line to the right*/
    private Color text = new Color(240,240,240);/**color of the text*/

    private Comparable<?>  search;/**search method*/

    public panel(RBTree<?> tree){
        this.tree = tree;
    }

    public void setSearch(Comparable<?> c){
        search = c;
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        if(tree.isEmpty())
            return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        paintTree(g2d,(RBNode<?>) tree.getRoot(), getWidth() / 2, 30, getGap());
    }/**method to paint a component of the tree*/

    private void paintTree(Graphics2D graphics, RBNode<?> root, int x, int y, int xOffset){

        if(x<0)
            setPreferredSize(new Dimension(2 * getWidth(), getHeight()));

        if(search != null && RBNode.compare(root, search) == 0)
            drawHalo(graphics, x, y);

        drawNode(graphics, root, x, y);

        if(root.getLeft() != null){
            join(graphics, x - xOffset, y + yOffset, x, y);
            paintTree(graphics, (RBNode<?>) root.getLeft(), x - xOffset, y + yOffset, xOffset / 2);
        }

        if(root.getRight() != null){
            join(graphics, x + xOffset, y + yOffset, x, y);
            paintTree(graphics, (RBNode<?>) root.getRight(), x + xOffset, y + yOffset, xOffset / 2);
        }


    }/**method that paints the tree*/
    private void drawHalo(Graphics2D graphics, int x, int y){

        graphics.setColor(new Color(10, 100, 20));
        rad += 5;
        graphics.fillOval(x-rad,y-rad,rad*2, rad*2);
        rad -= 5;
    }/**method that draws a blue halo when search for an item is triggered*/

    private void drawNode(Graphics2D graphics, RBNode<?> node, int x, int y){

        graphics.setColor(node.getRealColor());
        graphics.fillOval(x-rad, y-rad,rad*2,rad*2);
        graphics.setColor(text);

        String txt = node.toString();
        drawCenterTxt(graphics, txt, x, y);
        graphics.setColor(Color.BLACK);
    }/**method that draws the node and paints it based on the algorithm implemented in RBNode.java*/

    private void drawCenterTxt(Graphics2D graphics, String txt, int x, int y){

        FontMetrics metrics = graphics.getFontMetrics();
        double t_width = metrics.getStringBounds(txt, graphics).getWidth();
        graphics.drawString(txt, (int) (x - t_width /2), (int) (y + metrics.getMaxAscent() / 2));
    }/**print/show what the number/character/string in the node is*/

    private void join(Graphics2D graphics, int x1, int y1, int x2, int y2){

        double hypot = Math.hypot(yOffset, x2 -x1);
        int x11 = (int) (x1 + rad * (x2 - x1) / hypot);
        int y11 = (int) (y1 - rad * yOffset / hypot);
        int x21 = (int) (x2 - rad * (x2 - x1) / hypot);
        int y21 = (int) (y2 + rad * yOffset / hypot);
        graphics.drawLine(x11,y11,x21,y21);
    }/**method that separates the nodes and adjusts their place accordingly. Draws a line to connect the nodes as well.*/

    private int getGap(){

        int depth = tree.getDepth();
        int multi = 30;
        float exp = (float) 1.4;

        if(depth > 6){
            multi += depth * 3;
            exp += .1;
        }

        return (int) Math.pow(depth, exp) * multi;
    }
}/**the gap that is in between each node*/
