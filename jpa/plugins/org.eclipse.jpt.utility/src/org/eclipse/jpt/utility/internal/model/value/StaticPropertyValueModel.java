/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.AbstractModel;

/**
 * Implementation of PropertyValueModel that can be used for
 * returning a static value, but still allows listeners to be added.
 * Listeners will NEVER be notified of any changes, because there should be none.
 */
public class StaticPropertyValueModel<T>
	extends AbstractModel
	implements PropertyValueModel<T>
{
	/** The value. */
	protected final T value;

	private static final long serialVersionUID = 1L;


	/**
	 * Construct a read-only PropertyValueModel for the specified value.
	 */
	public StaticPropertyValueModel(T value) {
		super();
		this.value = value;
	}


	// ********** ValueModel implementation **********

	public T value() {
		return this.value;
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.value);
	}

}
