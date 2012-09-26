/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

import org.eclipse.jdt.core.IJavaElement;

public interface JavaElementReference 
{
	/**
	 * Return IType for a persistent type or return IField or IMethod 
	 * for a persistent attribute according to its access type
	 */
	IJavaElement getJavaElement();
}
