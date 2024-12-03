import java.util.*;
import java.util.LinkedList;
import java.util.Queue;

class BinarySearchTree{
    static class Node{
        int key;
        Node left, right;
        Node(int key){
            this.key = key;
            this.left = null;
            this.right = null;
        }
    }
    private Node root;
    public BinarySearchTree(){
        this.root = null;
    }

    //Height
    public int height() {
        return heightRec(root);
    }
    private int heightRec(Node node) {
        if (node == null) {
            return 0;
        }
        int leftHeight = heightRec(node.left);
        int rightHeight = heightRec(node.right);
        return 1 + Math.max(leftHeight, rightHeight);
    }

    //Insert
    public void insert(int key){
        root = insertKey(root, key);
    }
    private Node insertKey(Node root, int key){
        if (root == null){
            root = new Node(key);
            return root;
        }
        if(key < root.key){
            root.left = insertKey(root.left, key);
        }
        else if (key > root.key){
            root.right = insertKey(root.right, key);
        }
        return root;
    }

    //Delete
    public void delete(int key){
        root = deleteKey(root, key);
    }
    private Node deleteKey(Node root, int key){
        if (root == null){
            return null;
        }
        if (key < root.key){
            root.left = deleteKey(root.left, key);
        }
        else if (key > root.key){
            root.right = deleteKey(root.right, key);
        }
        else{
            if (root.left == null){
                return root.right;
            }
            else if (root.right == null){
                return root.left;
            }
            root.key = getMax(root.left).key;
            root.left = deleteKey(root.left, root.key);
        }
        return root;
    }

    //getMax
    private Node getMax(Node root){
        while (root.right != null){
            root = root.right;
        }
        return root;
    }

    //search
    public boolean search(int key){
        return searchKey(root, key);
    }
    private boolean searchKey(Node root, int key){
        if (root == null) return false;
        if (key == root.key) return true;
        if (key < root.key){
            return searchKey(root.left, key);
        }
        else return searchKey(root.right, key);
    }

    //printTree
    public void printTree(){
        System.out.println("Прямой обход: ");
        printPre(root);
        System.out.println();
        System.out.println("Симметричный обход: ");
        printIn(root);
        System.out.println();
        System.out.println("Обратный обход: ");
        printPost(root);
        System.out.println();
        System.out.println("Уровневый обход(в ширину): ");
        printLevel(root);
        System.out.println();
    }
    // Прямой обход (Pre-order)
    private void printPre(Node node) {
        if (node != null) {
            System.out.print(node.key + " "); // Посещение узла
            printPre(node.left);             // Левое поддерево
            printPre(node.right);            // Правое поддерево
        }
    }
    // Симметричный обход (In-order)
    private void printIn(Node node) {
        if (node != null) {
            printIn(node.left);              // Левое поддерево
            System.out.print(node.key + " "); // Посещение узла
            printIn(node.right);             // Правое поддерево
        }
    }
    // Обратный обход (Post-order)
    private void printPost(Node node) {
        if (node != null) {
            printPost(node.left);             // Левое поддерево
            printPost(node.right);            // Правое поддерево
            System.out.print(node.key + " "); // Посещение узла
        }
    }
    // Уровневый обход (Level-order)
    private void printLevel(Node root) {
        if (root == null) {
            return;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node node = queue.poll(); // Извлечение узла
            System.out.print(node.key + " "); // Посещение узла

            if (node.left != null) {
                queue.add(node.left); // Левый потомок
            }

            if (node.right != null) {
                queue.add(node.right); // Правый потомок
            }
        }
    }

}

class AVLTree{
    static class Node{
        int key, height;
        Node left, right;
        Node(int key){
            this.key = key;
            this.height = 1; //Высота нового узла
        }
    }

    private Node root;

