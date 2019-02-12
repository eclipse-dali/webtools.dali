/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
