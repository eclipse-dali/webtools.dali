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
 * Corresponds to the javax.persistence.Enumerated annotation
 */
public interface Enumerated extends JavaResource
{
	/**
	 * Corresponds to the value element of the Enumerated annotation.
	 * Returns null if the value element does not exist in java.
	 */
	EnumType getValue();
	
	/**
	 * Corresponds to the value element of the Enumerated annotation.
	 * Set to null to remove the value element.
	 */
	void setValue(EnumType value);
		
	/**
	 * Return the ITextRange for the value element.  If the value element 
	 * does not exist return the ITextRange for the Enumerated annotation.
	 */
	ITextRange valueTextRange(CompilationUnit astRoot);

}