    //Height
    public int height() {
        return heightRec(root);
    }
    private int heightRec(AVLTree.Node node) {
        if (node == null) {
            return 0;
        }
        int leftHeight = heightRec(node.left);
        int rightHeight = heightRec(node.right);
        return 1 + Math.max(leftHeight, rightHeight);
    }
    //Height
    public int getHeight(Node node){
        if (node == null) return 0;
        else return node.height;
    }

    //Balance
    public int getBalance(Node node){
        if (node == null) return 0;
        else return getHeight(node.left) - getHeight(node.right);
    }

    //NewHeight
    private int newHeight(Node node){
        return Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    //turnRight
    private Node turnRight(Node a){
        Node b = a.left;
        Node c = b.right;
        b.right = a;
        a.left = c;
        a.height = newHeight(a);
        b.height = newHeight(b);
        return b;
    }
    //turnLeft
    private Node turnLeft(Node a){
        Node b = a.right;
        Node c = b.left;
        b.left = a;
        a.right = c;
        a.height = newHeight(a);
        b.height = newHeight(b);
        return b;
    }

    //getMax
    private Node getMax(Node root){
        while (root.right != null){
            root = root.right;
        }
        return root;
    }
    //getMin
    private Node getMin(Node root){
        while (root.left != null){
            root = root.left;
        }
        return root;
    }

    //Insert
    public void insert(int key){
        root = insertKey(root, key);
    }
    private Node insertKey(Node root, int key){
        if (root == null){
            root = new Node(key);
            return root;
        }
        if(key < root.key){
            root.left = insertKey(root.left, key);
        }
        else if (key > root.key){
            root.right = insertKey(root.right, key);
        }

        root.height = newHeight(root);
        int balance = getBalance(root);

        // Правый поворот
        if (balance > 1 && key < root.left.key) {
            return turnRight(root);
        }

        // Левый поворот
        if (balance < -1 && key > root.right.key) {
            return turnLeft(root);
        }

        // Левый правый поворот
        if (balance > 1 && key > root.left.key) {
            root.left = turnLeft(root.left);
            return turnRight(root);
        }

        // Правый левый поворот
        if (balance < -1 && key < root.right.key) {
            root.right = turnRight(root.right);
            return turnLeft(root);
        }

        return root;
    }

    //Delete
    public void delete(int key){
        root = deleteKey(root, key);
    }
    private Node deleteKey(Node root, int key){
        if (root == null){
            return null;
        }
        if (key < root.key){
            root.left = deleteKey(root.left, key);
        }
        else if (key > root.key){
            root.right = deleteKey(root.right, key);
        }
        else{
            if (root.left == null){
                return root.right;
            }
            else if (root.right == null){
                return root.left;
            }
            root.key = getMax(root.left).key;
            root.left = deleteKey(root.left, root.key);
        }

        root.height = newHeight(root);
        int balance = getBalance(root);

        // Правый поворот
        if (balance > 1 && getBalance(root.left) >= 0) {
            return turnRight(root);
        }

        // Левый правый поворот
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = turnLeft(root.left);
            return turnRight(root);
        }

        // Левый поворот
        if (balance < -1 && getBalance(root.right) <= 0) {
            return turnLeft(root);
        }

        // Правый левый поворот
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = turnRight(root.right);
            return turnLeft(root);
        }

