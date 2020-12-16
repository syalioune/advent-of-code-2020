package fr.syalioune.puzzle.adapter;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Node {

  private Integer value;

  private List<Node> childrens = new LinkedList<>();

  public Node(Integer value) {
    this.value = value;
  }

  public void addChild(Node child) {
    if(!childrens.contains(child)) {
      childrens.add(child);
    }
  }

  public boolean isLeaf() {
    return childrens.size() == 0;
  }

  public Integer getValue() {
    return value;
  }

  public List<Node> getChildrens() {
    return childrens;
  }

  public void setChildrens(List<Node> childrens) {
    this.childrens = childrens;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Node node = (Node) o;
    return value.equals(node.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
