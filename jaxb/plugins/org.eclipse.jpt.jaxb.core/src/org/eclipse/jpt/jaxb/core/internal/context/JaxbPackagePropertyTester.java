/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;


public class JaxbPackagePropertyTester
		extends PropertyTester {
	
	public static final String HAS_PACKAGE_INFO = "hasPackageInfo"; //$NON-NLS-1$
	
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (receiver instanceof JaxbPackage) {
			return test((JaxbPackage) receiver, property, expectedValue);
		}
		return false;
	}
	
	private boolean test(JaxbPackage jaxbPackage, String property, Object expectedValue) {
		if (property.equals(HAS_PACKAGE_INFO)) {
			boolean hasPackageInfo = jaxbPackage.getJaxbProject().getJavaResourcePackage(jaxbPackage.getName()) != null;
			return ObjectTools.equals(hasPackageInfo, expectedValue);
		}
		return false;
	}
}
