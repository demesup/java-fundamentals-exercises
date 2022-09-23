package com.bobocode.cs;

import com.bobocode.util.ExerciseNotCompletedException;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * {@link RecursiveBinarySearchTree} is an implementation of a {@link BinarySearchTree} that is based on a linked nodes
 * and recursion. A tree node is represented as a nested class {@link Node}. It holds an element (a value) and
 * two references to the left and right child nodes.
 * <p><p>
 * <strong>TODO: to get the most out of your learning, <a href="https://www.bobocode.com/learn">visit our website</a></strong>
 * <p>
 *
 * @param <T> a type of elements that are stored in the tree
 * @author Taras Boychuk
 * @author Maksym Stasiuk
 */
public class RecursiveBinarySearchTree<T extends Comparable<T>> implements BinarySearchTree<T> {

    @AllArgsConstructor
    static class Node<T> {
        T element;
        Node<T> right;
        Node<T> left;

        public Node(T element) {
            this.element = element;
        }

        public Node(T element, Node<T> right) {
            this.element = element;
            this.right = right;
        }
    }

    private Node<T> root;
    private int size = 0;

    public static <T extends Comparable<T>> RecursiveBinarySearchTree<T> of(T... elements) {
        RecursiveBinarySearchTree<T> tree = new RecursiveBinarySearchTree<>();
        Arrays.stream(elements).forEach(tree::insert);
        return tree;
    }

    @Override
    public boolean insert(T element) {
        return insertElement(Objects.requireNonNull(element));
    }

    private boolean insertElement(T element) {
        if (root == null) {
            root = new Node<>(element);
            size++;
            return true;
        } else {
            return insertToSubTree(element, root);
        }
    }

    private boolean insertToSubTree(T element, Node<T> root) {
        int compare = element.compareTo(root.element);
        if (compare < 0) {
            return insertToLeft(element, root);
        } else if (compare > 0) {
            return insertToRight(element, root);
        } else {
            return false;
        }
    }

    private boolean insertToRight(T element, Node<T> root) {
        if (root.right == null) {
            root.right = new Node<>(element);
            size++;
            return true;
        } else return insertToSubTree(element, root.right);
    }

    private boolean insertToLeft(T element, Node<T> root) {
        if (root.left == null) {
            root.left = new Node<>(element);
            size++;
            return true;
        } else return insertToSubTree(element, root.left);
    }

    @Override
    public boolean contains(T element) {
        return findByElement(Objects.requireNonNull(element), root) != null;
    }

    private Node<T> findByElement(T element, Node<T> root) {
        if (root == null) {
            return null;
        }
        int compare = element.compareTo(root.element);
        if (compare < 0) {
            return findByElement(element, root.left);
        } else if (compare > 0) {
            return findByElement(element, root.right);
        } else return root;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int depth() {
        return root != null ? depth(root) - 1 : 0;
    }

    private int depth(Node<T> node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + Math.max(depth(node.left), depth(node.right));
        }
    }

    @Override
    public void inOrderTraversal(Consumer<T> consumer) {
        inOrderTraversal(consumer, root);
    }

    private void inOrderTraversal(Consumer<T> consumer, Node<T> root) {
        if (root!=null){
            inOrderTraversal(consumer,root.left);
            consumer.accept(root.element);
            inOrderTraversal(consumer,root.right);
        }
    }
}
