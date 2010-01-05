/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.model.listener;

import java.lang.reflect.Method;

import org.eclipse.jpt.utility.internal.ReflectionTools;
import org.eclipse.jpt.utility.model.event.ChangeEvent;
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
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.utility.model.event.TreeAddEvent;
import org.eclipse.jpt.utility.model.event.TreeChangeEvent;
import org.eclipse.jpt.utility.model.event.TreeClearEvent;
import org.eclipse.jpt.utility.model.event.TreeEvent;
import org.eclipse.jpt.utility.model.event.TreeRemoveEvent;

/**
 * This factory builds listeners that reflectively forward change events.
 * If you are worried about having too many little classes that have to be
 * loaded and maintained by the class loader, you can use one of these.
 * Of course, this comes with the additional overhead of reflection....
 * Also note that the validity of the method name is not checked at compile
 * time, but at runtime; although we <em>do</em> check the method as soon as the
 * listener is instantiated.
 * <p>
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public abstract class ReflectiveChangeListener {

	/** the target object on which we will invoke the method */
	protected final Object target;


	protected static final Class<StateChangeEvent> STATE_CHANGE_EVENT_CLASS = StateChangeEvent.class;
	@SuppressWarnings("unchecked")
	protected static final Class<StateChangeEvent>[] STATE_CHANGE_EVENT_CLASS_ARRAY = new Class[] {STATE_CHANGE_EVENT_CLASS};


	protected static final Class<PropertyChangeEvent> PROPERTY_CHANGE_EVENT_CLASS = PropertyChangeEvent.class;
	@SuppressWarnings("unchecked")
	protected static final Class<PropertyChangeEvent>[] PROPERTY_CHANGE_EVENT_CLASS_ARRAY = new Class[] {PROPERTY_CHANGE_EVENT_CLASS};


	protected static final Class<CollectionEvent> COLLECTION_EVENT_CLASS = CollectionEvent.class;
	@SuppressWarnings("unchecked")
	protected static final Class<CollectionEvent>[] COLLECTION_EVENT_CLASS_ARRAY = new Class[] {COLLECTION_EVENT_CLASS};

	protected static final Class<CollectionAddEvent> COLLECTION_ADD_EVENT_CLASS = CollectionAddEvent.class;
	@SuppressWarnings("unchecked")
	protected static final Class<CollectionAddEvent>[] COLLECTION_ADD_EVENT_CLASS_ARRAY = new Class[] {COLLECTION_ADD_EVENT_CLASS};

	protected static final Class<CollectionRemoveEvent> COLLECTION_REMOVE_EVENT_CLASS = CollectionRemoveEvent.class;
	@SuppressWarnings("unchecked")
	protected static final Class<CollectionRemoveEvent>[] COLLECTION_REMOVE_EVENT_CLASS_ARRAY = new Class[] {COLLECTION_REMOVE_EVENT_CLASS};

	protected static final Class<CollectionClearEvent> COLLECTION_CLEAR_EVENT_CLASS = CollectionClearEvent.class;
	@SuppressWarnings("unchecked")
	protected static final Class<CollectionClearEvent>[] COLLECTION_CLEAR_EVENT_CLASS_ARRAY = new Class[] {COLLECTION_CLEAR_EVENT_CLASS};

	protected static final Class<CollectionChangeEvent> COLLECTION_CHANGE_EVENT_CLASS = CollectionChangeEvent.class;
	@SuppressWarnings("unchecked")
	protected static final Class<CollectionChangeEvent>[] COLLECTION_CHANGE_EVENT_CLASS_ARRAY = new Class[] {COLLECTION_CHANGE_EVENT_CLASS};


	protected static final Class<ListEvent> LIST_EVENT_CLASS = ListEvent.class;
	@SuppressWarnings("unchecked")
	protected static final Class<ListEvent>[] LIST_EVENT_CLASS_ARRAY = new Class[] {LIST_EVENT_CLASS};

	protected static final Class<ListAddEvent> LIST_ADD_EVENT_CLASS = ListAddEvent.class;
	@SuppressWarnings("unchecked")
	protected static final Class<ListAddEvent>[] LIST_ADD_EVENT_CLASS_ARRAY = new Class[] {LIST_ADD_EVENT_CLASS};

	protected static final Class<ListRemoveEvent> LIST_REMOVE_EVENT_CLASS = ListRemoveEvent.class;
	@SuppressWarnings("unchecked")
	protected static final Class<ListRemoveEvent>[] LIST_REMOVE_EVENT_CLASS_ARRAY = new Class[] {LIST_REMOVE_EVENT_CLASS};

	protected static final Class<ListReplaceEvent> LIST_REPLACE_EVENT_CLASS = ListReplaceEvent.class;
	@SuppressWarnings("unchecked")
	protected static final Class<ListReplaceEvent>[] LIST_REPLACE_EVENT_CLASS_ARRAY = new Class[] {LIST_REPLACE_EVENT_CLASS};

	protected static final Class<ListMoveEvent> LIST_MOVE_EVENT_CLASS = ListMoveEvent.class;
	@SuppressWarnings("unchecked")
	protected static final Class<ListMoveEvent>[] LIST_MOVE_EVENT_CLASS_ARRAY = new Class[] {LIST_MOVE_EVENT_CLASS};

	protected static final Class<ListClearEvent> LIST_CLEAR_EVENT_CLASS = ListClearEvent.class;
	@SuppressWarnings("unchecked")
	protected static final Class<ListClearEvent>[] LIST_CLEAR_EVENT_CLASS_ARRAY = new Class[] {LIST_CLEAR_EVENT_CLASS};

	protected static final Class<ListChangeEvent> LIST_CHANGE_EVENT_CLASS = ListChangeEvent.class;
	@SuppressWarnings("unchecked")
	protected static final Class<ListChangeEvent>[] LIST_CHANGE_EVENT_CLASS_ARRAY = new Class[] {LIST_CHANGE_EVENT_CLASS};


	protected static final Class<TreeEvent> TREE_EVENT_CLASS = TreeEvent.class;
	@SuppressWarnings("unchecked")
	protected static final Class<TreeEvent>[] TREE_EVENT_CLASS_ARRAY = new Class[] {TREE_EVENT_CLASS};

	protected static final Class<TreeAddEvent> TREE_ADD_EVENT_CLASS = TreeAddEvent.class;
	@SuppressWarnings("unchecked")
	protected static final Class<TreeAddEvent>[] TREE_ADD_EVENT_CLASS_ARRAY = new Class[] {TREE_ADD_EVENT_CLASS};

	protected static final Class<TreeRemoveEvent> TREE_REMOVE_EVENT_CLASS = TreeRemoveEvent.class;
	@SuppressWarnings("unchecked")
	protected static final Class<TreeRemoveEvent>[] TREE_REMOVE_EVENT_CLASS_ARRAY = new Class[] {TREE_REMOVE_EVENT_CLASS};

	protected static final Class<TreeClearEvent> TREE_CLEAR_EVENT_CLASS = TreeClearEvent.class;
	@SuppressWarnings("unchecked")
	protected static final Class<TreeClearEvent>[] TREE_CLEAR_EVENT_CLASS_ARRAY = new Class[] {TREE_CLEAR_EVENT_CLASS};

	protected static final Class<TreeChangeEvent> TREE_CHANGE_EVENT_CLASS = TreeChangeEvent.class;
	@SuppressWarnings("unchecked")
	protected static final Class<TreeChangeEvent>[] TREE_CHANGE_EVENT_CLASS_ARRAY = new Class[] {TREE_CHANGE_EVENT_CLASS};

	protected static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];


	// ********** helper methods **********

	/**
	 * Find and return a method implemented by the target that can be invoked
	 * reflectively when a change event occurs.
	 */
	private static Method findChangeListenerMethod(Object target, String methodName, Class<? extends ChangeEvent>[] eventClassArray) {
		try {
			return ReflectionTools.getMethod(target, methodName, eventClassArray);
		} catch (RuntimeException ex1) {
			return ReflectionTools.getMethod(target, methodName);
		}
	}

	/**
	 * Check whether the specified method is suitable for being invoked when a
	 * change event has occurred. Throw an exception if it is not suitable.
	 */
	private static void checkChangeListenerMethod(Method method, Class<? extends ChangeEvent> eventClass) {
		Class<?>[] parmTypes = method.getParameterTypes();
		int parmTypesLength = parmTypes.length;
		if (parmTypesLength == 0) {
			return;
		}
		if ((parmTypesLength == 1) && parmTypes[0].isAssignableFrom(eventClass)) {
			return;
		}
		throw new IllegalArgumentException(method.toString());
	}


	// ********** factory methods: StateChangeListener **********

	/**
	 * Construct a state change listener that will invoke the specified method
	 * on the specified target.
	 */
	public static StateChangeListener buildStateChangeListener(Object target, Method method) {
		checkChangeListenerMethod(method, STATE_CHANGE_EVENT_CLASS);
		return new SingleMethodReflectiveChangeListener(target, method);
	}

	/**
	 * Construct a state change listener that will invoke the specified method
	 * on the specified target. If a single-argument method with the specified
	 * name and appropriate argument is found, it will be invoked; otherwise,
	 * a zero-argument method with the specified name will be invoked.
	 */
	public static StateChangeListener buildStateChangeListener(Object target, String methodName) {
		return buildStateChangeListener(target, findChangeListenerMethod(target, methodName, STATE_CHANGE_EVENT_CLASS_ARRAY));
	}


	// ********** factory methods: PropertyChangeListener **********

	/**
	 * Construct a property change listener that will invoke the specified method
	 * on the specified target.
	 */
	public static PropertyChangeListener buildPropertyChangeListener(Object target, Method method) {
		checkChangeListenerMethod(method, PROPERTY_CHANGE_EVENT_CLASS);
		return new SingleMethodReflectiveChangeListener(target, method);
	}

	/**
	 * Construct a property change listener that will invoke the specified method
	 * on the specified target. If a single-argument method with the specified
	 * name and appropriate argument is found, it will be invoked; otherwise,
	 * a zero-argument method with the specified name will be invoked.
	 */
	public static PropertyChangeListener buildPropertyChangeListener(Object target, String methodName) {
		return buildPropertyChangeListener(target, findChangeListenerMethod(target, methodName, PROPERTY_CHANGE_EVENT_CLASS_ARRAY));
	}


	// ********** factory methods: CollectionChangeListener **********

	/**
	 * Construct a collection change listener that will invoke the specified methods
	 * on the specified target.
	 */
	public static CollectionChangeListener buildCollectionChangeListener(Object target, Method addMethod, Method removeMethod, Method clearMethod, Method changeMethod) {
		checkChangeListenerMethod(addMethod, COLLECTION_ADD_EVENT_CLASS);
		checkChangeListenerMethod(removeMethod, COLLECTION_REMOVE_EVENT_CLASS);
		checkChangeListenerMethod(clearMethod, COLLECTION_CLEAR_EVENT_CLASS);
		checkChangeListenerMethod(changeMethod, COLLECTION_CHANGE_EVENT_CLASS);
		return new MultiMethodReflectiveChangeListener(target, addMethod, removeMethod, clearMethod, changeMethod);
	}

	/**
	 * Construct a collection change listener that will invoke the specified method
	 * on the specified target for any change event.
	 */
	public static CollectionChangeListener buildCollectionChangeListener(Object target, Method method) {
		return buildCollectionChangeListener(target, method, method, method, method);
	}

	/**
	 * Construct a collection change listener that will invoke the specified methods
	 * on the specified target for change events. If a single-argument method
	 * with the specified name and appropriate argument is found, it will be invoked;
	 * otherwise, a zero-argument method with the specified name will be invoked.
	 */
	public static CollectionChangeListener buildCollectionChangeListener(Object target, String addMethodName, String removeMethodName, String clearMethodName, String changeMethodName) {
		return buildCollectionChangeListener(
				target,
				findChangeListenerMethod(target, addMethodName, COLLECTION_ADD_EVENT_CLASS_ARRAY),
				findChangeListenerMethod(target, removeMethodName, COLLECTION_REMOVE_EVENT_CLASS_ARRAY),
				findChangeListenerMethod(target, clearMethodName, COLLECTION_CLEAR_EVENT_CLASS_ARRAY),
				findChangeListenerMethod(target, changeMethodName, COLLECTION_CHANGE_EVENT_CLASS_ARRAY)
		);
	}

	/**
	 * Construct a collection change listener that will invoke the specified method
	 * on the specified target for any change event. If a single-argument method
	 * with the specified name and appropriate argument is found, it will be invoked;
	 * otherwise, a zero-argument method with the specified name will be invoked.
	 */
	public static CollectionChangeListener buildCollectionChangeListener(Object target, String methodName) {
		return buildCollectionChangeListener(target, findChangeListenerMethod(target, methodName, COLLECTION_EVENT_CLASS_ARRAY));
	}


	// ********** factory methods: ListChangeListener **********

	/**
	 * Construct a list change listener that will invoke the specified methods
	 * on the specified target.
	 */
	public static ListChangeListener buildListChangeListener(Object target, Method addMethod, Method removeMethod, Method replaceMethod, Method moveMethod, Method clearMethod, Method changeMethod) {
		checkChangeListenerMethod(addMethod, LIST_ADD_EVENT_CLASS);
		checkChangeListenerMethod(removeMethod, LIST_REMOVE_EVENT_CLASS);
		checkChangeListenerMethod(replaceMethod, LIST_REPLACE_EVENT_CLASS);
		checkChangeListenerMethod(moveMethod, LIST_MOVE_EVENT_CLASS);
		checkChangeListenerMethod(clearMethod, LIST_CLEAR_EVENT_CLASS);
		checkChangeListenerMethod(changeMethod, LIST_CHANGE_EVENT_CLASS);
		return new MultiMethodReflectiveChangeListener(target, addMethod, removeMethod, replaceMethod, moveMethod, clearMethod, changeMethod);
	}

	/**
	 * Construct a list change listener that will invoke the specified method
	 * on the specified target for any change event.
	 */
	public static ListChangeListener buildListChangeListener(Object target, Method method) {
		return buildListChangeListener(target, method, method, method, method, method, method);
	}

	/**
	 * Construct a list change listener that will invoke the specified methods
	 * on the specified target for change events. If a single-argument method
	 * with the specified name and appropriate argument is found, it will be invoked;
	 * otherwise, a zero-argument method with the specified name will be invoked.
	 */
	public static ListChangeListener buildListChangeListener(Object target, String addMethodName, String removeMethodName, String replaceMethodName, String moveMethodName, String clearMethodName, String changeMethodName) {
		return buildListChangeListener(
				target,
				findChangeListenerMethod(target, addMethodName, LIST_ADD_EVENT_CLASS_ARRAY),
				findChangeListenerMethod(target, removeMethodName, LIST_REMOVE_EVENT_CLASS_ARRAY),
				findChangeListenerMethod(target, replaceMethodName, LIST_REPLACE_EVENT_CLASS_ARRAY),
				findChangeListenerMethod(target, moveMethodName, LIST_MOVE_EVENT_CLASS_ARRAY),
				findChangeListenerMethod(target, clearMethodName, LIST_CLEAR_EVENT_CLASS_ARRAY),
				findChangeListenerMethod(target, changeMethodName, LIST_CHANGE_EVENT_CLASS_ARRAY)
		);
	}

	/**
	 * Construct a list change listener that will invoke the specified method
	 * on the specified target for any change event. If a single-argument method
	 * with the specified name and appropriate argument is found, it will be invoked;
	 * otherwise, a zero-argument method with the specified name will be invoked.
	 */
	public static ListChangeListener buildListChangeListener(Object target, String methodName) {
		return buildListChangeListener(target, findChangeListenerMethod(target, methodName, LIST_EVENT_CLASS_ARRAY));
	}


	// ********** factory methods: TreeChangeListener **********

	/**
	 * Construct a tree change listener that will invoke the specified methods
	 * on the specified target.
	 */
	public static TreeChangeListener buildTreeChangeListener(Object target, Method addMethod, Method removeMethod, Method clearMethod, Method changeMethod) {
		checkChangeListenerMethod(addMethod, TREE_ADD_EVENT_CLASS);
		checkChangeListenerMethod(removeMethod, TREE_REMOVE_EVENT_CLASS);
		checkChangeListenerMethod(clearMethod, TREE_CLEAR_EVENT_CLASS);
		checkChangeListenerMethod(changeMethod, TREE_CHANGE_EVENT_CLASS);
		return new MultiMethodReflectiveChangeListener(target, addMethod, removeMethod, clearMethod, changeMethod);
	}

	/**
	 * Construct a tree change listener that will invoke the specified method
	 * on the specified target for any change event.
	 */
	public static TreeChangeListener buildTreeChangeListener(Object target, Method method) {
		return buildTreeChangeListener(target, method, method, method, method);
	}

	/**
	 * Construct a tree change listener that will invoke the specified methods
	 * on the specified target for change events. If a single-argument method
	 * with the specified name and appropriate argument is found, it will be invoked;
	 * otherwise, a zero-argument method with the specified name will be invoked.
	 */
	public static TreeChangeListener buildTreeChangeListener(Object target, String addMethodName, String removeMethodName, String clearMethodName, String changeMethodName) {
		return buildTreeChangeListener(
				target,
				findChangeListenerMethod(target, addMethodName, TREE_ADD_EVENT_CLASS_ARRAY),
				findChangeListenerMethod(target, removeMethodName, TREE_REMOVE_EVENT_CLASS_ARRAY),
				findChangeListenerMethod(target, clearMethodName, TREE_CLEAR_EVENT_CLASS_ARRAY),
				findChangeListenerMethod(target, changeMethodName, TREE_CHANGE_EVENT_CLASS_ARRAY)
		);
	}

	/**
	 * Construct a tree change listener that will invoke the specified method
	 * on the specified target for any change event. If a single-argument method
	 * with the specified name and appropriate argument is found, it will be invoked;
	 * otherwise, a zero-argument method with the specified name will be invoked.
	 */
	public static TreeChangeListener buildTreeChangeListener(Object target, String methodName) {
		return buildTreeChangeListener(target, findChangeListenerMethod(target, methodName, TREE_EVENT_CLASS_ARRAY));
	}


	// ********** constructor **********

	/**
	 * Construct a listener that will invoke the specified method
	 * on the specified target.
	 */
	protected ReflectiveChangeListener(Object target) {
		super();
		this.target = target;
	}

}
