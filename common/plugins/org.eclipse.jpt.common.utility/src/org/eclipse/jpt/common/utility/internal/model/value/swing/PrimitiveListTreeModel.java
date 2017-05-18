/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value.swing;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.eclipse.jpt.common.utility.internal.model.listener.awt.AWTListChangeListenerWrapper;
import org.eclipse.jpt.common.utility.model.event.ListAddEvent;
import org.eclipse.jpt.common.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.common.utility.model.event.ListClearEvent;
import org.eclipse.jpt.common.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;

/**
 * This TreeModel implementation provides a tree with a "null" root that
 * has a set of "primitive" children. These "primitive" children do not have
 * children themselves, making the tree a maximum of 2 levels deep.
 * This model automatically synchronizes the root's children with a
 * ListValueModel that holds a collection of primitive (non-model) objects
 * (e.g. Strings).
 * 
 * This is useful for providing an "editable" list of primitives. Since the JDK
 * does not provide us with an editable listbox, we must use an editable tree.
 * We wrap everything in DefaultMutableTreeNodes.
 * 
 * Subclasses must implement #primitiveChanged(int, Object) and update
 * the model appropriately. This method is called when the user edits the
 * list directly and presses <Enter>.
 * 
 * The JTree using this model must be configured as "editable":
 * 	tree.setEditable(true);
 */
// TODO convert to use an adapter instead of requiring subclass
public abstract class PrimitiveListTreeModel
	extends DefaultTreeModel
{
	/** a model on the list of primitives */
	private final ListValueModel<?> listHolder;

	/** a listener that handles the adding, removing, and replacing of the primitives */
	private final ListChangeListener listChangeListener;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********
	
	/**
	 * Public constructor - the list holder is required
	 */
	public PrimitiveListTreeModel(ListValueModel<?> listHolder) {
		super(new DefaultMutableTreeNode(null, true));  // true = the root can have children
		if (listHolder == null) {
			throw new NullPointerException();
		}
		this.listHolder = listHolder;
		this.listChangeListener = this.buildListChangeListener();
		// postpone listening to the model until we have listeners ourselves
	}

	protected ListChangeListener buildListChangeListener() {
		return new AWTListChangeListenerWrapper(this.buildListChangeListener_());
	}

	protected ListChangeListener buildListChangeListener_() {
		return new PrimitiveListChangeListener();
	}


	// ********** behavior **********

	/**
	 * Subclasses should override this method to update the 
	 * model appropriately. The primitive at the specified index was
	 * edited directly by the user and the new value is as specified.
	 * Convert the value appropriately and place it in the model.
	 */
	protected abstract void primitiveChanged(int index, Object newValue);


	// ********** TreeModel implementation **********

	/**
	 * Override to change the underlying model instead of changing the node directly.
	 */
    @Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		TreeNode node = (TreeNode) path.getLastPathComponent();
		int index = ((TreeNode) this.getRoot()).getIndex(node);
		this.primitiveChanged(index, newValue);
	}

	/**
	 * Extend to start listening to the underlying model if necessary.
	 */
    @Override
	public void addTreeModelListener(TreeModelListener l) {
		if (this.getTreeModelListeners().length == 0) {
			this.listHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.listChangeListener);
			this.synchronizeList();
		}
		super.addTreeModelListener(l);
	}

	/**
	 * Extend to stop listening to the underlying model if appropriate.
	 */
    @Override
	public void removeTreeModelListener(TreeModelListener l) {
		super.removeTreeModelListener(l);
		if (this.getTreeModelListeners().length == 0) {
			this.listHolder.removeListChangeListener(ListValueModel.LIST_VALUES, this.listChangeListener);
		}
	}


	// ********** behavior **********

	/**
	 * Synchronize our list of nodes with the list of primitives
	 */
	void synchronizeList() {
		this.clearList();
		this.buildList();
	}

	void clearList() {
		int childcount = this.root.getChildCount();
		for (int i = childcount - 1; i >= 0; i--) {
			this.removeNodeFromParent((MutableTreeNode)this.root.getChildAt(i));
		}
	}
		
	private void buildList() {
		for (Iterator<?> stream = this.listHolder.iterator(); stream.hasNext(); ) {
			this.addPrimitive(stream.next());
		}
	}

	/**
	 * Add the specified primitive to the end of the list.
	 */
	private void addPrimitive(Object primitive) {
		this.insertPrimitive(this.root.getChildCount(), primitive);
	}

	/**
	 * Create a node for the specified primitive
	 * and insert it as a child of the root.
	 */
	void insertPrimitive(int index, Object primitive) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(primitive, false); // don't allow children on the child node
		this.insertNodeInto(node, (MutableTreeNode) this.root, index);
	}

	/**
	 * Remove node at the specified index.
	 */
	MutableTreeNode removeNode(int index) {
		MutableTreeNode node = (MutableTreeNode) this.root.getChildAt(index);
		this.removeNodeFromParent(node);
		return node;
	}

	/**
	 * Replace the user object of the node at childIndex.
	 */
	void replacePrimitive(int index, Object primitive) {
		MutableTreeNode node = (MutableTreeNode) this.root.getChildAt(index);
		node.setUserObject(primitive);
		this.nodeChanged(node);
	}


	// ********** inner class **********

	private class PrimitiveListChangeListener implements ListChangeListener {
		PrimitiveListChangeListener() {
			super();
		}

		public void itemsAdded(ListAddEvent event) {
			int i = event.getIndex();
			for (Object item : event.getItems()) {
				PrimitiveListTreeModel.this.insertPrimitive(i++, item);
			}
		}

		public void itemsRemoved(ListRemoveEvent event) {
			for (int i = 0; i < event.getItemsSize(); i++) {
				PrimitiveListTreeModel.this.removeNode(event.getIndex());
			}
		}

		public void itemsReplaced(ListReplaceEvent event) {
			int i = event.getIndex();
			for (Object item : event.getNewItems()) {
				PrimitiveListTreeModel.this.replacePrimitive(i++, item);
			}
		}

		public void itemsMoved(ListMoveEvent event) {
			ArrayList<MutableTreeNode> temp = new ArrayList<>(event.getLength());
			for (int i = 0; i < event.getLength(); i++) {
				temp.add(PrimitiveListTreeModel.this.removeNode(event.getSourceIndex()));
			}
			int i = event.getTargetIndex();
			for (MutableTreeNode node : temp) {
				PrimitiveListTreeModel.this.insertPrimitive(i++, node);
			}
		}

		public void listCleared(ListClearEvent event) {
			PrimitiveListTreeModel.this.clearList();
		}

		public void listChanged(ListChangeEvent event) {
			PrimitiveListTreeModel.this.synchronizeList();
		}

	}

}
