package project.trees.RedBlackTree;

import project.trees.RedBlackTree.RBNode;

import java.io.Serializable;

public class RBTree<T extends Comparable<T>> implements Serializable {
    protected RBNode<T> root;/**root*/
    protected int sz = 0;/**size of the tree*/

    public RBNode<T> getRoot() {
        return this.root;
    }/**return the root*/

    public void insert(T item){
        if(this.isEmpty()){
            this.root = new RBNode<T>(item);
        }else{
            this.insert(this.root, item);
        }
        this.root.setColor(RBNode.BLACK);
        ++this.sz;
    }/**insert the given type of data/item
     the root gets colored black*/

    private void insert(RBNode<T> node,T item) {
        if (this.contains(item)) {
            return;
        }
        if (node.getData().compareTo(item) > 0) {
            if (node.hasLeft()) {
                this.insert(node.getLeft(), item);
            } else {
                RBNode<T> inserted = new RBNode<T>(item);
                node.setLeft(inserted);
                this.balanceTreeInsert(inserted);
            }
        } else if (node.hasRight()) {
            this.insert(node.getRight(), item);
        } else {
            RBNode<T> inserted = new RBNode<T>(item);
            node.setRight(inserted);
            this.balanceTreeInsert(inserted);
        }
    }/**insert the given item into the node.
     Whether the node hasLeft or hasRight the item is inserted left or right respectively.
     The tre is balanced after the insertion with the balanceTreeInsert method based on the inserted item*/

    public void remove(T data){
        if (!this.contains(data)){
            return;
        }/**if there is no data to be deleted, then we return*/

        RBNode<T> node = this.find(data);
        if(node.getLeft() != null && node.getRight() != null){
            RBNode<T> successor = this.getSuccessor(node);
            node.setData(successor.getData());
            node =successor;
        }/**if there data in a node, we search for it.
         If the given node has both left and right, we get the successor and set it to the given node*/
        
        RBNode<T> pullUp = node.getLeft() == null ? node.getRight() : node.getLeft();/**pullUp is the element that will be rearranged after deletion*/
        if(pullUp != null){
            if(node == this.root){
                node.removeFromParent();
                this.root = node; /**if the selected node is the only child, it is removed from the parent and it becomes the root*/
            }else if(RBNode.getLeft(node.getParent()) == node){
                node.getParent().setLeft(pullUp);/**if the parent node equals the node on the left side, we set pullUp on the left */
            }else{
                node.getParent().setRight(pullUp);/**if the parent node equals the node on the right side, we set pullUp on the right */
            }
            if(RBNode.isBlack(node)){
                this.balanceTreeDelete(pullUp);
            }/**if the node is black, balanceTreeDelete method is called to balance the tree depending on the pullUp*/
        }else if(node == this.root) {
            this.root = null;/**if the selected node is the root then we delete it and return null*/
        }else{
            if(RBNode.isBlack(node)){
                this.balanceTreeDelete(node);
            }
            node.removeFromParent();
        }

    }
    
    public boolean isEmpty() {
        if(this.root == null){
            return true;
        }
        return false;
    }/**standard method to check if the tree is empty*/
    
    public void clear(){
        this.root = null;
    }/**set the root to null*/

    public int getSz(){
        return this.sz;
    }/**get the size of the tree*/
    
    public void inOrder(){
        this.inOrder(this.root);
    }/**inOrder traversal*/
    
    private void inOrder(RBNode<T> node){
        if(node != null){
            this.inOrder(node.getLeft());
            System.out.println(node + " ");
            this.inOrder(node.getRight());
        }
    }/**inOrder traversal*/
    
    public boolean contains(T data){
        return this.contains(this.root, data);
    }/**check if the tree contains an element*/
    
    private boolean contains(RBNode<T> root, T data){
        if(root == null){
            return false;
        }/**if there is no element in the tree, than return value is false*/
        
        if(root.getData().compareTo(data) > 0){
            return this.contains(root.getLeft(), data);
        }/**return the data on the left side*/
        
        if(root.getData().compareTo(data) < 0){
            return this.contains(root.getRight(), data);
        }/**return the data on the right side*/
        return true;
    }

    public RBNode<T> find(T data){
        return this.find(this.root, data);
    }/**method that searches for an element*/

    private RBNode<T> find(RBNode<T> root, T data){
        if(root == null){
            return null;
        }/**if the root is null, there is nothing to find*/

        if(root.getData().compareTo(data) > 0){
            return this.find(root.getLeft(), data);
        }/**find data on the left side*/

        if(root.getData().compareTo(data) < 0){
            return this.find(root.getRight(), data);
        }/**find data on the right side*/
        return root;
    }

