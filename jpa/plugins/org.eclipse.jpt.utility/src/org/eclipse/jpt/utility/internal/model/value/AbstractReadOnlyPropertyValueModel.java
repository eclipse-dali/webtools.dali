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

import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.NullModel;

/**
 * Implementation of ValueModel that can be subclassed and used for
 * returning a static value, but still allows listeners to be added.
 * Listeners will NEVER be notified of any changes, because there should be none.
 * Subclasses need only implement the #getValue() method to
 * return the static value required by the client code. This class is
 * really only useful for simplifying the building of anonymous inner
 * classes that implement the PropertyValueModel interface:
 * 	private PropertyValueModel buildAbstractModifierHolder() {
 * 		return new AbstractReadOnlyPropertyValueModel() {
 * 			public Object getValue() {
 * 				return Modifier.ABSTRACT;
 * 			}
 * 		};
 * 	}
 */
public abstract class AbstractReadOnlyPropertyValueModel
	extends NullModel
	implements PropertyValueModel
{
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	protected AbstractReadOnlyPropertyValueModel() {
		super();
	}


	// ********** PropertyValueModel implementation **********

	public void setValue(Object value) {
		throw new UnsupportedOperationException();
	}


	// ********** Object overrides **********

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.getValue());
	}

}
