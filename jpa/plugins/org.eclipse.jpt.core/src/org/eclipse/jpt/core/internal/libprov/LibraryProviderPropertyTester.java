/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.libprov;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jst.common.project.facet.core.libprov.ILibraryProvider;

public class LibraryProviderPropertyTester
		extends PropertyTester {
	
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (! (property.equals("id") || property.equals("extendsId")) //$NON-NLS-1$
				|| ! (receiver instanceof ILibraryProvider) 
				|| ! (expectedValue instanceof String)) { 
			return false;
		}
		
		ILibraryProvider libraryProvider = (ILibraryProvider) receiver;
		
		if (property.equals("id")) {
			return libraryProvider.getId().equals(expectedValue);
		}
		else if (property.equals("extendsId")) {
			while (libraryProvider != null) {
				if (libraryProvider.getId().equals(expectedValue)) {
					return true;
				}
				libraryProvider = libraryProvider.getBaseProvider();
			}
		}
		
		return false;	
	}
}
