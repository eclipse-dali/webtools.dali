/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;

/**
 * Corresponds to the javax.persistence.OrderBy annotation
 */
public interface OrderBy extends JavaResource
{
	/**
	 * Corresponds to the value element of the javax.persistence.OrderBy annotation.
	 * Returns null if the value valuePair does not exist in the annotation
	 */
	String getValue();
	
	/**
	 * Corresponds to the value element of the javax.persistence.OrderBy annotation.
	 * Setting the value to null will not remove the OrderBy annotation
	 */
	void setValue(String value);
	
	/**
	 * Return the ITextRange for the value element.  If the value element 
	 * does not exist return the ITextRange for the OrderBy annotation.
	 */
	ITextRange valueTextRange(CompilationUnit astRoot);

}
