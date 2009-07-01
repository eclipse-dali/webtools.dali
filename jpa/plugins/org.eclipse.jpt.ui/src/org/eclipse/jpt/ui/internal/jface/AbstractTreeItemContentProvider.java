/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.utility.internal.model.value.ListCollectionValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.NullCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.utility.model.listener.CollectionChangeListener;
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
	
	private CollectionValueModel<E> childrenModel;
	
	private CollectionChangeListener childrenListener;
	
	
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
	protected CollectionChangeListener buildChildrenListener() {
		return new CollectionChangeListener() {
			
			public void itemsAdded(CollectionAddEvent event) {
				getTreeContentProvider().updateContent(getModel());
			}
			
			public void itemsRemoved(CollectionRemoveEvent event) {
				getTreeContentProvider().updateContent(getModel());
				for (Object item : event.getItems()) {
					getTreeContentProvider().dispose(item);
				}
			}
			
			public void collectionChanged(CollectionChangeEvent event) {
				getTreeContentProvider().updateContent(getModel());
				// in the case of a list changed event, we don't have 
				// access to the removed objects, so we can't dispose them.
				// keep a watch on this to see if this becomes a problem.
			}
			
			public void collectionCleared(CollectionClearEvent event) {
				getTreeContentProvider().updateContent(getModel());
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
	protected synchronized Iterator<E> childrenModel() {
		if (this.childrenModel == null) {
			this.childrenModel = buildChildrenModel();
			engageChildren();
		}
		return this.childrenModel.iterator();
	}
	
	/**
	 * Construct a children model
	 */
	protected CollectionValueModel<E> buildChildrenModel() {
		return new NullCollectionValueModel<E>();
	}
	
	/**
	 * Utility method that can be used if the children model is better represented
	 * as a collection.
	 * This wraps the children collection model and uses it internally as a list
	 * model.
	 */
	protected CollectionValueModel<E> buildChildrenModel(ListValueModel<E> lvm) {
		return new ListCollectionValueModelAdapter<E>(lvm);
	}
	
	/**
	 * Utility method that can be used if the children model is better represented
	 * as a single value property.
	 * This wraps the children (child) property model and uses it internally as a list
	 * model.
	 */
	protected ListValueModel<E> buildChildrenModel(PropertyValueModel<E> lvm) {
		return new PropertyListValueModelAdapter<E>(lvm);
	}
	
	/**
	 * Return the model object represented by this node
	 */
	public Model getModel() {
		return this.model;
	}
	
	/**
	 * Return the tree content provider that delegates to this node
	 */
	public DelegatingTreeContentAndLabelProvider getTreeContentProvider() {
		return this.treeContentProvider;
	}
	
	public Object getParent() {
		return null;
	}
	
	public Object[] getElements() {
		return getChildren();
	}
	
	public Object[] getChildren() {
		return CollectionTools.array(this.childrenModel());
	}
	
	/**
	 * Override with potentially more efficient logic
	 */
	public boolean hasChildren() {
		return this.childrenModel().hasNext();
	}
	
	/**
	 * Should only be overridden with a call to super.dispose()
	 */
	public void dispose() {
		for (Object child : getChildren()) {
			getTreeContentProvider().dispose(child);
		}
		disposeChildrenModel();
	}
	
	/** 
	 * Should only be overridden with a call to super.engageChildren() before 
	 * subclass logic 
	 */
	protected void engageChildren() {
		this.childrenModel.addCollectionChangeListener(CollectionValueModel.VALUES, this.childrenListener);
	}
	
	protected synchronized void disposeChildrenModel() {
		if (this.childrenModel != null) {
			this.disengageChildrenModel();
			this.childrenModel = null;
		}
	}
	/** 
	 * Should only be overridden with a call to super.disengageChildren() after 
	 * subclass logic 
	 */
	protected void disengageChildrenModel() {
		this.childrenModel.removeCollectionChangeListener(CollectionValueModel.VALUES, this.childrenListener);
	}
}
