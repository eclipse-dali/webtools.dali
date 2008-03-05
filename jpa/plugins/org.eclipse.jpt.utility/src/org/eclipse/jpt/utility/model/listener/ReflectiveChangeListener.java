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
import org.eclipse.jpt.utility.model.event.ChangeEvent;
import org.eclipse.jpt.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.utility.model.event.TreeChangeEvent;

/**
 * This factory builds listeners that reflectively forward ChangeEvents.
 * If you are worried about having too many little classes that have to be
 * loaded and maintained by the class loader, you can use one of these.
 * Of course, this comes with the additional overhead of reflection....
 * Also note that the validity of the method name is not checked at compile
 * time, but at runtime; although we *do* check the method as soon as the
 * listener is instantiated.
 * 
 * Provisional API: This interface is part of an interim API that is still
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
	protected static final StateChangeEvent[] EMPTY_STATE_CHANGE_EVENT_ARRAY = new StateChangeEvent[0];

	protected static final Class<PropertyChangeEvent> PROPERTY_CHANGE_EVENT_CLASS = PropertyChangeEvent.class;
	@SuppressWarnings("unchecked")
	protected static final Class<PropertyChangeEvent>[] PROPERTY_CHANGE_EVENT_CLASS_ARRAY = new Class[] {PROPERTY_CHANGE_EVENT_CLASS};
	protected static final PropertyChangeEvent[] EMPTY_PROPERTY_CHANGE_EVENT_ARRAY = new PropertyChangeEvent[0];

	protected static final Class<CollectionChangeEvent> COLLECTION_CHANGE_EVENT_CLASS = CollectionChangeEvent.class;
	@SuppressWarnings("unchecked")
	protected static final Class<CollectionChangeEvent>[] COLLECTION_CHANGE_EVENT_CLASS_ARRAY = new Class[] {COLLECTION_CHANGE_EVENT_CLASS};
	protected static final CollectionChangeEvent[] EMPTY_COLLECTION_CHANGE_EVENT_ARRAY = new CollectionChangeEvent[0];

	protected static final Class<ListChangeEvent> LIST_CHANGE_EVENT_CLASS = ListChangeEvent.class;
	@SuppressWarnings("unchecked")
	protected static final Class<ListChangeEvent>[] LIST_CHANGE_EVENT_CLASS_ARRAY = new Class[] {LIST_CHANGE_EVENT_CLASS};
	protected static final ListChangeEvent[] EMPTY_LIST_CHANGE_EVENT_ARRAY = new ListChangeEvent[0];

	protected static final Class<TreeChangeEvent> TREE_CHANGE_EVENT_CLASS = TreeChangeEvent.class;
	@SuppressWarnings("unchecked")
	protected static final Class<TreeChangeEvent>[] TREE_CHANGE_EVENT_CLASS_ARRAY = new Class[] {TREE_CHANGE_EVENT_CLASS};
	protected static final TreeChangeEvent[] EMPTY_TREE_CHANGE_EVENT_ARRAY = new TreeChangeEvent[0];



	// ********** helper methods **********

	/**
	 * Find and return a method implemented by the target that can be invoked
	 * reflectively when a change event occurs.
	 */
	private static Method findChangeListenerMethod(Object target, String methodName, Class<? extends ChangeEvent>[] eventClassArray) {
		Method method;
		try {
			method = ClassTools.method(target, methodName, eventClassArray);
		} catch (NoSuchMethodException ex1) {
			try {
				method = ClassTools.method(target, methodName);
			} catch (NoSuchMethodException ex2) {
				throw new RuntimeException(ex2);  // "checked" exceptions bite
			}
		}
		return method;
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
		checkChangeListenerMethod(addMethod, COLLECTION_CHANGE_EVENT_CLASS);
		checkChangeListenerMethod(removeMethod, COLLECTION_CHANGE_EVENT_CLASS);
		checkChangeListenerMethod(clearMethod, COLLECTION_CHANGE_EVENT_CLASS);
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
				findChangeListenerMethod(target, addMethodName, COLLECTION_CHANGE_EVENT_CLASS_ARRAY),
				findChangeListenerMethod(target, removeMethodName, COLLECTION_CHANGE_EVENT_CLASS_ARRAY),
				findChangeListenerMethod(target, clearMethodName, COLLECTION_CHANGE_EVENT_CLASS_ARRAY),
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
		return buildCollectionChangeListener(target, findChangeListenerMethod(target, methodName, COLLECTION_CHANGE_EVENT_CLASS_ARRAY));
	}


	// ********** factory methods: ListChangeListener **********

	/**
	 * Construct a list change listener that will invoke the specified methods
	 * on the specified target.
	 */
	public static ListChangeListener buildListChangeListener(Object target, Method addMethod, Method removeMethod, Method replaceMethod, Method moveMethod, Method clearMethod, Method changeMethod) {
		checkChangeListenerMethod(addMethod, LIST_CHANGE_EVENT_CLASS);
		checkChangeListenerMethod(removeMethod, LIST_CHANGE_EVENT_CLASS);
		checkChangeListenerMethod(replaceMethod, LIST_CHANGE_EVENT_CLASS);
		checkChangeListenerMethod(moveMethod, LIST_CHANGE_EVENT_CLASS);
		checkChangeListenerMethod(clearMethod, LIST_CHANGE_EVENT_CLASS);
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
				findChangeListenerMethod(target, addMethodName, LIST_CHANGE_EVENT_CLASS_ARRAY),
				findChangeListenerMethod(target, removeMethodName, LIST_CHANGE_EVENT_CLASS_ARRAY),
				findChangeListenerMethod(target, replaceMethodName, LIST_CHANGE_EVENT_CLASS_ARRAY),
				findChangeListenerMethod(target, moveMethodName, LIST_CHANGE_EVENT_CLASS_ARRAY),
				findChangeListenerMethod(target, clearMethodName, LIST_CHANGE_EVENT_CLASS_ARRAY),
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
		return buildListChangeListener(target, findChangeListenerMethod(target, methodName, LIST_CHANGE_EVENT_CLASS_ARRAY));
	}


	// ********** factory methods: TreeChangeListener **********

	/**
	 * Construct a tree change listener that will invoke the specified methods
	 * on the specified target.
	 */
	public static TreeChangeListener buildTreeChangeListener(Object target, Method addMethod, Method removeMethod, Method clearMethod, Method changeMethod) {
		checkChangeListenerMethod(addMethod, TREE_CHANGE_EVENT_CLASS);
		checkChangeListenerMethod(removeMethod, TREE_CHANGE_EVENT_CLASS);
		checkChangeListenerMethod(clearMethod, TREE_CHANGE_EVENT_CLASS);
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
				findChangeListenerMethod(target, addMethodName, TREE_CHANGE_EVENT_CLASS_ARRAY),
				findChangeListenerMethod(target, removeMethodName, TREE_CHANGE_EVENT_CLASS_ARRAY),
				findChangeListenerMethod(target, clearMethodName, TREE_CHANGE_EVENT_CLASS_ARRAY),
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
		return buildTreeChangeListener(target, findChangeListenerMethod(target, methodName, TREE_CHANGE_EVENT_CLASS_ARRAY));
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
