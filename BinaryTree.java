package coverage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.tree.TreeNode;

class Node {
    Node left, right;
    int data;

    public Node(int data) {
        this.data = data;
    }
}

public class BinaryTree {
    static Scanner sc = null;

    static Node createtree() {
        Node root = null;
        System.out.println("enter data:  ");
        int data = sc.nextInt();
        if (data == -1) {
            return null;
        }
        root = new Node(data);
        System.out.println("enter left for  " + data);
        root.left = createtree();
        System.out.println("enter for right  " + data);
        root.right = createtree();
        return root;
    }

    public static int height_of_tree(Node root) {
        if (root == null) {
            return 0;
        }
        return Math.max(height_of_tree(root.left), height_of_tree(root.right)) + 1;
    }

    public int diameterOfBinaryTree(Node root) {
        int[] diameter = new int[1];
        height(root, diameter);
        return diameter[0];
    }

    public int height(Node node, int[] diameter) {
        if (node == null) {
            return 0;
        }
        int lh = height(node.left, diameter);
        int rh = height(node.right, diameter);
        diameter[0] = Math.max(diameter[0], lh + rh);
        return 1 + Math.max(lh, rh);
    }

    public void postorder(Node node) {
        if (node == null)
            return;

        // Traverse left
        postorder(node.left);
        // Traverse right
        postorder(node.right);
        // Traverse root
        System.out.print(node.data + "->");
    }

    public void inorder(Node node) {
        if (node == null)
            return;

        // Traverse left
        inorder(node.left);
        // Traverse root
        System.out.print(node.data + "->");
        // Traverse right
        inorder(node.right);
    }

    public void preorder(Node node) {
        if (node == null)
            return;

        // Traverse root
        System.out.print(node.data + "->");
        // Traverse left
        preorder(node.left);
        // Traverse right
        preorder(node.right);
    }

    public List<Integer> levelOrder(Node root) {

        Queue<Node> queue = new LinkedList<Node>();
        List<Integer> wrapList = new ArrayList<Integer>();

        if (root == null)
            return wrapList;

        queue.offer(root);
        while (!queue.isEmpty()) {

            if (queue.peek().left != null)
                queue.offer(queue.peek().left);

            if (queue.peek().right != null)
                queue.offer(queue.peek().right);

            wrapList.add(queue.poll().data);
        }
        return wrapList;
    }

    public static int maxPathSum(Node root) {
        int maxValue[] = new int[1];
        maxValue[0] = Integer.MIN_VALUE;
        maxPathDown(root, maxValue);
        return maxValue[0];
    }

    public static int maxPathDown(Node node, int maxValue[]) {
        if (node == null)
            return 0;
        int left = Math.max(0, maxPathDown(node.left, maxValue));
        int right = Math.max(0, maxPathDown(node.right, maxValue));
        maxValue[0] = Math.max(maxValue[0], left + right + node.data);
        return Math.max(left, right) + node.data;
    }

    public int isIdentical(Node root1, Node root2) {
        // Check if both the trees are empty
        if (root1 == null && root2 == null)
            return 1;

        // If any one of the tree is non-empty
        // and other is empty, return false
        else if (root1 != null &&
                root2 == null)
            return 0;
        else if (root1 == null &&
                root2 != null)
            return 0;
        else {
            // Check if current data of both trees equal
            // and recursively check for left and right subtrees
            if (root1.data == root2.data && isIdentical(root1.left, root2.left) == 1
                    && isIdentical(root1.right, root2.right) == 1)
                return 1;
            else
                return 0;
        }
    }

    public ArrayList<ArrayList<Integer>> zigzagLevelOrder(Node root) {
        Queue<Node> queue = new LinkedList<Node>();
        ArrayList<ArrayList<Integer>> wrapList = new ArrayList<>();

        if (root == null)
            return wrapList;

        queue.offer(root);
        boolean flag = true;
        while (!queue.isEmpty()) {
            int levelNum = queue.size();
            ArrayList<Integer> subList = new ArrayList<Integer>(levelNum);
            for (int i = 0; i < levelNum; i++) {
                int index = i;
                if (queue.peek().left != null)
                    queue.offer(queue.peek().left);
                if (queue.peek().right != null)
                    queue.offer(queue.peek().right);
                if (flag == true)
                    subList.add(queue.poll().data);
                else
                    subList.add(0, queue.poll().data);
            }
            flag = !flag;
            wrapList.add(subList);
        }
        return wrapList;
    }

    static Boolean isLeaf(Node root) {
        return (root.left == null) && (root.right == null);
    }

    static void addLeftBoundary(Node root, ArrayList<Integer> res) {
        Node cur = root.left;
        while (cur != null) {
            if (isLeaf(cur) == false)
                res.add(cur.data);
            if (cur.left != null)
                cur = cur.left;
            else
                cur = cur.right;
        }
    }

    static void addRightBoundary(Node root, ArrayList<Integer> res) {
        Node cur = root.right;
        ArrayList<Integer> tmp = new ArrayList<Integer>();
        while (cur != null) {
            if (isLeaf(cur) == false)
                tmp.add(cur.data);
            if (cur.right != null)
                cur = cur.right;
            else
                cur = cur.left;
        }
        int i;
        for (i = tmp.size() - 1; i >= 0; --i) {
            res.add(tmp.get(i));
        }
    }

