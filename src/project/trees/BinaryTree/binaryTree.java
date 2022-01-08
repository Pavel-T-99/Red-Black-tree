package project.trees.BinaryTree;

import java.io.Serializable;
//"T" type extends comparable since the inserted values must compare to each other in order for the tree to work properly
public class binaryTree<T extends Comparable<T>> implements Serializable {


    private Node<T> root;
    /**
     * starting node that serves as a root for the tree
     */
    protected int sz = 0;
    /**
     *  size of the tree
     */

    public Node<T> getRoot(){
        return root;
    }
    /**
     * @return the root
     */

    public void insertItem(T item){

        /**
         * @param item
         * //if the tree isEmpty, make a new node and make it the root
         * //if the tree !isEmpty, insert an item either left or right
         * //inserting an item and increasing the size of the tree
         */

        if(isEmpty()){
            root = new Node<T>(item);
        }else{
            insertItem(root, item);
        }
        sz++;
    }



    private void insertItem(Node<T> node, T item){

        if(node.getData().compareTo(item) >= 0){

            if(node.hasLeft())
                insertItem(node.getLeft(), item);
            else
                node.setLeft(new Node<T>(item));


        }
        /**
         * @param node
         * @param item
         * //if the node has left, we get to the position and insert from there
         * //if the node is missing left, we assign the new inserted item as a left
         */
        else{

            if(node.hasRight())
                insertItem(node.getRight(), item);
            else
                node.setRight(new Node<T>(item));

        }
        /**
         * @param node
         * @param item
         * //if the node has right, we get to the position and insert from there
         * //if the node is missing right, we assign the new inserted item as a right
         */
    }// overriding the insertion


    public boolean isEmpty() {
        return root == null;
    }
    /**
     * @return
     * //checks if the tree is empty
     */
    public int getSz(){
        return sz;
    }
    /**
     * returns the size of the tree
     * return type int
     */
    public void inOrder(){
        inOrder(root);
    }
    /**
     * inOrder traversal
     */
    private void inOrder(Node<T> node){
        /**
         * implementation
         * //Explore the left side of the tree, then explore the right side of the tree
         */
        if(node != null){
            inOrder(node.getLeft());
            System.out.println(node + " ");
            inOrder(node.getRight());
        }
    }


    public boolean contains(T data){
        /**
         * //method that returns if the tree contains a specific element
         */
        return contains(root, data);
    }

    public boolean contains(Node<T> root, T data){
        /**
         * //overriding the contains method
         */
        if(root == null)
            return false;


        if(root.getData().compareTo(data) > 0)
            return contains(root.getLeft(), data);
        else if(root.getData().compareTo(data) < 0)
            return contains(root.getRight(), data);
        else
            return true;
    }

    public int getDepth(){
        return getDepth(root);
    }


    private int getDepth(Node<T> node){
        /**
         * @param node
         * @return
         * //get the depth of the tree
         */
        if(node != null){
            int left_depth = getDepth(node.getLeft());
            int right_depth = getDepth(node.getRight());

            return(left_depth > right_depth) ? left_depth + 1 : right_depth + 1;
        }

        return 0;
    }

    private Node<T> deleteNode(T data, Node<T> root){
        if(root == null)
            return root;
        /**if root is null, there is nothing to delete*/
        else if(data.compareTo(root.getData()) < 0)
            root.setLeft(deleteNode(data, root.getLeft())); /**deleting a node on the left*/
        else if(data.compareTo(root.getData()) > 0)
            root.setRight(deleteNode(data, root.getRight()));/**deleting a node on the right*/
        else if(root.getRight() != null && root.getLeft() != null)
        {
            root.setData(getSuccessor(root.getRight()).getData());/**Get the successor/the deepest rightmost node*/
            root.setRight(deleteNode(root.getData(), root.getRight()));/**delete the deepest rightmost node*/
        }
        else
            root = (root.getLeft() != null) ? root.getLeft() : root.getRight();/**get the root*/

        return root; /**return the root*/

    }/**overridden method*/

    private Node<T> getSuccessor(Node<T> root){
        if(root == null)
            return null;
        else if(root.getLeft() == null)
            return root;
        return getSuccessor(root.getLeft());
    }/**get the successor*/

    public void deleteNode(T data){
        root = deleteNode(data, this.root);
    }/**delete a node*/
}
