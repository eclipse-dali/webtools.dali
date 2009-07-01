/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.utility.model.event.CollectionEvent;
import org.eclipse.jpt.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.utility.model.event.ListAddEvent;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.event.ListClearEvent;
import org.eclipse.jpt.utility.model.event.ListEvent;
import org.eclipse.jpt.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.utility.model.event.TreeAddEvent;
import org.eclipse.jpt.utility.model.event.TreeChangeEvent;
import org.eclipse.jpt.utility.model.event.TreeClearEvent;
import org.eclipse.jpt.utility.model.event.TreeEvent;
import org.eclipse.jpt.utility.model.event.TreeRemoveEvent;

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

	private void invoke(Method method, CollectionEvent event) {
		if (method.getParameterTypes().length == 0) {
			ClassTools.executeMethod(method, this.target, EMPTY_OBJECT_ARRAY);
		} else {
			ClassTools.executeMethod(method, this.target, new CollectionEvent[] {event});
		}
	}

	public void itemsAdded(CollectionAddEvent event) {
		this.invoke(this.addMethod, event);
	}

	public void itemsRemoved(CollectionRemoveEvent event) {
		this.invoke(this.removeMethod, event);
	}

	public void collectionCleared(CollectionClearEvent event) {
		this.invoke(this.clearMethod, event);
	}

	public void collectionChanged(CollectionChangeEvent event) {
		this.invoke(this.changeMethod, event);
	}


	// ********** ListChangeListener implementation **********

	private void invoke(Method method, ListEvent event) {
		if (method.getParameterTypes().length == 0) {
			ClassTools.executeMethod(method, this.target, EMPTY_OBJECT_ARRAY);
		} else {
			ClassTools.executeMethod(method, this.target, new ListEvent[] {event});
		}
	}

	public void itemsAdded(ListAddEvent event) {
		this.invoke(this.addMethod, event);
	}

	public void itemsRemoved(ListRemoveEvent event) {
		this.invoke(this.removeMethod, event);
	}

	public void itemsReplaced(ListReplaceEvent event) {
		this.invoke(this.replaceMethod, event);
	}

	public void itemsMoved(ListMoveEvent event) {
		this.invoke(this.moveMethod, event);
	}

	public void listCleared(ListClearEvent event) {
		this.invoke(this.clearMethod, event);
	}

	public void listChanged(ListChangeEvent event) {
		this.invoke(this.changeMethod, event);
	}


	// ********** TreeChangeListener implementation **********

	private void invoke(Method method, TreeEvent event) {
		if (method.getParameterTypes().length == 0) {
			ClassTools.executeMethod(method, this.target, EMPTY_OBJECT_ARRAY);
		} else {
			ClassTools.executeMethod(method, this.target, new TreeEvent[] {event});
		}
	}

	public void nodeAdded(TreeAddEvent event) {
		this.invoke(this.addMethod, event);
	}

	public void nodeRemoved(TreeRemoveEvent event) {
		this.invoke(this.removeMethod, event);
	}

	public void treeCleared(TreeClearEvent event) {
		this.invoke(this.clearMethod, event);
	}

	public void treeChanged(TreeChangeEvent event) {
		this.invoke(this.changeMethod, event);
	}

}
