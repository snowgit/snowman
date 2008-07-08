/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sun.darkstar.example.tool;

import com.jme.scene.Node;
import com.jme.scene.Spatial;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * This class interprets a JME scene graph as a Swing JTree Model
 * @author Jeffrey Kesselman
 */
class JMonkeyTreeModel implements TreeModel {

    Spatial root;
    List<TreeModelListener> listeners = new ArrayList<TreeModelListener>();

    public JMonkeyTreeModel(Spatial sceneRoot) {
        root = sceneRoot;
    }

    public Object getRoot() {
        return root;
    }

    public Object getChild(Object parent, int index) {
        return ((Node) parent).getChild(index);
    }

    public int getChildCount(Object parent) {
        if (parent instanceof Node) {
            List<Spatial> children = ((Node) parent).getChildren();
            if (children != null) {
                return children.size();
            }
        }
        return 0;
    }

    public boolean isLeaf(Object node) {
        return !(node instanceof Node);
    }

    public void addChild(Node parent, Spatial child) {
        parent.attachChild(child);
        for (TreeModelListener l : listeners) {
            l.treeNodesInserted(new TreeModelEvent(this, makePath(parent),
                    new int[]{parent.getChildIndex(child)},
                    new Object[]{child}));
        }
    }

    void deleteNode(Spatial node) {
        Node parent = node.getParent();
        if (parent != null) {
            int idx = parent.getChildIndex(node);
            node.removeFromParent();
            for (TreeModelListener l : listeners) {
                l.treeNodesRemoved(new TreeModelEvent(this, makePath(parent),
                        new int[]{idx}, new Object[]{node}));
            }
        }
    }

    public void valueForPathChanged(TreePath path, Object newValue) {
    }

    public int getIndexOfChild(Object parent, Object child) {
        return ((DefaultMutableTreeNode) parent).getIndex(
                (DefaultMutableTreeNode) child);
    }

    public void addTreeModelListener(TreeModelListener l) {
        listeners.add(l);
    }

    public void removeTreeModelListener(TreeModelListener l) {
        listeners.remove(l);
    }

    private Object[] makePath(Spatial node) {
        List<Object> pathList = new LinkedList<Object>();
        pathList.add(node);
        while (node.getParent() != null) {
            node = node.getParent();
            pathList.add(0, node);
        }
        return pathList.toArray();
    }
}
