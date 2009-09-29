/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;

/**
 * Implementation of {@link PropertyValueModel} that can be used for
 * returning a static value, but still allows listeners to be added.
 * Listeners will <em>never</em> be notified of any changes, because there should be none.
 */
public class StaticPropertyValueModel<T>
	extends AbstractModel
	implements PropertyValueModel<T>
{
	/** The value. */
	protected final T value;

	private static final long serialVersionUID = 1L;


	/**
	 * Construct a static property value model for the specified value.
	 */
	public StaticPropertyValueModel(T value) {
		super();
		this.value = value;
	}


	// ********** PropertyValueModel implementation **********

	public T getValue() {
		return this.value;
	}


	// ********** Object overrides **********

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}

}
