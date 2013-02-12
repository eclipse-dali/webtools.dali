/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.java;

import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Corresponds to the JPA annotations:<code><ul>
 * <li>javax.persistence.Column
 * <li>javax.persistence.MapKeyColumn
 * <li>javax.persistence.JoinColumn
 * <li>javax.persistence.MapKeyJoinColumn
 * <li>javax.persistence.DiscriminatorColumn
 * <li>javax.persistence.OrderColumn
 * <li>javax.persistence.PrimaryKeyJoinColumn
 * </ul></code>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.0
 */
public interface NamedColumnAnnotation
	extends Annotation
{
	/**
	 * Return whether the annotation exists in Java.
	 */
	boolean isSpecified();


	// ********** name **********

	/**
	 * Corresponds to the 'name' element of the *Column annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getName();
		String NAME_PROPERTY = "name"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'name' element of the *Column annotation.
	 * Set to null to remove the element.
	 */
	void setName(String name);

	/**
	 * Return the {@link TextRange} for the 'name' element. If the element
	 * does not exist return the {@link TextRange} for the *Column annotation.
	 */
	TextRange getNameTextRange();
	
	/**
	 * Return whether the specified position touches the 'name' element.
	 * Return false if the element does not exist.
	 */
	boolean nameTouches(int pos);

	TextRange getNameValidationTextRange();

	boolean nameValidationTouches(int pos);


	// ********** column definition **********

	/**
	 * Corresponds to the 'columnDefinition' element of the *Column annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getColumnDefinition();
		String COLUMN_DEFINITION_PROPERTY = "columnDefinition"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'columnDefinition' element of the *Column annotation.
	 * Set to null to remove the element.
	 */
	void setColumnDefinition(String columnDefinition);

	/**
	 * Return the {@link TextRange} for the 'columnDefinition' element. If the
	 * element does not exist return the {@link TextRange} for the *Column annotation.
	 */
	TextRange getColumnDefinitionTextRange();

}
