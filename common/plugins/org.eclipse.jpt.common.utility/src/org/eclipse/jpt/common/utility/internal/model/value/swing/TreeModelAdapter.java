/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value.swing;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.model.listener.awt.AWTListChangeListenerWrapper;
import org.eclipse.jpt.common.utility.internal.model.listener.awt.AWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.common.utility.internal.model.listener.awt.AWTStateChangeListenerWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.StaticPropertyValueModel;
import org.eclipse.jpt.common.utility.model.event.ListAddEvent;
import org.eclipse.jpt.common.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.common.utility.model.event.ListClearEvent;
import org.eclipse.jpt.common.utility.model.event.ListEvent;
import org.eclipse.jpt.common.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.listener.StateChangeListener;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.TreeNodeValueModel;

/**
 * This javax.swing.tree.TreeModel can be used to keep a TreeModelListener
 * (e.g. a JTree) in synch with a tree of TreeNodeValueModel objects. Unlike
 * javax.swing.tree.DefaultTreeModel, you do not add and remove nodes with
 * methods implemented here. You can add and remove nodes by adding and
 * removing them directly to/from the nodes (or, more typically, the domain
 * objects the nodes are wrapping and listening to).
 * 
 * Due to limitations in JTree, the root of the tree can never be null,
 * which, typically, should not be a problem. (If you want to display an empty
 * tree you can set the JTree's treeModel to null.)
 */
