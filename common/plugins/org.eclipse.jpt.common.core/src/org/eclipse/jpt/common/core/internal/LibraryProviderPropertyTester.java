/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jst.common.project.facet.core.libprov.ILibraryProvider;

/**
 * Property tester for {@link ILibraryProvider}.
 * See <code>org.eclipse.jpt.common.core/plugin.xml:org.eclipse.core.expressions.propertyTesters</code>
 */
public class LibraryProviderPropertyTester
	extends PropertyTester
{
	public static final String ID = "id"; //$NON-NLS-1$
	public static final String EXTENDS_ID = "extendsId"; //$NON-NLS-1$

	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (receiver instanceof ILibraryProvider) {
			return this.test((ILibraryProvider) receiver, property, expectedValue);
		}
		return false;
	}

	private boolean test(ILibraryProvider libraryProvider, String property, Object expectedValue) {
		if (property.equals(ID)) {
			return libraryProvider.getId().equals(expectedValue);
		}
		if (property.equals(EXTENDS_ID)) {
			while (libraryProvider != null) {
				if (libraryProvider.getId().equals(expectedValue)) {
					return true;
				}
				libraryProvider = libraryProvider.getBaseProvider();
			}
			return false;
		}
		return false;	
	}
}
