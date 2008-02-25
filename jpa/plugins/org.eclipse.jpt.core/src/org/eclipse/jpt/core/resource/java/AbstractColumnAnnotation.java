 /*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.TextRange;


public interface AbstractColumnAnnotation extends NamedColumnAnnotation
{

	/**
	 * Corresponds to the unique element of the *Column annotation.
	 * Returns null if the unique valuePair does not exist in the annotation
	 */
	Boolean getUnique();
	
	/**
	 * Corresponds to the unique element of the *Column annotation.
	 * Set to null to remove the unique valuePair from the annotation
	 */
	void setUnique(Boolean unique);	
		String UNIQUE_PROPERTY = "uniqueProperty";
	
	/**
	 * Corresponds to the nullable element of the *Column annotation.
	 * Returns null if the nullable valuePair does not exist in the annotation
	 */
	Boolean getNullable();
	
	/**
	 * Corresponds to the nullable element of the *Column annotation.
	 * Set to null to remove the nullable valuePair from the annotation
	 */
	void setNullable(Boolean nullable);
		String NULLABLE_PROPERTY = "nullableProperty";
	
	/**
	 * Corresponds to the insertable element of the *Column annotation.
	 * Returns null if the insertable valuePair does not exist in the annotation
	 */
	Boolean getInsertable();
	
	/**
	 * Corresponds to the insertable element of the *Column annotation.
	 * Set to null to remove the insertable valuePair from the annotation
	 */
	void setInsertable(Boolean insertable);
		String INSERTABLE_PROPERTY = "insertableProperty";
	
	/**
	 * Corresponds to the updatable element of the *Column annotation.
	 * Returns null if the updatable valuePair does not exist in the annotation
	 */
	Boolean getUpdatable();
	
	/**
	 * Corresponds to the updatable element of the *Column annotation.
	 * Set to null to remove the updatable valuePair from the annotation
	 */
	void setUpdatable(Boolean updatable);
		String UPDATABLE_PROPERTY = "updatableProperty";
	
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
		String TABLE_PROPERTY = "tableProperty";

	
	/**
	 * Return the ITextRange for the unique element. If the unique element
	 * does not exist return the ITextRange for the *Column annotation.
	 */
	TextRange uniqueTextRange(CompilationUnit astRoot);

	/**
	 * Return the ITextRange for the nullable element. If the nullable element
	 * does not exist return the ITextRange for the *Column annotation.
	 */
	TextRange nullableTextRange(CompilationUnit astRoot);

	/**
	 * Return the ITextRange for the insertable element. If the insertable element
	 * does not exist return the ITextRange for the *Column annotation.
	 */
	TextRange insertableTextRange(CompilationUnit astRoot);

	/**
	 * Return the ITextRange for the updatable element. If the updatable element
	 * does not exist return the ITextRange for the *Column annotation.
	 */
	TextRange updatableTextRange(CompilationUnit astRoot);

	/**
	 * Return the ITextRange for the table element. If the table element
	 * does not exist return the ITextRange for the *Column annotation.
	 */
	TextRange tableTextRange(CompilationUnit astRoot);

	/**
	 * Return whether the specified postition touches the table element.
	 * Return false if the table element does not exist.
	 */
	boolean tableTouches(int pos, CompilationUnit astRoot);

}
