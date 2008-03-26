/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface BaseColumnAnnotation extends NamedColumnAnnotation
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
	 * Return the {@link TextRange} for the unique element. If the unique element
	 * does not exist return the {@link TextRange} for the *Column annotation.
	 */
	TextRange getUniqueTextRange(CompilationUnit astRoot);

	/**
	 * Return the {@link TextRange} for the nullable element. If the nullable element
	 * does not exist return the {@link TextRange} for the *Column annotation.
	 */
	TextRange getNullableTextRange(CompilationUnit astRoot);

	/**
	 * Return the {@link TextRange} for the insertable element. If the insertable element
	 * does not exist return the {@link TextRange} for the *Column annotation.
	 */
	TextRange getInsertableTextRange(CompilationUnit astRoot);

	/**
	 * Return the {@link TextRange} for the updatable element. If the updatable element
	 * does not exist return the {@link TextRange} for the *Column annotation.
	 */
	TextRange getUpdatableTextRange(CompilationUnit astRoot);

	/**
	 * Return the {@link TextRange} for the table element. If the table element
	 * does not exist return the {@link TextRange} for the *Column annotation.
	 */
	TextRange getTableTextRange(CompilationUnit astRoot);

	/**
	 * Return whether the specified postition touches the table element.
	 * Return false if the table element does not exist.
	 */
	boolean tableTouches(int pos, CompilationUnit astRoot);

}
