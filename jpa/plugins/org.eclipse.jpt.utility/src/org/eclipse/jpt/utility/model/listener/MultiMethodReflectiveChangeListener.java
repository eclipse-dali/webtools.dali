/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.model.listener;

import java.lang.reflect.Method;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.event.TreeChangeEvent;

/**
 * This class is used by ReflectiveChangeListener when the requested listener
 * needs to implement multiple methods (i.e. CollectionChangeListener,
 * ListChangeListener, or TreeChangeListener).
 */
class MultiMethodReflectiveChangeListener
	extends ReflectiveChangeListener 
	implements CollectionChangeListener, ListChangeListener, TreeChangeListener
{
	/** the methods we will invoke on the target object */
	private final Method addMethod;
	private final Method removeMethod;
	private final Method replaceMethod;	// this can be null
	private final Method moveMethod;	// this can be null
	private final Method clearMethod;
	private final Method changeMethod;


	/**
	 * The "replace" and "move" methods are optional.
	 */
	MultiMethodReflectiveChangeListener(Object target, Method addMethod, Method removeMethod, Method replaceMethod, Method moveMethod, Method clearMethod, Method changeMethod) {
		super(target);
		this.addMethod = addMethod;
		this.removeMethod = removeMethod;
		this.replaceMethod = replaceMethod;
		this.moveMethod = moveMethod;
		this.clearMethod = clearMethod;
		this.changeMethod = changeMethod;
	}

	/**
	 * No "replace" or "move" methods.
	 */
	MultiMethodReflectiveChangeListener(Object target, Method addMethod, Method removeMethod, Method clearMethod, Method changeMethod) {
		this(target, addMethod, removeMethod, null, null, clearMethod, changeMethod);
	}


	// ********** CollectionChangeListener implementation **********

	private void invoke(Method method, CollectionChangeEvent event) {
		if (method.getParameterTypes().length == 0) {
			ClassTools.executeMethod(method, this.target, EMPTY_COLLECTION_CHANGE_EVENT_ARRAY);
		} else {
			ClassTools.executeMethod(method, this.target, new CollectionChangeEvent[] {event});
		}
	}

	public void itemsAdded(CollectionChangeEvent event) {
		this.invoke(this.addMethod, event);
	}

	public void itemsRemoved(CollectionChangeEvent event) {
		this.invoke(this.removeMethod, event);
	}

	public void collectionCleared(CollectionChangeEvent event) {
		this.invoke(this.clearMethod, event);
	}

	public void collectionChanged(CollectionChangeEvent event) {
		this.invoke(this.changeMethod, event);
	}


	// ********** ListChangeListener implementation **********

	private void invoke(Method method, ListChangeEvent event) {
		if (method.getParameterTypes().length == 0) {
			ClassTools.executeMethod(method, this.target, EMPTY_LIST_CHANGE_EVENT_ARRAY);
		} else {
			ClassTools.executeMethod(method, this.target, new ListChangeEvent[] {event});
		}
	}

	public void itemsAdded(ListChangeEvent event) {
		this.invoke(this.addMethod, event);
	}

	public void itemsRemoved(ListChangeEvent event) {
		this.invoke(this.removeMethod, event);
	}

	public void itemsReplaced(ListChangeEvent event) {
		this.invoke(this.replaceMethod, event);
	}

	public void itemsMoved(ListChangeEvent event) {
		this.invoke(this.moveMethod, event);
	}

	public void listCleared(ListChangeEvent event) {
		this.invoke(this.clearMethod, event);
	}

	public void listChanged(ListChangeEvent event) {
		this.invoke(this.changeMethod, event);
	}


	// ********** TreeChangeListener implementation **********

	private void invoke(Method method, TreeChangeEvent event) {
		if (method.getParameterTypes().length == 0) {
			ClassTools.executeMethod(method, this.target, EMPTY_TREE_CHANGE_EVENT_ARRAY);
		} else {
			ClassTools.executeMethod(method, this.target, new TreeChangeEvent[] {event});
		}
	}

	public void nodeAdded(TreeChangeEvent event) {
		this.invoke(this.addMethod, event);
	}

	public void nodeRemoved(TreeChangeEvent event) {
		this.invoke(this.removeMethod, event);
	}

	public void treeCleared(TreeChangeEvent event) {
		this.invoke(this.clearMethod, event);
	}

	public void treeChanged(TreeChangeEvent event) {
		this.invoke(this.changeMethod, event);
	}

}