    public int getDepth(){
        return this.getDepth(this.root);
    }/**return the depth of the tree*/

    private int getDepth(RBNode<T> node){
        if(node != null){
            int right_depth;
            int left_depth = this.getDepth(node.getLeft());
            return left_depth > (right_depth = this.getDepth(node.getRight())) ?
                    left_depth + 1 : right_depth + 1;
        }/**we check which depth is bigger, left or right, and return the result*/
        return 0;
    }

    private RBNode<T> getSuccessor(RBNode<T> root){
        RBNode<T> left_tree = root.getLeft();
        if(left_tree != null){
            while(left_tree.getRight() != null){
                left_tree = left_tree.getRight();
            }
        }
        return left_tree;
    }/**left_tree serves as a subtree that is on the left side of the root.
     While there are elements in the right side of the left_tree subtree, the method searches for them on the right side.
     The method returns the left subtree.*/

    private void balanceTreeInsert(RBNode<T> node){
        if(node == null || node == this.root || RBNode.isBlack(node.getParent())){
            return;
        }/**Whether the node is null or the node is the root the node's parent is black, the method returns nothing*/

        if(RBNode.isRed(node.getUncle())){
            RBNode.toggleColor(node.getParent());
            RBNode.toggleColor(node.getUncle());
            RBNode.toggleColor(node.getGrandParent());
            this.balanceTreeInsert(node.getGrandParent());                                     /**if the uncle node is red, we toggle the parent and grandparent colors and rebalance the tree*/
        }else if(this.hasLeftParent(node)){
            if(this.isRightChild(node)){
                node = node.getParent();
                this.rotateLeft(node);
            }/**if the node has a left parent and is a right child,
             the new node becomes the parent and we rotate the tree to the left*/

            RBNode.setColor(node.getParent(), RBNode.BLACK);                                   /**set the parent node to be black*/
            RBNode.setColor(node.getGrandParent(), RBNode.RED);                                /**set the grandparent node to be red*/
            this.rotateRight(node.getGrandParent());                                           /**rotate the grandparent node to the right*/
        }else if(this.hasRightParent(node)){
            if(this.isLeftChild(node)){
                node=  node.getParent();
                this.rotateRight(node);
            }/**if the node has a right parent and is a left child,
             the new node becomes the parent and we rotate the tree to the right*/

            RBNode.setColor(node.getParent(), RBNode.BLACK);                                   /**set the parent node to be black*/
            RBNode.setColor(node.getGrandParent(), RBNode.RED);                                /**set the grandparent node to be red*/
            this.rotateLeft(node.getGrandParent());                                            /**rotate the grandparent node to the left*/
        }
    }

