/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility;

import java.io.FileNotFoundException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IParent;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * {@link IJavaElement} convenience methods.
 */
public class JavaElementTools {

	// ********** children **********

	/**
	 * Wrap checked exception and check for out of sync workspace.
	 */
	public static IJavaElement[] getChildren(IParent parent) {
		try {
			return parent.getChildren();
		} catch (JavaModelException ex) {
			// ignore FNFE - which can happen when the workspace is out of sync with O/S file system
			if ( ! (ex.getCause() instanceof FileNotFoundException)) {
				JptCommonCorePlugin.instance().logError(ex);
			}
			return EMPTY_JAVA_ELEMENT_ARRAY;
		}
	}
	private static final IJavaElement[] EMPTY_JAVA_ELEMENT_ARRAY = new IJavaElement[0];


	// ********** exists **********

	public static final Predicate<IJavaElement> EXISTS = new Exists();

	public static class Exists
		extends PredicateAdapter<IJavaElement>
	{
		@Override
		public boolean evaluate(IJavaElement javaElement) {
			return javaElement.exists();
		}
	}


	// ********** disabled constructor **********

	private JavaElementTools() {
		throw new UnsupportedOperationException();
	}
}
