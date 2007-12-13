/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.model.NullModel;

/**
 * A read-only property value model for when you
 * don't need to support a value.
 */
public final class NullPropertyValueModel<T>
	extends NullModel
	implements PropertyValueModel<T>
{

	private static final long serialVersionUID = 1L;

	// singleton
	@SuppressWarnings("unchecked")
	private static final NullPropertyValueModel INSTANCE = new NullPropertyValueModel();

	/**
	 * Return the singleton.
	 */
	@SuppressWarnings("unchecked")
	public static synchronized <T> PropertyValueModel<T> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private NullPropertyValueModel() {
		super();
	}
	

	// ********** PropertyValueModel implementation **********

	public T value() {
		return null;
	}


	// ********** Object overrides **********

    @Override
	public String toString() {
		return ClassTools.shortClassNameForObject(this);
	}

	/**
	 * Serializable singleton support
	 */
	private Object readResolve() {
		return instance();
	}

}