    public void addLeaves(Node root, ArrayList<Integer> res) {
        if (isLeaf(root)) {
            res.add(root.data);
            return;
        }
        if (root.left != null)
            addLeaves(root.left, res);
        if (root.right != null)
            addLeaves(root.right, res);
    }

    public ArrayList<Integer> printBoundary(Node node) {
        ArrayList<Integer> ans = new ArrayList<Integer>();
        if (isLeaf(node) == false)
            ans.add(node.data);
        addLeftBoundary(node, ans);
        addLeaves(node, ans);
        addRightBoundary(node, ans);
        return ans;
    }

    public static List<List<Integer>> findVertical(Node root) {
        TreeMap<Integer, TreeMap<Integer, PriorityQueue<Integer>>> map = new TreeMap<>();
        Queue<Tuple> q = new LinkedList<Tuple>();
        q.offer(new Tuple(root, 0, 0));
        while (!q.isEmpty()) {
            Tuple tuple = q.poll();
            Node node = (Node) tuple.node;
            int x = tuple.row;
            int y = tuple.col;

            if (!map.containsKey(x)) {
                map.put(x, new TreeMap<>());
            }
            if (!map.get(x).containsKey(y)) {
                map.get(x).put(y, new PriorityQueue<>());
            }
            map.get(x).get(y).offer(node.data);

            if (node.left != null) {
                q.offer(new Tuple(node.left, x - 1, y + 1));
            }
            if (node.right != null) {
                q.offer(new Tuple(node.right, x + 1, y + 1));
            }
        }
        List<List<Integer>> list = new ArrayList<>();
        for (TreeMap<Integer, PriorityQueue<Integer>> ys : map.values()) {
            list.add(new ArrayList<>());
            for (PriorityQueue<Integer> nodes : ys.values()) {
                while (!nodes.isEmpty()) {
                    list.get(list.size() - 1).add(nodes.poll());
                }
            }
        }
        return list;
    }

    public static ArrayList<Integer> topView(Node root) {
        ArrayList<Integer> ans = new ArrayList<>();
        if (root == null)
            return ans;
        Map<Integer, Integer> map = new TreeMap<>();
        Queue<Pair> q = new LinkedList<Pair>();
        q.add(new Pair(root, 0));
        while (!q.isEmpty()) {
            Pair it = q.remove();
            int hd = it.hd;
            Node temp = it.node;
            if (map.get(hd) == null)
                map.put(hd, temp.data);
            if (temp.left != null) {

                q.add(new Pair(temp.left, hd - 1));
            }
            if (temp.right != null) {

                q.add(new Pair(temp.right, hd + 1));
            }
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            ans.add(entry.getValue());
        }
        return ans;

    }

    public ArrayList<Integer> bottomView(Node root) {
        ArrayList<Integer> ans = new ArrayList<>();
        if (root == null)
            return ans;
        Map<Integer, Integer> map = new TreeMap<>();
        Queue<Node> q = new LinkedList<Node>();
        root.hd = 0;
        q.add(root);
        while (!q.isEmpty()) {
            Node temp = q.remove();
            int hd = temp.hd;
            map.put(hd, temp.data);
            if (temp.left != null) {
                temp.left.hd = hd - 1;
                q.add(temp.left);
            }
            if (temp.right != null) {
                temp.right.hd = hd + 1;
                q.add(temp.right);
            }
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            ans.add(entry.getValue());
        }
        return ans;

    }

    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        rightView(root, result, 0);
        return result;
    }

    public void rightView(TreeNode curr, List<Integer> result, int currDepth) {
        if (curr == null) {
            return;
        }
        if (currDepth == result.size()) {
            result.add(curr.val);
        }

        rightView(curr.right, result, currDepth + 1);
        rightView(curr.left, result, currDepth + 1);

    }

    public List<Integer> lightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        leftView(root, result, 0);
        return result;
    }

    public void leftView(TreeNode curr, List<Integer> result, int currDepth) {
        if (curr == null) {
            return;
        }
        if (currDepth == result.size()) {
            result.add(curr.val);
        }

        leftView(curr.left, result, currDepth + 1);
        leftView(curr.right, result, currDepth + 1);

    }

    static boolean isSymmetricUtil(Node root1, Node root2) {
        if (root1 == null || root2 == null)
            return root1 == root2;
        return (root1.data == root2.data) && isSymmetricUtil(root1.left, root2.right)
                && isSymmetricUtil(root1.right, root2.left);
    }

    static boolean isSymmetric(Node root) {
        if (root == null)
            return true;
        return isSymmetricUtil(root.left, root.right);
    }

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        Node root = createtree();
        int height = height_of_tree(root);
        System.out.println(height);
        // call the function write above
    }

}

class Tuple {
    Node node;
    int row;
    int col;

    public Tuple(Node root, int _row, int _col) {
        node = root;
        row = _row;
        col = _col;
    }
}
