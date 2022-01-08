package project.trees.RedBlackTree;

import java.awt.*;
import java.io.Serializable;

public class RBNode<T extends Comparable<T>> implements Serializable {

    public static boolean RED = false;/**initialize Red color with false*/
    public static boolean BLACK = true;/**initialize Black color as true*/

    private boolean color = RED;/**initialize color to be red*/
    private RBNode<T> left;/**left RBNode*/
    private RBNode<T> right;/**Right RBNode*/
    private RBNode<T> parent;/**parent RBNode*/
    private T data;/**type of data to be stored*/

    public RBNode(){

    }

    public RBNode(T data){
        this.data = data;
    }/**RBNode that stores data*/

    public void setData(T data){
        this.data = data;
    }/**set the data to be stored*/

    public void removeFromParent(){
        if(getParent() == null)
            return;

        if(parent.getLeft() == this)
            parent.setLeft(null);
        else if(parent.getRight() == this)
            parent.setRight(null);

        this.parent = null;
    }/**remove a child node from the parent node*/

    public void setLeft(RBNode<T> child){
        if(getLeft() != null)
            getLeft().setParent(null);

        if(child != null){
            child.removeFromParent();
            child.setParent(this);
        }

        this.left = child;
    }/**set left child*/

    public void setRight(RBNode<T> child){
        if(getRight() != null){
            getRight().setParent(null);
        }

        if(child != null){
            child.removeFromParent();
            child.setParent(this);
        }

        this.right = child;
    }/**set right child*/

    public T getData(){
        return data;
    } /**return data*/

    public RBNode<T> getLeft(){
        return left;
    }/**return the left */

    public static RBNode<?> getLeft(RBNode<?> node){
        return node == null ? null : node.getLeft();
    }/**return left node*/

    public RBNode<T> getRight(){
        return right;
    }/**return the right */

    public static RBNode<?> getRight(RBNode<?> node){
        return node == null ? null : node.getRight();
    }/**return right node*/

    public boolean hasLeft(){
        return left != null;
    }/**check if there is a left node*/

    public boolean hasRight() {
        return right != null;
    }/**check if there is a right node*/

    public String toString(){
        return data.toString();
    }/**return data as a string*/

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static int compare(RBNode<?> node, Comparable<?> b){
        return ((Comparable) node.getData()).compareTo(b);
    }/**compare function that serves as a key for the balancing of the tree later*/

    public boolean isRed(){
        return getColor() == RED;
    }/**check if color is RED*/

    public boolean isBlack(){
        return !isRed();
    }/**check if a color BLACK*/

    public static boolean isRed(RBNode<?> node){
        return getColor(node) == RED;
    }/**check if a node is RED*/

    public static boolean isBlack(RBNode<?> node){
        return !isRed(node);
    }/**check if node is BLACK*/

    public void setColor(boolean color){
        this.color = color;
    }/**set the color*/

    public static void setColor(RBNode<?> node, boolean color){
        if(node == null)
            return;
        node.setColor(color);
    }/**set the color of the node*/

    public boolean getColor(){
        return color;
    }/**get the color*/

    public static boolean getColor(RBNode<?> node){
        return node == null ? BLACK : node.getColor();
    }/**get the color of the node*/

    public Color getRealColor(){
        if(isRed())
            return new Color(250, 70,70);
        else
            return new Color(70, 70,70);
    }/**initialize the colors*/

    public void toggleColor(){
        color = !color;
    }/**set the new color*/

    public static void toggleColor(RBNode<?> node){
        if(node == null)
            return;
        node.setColor(!node.getColor());
    }/**setting the new color of the node after changes are made*/

    public void setParent(RBNode<T> parent){
         this.parent = parent;
    }/**set a parent*/

    public RBNode<T> getParent(){
        return parent;
    }/**return the parent*/

    public static RBNode<?> getParent(RBNode<?> node){
        return (node == null) ? null : node.getParent();
    }/**if node not null, get the node's parent*/

    public RBNode<T> getGrandParent(){
        RBNode<T> parent = getParent();
        return (parent == null) ? null : parent.getParent();
    }/**getting the grandparent by getting the parent of the parent*/

    public static RBNode<?> getGrandParent(RBNode<?> node){
        return(node == null) ? null : node.getGrandParent();
    }/**return the grandparent*/

    public RBNode<T> getSibling(){
        RBNode<T> parent = getParent();
        if(parent != null){
            if(this == parent.getRight())
                return(RBNode<T>) parent.getLeft();
            else
                return(RBNode<T>) parent.getRight();
        }
        return null;
    }/**getting the sibling by checking either left or right on the parent*/

    public static RBNode<?> getSibling(RBNode<?> node){
        return (node == null) ? null : node.getSibling();
    }/**return the sibling*/

    public RBNode<?> getUncle(){
        RBNode<T> parent = getParent();
        if(parent != null){
            return parent.getSibling();
        }
        return null;
    }/**getting the uncle by checking for the sibling to the parent*/

    public static RBNode<?> getUncle(RBNode<?> node){
        return (node == null) ? null : node.getUncle();
    }/**return the uncle*/
}
