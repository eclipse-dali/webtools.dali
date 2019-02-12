/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.model.listener;

import java.lang.reflect.Method;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.event.StateChangeEvent;

/**
 * This class is used by {@link ReflectiveChangeListener} when the requested listener
 * need only implement a single method (i.e. {@link StateChangeListener} or
 * {@link PropertyChangeListener}).
 */
class SingleMethodReflectiveChangeListener
	extends ReflectiveChangeListener
	implements StateChangeListener, PropertyChangeListener
{

	/** the method we will invoke on the target object */
	private final Method method;
	/** cache the number of arguments */
	private final boolean methodIsZeroArgument;

	SingleMethodReflectiveChangeListener(Object target, Method method) {
		super(target);
		this.method = method;
		this.methodIsZeroArgument = method.getParameterTypes().length == 0;
	}


	// ********** StateChangeListener implementation **********

	public void stateChanged(StateChangeEvent event) {
		if (this.methodIsZeroArgument) {
			ObjectTools.execute(this.target, this.method, ObjectTools.EMPTY_OBJECT_ARRAY);
		} else {
			ObjectTools.execute(this.target, this.method, new StateChangeEvent[] {event});
		}
	}


	// ********** PropertyChangeListener implementation **********

	public void propertyChanged(PropertyChangeEvent event) {
		if (this.methodIsZeroArgument) {
			ObjectTools.execute(this.target, this.method, ObjectTools.EMPTY_OBJECT_ARRAY);
		} else {
			ObjectTools.execute(this.target, this.method, new PropertyChangeEvent[] {event});
		}
	}
}
