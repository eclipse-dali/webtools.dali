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

import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;


/**
 * A read-only list value model for when you don't
 * need to support a list.
 */
public final class NullListValueModel
	extends AbstractReadOnlyListValueModel
{

	private static final long serialVersionUID = 1L;

	// singleton
	private static final NullListValueModel INSTANCE = new NullListValueModel();

	/**
	 * Return the singleton.
	 */
	public static synchronized ListValueModel instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private NullListValueModel() {
		super();
	}


	// ********** ListValueModel implementation **********

    @Override
	public int size() {
		return 0;
	}

	public Object value() {
		return EmptyListIterator.instance();
	}


	// ********** Object overrides **********

    @Override
	public String toString() {
		return "NullListValueModel";
	}

	/**
	 * Serializable singleton support
	 */
	private Object readResolve() {
		return instance();
	}

}
