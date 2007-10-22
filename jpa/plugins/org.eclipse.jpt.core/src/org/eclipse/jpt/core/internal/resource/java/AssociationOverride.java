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

import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;

/**
 * Corresponds to the javax.persistence.AssociationOverride annotation
 */
public interface AssociationOverride extends JavaResource
{
	/**
	 * Corresponds to the name element of the AssociationOverride annotation.
	 * Returns null if the name element does not exist in java.
	 */
	String getName();
	
	/**
	 * Corresponds to the name element of the AssociationOverride annotation.
	 * Set to null to remove the name element.
	 */
	void setName(String name);

	/**
	 * Corresponds to the joinColumns element of the AssociationOverride annotation.
	 * Returns an empty iterator if the joinColumns element does not exist in java.
	 */
	ListIterator<JoinColumn> joinColumns();
	
	JoinColumn joinColumnAt(int index);
	
	int indexOfJoinColumn(JoinColumn joinColumn);
	
	int joinColumnsSize();

	JoinColumn addJoinColumn(int index);
	
	void removeJoinColumn(int index);
	
	void moveJoinColumn(int oldIndex, int newIndex);
	
	/**
	 * Return the ITextRange for the name element. If name element
	 * does not exist return the ITextRange for the AssociationOverride annotation.
	 */
	ITextRange nameTextRange(CompilationUnit astRoot);
	
	/**
	 * Return whether the specified postition touches the name element.
	 * Return falamese if the n element does not exist.
	 */
	boolean nameTouches(int pos, CompilationUnit astRoot);

}
