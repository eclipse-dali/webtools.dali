/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Corresponds to the JPA annotations:<ul>
 * <li><code>javax.persistence.JoinColumn</code>
 * <li><code>javax.persistence.MapKeyJoinColumn</code>
 * </ul>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.1
 * @since 2.3
 */
public interface BaseJoinColumnAnnotation
	extends NamedColumnAnnotation, NestableAnnotation
{
	// ********** referenced column name **********
	/**
	 * Corresponds to the 'referencedColumnName' element of the JoinColumn annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getReferencedColumnName();
		String REFERENCED_COLUMN_NAME_PROPERTY = "referencedColumnName"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'referencedColumnName' element of the JoinColumn annotation.
	 * Set to null to remove the element.
	 */
	void setReferencedColumnName(String referencedColumnName);

	/**
	 * Return the {@link TextRange} for the 'referencedColumnName' element. If element
	 * does not exist return the {@link TextRange} for the JoinColumn annotation.
	 */
	TextRange getReferencedColumnNameTextRange(CompilationUnit astRoot);

	/**
	 * Return whether the specified position touches the 'referencedColumnName' element.
	 * Return false if the element does not exist.
	 */
	boolean referencedColumnNameTouches(int pos, CompilationUnit astRoot);

}
