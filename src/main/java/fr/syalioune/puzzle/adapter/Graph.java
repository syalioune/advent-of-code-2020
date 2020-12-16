package fr.syalioune.puzzle.adapter;

public class Graph {

  private Node root;

  public Graph(int i) {
    root = new Node(i);
  }

  public Node getRoot() {
    return root;
  }

  public int nbOfLeaves() {
    int res = 0;
    if(root.isLeaf()) {
      res = 1;
    } else {
      for(Node children : root.getChildrens()) {
        res += nbOfLeaves(children);
      }
    }
    return res;
  }

  private int nbOfLeaves(Node node) {
    int res = 0;
    if(node.isLeaf()) {
      res = 1;
    } else {
      for(Node child : node.getChildrens()) {
        res += nbOfLeaves(child);
      }
    }
    return res;
  }

  public void printInDepth() {
    printInDepth(root);
  }

  private void printInDepth(Node node) {
    if(node.isLeaf()) {
      System.out.println(node.getValue());
    } else {
      for(Node child : node.getChildrens()) {
        System.out.print(child.getValue()+ " ");
        printInDepth(child);
        System.out.println("");
      }
    }
  }
}