        return root;
    }

    //search
    public boolean search(int key){
        return searchKey(root, key);
    }
    private boolean searchKey(Node root, int key){
        if (root == null) return false;
        if (key == root.key) return true;
        if (key < root.key){
            return searchKey(root.left, key);
        }
        else return searchKey(root.right, key);
    }

    //printTree
    public void printTree() {
        System.out.println("Прямой обход: ");
        printPre(root);
        System.out.println();
        System.out.println("Симметричный обход: ");
        printIn(root);
        System.out.println();
        System.out.println("Обратный обход: ");
        printPost(root);
        System.out.println();
        System.out.println("Уровневый обход(в ширину): ");
        printLevel(root);
        System.out.println();
    }
    // Прямой обход (Pre-order)
    private void printPre(AVLTree.Node node) {
        if (node != null) {
            System.out.print(node.key + " "); // Посещение узла
            printPre(node.left);             // Левое поддерево
            printPre(node.right);            // Правое поддерево
        }
    }
    // Симметричный обход (In-order)
    private void printIn(AVLTree.Node node) {
        if (node != null) {
            printIn(node.left);              // Левое поддерево
            System.out.print(node.key + " "); // Посещение узла
            printIn(node.right);             // Правое поддерево
        }
    }
    // Обратный обход (Post-order)
    private void printPost(AVLTree.Node node) {
        if (node != null) {
            printPost(node.left);             // Левое поддерево
            printPost(node.right);            // Правое поддерево
            System.out.print(node.key + " "); // Посещение узла
        }
    }
    // Уровневый обход (Level-order)
    private void printLevel(AVLTree.Node root) {
        if (root == null) {
            return;
        }

        Queue<AVLTree.Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            AVLTree.Node node = queue.poll(); // Извлечение узла
            System.out.print(node.key + " "); // Посещение узла

            if (node.left != null) {
                queue.add(node.left); // Левый потомок
            }

            if (node.right != null) {
                queue.add(node.right); // Правый потомок
            }
        }
    }

}

class RedBlackTree{
    // Константы цвета
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    // Узел дерева
    static class Node {
        int key;
        Node left, right, parent;
        boolean color;

        Node(int key) {
            this.key = key;
            this.color = RED; // Новый узел всегда красный
        }
    }

    private Node root;

    //Height
    public int height() {
        return heightRec(root);
    }
    private int heightRec(RedBlackTree.Node node) {
        if (node == null) {
            return 0;
        }
        int leftHeight = heightRec(node.left);
        int rightHeight = heightRec(node.right);
        return 1 + Math.max(leftHeight, rightHeight);
    }

    // Левый поворот
    private void turnLeft(Node a) {
        Node b = a.right;
        a.right = b.left;

        if (b.left != null) {
            b.left.parent = a;
        }

        b.parent = a.parent;

        if (a.parent == null) {
            root = b;
        } else if (a == a.parent.left) {
            a.parent.left = b;
        } else {
            a.parent.right = b;
        }

        b.left = a;
        a.parent = b;
    }
    // Правый поворот
    private void turnRight(Node a) {
        Node b = a.left;
        a.left = b.right;

        if (b.right != null) {
            b.right.parent = a;
        }

        b.parent = a.parent;

        if (a.parent == null) {
            root = b;
        } else if (a == a.parent.left) {
            a.parent.left = b;
        } else {
            a.parent.right = b;
        }

        b.right = a;
        a.parent = b;
    }

    //Insert
    public void insert(int key) {
        Node newNode = new Node(key);
        root = insertKey(root, newNode);
        balanceInsert(newNode);
    }
    private Node insertKey(Node root, Node node) {
        if (root == null) {
            return node; // Возвращаем новый узел
        }

        if (node.key < root.key) {
            root.left = insertKey(root.left, node);
            root.left.parent = root;
        } else if (node.key > root.key) {
            root.right = insertKey(root.right, node);
            root.right.parent = root;
        }

        return root;
    }

