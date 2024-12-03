
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
    //getMin
    private Node getMin(Node root){
        while (root.left != null){
            root = root.left;
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
        print(root);
        System.out.println();
    }
    private void print(Node root){
        if (root != null){
            print(root.left);
            System.out.print(root.key + " ");
            print(root.right);
        }
    }

    //Check
    public static void main(String[] args){
        BinarySearchTree tree = new BinarySearchTree();

        tree.insert(10);
        tree.insert(50);
        tree.insert(20);
        tree.insert(5);

        System.out.println("Tree: ");
        tree.printTree();

        System.out.println("Search 20: " + tree.search(20));
        System.out.println("Search 100: " + tree.search(100));

        System.out.println("Delete 20: ");
        tree.delete(20);
        tree.printTree();
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
    public void printTree(){
        print(root);
        System.out.println();
    }
    private void print(Node root){
        if (root != null){
            print(root.left);
            System.out.print(root.key + " ");
            print(root.right);
        }
    }

    //Check
    public static void main(String[] args){
        AVLTree tree = new AVLTree();

        tree.insert(10);
        tree.insert(50);
        tree.insert(20);
        tree.insert(5);

        System.out.println("Tree: ");
        tree.printTree();

        System.out.println("Search 20: " + tree.search(20));
        System.out.println("Search 100: " + tree.search(100));

        System.out.println("Delete 20: ");
        tree.delete(20);
        tree.printTree();
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

    // Левый поворот
    private void rotateLeft(Node a) {
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
    private void rotateRight(Node a) {
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
        root = insertRec(root, newNode);
        balanceInsert(newNode);
    }
    private Node insertRec(Node root, Node node) {
        if (root == null) {
            return node; // Возвращаем новый узел
        }

        if (node.key < root.key) {
            root.left = insertRec(root.left, node);
            root.left.parent = root;
        } else if (node.key > root.key) {
            root.right = insertRec(root.right, node);
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
                        rotateLeft(parent);
                        node = parent;
                        parent = node.parent;
                    }
                    // Случай 3: Левый потомок
                    rotateRight(grandparent);
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
                        rotateRight(parent);
                        node = parent;
                        parent = node.parent;
                    }
                    // Случай 3: Правый потомок
                    rotateLeft(grandparent);
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
            y = minimum(node.right);
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
            fixDelete(x);
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

    private Node minimum(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private void fixDelete(Node x) {
        while (x != root && getColor(x) == BLACK) {
            if (x == x.parent.left) {
                Node w = x.parent.right;

                if (getColor(w) == RED) { // Случай 1
                    w.color = BLACK;
                    x.parent.color = RED;
                    rotateLeft(x.parent);
                    w = x.parent.right;
                }

                if (getColor(w.left) == BLACK && getColor(w.right) == BLACK) { // Случай 2
                    w.color = RED;
                    x = x.parent;
                } else {
                    if (getColor(w.right) == BLACK) { // Случай 3
                        w.left.color = BLACK;
                        w.color = RED;
                        rotateRight(w);
                        w = x.parent.right;
                    }
                    // Случай 4
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.right.color = BLACK;
                    rotateLeft(x.parent);
                    x = root;
                }
            } else {
                Node w = x.parent.left;

                if (getColor(w) == RED) { // Случай 1
                    w.color = BLACK;
                    x.parent.color = RED;
                    rotateRight(x.parent);
                    w = x.parent.left;
                }

                if (getColor(w.right) == BLACK && getColor(w.left) == BLACK) { // Случай 2
                    w.color = RED;
                    x = x.parent;
                } else {
                    if (getColor(w.left) == BLACK) { // Случай 3
                        w.right.color = BLACK;
                        w.color = RED;
                        rotateLeft(w);
                        w = x.parent.left;
                    }
                    // Случай 4
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.left.color = BLACK;
                    rotateRight(x.parent);
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

    // Обход дерева (in-order)
    public void inorder() {
        inorderRec(root);
        System.out.println();
    }
    private void inorderRec(Node node) {
        if (node != null) {
            inorderRec(node.left);
            System.out.print(node.key + (node.color == RED ? "R " : "B "));
            inorderRec(node.right);
        }
    }

    public static void main(String[] args) {
        RedBlackTree rbt = new RedBlackTree();

        // Пример работы
        rbt.insert(10);
        rbt.insert(20);
        rbt.insert(30);
        rbt.insert(40);
        rbt.insert(50);
        rbt.insert(25);

        System.out.println("Обход дерева после вставок:");
        rbt.inorder();

        rbt.delete(20);
        System.out.println("Обход дерева после удаления 20:");
        rbt.inorder();

        rbt.delete(30);
        System.out.println("Обход дерева после удаления 30:");
        rbt.inorder();
    }
}