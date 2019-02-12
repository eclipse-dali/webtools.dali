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
 * A property value model for when you
 * don't need to support a value.
 * <p>
 * We don't use a singleton because we hold on to listeners.
 */
public final class NullPropertyValueModel<T>
	extends AbstractModel
	implements PropertyValueModel<T>, Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public NullPropertyValueModel() {
		super();
	}
	
	public T getValue() {
		return null;
	}

    @Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
