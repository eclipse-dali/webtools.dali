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
 * Corresponds to the javax.persistence.AttributeOverride annotation
 */
public interface AttributeOverride extends JavaResource
{
	/**
	 * Corresponds to the name element of the AttributeOverride annotation.
	 * Returns null if the name element does not exist in java.
	 */
	String getName();
	
	/**
	 * Corresponds to the name element of the AttributeOverride annotation.
	 * Set to null to remove the name element.
	 */
	void setName(String name);
	
	/**
	 * Corresponds to the column element of the AttributeOverride annotation.
	 * Returns null if the column element does not exist in java.
	 */
	Column getColumn();
	
	/**
	 * Add the column element to the AttributeOverride annotation.
	 */
	Column addColumn();
	
	/**
	 * Remove the column element from the AttributeOverride annotation.
	 */
	void removeColumn();

	/**
	 * Return the ITextRange for the name element. If name element
	 * does not exist return the ITextRange for the AttributeOverride annotation.
	 */
	ITextRange nameTextRange(CompilationUnit astRoot);

}
