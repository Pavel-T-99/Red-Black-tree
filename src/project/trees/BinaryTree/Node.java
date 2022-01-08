package project.trees.BinaryTree;

import java.io.Serializable;
//"T" type extends comparable since the inserted values must compare to each other in order for the tree to work properly
public class Node<T extends Comparable<T>> implements Serializable {
    
    private T data; /**data*/
    private Node<T> left; /**Left node*/
    private Node<T> right;/**Right node*/
    
    public Node(){
        
    }
    
    public Node(T data){
        this.data = data;
    }
    
    public void setLeft(Node<T> left){
        this.left = left;
    } /**set left node*/
    
    public void setRight(Node<T> right){
        this.right = right;
    } /**set right node*/

    public void setData(T data) {
        this.data = data;
    } /**set data*/
    
    public T getData(){
        return data;
    } /**return data*/

    public Node<T> getLeft(){
        return left;
    } /**return left node*/
    
    public Node<T> getRight(){
        return right;
    } /** return right node*/

    public boolean hasLeft(){
        return left != null;
    } /**check if there is a left node*/

    public boolean hasRight(){
        return right != null;
    } /** check if there is a right node */

    public String toString(){
        return data.toString();
    } /**return the data as a string*/
}
