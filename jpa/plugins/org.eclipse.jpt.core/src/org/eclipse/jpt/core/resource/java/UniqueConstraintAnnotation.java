/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * Corresponds to the JPA annotation
 * javax.persistence.UniqueConstraint
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.0
 */
public interface UniqueConstraintAnnotation
	extends Annotation
{
	String ANNOTATION_NAME = JPA.UNIQUE_CONSTRAINT;

	/**
	 * Corresponds to the 'columnNames' element of the UniqueConstraint annotation.
	 * Return null if the element does not exist in the annotation.
	 */
	ListIterator<String> columnNames();
		String COLUMN_NAMES_LIST = "columnNames"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'columnNames' element of the UniqueConstraint annotation.
	 */
	int columnNamesSize();
	
	/**
	 * Corresponds to the 'columnNames' element of the UniqueConstraint annotation.
	 */
	void addColumnName(String columnName);

	/**
	 * Corresponds to the 'columnNames' element of the UniqueConstraint annotation.
	 */
	void addColumnName(int index, String columnName);
	
	/**
	 * Corresponds to the 'columnNames' element of the UniqueConstraint annotation.
	 */
	void moveColumnName(int targetIndex, int sourceIndex);
		
	/**
	 * Corresponds to the 'columnNames' element of the UniqueConstraint annotation.
	 */
	void removeColumnName(String columnName);
	
	/**
	 * Corresponds to the 'columnNames' element of the UniqueConstraint annotation.
	 */
	void removeColumnName(int index);
	
	/**
	 * Return whether the specified position touches the 'columnNames' element.
	 * Return false if the element does not exist.
	 */
	boolean columnNamesTouches(int pos, CompilationUnit astRoot);

}
