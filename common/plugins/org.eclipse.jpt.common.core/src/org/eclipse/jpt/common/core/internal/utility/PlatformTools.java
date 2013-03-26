/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jpt.common.utility.internal.StringTools;

/**
 * {@link Platform} utility methods
 */
public class PlatformTools {

	// ********** adapter **********

	/**
	 * Add some Generic Goodness to the method signature.
	 * @see org.eclipse.core.runtime.IAdapterManager#getAdapter(Object, Class)
	 */
	@SuppressWarnings("unchecked")
	public static <A> A getAdapter(Object o, Class<A> adapterType) {
		return (A) Platform.getAdapterManager().getAdapter(o, adapterType);
	}


	// ********** workspace preferences **********

	public static String getNewTextFileLineDelimiter() {
		IScopeContext[] contexts = new IScopeContext[] { DefaultScope.INSTANCE };
		return Platform.getPreferencesService().getString(
				Platform.PI_RUNTIME, 
				Platform.PREF_LINE_SEPARATOR, 
				StringTools.CR, 
				contexts
			);
	}


	// ********** disabled constructor **********

	private PlatformTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