    // Исправление после вставки
    private void balanceInsert(Node node) {
        Node parent, grandparent;

        //Исключение первого и второго случая
        while (node != root && node.color == RED && node.parent.color == RED) {
            parent = node.parent;
            grandparent = parent.parent;

            if (parent == grandparent.left) { // Родитель — левый потомок
                Node uncle = grandparent.right;

                if (uncle != null && uncle.color == RED) { // Случай 1: Дядя красный
                    grandparent.color = RED;
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    node = grandparent;
                } else {
                    if (node == parent.right) { // Случай 2: Правый потомок
                        turnLeft(parent);
                        node = parent;
                        parent = node.parent;
                    }
                    // Случай 3: Левый потомок
                    turnRight(grandparent);
                    boolean tempColor = parent.color;
                    parent.color = grandparent.color;
                    grandparent.color = tempColor;
                    node = parent;
                }
            } else { // Родитель — правый потомок
                Node uncle = grandparent.left;

                if (uncle != null && uncle.color == RED) { // Случай 1: Дядя красный
                    grandparent.color = RED;
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    node = grandparent;
                } else {
                    if (node == parent.left) { // Случай 2: Левый потомок
                        turnRight(parent);
                        node = parent;
                        parent = node.parent;
                    }
                    // Случай 3: Правый потомок
                    turnLeft(grandparent);
                    boolean tempColor = parent.color;
                    parent.color = grandparent.color;
                    grandparent.color = tempColor;
                    node = parent;
                }
            }
        }
        //Первый случай
        root.color = BLACK; // Корень всегда черный
    }

    // Удаление элемента
    public void delete(int key) {
        Node node = searchNode(root, key);
        if (node != null) {
            deleteNode(node);
        }
    }

    private Node searchNode(Node root, int key) {
        while (root != null) {
            if (key < root.key) {
                root = root.left;
            } else if (key > root.key) {
                root = root.right;
            } else {
                return root;
            }
        }
        return null;
    }
    private void deleteNode(Node node) {
        Node y = node;
        Node x;
        boolean yOriginalColor = y.color;

        if (node.left == null) {
            x = node.right;
            transplant(node, node.right);
        } else if (node.right == null) {
            x = node.left;
            transplant(node, node.left);
        } else {
            y = getMin(node.right);
            yOriginalColor = y.color;
            x = y.right;

            if (y.parent == node) {
                if (x != null) {
                    x.parent = y;
                }
            } else {
                transplant(y, y.right);
                y.right = node.right;
                y.right.parent = y;
            }

            transplant(node, y);
            y.left = node.left;
            y.left.parent = y;
            y.color = node.color;
        }

        if (yOriginalColor == BLACK) {
            balanceDelete(x);
        }
    }

    private void transplant(Node a, Node b) {
        if (a.parent == null) {
            root = b;
        } else if (a == a.parent.left) {
            a.parent.left = b;
        } else {
            a.parent.right = b;
        }

        if (b != null) {
            b.parent = a.parent;
        }
    }

    private Node getMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private void balanceDelete(Node x) {
        while (x != root && getColor(x) == BLACK) {
            if (x == x.parent.left) {
                Node w = x.parent.right;

                if (getColor(w) == RED) { // Случай 1
                    w.color = BLACK;
                    x.parent.color = RED;
                    turnLeft(x.parent);
                    w = x.parent.right;
                }

                if (getColor(w.left) == BLACK && getColor(w.right) == BLACK) { // Случай 2
                    w.color = RED;
                    x = x.parent;
                } else {
                    if (getColor(w.right) == BLACK) { // Случай 3
                        w.left.color = BLACK;
                        w.color = RED;
                        turnRight(w);
                        w = x.parent.right;
                    }
                    // Случай 4
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.right.color = BLACK;
                    turnLeft(x.parent);
                    x = root;
                }
            } else {
                Node w = x.parent.left;

                if (getColor(w) == RED) { // Случай 1
                    w.color = BLACK;
                    x.parent.color = RED;
                    turnRight(x.parent);
                    w = x.parent.left;
                }

                if (getColor(w.right) == BLACK && getColor(w.left) == BLACK) { // Случай 2
                    w.color = RED;
                    x = x.parent;
                } else {
                    if (getColor(w.left) == BLACK) { // Случай 3
                        w.right.color = BLACK;
                        w.color = RED;
                        turnLeft(w);
                        w = x.parent.left;
                    }
                    // Случай 4
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.left.color = BLACK;
                    turnRight(x.parent);
                    x = root;
                }
            }
        }

        if (x != null) {
            x.color = BLACK;
        }
    }