    @SuppressWarnings("unchecked")
    private void balanceTreeDelete(RBNode<T> node){
        while(node != this.root && node.isBlack()){
            RBNode<T> sibling = node.getSibling();                                             /**get the sibling node*/
            if(node == RBNode.getLeft(node.getParent())){                                      /**if the node equals parent on the left side of the tree*/
                if(RBNode.isRed(sibling)){                                                     /**and the sibling node is red*/
                    RBNode.setColor(sibling, RBNode.BLACK);                                    /**set the sibling color to black*/
                    RBNode.setColor(node.getParent(), RBNode.RED);                             /**set the parent node to be red*/
                    this.rotateLeft(node.getParent());                                         /**rotate the parent node*/
                    sibling = (RBNode<T>) RBNode.getRight(node.getParent());                   /**the sibling becomes the parent on the rights side of the tree*/
                }

                if(RBNode.isBlack(RBNode.getLeft(sibling)) && RBNode.isBlack(RBNode.getRight(sibling))){/**if the sibling nodes on both side of the tree are black*/
                    RBNode.setColor(sibling, RBNode.RED);                                      /**the sibling node becomes red*/
                    node = node.getParent();                                                   /**the node becomes a parent and we continue*/
                    continue;
                }

                if(RBNode.isBlack(RBNode.getRight(sibling))){                                  /**if the right sibling node is black*/
                    RBNode.setColor(RBNode.getLeft(sibling), RBNode.BLACK);                    /**the left sibling node becomes black*/
                    RBNode.setColor(sibling, RBNode.RED);                                      /**the new sibling node becomes red*/
                    this.rotateRight(sibling);                                                 /**rotate the tree to the right*/
                    sibling = (RBNode<T>) RBNode.getRight(node.getParent());                   /**the new sibling node becomes a parent on the right*/
                }

                RBNode.setColor(sibling, RBNode.getColor(node.getParent()));                   /**the sibling gets the color of the parent node*/
                RBNode.setColor(node.getParent(), RBNode.BLACK);                               /**the new node becomes black*/
                RBNode.setColor(RBNode.getRight(sibling), RBNode.BLACK);                       /**the right side sibling becomes black as well*/
                this.rotateLeft(node.getParent());                                             /**rotate the node's parent to the left*/
                node = this.root;                                                              /**the new node becomes the root*/
                continue;
            }

            if(RBNode.isRed(sibling)){                                                         /**if the sibling node is red*/
                RBNode.setColor(sibling, RBNode.BLACK);                                        /**set the sibling node to black*/
                RBNode.setColor((node.getParent()), RBNode.RED);                               /**set the node's parent node to red*/
                this.rotateRight(node.getParent());                                            /**rotate the node's parent right*/
                sibling = (RBNode<T>) RBNode.getLeft(node.getParent());                        /**the sibling node now becomes a parent node on the left side*/
            }

            if(RBNode.isBlack(RBNode.getLeft(sibling)) && RBNode.isBlack(RBNode.getRight(sibling))){/**if the sibling nodes on both sides are black*/
                RBNode.setColor(sibling, RBNode.RED);                                          /**set the sibling node to be red*/
                node = node.getParent();                                                       /**the node takes the place of its parent*/
                continue;
            }

            if(RBNode.isBlack(RBNode.getLeft(sibling))){                                       /**if the left sibling is black*/
                RBNode.setColor(RBNode.getRight(sibling), RBNode.BLACK);                       /**set the right sibling color to black*/
                RBNode.setColor(sibling, RBNode.RED);                                          /**set the sibling node to red*/
                this.rotateLeft(sibling);                                                      /**rotate the sibling node to the left*/
                sibling = (RBNode<T>) RBNode.getLeft(node.getParent());                        /**the sibling node now becomes a parent node on the right side*/
            }

            RBNode.setColor(sibling, RBNode.getColor(node.getParent()));                       /**set the sibling color to the color of the new node's parent color*/
            RBNode.setColor(node.getParent(), RBNode.BLACK);                                   /**set the new node's color to black*/
            RBNode.setColor(RBNode.getLeft(sibling), RBNode.BLACK);                            /**set the sibling on the left to black color*/
            this.rotateRight(node.getParent());                                                /**rotate the new node's parent to the right*/
            node = this.root;                                                                  /**the new node becomes the root*/
        }
        RBNode.setColor(node, RBNode.BLACK); /**set the new node color to black*/
    }

    private void rotateRight(RBNode<T> node){
        if(node.getLeft() == null){
            return;
        }/**if there is nothing on the left node, the method returns nothing*/

        RBNode<T> left_tree = node.getLeft();                               /**subtree to the left of the node*/
        node.setLeft(left_tree.getRight());                                 /**get the right side of the left subtree*/
        if(node.getParent() == null){
            this.root = left_tree;                                          /**if the left_tree has no parent then it becomes the parent*/
        }else if(node.getParent().getLeft() == node){
            node.getParent().setLeft(left_tree);                            /**if the node equals the parent on the left, we set left_tree to the left side*/
        }else{
            node.getParent().setRight(left_tree);                           /**else set the left_tree to the right*/
        }
        left_tree.setRight(node);                                           /**set left_tree to the right*/
    }

    private void rotateLeft(RBNode<T> node){
        if(node.getRight() == null){
            return;
        }

        RBNode<T> right_tree = node.getRight();                            /**subtree to the right of the node*/
        node.setRight(right_tree.getLeft());
        if(node.getParent() == null){
            this.root = right_tree;                                        /**if the right_tree has no parent then it becomes the parent*/
        }else if(node.getParent().getLeft() == node){
            node.getParent().setLeft(right_tree);                          /**if the node equals the parent on the left, we set right_tree to the left side*/
        }else{
            node.getParent().setRight(right_tree);                         /**else set the right_tree to the right*/
        }

        right_tree.setLeft(node);                                          /**set right_tree to the left*/
    }

    private boolean hasRightParent(RBNode<T> node){
        if(RBNode.getRight(node.getGrandParent()) == node.getParent()){
            return true;
        }
        return false;
    }/**check if a node has right parent*/

    private boolean hasLeftParent(RBNode<T> node){
        if(RBNode.getLeft(node.getGrandParent()) == node.getParent()){
            return true;
        }
        return false;
    }/**check if a node has left parent*/

    private boolean isRightChild(RBNode<T> node){
        if(RBNode.getRight(node.getParent()) == node){
            return true;
        }
        return false;
    }/**check if a node is a right child*/

    private boolean isLeftChild(RBNode<T> node){
        if(RBNode.getLeft(node.getParent()) == node){
            return true;
        }
        return false;
    }/**check if a node is a left child*/
}
