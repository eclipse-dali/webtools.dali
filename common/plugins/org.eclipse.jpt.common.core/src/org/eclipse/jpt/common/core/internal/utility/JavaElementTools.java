/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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

/**
 * {@link IJavaElement} convenience methods.
 */
public class JavaElementTools {

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

	private JavaElementTools() {
		throw new UnsupportedOperationException();
	}
}
