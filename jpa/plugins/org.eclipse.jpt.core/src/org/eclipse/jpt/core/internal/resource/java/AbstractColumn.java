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


public interface AbstractColumn extends NamedColumn
{

	/**
	 * Corresponds to the unique element of the *Column annotation.
	 * Returns null if the unique valuePair does not exist in the annotation
	 */
	Boolean isUnique();
	
	/**
	 * Corresponds to the unique element of the *Column annotation.
	 * Set to null to remove the unique valuePair from the annotation
	 */
	void setUnique(Boolean unique);
	
	/**
	 * Corresponds to the nullable element of the *Column annotation.
	 * Returns null if the nullable valuePair does not exist in the annotation
	 */
	Boolean isNullable();
	
	/**
	 * Corresponds to the nullable element of the *Column annotation.
	 * Set to null to remove the nullable valuePair from the annotation
	 */
	void setNullable(Boolean nullable);
	
	/**
	 * Corresponds to the insertable element of the *Column annotation.
	 * Returns null if the insertable valuePair does not exist in the annotation
	 */
	Boolean isInsertable();
	
	/**
	 * Corresponds to the insertable element of the *Column annotation.
	 * Set to null to remove the insertable valuePair from the annotation
	 */
	void setInsertable(Boolean insertable);
	
	/**
	 * Corresponds to the updatable element of the *Column annotation.
	 * Returns null if the updatable valuePair does not exist in the annotation
	 */
	Boolean isUpdatable();
	
	/**
	 * Corresponds to the updatable element of the *Column annotation.
	 * Set to null to remove the updatable valuePair from the annotation
	 */
	void setUpdatable(Boolean updatable);
	
	/**
	 * Corresponds to the table element of the *Column annotation.
	 * Returns null if the table valuePair does not exist in the annotation
	 */
	String getTable();
	
	/**
	 * Corresponds to the table element of the *Column annotation.
	 * Set to null to remove the table valuePair from the annotation
	 */
	void setTable(String table);

	
	/**
	 * Return the ITextRange for the unique element. If the unique element
	 * does not exist return the ITextRange for the *Column annotation.
	 */
	ITextRange uniqueTextRange(CompilationUnit astRoot);

	/**
	 * Return the ITextRange for the nullable element. If the nullable element
	 * does not exist return the ITextRange for the *Column annotation.
	 */
	ITextRange nullableTextRange(CompilationUnit astRoot);

	/**
	 * Return the ITextRange for the insertable element. If the insertable element
	 * does not exist return the ITextRange for the *Column annotation.
	 */
	ITextRange insertableTextRange(CompilationUnit astRoot);

	/**
	 * Return the ITextRange for the updatable element. If the updatable element
	 * does not exist return the ITextRange for the *Column annotation.
	 */
	ITextRange updatableTextRange(CompilationUnit astRoot);

	/**
	 * Return the ITextRange for the table element. If the table element
	 * does not exist return the ITextRange for the *Column annotation.
	 */
	ITextRange tableTextRange(CompilationUnit astRoot);

}
