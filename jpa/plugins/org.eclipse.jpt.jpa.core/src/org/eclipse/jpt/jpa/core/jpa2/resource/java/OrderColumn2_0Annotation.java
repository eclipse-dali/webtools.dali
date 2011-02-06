/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.java.NamedColumnAnnotation;

/**
 * Corresponds to the JPA 2.0 annotation
 * <code>javax.persistence.OrderColumn</code>
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface OrderColumn2_0Annotation
	extends NamedColumnAnnotation
{
	String ANNOTATION_NAME = JPA2_0.ORDER_COLUMN;
	

	// ********** nullable **********

	/**
	 * Corresponds to the 'nullable' element of the OrderColumn annotation.
	 * Return null if the element does not exist in Java.
	 */
	Boolean getNullable();
		String NULLABLE_PROPERTY = "nullable"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'nullable' element of the OrderColumn annotation.
	 * Set to null to remove the element.
	 */
	void setNullable(Boolean nullable);

	/**
	 * Return the {@link TextRange} for the 'nullable' element. If the element
	 * does not exist return the {@link TextRange} for the OrderColumn annotation.
	 */
	TextRange getNullableTextRange(CompilationUnit astRoot);


	// ********** insertable **********

	/**
	 * Corresponds to the 'insertable' element of the OrderColumn annotation.
	 * Return null if the element does not exist in Java.
	 */
	Boolean getInsertable();
		String INSERTABLE_PROPERTY = "insertable"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'insertable' element of the OrderColumn annotation.
	 * Set to null to remove the element.
	 */
	void setInsertable(Boolean insertable);

	/**
	 * Return the {@link TextRange} for the 'insertable' element. If the element
	 * does not exist return the {@link TextRange} for the OrderColumn annotation.
	 */
	TextRange getInsertableTextRange(CompilationUnit astRoot);


	// ********** updatable **********

	/**
	 * Corresponds to the 'updatable' element of the OrderColumn annotation.
	 * Return null if the element does not exist in Java.
	 */
	Boolean getUpdatable();
		String UPDATABLE_PROPERTY = "updatable"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'updatable' element of the OrderColumn annotation.
	 * Set to null to remove the element.
	 */
	void setUpdatable(Boolean updatable);

	/**
	 * Return the {@link TextRange} for the 'updatable' element. If the element
	 * does not exist return the {@link TextRange} for the OrderColumn annotation.
	 */
	TextRange getUpdatableTextRange(CompilationUnit astRoot);

}
