/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;

/**
 * Implementation of {@link PropertyValueModel} that can be used for
 * returning a static value, but still allows listeners to be added.
 * Listeners will <em>never</em> be notified of any changes, because there should be none.
 */
public class StaticPropertyValueModel<T>
	extends AbstractModel
	implements PropertyValueModel<T>, Serializable
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
