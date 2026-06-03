// AgroChain - Crop Inventory Management using AVL Tree

class AVLNode {
    int batchID;
    int height;
    AVLNode left, right;

    AVLNode(int batchID) {
        this.batchID = batchID;
        this.height = 1;
    }
}

class AVLTree {

    int height(AVLNode node) {
        return (node == null) ? 0 : node.height;
    }

    int getBalance(AVLNode node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    AVLNode insert(AVLNode node, int batchID) {

        if (node == null)
            return new AVLNode(batchID);

        if (batchID < node.batchID)
            node.left = insert(node.left, batchID);
        else if (batchID > node.batchID)
            node.right = insert(node.right, batchID);
        else
            return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        // LL Rotation
        if (balance > 1 && batchID < node.left.batchID)
            return rightRotate(node);

        // RR Rotation
        if (balance < -1 && batchID > node.right.batchID)
            return leftRotate(node);

        // LR Rotation
        if (balance > 1 && batchID > node.left.batchID) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL Rotation
        if (balance < -1 && batchID < node.right.batchID) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    boolean search(AVLNode root, int batchID) {
        if (root == null)
            return false;

        if (root.batchID == batchID)
            return true;

        if (batchID < root.batchID)
            return search(root.left, batchID);

        return search(root.right, batchID);
    }

    void inorder(AVLNode root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.batchID + " ");
            inorder(root.right);
        }
    }
}

public class AgroChainAVL {
    public static void main(String[] args) {

        AVLTree tree = new AVLTree();
        AVLNode root = null;

        // Crop Batch IDs
        root = tree.insert(root, 105);
        root = tree.insert(root, 120);
        root = tree.insert(root, 110);
        root = tree.insert(root, 130);
        root = tree.insert(root, 115);

        System.out.println("Sorted Crop Batch IDs:");
        tree.inorder(root);

        System.out.println("\n\nSearching Batch ID 115:");

        if (tree.search(root, 115))
            System.out.println("Batch Found");
        else
            System.out.println("Batch Not Found");
    }
}