    private boolean getColor(Node node) {
        return node == null ? BLACK : node.color;
    }

    //printTree
    public void printTree(){
        System.out.println("Прямой обход: ");
        printPre(root);
        System.out.println();
        System.out.println("Симметричный обход: ");
        printIn(root);
        System.out.println();
        System.out.println("Обратный обход: ");
        printPost(root);
        System.out.println();
        System.out.println("Уровневый обход(в ширину): ");
        printLevel(root);
        System.out.println();
    }
    // Прямой обход (Pre-order)
    private void printPre(RedBlackTree.Node node) {
        if (node != null) {
            System.out.print(node.key + " "); // Посещение узла
            printPre(node.left);             // Левое поддерево
            printPre(node.right);            // Правое поддерево
        }
    }
    // Симметричный обход (In-order)
    private void printIn(RedBlackTree.Node node) {
        if (node != null) {
            printIn(node.left);              // Левое поддерево
            System.out.print(node.key + " "); // Посещение узла
            printIn(node.right);             // Правое поддерево
        }
    }
    // Обратный обход (Post-order)
    private void printPost(RedBlackTree.Node node) {
        if (node != null) {
            printPost(node.left);             // Левое поддерево
            printPost(node.right);            // Правое поддерево
            System.out.print(node.key + " "); // Посещение узла
        }
    }
    // Уровневый обход (Level-order)
    public static void printLevel(RedBlackTree.Node root) {
        if (root == null) {
            return;
        }

        Queue<RedBlackTree.Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            RedBlackTree.Node node = queue.poll(); // Извлечение узла
            System.out.print(node.key + " "); // Посещение узла

            if (node.left != null) {
                queue.add(node.left); // Левый потомок
            }

            if (node.right != null) {
                queue.add(node.right); // Правый потомок
            }
        }
    }

}

class Main{

    public static void main(String[] args){

        int[] sizes = {10, 100, 500, 1000, 2000, 4000, 6000, 8000, 10000, 12000};
        Random rand = new Random();
        System.out.println("Случайные величины, распределенные равномерно: ");
        for (int size : sizes){
            BinarySearchTree BStree = new BinarySearchTree();
            AVLTree AVLtree = new AVLTree();
            RedBlackTree RBtree = new RedBlackTree();

            for (int i = 0; i < size; i++){
                BStree.insert(rand.nextInt(Integer.MAX_VALUE));
                AVLtree.insert(rand.nextInt(Integer.MAX_VALUE));
                RBtree.insert(rand.nextInt(Integer.MAX_VALUE));
            }
            System.out.printf("Keys: %d, BST height: %d%n", size, BStree.height());
            System.out.printf("Keys: %d, AVLT height: %d%n", size, AVLtree.height());
            System.out.printf("Keys: %d, RBT height: %d%n", size, RBtree.height());
        }
        System.out.println("Равномерно возрастающие ключи: ");
        for (int size : sizes){
            AVLTree AVLtree = new AVLTree();
            RedBlackTree RBtree = new RedBlackTree();
            for(int i = 0; i < size; i++){
                AVLtree.insert(i);
                RBtree.insert(i);
            }
            System.out.printf("Keys: %d, AVLT height: %d%n", size, AVLtree.height());
            System.out.printf("Keys: %d, RBT height: %d%n", size, RBtree.height());
        }
        BinarySearchTree BStree = new BinarySearchTree();
        AVLTree AVLtree = new AVLTree();
        RedBlackTree RBtree = new RedBlackTree();
        int[] tree = {10, 5, 15, 3, 6, 14, 16};
        for (int i = 0; i < tree.length; i++){
            BStree.insert(tree[i]);
            AVLtree.insert(tree[i]);
            RBtree.insert(tree[i]);
        }
        System.out.println("BST: ");
        BStree.printTree();
        System.out.println("AVL: ");
        AVLtree.printTree();
        System.out.println("RBT: ");
        RBtree.printTree();
    }
}