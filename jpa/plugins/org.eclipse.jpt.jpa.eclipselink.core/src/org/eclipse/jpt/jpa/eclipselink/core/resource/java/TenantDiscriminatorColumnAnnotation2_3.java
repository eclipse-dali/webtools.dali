/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.java;

import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.java.DiscriminatorColumnAnnotation;

/**
 * Corresponds to the EclipseLink annotation
 * <code>org.eclipse.persistence.annotations.TenantDiscriminatorColumn</code>
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.1
 * @since 3.1
 */
public interface TenantDiscriminatorColumnAnnotation2_3
	extends DiscriminatorColumnAnnotation, NestableAnnotation
{
	String ANNOTATION_NAME = EclipseLink.TENANT_DISCRIMINATOR_COLUMN;

	// ********** contextProperty **********

	/**
	 * Corresponds to the 'contextProperty' element of the TenantDiscriminatorColumn annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getContextProperty();
		String CONTEXT_PROPERTY_PROPERTY = "contextProperty"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'contextProperty' element of the TenantDiscriminatorColumn annotation.
	 * Set to null to remove the element.
	 */
	void setContextProperty(String contextProperty);

	/**
	 * Return the {@link TextRange} for the 'contextProperty' element. If the element
	 * does not exist return the {@link TextRange} for the TenantDiscriminatorColumn annotation.
	 */
	TextRange getContextPropertyTextRange();
	
	// ********** table **********

	/**
	 * Corresponds to the 'table' element of the TenantDiscriminatorColumn annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getTable();
		String TABLE_PROPERTY = "table"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'table' element of the TenantDiscriminatorColumn annotation.
	 * Set to null to remove the element.
	 */
	void setTable(String table);

	/**
	 * Return the {@link TextRange} for the 'table' element. If the element
	 * does not exist return the {@link TextRange} for the TenantDiscriminatorColumn annotation.
	 */
	TextRange getTableTextRange();

	/**
	 * Return whether the specified position touches the 'table' element.
	 * Return false if the element does not exist.
	 */
	boolean tableTouches(int pos);

	// ********** primaryKey **********

	/**
	 * Corresponds to the 'primaryKey' element of the TenantDiscriminatorColumn annotation.
	 * Return null if the element does not exist in Java.
	 */
	Boolean getPrimaryKey();
		String PRIMARY_KEY_PROPERTY = "primaryKey"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'primaryKey' element of the TenantDiscriminatorColumn annotation.
	 * Set to null to remove the element.
	 */
	void setPrimaryKey(Boolean primaryKey);

	/**
	 * Return the {@link TextRange} for the 'primaryKey' element. If the element
	 * does not exist return the {@link TextRange} for the TenantDiscriminatorColumn annotation.
	 */
	TextRange getPrimaryKeyTextRange();
}
