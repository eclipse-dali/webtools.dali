/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.factory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.eclipse.jpt.common.utility.factory.Factory;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * A factory that uses Java reflection to return the value of a static field.
 * Checked exceptions are converted to {@link RuntimeException}s.
 * 
 * @param <T> the type of objects returned by the factory
 * 
 * @see StaticMethodFactory
 * @see java.lang.reflect.Field#get(Object)
 */
public class StaticFieldFactory<T>
	implements Factory<T>
{
	private final Field field;


	public StaticFieldFactory(Field field) {
		super();
		if (field == null) {
			throw new NullPointerException();
		}
		if ( ! Modifier.isStatic(field.getModifiers())) {
			throw new IllegalArgumentException("field must be static: " + field); //$NON-NLS-1$
		}
		if ( ! field.isAccessible()) {
			throw new IllegalArgumentException("field must be accessible: " + field); //$NON-NLS-1$
		}
		this.field = field;
	}

	@SuppressWarnings("unchecked")
	public T create() {
		try {
			return (T) this.field.get(null);
		} catch (IllegalArgumentException ex) {
			throw new RuntimeException(ex);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.field);
	}
}