public class TreeModelAdapter<T>
	extends AbstractTreeModel
{
	/**
	 * A value model on the underlying tree's root node and its
	 * corresponding listener. This allows clients to swap out
	 * the entire tree. Due to limitations in JTree, the root should
	 * never be set to null while we have listeners.
	 */
	private final PropertyValueModel<TreeNodeValueModel<T>> rootHolder;
	private final PropertyChangeListener rootListener;

	/**
	 * A listener that notifies us when a node's internal
	 * "state" changes (as opposed to the node's value or list of
	 * children), allowing us to forward notification to our listeners.
	 */
	private final StateChangeListener nodeStateListener;

	/**
	 * A listener that notifies us when a node's "value"
	 * changes (as opposed to the node's state or list of
	 * children), allowing us to forward notification to our listeners.
	 * Typically, this will only happen with nodes that hold
	 * primitive data.
	 */
	private final PropertyChangeListener nodeValueListener;

	/**
	 * A listener that notifies us when an underlying node's
	 * "list" of children changes, allowing us to keep our
	 * internal tree in synch with the underlying tree model.
	 */
	private final ListChangeListener childrenListener;

	/* these attributes make up our internal tree */
	/**
	 * The root cannot be null while we have listeners, which is
	 * most of the time. The root is cached so we can disengage
	 * from it when it has been swapped out.
	 */
	private TreeNodeValueModel<T> root;

	/**
	 * Map the nodes to their lists of children.
	 * We cache these so we can swap out the entire list of children
	 * when we receive a #listChanged() event (which does not include
	 * the items that were affected).
	 * @see ChangeEventChangePolicy#rebuildChildren()
	 */
	final IdentityHashMap<TreeNodeValueModel<T>, List<TreeNodeValueModel<T>>> childrenLists;

	/**
	 * Map the children models to their parents.
	 * We cache these so we can figure out the "real" source of the
	 * list change events (the parent).
	 * @see EventChangePolicy#parent()
	 */
	final IdentityHashMap<ListValueModel<TreeNodeValueModel<T>>, TreeNodeValueModel<T>> parents;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a tree model for the specified root.
	 */
	public TreeModelAdapter(PropertyValueModel<TreeNodeValueModel<T>> rootHolder) {
		super();
		if (rootHolder == null) {
			throw new NullPointerException();
		}
		this.rootHolder = rootHolder;
		this.rootListener = this.buildRootListener();
		this.nodeStateListener = this.buildNodeStateListener();
		this.nodeValueListener = this.buildNodeValueListener();
		this.childrenListener = this.buildChildrenListener();
		this.childrenLists = new IdentityHashMap<TreeNodeValueModel<T>, List<TreeNodeValueModel<T>>>();
		this.parents = new IdentityHashMap<ListValueModel<TreeNodeValueModel<T>>, TreeNodeValueModel<T>>();
	}

	/**
	 * Construct a tree model for the specified root.
	 */
	public TreeModelAdapter(TreeNodeValueModel<T> root) {
		this(new StaticPropertyValueModel<TreeNodeValueModel<T>>(root));
	}


	// ********** initialization **********

	protected PropertyChangeListener buildRootListener() {
		return new AWTPropertyChangeListenerWrapper(this.buildRootListener_());
	}

	protected PropertyChangeListener buildRootListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				TreeModelAdapter.this.rootChanged();
			}
			@Override
			public String toString() {
				return "root listener"; //$NON-NLS-1$
			}
		};
	}

	protected PropertyChangeListener buildNodeValueListener() {
		return new AWTPropertyChangeListenerWrapper(this.buildNodeValueListener_());
	}

	protected PropertyChangeListener buildNodeValueListener_() {
		return new PropertyChangeListener() {
			@SuppressWarnings("unchecked")
			public void propertyChanged(PropertyChangeEvent event) {
				TreeModelAdapter.this.nodeChanged((TreeNodeValueModel<T>) event.getSource());
			}
			@Override
			public String toString() {
				return "node value listener"; //$NON-NLS-1$
			}
		};
	}

	protected StateChangeListener buildNodeStateListener() {
		return new AWTStateChangeListenerWrapper(this.buildNodeStateListener_());
	}

	protected StateChangeListener buildNodeStateListener_() {
		return new StateChangeListener() {
			@SuppressWarnings("unchecked")
			public void stateChanged(StateChangeEvent event) {
				TreeModelAdapter.this.nodeChanged((TreeNodeValueModel<T>) event.getSource());
			}
			@Override
			public String toString() {
				return "node state listener"; //$NON-NLS-1$
			}
		};
	}

	protected ListChangeListener buildChildrenListener() {
		return new AWTListChangeListenerWrapper(this.buildChildrenListener_());
	}

	protected ListChangeListener buildChildrenListener_() {
		return new ListChangeListener() {
			public void itemsAdded(ListAddEvent event) {
				new AddEventChangePolicy(event).addChildren();
			}
			public void itemsRemoved(ListRemoveEvent event) {
				new RemoveEventChangePolicy(event).removeChildren();
			}
			public void itemsReplaced(ListReplaceEvent event) {
				new ReplaceEventChangePolicy(event).replaceChildren();
			}
			public void itemsMoved(ListMoveEvent event) {
				new MoveEventChangePolicy(event).moveChildren();
			}
			public void listCleared(ListClearEvent event) {
				new ClearEventChangePolicy(event).clearChildren();
			}
			public void listChanged(ListChangeEvent event) {
				new ChangeEventChangePolicy(event).rebuildChildren();
			}
			@Override
			public String toString() {
				return "children listener"; //$NON-NLS-1$
			}
		};
	}


	// ********** TreeModel implementation **********

	public Object getRoot() {
		return this.root;
	}

	@SuppressWarnings("unchecked")
	public Object getChild(Object parent, int index) {
		return ((TreeNodeValueModel<T>) parent).child(index);
	}

	@SuppressWarnings("unchecked")
	public int getChildCount(Object parent) {
		return ((TreeNodeValueModel<T>) parent).childrenSize();
	}

	@SuppressWarnings("unchecked")
	public boolean isLeaf(Object node) {
		return ((TreeNodeValueModel<T>) node).isLeaf();
	}

	@SuppressWarnings("unchecked")
	public void valueForPathChanged(TreePath path, Object newValue) {
		((TreeNodeValueModel<T>) path.getLastPathComponent()).setValue((T) newValue);
	}

	@SuppressWarnings("unchecked")
	public int getIndexOfChild(Object parent, Object child) {
		return ((TreeNodeValueModel<T>) parent).indexOfChild((TreeNodeValueModel<T>) child);
	}

	/**
	 * Extend to start listening to the underlying model if necessary.
	 */
    @Override
	public void addTreeModelListener(TreeModelListener l) {
		if (this.hasNoTreeModelListeners()) {
			this.engageModel();
		}
		super.addTreeModelListener(l);
	}

	/**
	 * Extend to stop listening to the underlying model if appropriate.
	 */
    @Override
	public void removeTreeModelListener(TreeModelListener l) {
		super.removeTreeModelListener(l);
		if (this.hasNoTreeModelListeners()) {
			this.disengageModel();
		}
	}


	// ********** behavior **********

	/**
	 * Listen to the root and all the other nodes
	 * in the underlying tree model.
	 */
	private void engageModel() {
		this.rootHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.rootListener);
		this.root = this.rootHolder.getValue();
		if (this.root == null) {
			throw new NullPointerException();	// the root cannot be null while we have listeners
		}
		this.engageNode(this.root);
		this.addRoot();
	}

	/**
	 * Add the root and all of the nodes to the underlying tree.
	 */
	private void addRoot() {
		this.addNode(0, this.root);
	}

	/**
	 * Stop listening to the root and all the other
	 * nodes in the underlying tree model.
	 */
	private void disengageModel() {
		this.removeRoot();
		this.disengageNode(this.root);
		this.root = null;
		this.rootHolder.removePropertyChangeListener(PropertyValueModel.VALUE, this.rootListener);
	}

	/**
	 * Remove the root and all of the nodes from the underlying tree.
	 */
	private void removeRoot() {
		this.removeNode(0, this.root);
	}

	/**
	 * The root has been swapped.
	 * This method is a bit gnarly because the API for notifying listeners
	 * that the root has changed is a bit inconsistent with that used for
	 * non-root nodes.
	 */
	void rootChanged() {
		TreeNodeValueModel<T> newRoot = this.rootHolder.getValue();
		if (newRoot == null) {
			throw new NullPointerException();	// the root cannot be null while we have listeners
		}
		// remove all the current root's children from the tree
		// and remove the it from the internal tree
		this.removeRoot(); 

		// save the old root and swap in the new root
		TreeNodeValueModel<T> oldRoot = this.root;
		this.root = newRoot;

		// we must be listening to both the old and new roots when we fire the event
		// because their values can be affected by whether they have listeners
		this.engageNode(this.root);
		this.fireTreeRootReplaced(this.root);
		// now we can stop listening to the old root
		this.disengageNode(oldRoot);

		// add the new root to the internal tree and
		// add all its children to the tree also
		this.addRoot();
	}

	/**
	 * Either the "value" or the "state" of the specified node has changed,
	 * forward notification to our listeners.
	 */
	void nodeChanged(TreeNodeValueModel<T> node) {
		TreeNodeValueModel<T> parent = node.parent();
		if (parent == null) {
			this.fireTreeRootChanged(node);
		} else {
			this.fireTreeNodeChanged(parent.path(), parent.indexOfChild(node), node);
		}
	}

	/**
	 * Listen to the nodes, notify our listeners that the nodes were added,
	 * and then add the nodes to our internal tree.
	 * We must listen to the nodes before notifying anybody, because
	 * adding a listener can change the value of a node.
	 */
	void addChildren(TreeNodeValueModel<T>[] path, int[] childIndices, TreeNodeValueModel<T>[] children) {
		int len = childIndices.length;
		for (int i = 0; i < len; i++) {
			this.engageNode(children[i]);
		}
		this.fireTreeNodesInserted(path, childIndices, children);
		for (int i = 0; i < len; i++) {
			this.addNode(childIndices[i], children[i]);
		}
	}

	/**
	 * Listen to the node and its children model.
	 */
	private void engageNode(TreeNodeValueModel<T> node) {
		node.addStateChangeListener(this.nodeStateListener);
		node.addPropertyChangeListener(PropertyValueModel.VALUE, this.nodeValueListener);
		node.childrenModel().addListChangeListener(ListValueModel.LIST_VALUES, this.childrenListener);
	}

	/**
	 * Add the node to our internal tree;
	 * then recurse down through the node's children,
	 * adding them to the internal tree also.
	 */
	private void addNode(int index, TreeNodeValueModel<T> node) {
		this.addNodeToInternalTree(node.parent(), index, node, node.childrenModel());
		new NodeChangePolicy(node).addChildren();
	}

	/**
	 * Add the specified node to our internal tree.
	 */
	private void addNodeToInternalTree(TreeNodeValueModel<T> parent, int index, TreeNodeValueModel<T> node, ListValueModel<TreeNodeValueModel<T>> childrenModel) {
		List<TreeNodeValueModel<T>> siblings = this.childrenLists.get(parent);
		if (siblings == null) {
			siblings = new ArrayList<TreeNodeValueModel<T>>();
			this.childrenLists.put(parent, siblings);
		}
		siblings.add(index, node);

		this.parents.put(childrenModel, node);
	}

	/**
	 * Remove nodes from our internal tree, notify our listeners that the
	 * nodes were removed, then stop listening to the nodes.
	 * We must listen to the nodes until after notifying anybody, because
	 * removing a listener can change the value of a node.
	 */
	void removeChildren(TreeNodeValueModel<T>[] path, int[] childIndices, TreeNodeValueModel<T>[] children) {
		int len = childIndices.length;
		for (int i = 0; i < len; i++) {
			// the indices slide down a notch each time we remove a child
			this.removeNode(childIndices[i] - i, children[i]);
		}
		this.fireTreeNodesRemoved(path, childIndices, children);
		for (int i = 0; i < len; i++) {
			this.disengageNode(children[i]);
		}
	}

	/**
	 * First, recurse down through the node's children,
	 * removing them from our internal tree;
	 * then remove the node itself from our internal tree.
	 */
	private void removeNode(int index, TreeNodeValueModel<T> node) {
		new NodeChangePolicy(node).removeChildren();
		this.removeNodeFromInternalTree(node.parent(), index, node.childrenModel());
	}

	/**
	 * Remove the specified node from our internal tree.
	 */
	private void removeNodeFromInternalTree(TreeNodeValueModel<T> parent, int index, ListValueModel<TreeNodeValueModel<T>> childrenModel) {
		this.parents.remove(childrenModel);

		List<TreeNodeValueModel<T>> siblings = this.childrenLists.get(parent);
		siblings.remove(index);
		if (siblings.isEmpty()) {
			this.childrenLists.remove(parent);
		}
	}

	/**
	 * Stop listening to the node and its children model.
	 */
	private void disengageNode(TreeNodeValueModel<T> node) {
		node.childrenModel().removeListChangeListener(ListValueModel.LIST_VALUES, this.childrenListener);
		node.removePropertyChangeListener(PropertyValueModel.VALUE, this.nodeValueListener);
		node.removeStateChangeListener(this.nodeStateListener);
	}

	void moveChildren(TreeNodeValueModel<T> parent, int targetIndex, int sourceIndex, int length) {
		List<TreeNodeValueModel<T>> childrenList = this.childrenLists.get(parent);
		ArrayList<TreeNodeValueModel<T>> temp = new ArrayList<TreeNodeValueModel<T>>(length);
		for (int i = 0; i < length; i++) {
			temp.add(childrenList.remove(sourceIndex));
		}
		childrenList.addAll(targetIndex, temp);

		this.fireTreeStructureChanged(parent.path());
	}


	// ********** standard methods **********

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.root);
	}


	// ********** inner classes **********

	/**
	 * Coalesce some of the common change policy behavior.
	 */
	abstract class ChangePolicy {

		ChangePolicy() {
			super();
		}

		/**
		 * Add the current set of children.
		 */
		void addChildren() {
			TreeModelAdapter.this.addChildren(this.parent().path(), this.childIndices(), this.childArray());
		}

		/**
		 * Remove the current set of children.
		 */
		void removeChildren() {
			TreeModelAdapter.this.removeChildren(this.parent().path(), this.childIndices(), this.childArray());
		}

		/**
		 * Return an array of the indices of the current set of children,
		 * which should be contiguous.
		 */
		int[] childIndices() {
			return this.buildIndices(this.childrenStartIndex(), this.childrenSize());
		}

		/**
		 * Return an array of the current set of children.
		 */
		TreeNodeValueModel<T>[] childArray() {
			return this.buildArray(this.getChildren(), this.childrenSize());
		}

		/**
		 * Build an array to hold the elements in the specified iterator.
		 * If they are different sizes, something is screwed up...
		 */
		TreeNodeValueModel<T>[] buildArray(Iterable<TreeNodeValueModel<T>> elements, int size) {
			@SuppressWarnings("unchecked")
			TreeNodeValueModel<T>[] array = new TreeNodeValueModel[size];
			int i = 0;
			for (TreeNodeValueModel<T> element : elements) {
				array[i++] = element;
			}
			return array;
		}

		/**
		 * Return a set of indices, starting at zero and
		 * continuing for the specified size.
		 */
		int[] buildIndices(int size) {
			return buildIndices(0, size);
		}

		/**
		 * Return a set of indices, starting at the specified index and
		 * continuing for the specified size.
		 */
		int[] buildIndices(int start, int size) {
			int[] indices = new int[size];
			int index = start;
			for (int i = 0; i < size; i++) {
				indices[i] = index++;
			}
			return indices;
		}

		/**
		 * Return the parent of the current set of children.
		 */
		abstract TreeNodeValueModel<T> parent();

		/**
		 * Return the starting index for the current set of children.
		 */
		abstract int childrenStartIndex();

		/**
		 * Return the size of the current set of children.
		 */
		abstract int childrenSize();

		/**
		 * Return the current set of children.
		 */
		abstract Iterable<TreeNodeValueModel<T>> getChildren();
	}


	/**
	 * Wraps a ListEvent for adding, removing, replacing,
	 * and changing children.
	 */
	/* CU private */ abstract class EventChangePolicy
		extends ChangePolicy
	{
		final ListEvent event;

		EventChangePolicy(ListEvent event) {
			super();
			this.event = event;
		}

		/**
		 * Map the ListChangeEvent's source to the corresponding parent.
		 */
		@Override
		TreeNodeValueModel<T> parent() {
			return TreeModelAdapter.this.parents.get(this.event.getSource());
		}
	}


	/**
	 * Wraps a ListAddEvent for adding children.
	 */
	/* CU private */ class AddEventChangePolicy
		extends EventChangePolicy
	{
		AddEventChangePolicy(ListAddEvent event) {
			super(event);
		}

		private ListAddEvent getEvent() {
			return (ListAddEvent) this.event;
		}

		/**
		 * The ListAddEvent's item index is the children start index.
		 */
		@Override
		int childrenStartIndex() {
			return this.getEvent().getIndex();
		}

		/**
		 * The ListAddEvent's size is the children size.
		 */
		@Override
		int childrenSize() {
			return this.getEvent().getItemsSize();
		}

		/**
		 * The ListAddEvent's items are the children.
		 */
		@Override
		@SuppressWarnings("unchecked")
		Iterable<TreeNodeValueModel<T>> getChildren() {
			return (Iterable<TreeNodeValueModel<T>>) this.getEvent().getItems();
		}
	}


	/**
	 * Wraps a ListRemoveEvent for adding children.
	 */
	/* CU private */ class RemoveEventChangePolicy
		extends EventChangePolicy
	{
		RemoveEventChangePolicy(ListRemoveEvent event) {
			super(event);
		}

		private ListRemoveEvent getEvent() {
			return (ListRemoveEvent) this.event;
		}

		/**
		 * The ListRemoveEvent's item index is the children start index.
		 */
		@Override
		int childrenStartIndex() {
			return this.getEvent().getIndex();
		}

		/**
		 * The ListRemoveEvent's size is the children size.
		 */
		@Override
		int childrenSize() {
			return this.getEvent().getItemsSize();
		}

		/**
		 * The ListRemoveEvent's items are the children.
		 */
		@Override
		@SuppressWarnings("unchecked")
		Iterable<TreeNodeValueModel<T>> getChildren() {
			return (Iterable<TreeNodeValueModel<T>>) this.getEvent().getItems();
		}
	}


	/**
	 * Wraps a ListReplaceEvent for replacing children.
	 */
	/* CU private */ class ReplaceEventChangePolicy
		extends EventChangePolicy
	{
		ReplaceEventChangePolicy(ListReplaceEvent event) {
			super(event);
		}

		private ListReplaceEvent getEvent() {
			return (ListReplaceEvent) this.event;
		}

		/**
		 * The ListReplaceEvent's item index is the children start index.
		 */
		@Override
		int childrenStartIndex() {
			return this.getEvent().getIndex();
		}

		/**
		 * The ListReplaceEvent's size is the children size.
		 */
		@Override
		int childrenSize() {
			return this.getEvent().getItemsSize();
		}

		/**
		 * The ListReplaceEvent's items are the children.
		 */
		@Override
		@SuppressWarnings("unchecked")
		Iterable<TreeNodeValueModel<T>> getChildren() {
			return (Iterable<TreeNodeValueModel<T>>) this.getEvent().getNewItems();
		}

		/**
		 * Remove the old nodes and add the new ones.
		 */
		void replaceChildren() {
			TreeNodeValueModel<T>[] parentPath = this.parent().path();
			int[] childIndices = this.childIndices();
			TreeModelAdapter.this.removeChildren(parentPath, childIndices, this.getOldChildren());
			TreeModelAdapter.this.addChildren(parentPath, childIndices, this.childArray());
		}

		TreeNodeValueModel<T>[] getOldChildren() {
			return this.buildArray(this.getOldItems(), this.getEvent().getItemsSize());
		}

		// minimized scope of suppressed warnings
		@SuppressWarnings("unchecked")
		protected Iterable<TreeNodeValueModel<T>> getOldItems() {
			return (Iterable<TreeNodeValueModel<T>>) this.getEvent().getOldItems();
		}
	}


	/**
	 * Wraps a ListMoveEvent for moving children.
	 */
	/* CU private */ class MoveEventChangePolicy
		extends EventChangePolicy
	{
		MoveEventChangePolicy(ListMoveEvent event) {
			super(event);
		}

		private ListMoveEvent getEvent() {
			return (ListMoveEvent) this.event;
		}

		void moveChildren() {
			TreeModelAdapter.this.moveChildren(this.parent(), this.getEvent().getTargetIndex(), this.getEvent().getSourceIndex(), this.getEvent().getLength());
		}

		@Override
		int childrenStartIndex() {
			throw new UnsupportedOperationException();
		}

		@Override
		int childrenSize() {
			throw new UnsupportedOperationException();
		}

		@Override
		Iterable<TreeNodeValueModel<T>> getChildren() {
			throw new UnsupportedOperationException();
		}
	}


	/**
	 * Wraps a ListClearEvent for clearing children.
	 */
	/* CU private */ class ClearEventChangePolicy
		extends EventChangePolicy
	{
		ClearEventChangePolicy(ListClearEvent event) {
			super(event);
		}

		/**
		 * Clear all the nodes.
		 */
		void clearChildren() {
			TreeNodeValueModel<T> parent = this.parent();
			TreeNodeValueModel<T>[] parentPath = parent.path();
			List<TreeNodeValueModel<T>> childrenList = TreeModelAdapter.this.childrenLists.get(parent);
			int[] childIndices = this.buildIndices(childrenList.size());
			TreeNodeValueModel<T>[] childArray = this.buildArray(childrenList, childrenList.size());
			TreeModelAdapter.this.removeChildren(parentPath, childIndices, childArray);
		}

		@Override
		int childrenStartIndex() {
			throw new UnsupportedOperationException();
		}

		@Override
		int childrenSize() {
			throw new UnsupportedOperationException();
		}

		@Override
		Iterable<TreeNodeValueModel<T>> getChildren() {
			throw new UnsupportedOperationException();
		}
	}


	/**
	 * Wraps a ListChangeEvent for clearing children.
	 */
	/* CU private */ class ChangeEventChangePolicy
		extends EventChangePolicy
	{
		ChangeEventChangePolicy(ListChangeEvent event) {
			super(event);
		}

		/**
		 * Remove all the old nodes and add all the new nodes.
		 */
		void rebuildChildren() {
			TreeNodeValueModel<T> parent = this.parent();
			TreeNodeValueModel<T>[] parentPath = parent.path();
			List<TreeNodeValueModel<T>> childrenList = TreeModelAdapter.this.childrenLists.get(parent);
			int[] childIndices = this.buildIndices(childrenList.size());
			TreeNodeValueModel<T>[] childArray = this.buildArray(childrenList, childrenList.size());
			TreeModelAdapter.this.removeChildren(parentPath, childIndices, childArray);

			childIndices = this.buildIndices(parent.childrenModel().size());
			childArray = this.buildArray(parent.childrenModel(), parent.childrenSize());
			TreeModelAdapter.this.addChildren(parentPath, childIndices, childArray);
		}

		@Override
		int childrenStartIndex() {
			throw new UnsupportedOperationException();
		}

		@Override
		int childrenSize() {
			throw new UnsupportedOperationException();
		}

		@Override
		Iterable<TreeNodeValueModel<T>> getChildren() {
			throw new UnsupportedOperationException();
		}
	}


	/**
	 * Wraps a TreeNodeValueModel for adding and removing its children.
	 */
	/* CU private */ class NodeChangePolicy
		extends ChangePolicy
	{
		private final TreeNodeValueModel<T> node;

		NodeChangePolicy(TreeNodeValueModel<T> node) {
			super();
			this.node = node;
		}

		/**
		 * The node itself is the parent.
		 */
		@Override
		TreeNodeValueModel<T> parent() {
			return this.node;
		}

		/**
		 * Since we will always be dealing with all of the node's
		 * children, the children start index is always zero.
		 */
		@Override
		int childrenStartIndex() {
			return 0;
		}

		/**
		 * Since we will always be dealing with all of the node's
		 * children, the children size is always equal to the size
		 * of the children model.
		 */
		@Override
		int childrenSize() {
			return this.node.childrenModel().size();
		}

		/**
		 * Since we will always be dealing with all of the node's
		 * children, the children are all the objects held by
		 * the children model.
		 */
		@Override
		Iterable<TreeNodeValueModel<T>> getChildren() {
			return this.node.childrenModel();
		}
	}
}
