/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.jface;

import java.util.Iterator;

import org.eclipse.jpt.ui.jface.TreeItemContentProvider;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.value.CollectionListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.NullListValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;

/**
 * Implementation of {@link TreeItemContentProvider} that provides updating
 * children information for a Model object.
 * 
 * The typical subclass will override the following methods:
 * #getParent()
 *     the default behavior for this method is to return null.  there is no 
 *     property value model for this as this should not be changing for a given
 *     node.  all such changes will be provided by the parent side of the relationship.
 * #buildChildrenModel()
 *     return a {@link ListValueModel} that represents the children for the represented
 *     model object.  #buildChildrenModel(CollectionValueModel) and 
 *     #buildChildrenModel(PropertyValueModel) are provided if the children are more
 *     easily represented as a collection or as a property (single child)
 *     the default behavior is to return a {@link NullListValueModel}
 * 
 * Other methods may be overridden, but take care to preserve the logic provided 
 * by this class.
 */
public abstract class AbstractTreeItemContentProvider<E>
	implements TreeItemContentProvider
{
	private DelegatingTreeContentAndLabelProvider treeContentProvider;
	
	private Model model;
	
	private ListValueModel<E> childrenModel;
	
	private ListChangeListener childrenListener;
	
	
	protected AbstractTreeItemContentProvider(
			Model model, DelegatingTreeContentAndLabelProvider treeContentProvider) {
		this.model = model;
		this.treeContentProvider = treeContentProvider;
		this.childrenListener = buildChildrenListener();
	}
	
	/**
	 * Construct a listener to refresh the tree (through the tree content provider)
	 * if the children change
	 */
	protected ListChangeListener buildChildrenListener() {
		return new ListChangeListener() {
			public void itemsAdded(ListChangeEvent event) {
				treeContentProvider().updateContent(model());
			}
			
			public void itemsMoved(ListChangeEvent event) {
				treeContentProvider().updateContent(model());
			}
			
			public void itemsRemoved(ListChangeEvent event) {
				treeContentProvider().updateContent(model());
				for (Iterator<?> stream = event.items(); stream.hasNext(); ) {
					treeContentProvider().dispose(stream.next());
				}
			}
			
			public void itemsReplaced(ListChangeEvent event) {
				treeContentProvider().updateContent(model());
				for (Iterator<?> stream = event.replacedItems(); stream.hasNext(); ) {
					treeContentProvider().dispose(stream.next());
				}
			}
			
			public void listChanged(ListChangeEvent event) {
				treeContentProvider().updateContent(model());
				// in the case of a list changed event, we don't have 
				// access to the removed objects, so we can't dispose them.
				// keep a watch on this to see if this becomes a problem.
			}
			
			public void listCleared(ListChangeEvent event) {
				treeContentProvider().updateContent(model());
				// in the case of a list cleared event, we don't have 
				// access to the removed objects, so we can't dispose them.
				// keep a watch on this to see if this becomes a problem.
			}
		};
	}
	
	/**
	 * Return the children model
	 * (lazy and just-in-time initialized)
	 */
	protected ListValueModel<E> childrenModel() {
		if (childrenModel == null) {
			childrenModel = buildChildrenModel();
			engageChildren();
		}
		return childrenModel;
	}
	
	/**
	 * Construct a children model
	 */
	@SuppressWarnings("unchecked")
	protected ListValueModel<E> buildChildrenModel() {
		return new NullListValueModel();
	}
	
	/**
	 * Utility method that can be used if the children model is better represented
	 * as a collection.
	 * This wraps the children collection model and uses it internally as a list
	 * model.
	 */
	protected ListValueModel<E> buildChildrenModel(CollectionValueModel<E> childrenModel) {
		return new CollectionListValueModelAdapter<E>(childrenModel);
	}
	
	/**
	 * Utility method that can be used if the children model is better represented
	 * as a single value property.
	 * This wraps the children (child) property model and uses it internally as a list
	 * model.
	 */
	protected ListValueModel<E> buildChildrenModel(PropertyValueModel<E> childrenModel) {
		return new PropertyListValueModelAdapter<E>(childrenModel);
	}
	
	/**
	 * Return the model object represented by this node
	 */
	public Model model() {
		return model;
	}
	
	/**
	 * Return the tree content provider that delegates to this node
	 */
	public DelegatingTreeContentAndLabelProvider treeContentProvider() {
		return treeContentProvider;
	}
	
	public Object getParent() {
		return null;
	}
	
	public Object[] getElements() {
		return getChildren();
	}
	
	public Object[] getChildren() {
		return CollectionTools.array(childrenModel().listIterator());
	}
	
	/**
	 * Override with potentially more efficient logic
	 */
	public boolean hasChildren() {
		return childrenModel().listIterator().hasNext();
	}
	
	/**
	 * Should only be overridden with a call to super.dispose()
	 */
	public void dispose() {
		for (Object child : getChildren()) {
			treeContentProvider().dispose(child);
		}
		disengageChildren();
	}
	
	/** 
	 * Should only be overridden with a call to super.engageChildren() before 
	 * subclass logic 
	 */
	protected void engageChildren() {
		childrenModel.addListChangeListener(ListValueModel.LIST_VALUES, childrenListener);
	}
	
	/** 
	 * Should only be overridden with a call to super.disengageChildren() after 
	 * subclass logic 
	 */
	protected void disengageChildren() {
		if (childrenModel != null) {
			childrenModel.removeListChangeListener(ListValueModel.LIST_VALUES, childrenListener);
		}
	}
}
