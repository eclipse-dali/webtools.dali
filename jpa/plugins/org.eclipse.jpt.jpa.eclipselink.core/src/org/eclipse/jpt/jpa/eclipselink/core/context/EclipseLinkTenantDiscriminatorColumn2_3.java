/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context;

import org.eclipse.jpt.jpa.core.context.NamedDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.TableColumn;

/**
 * tenant discriminator column
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
public interface EclipseLinkTenantDiscriminatorColumn2_3
	extends NamedDiscriminatorColumn, TableColumn
{
	String DEFAULT_NAME = "TENANT_ID"; //$NON-NLS-1$


	// ********** context property **********

	/**
	 * Return the specified context property if present, otherwise return
	 * the default context property.
	 */
	String getContextProperty();
	String getSpecifiedContextProperty();
		String SPECIFIED_CONTEXT_PROPERTY_PROPERTY = "specifiedContextProperty"; //$NON-NLS-1$
	String getDefaultContextProperty();
		String DEFAULT_CONTEXT_PROPERTY_PROPERTY = "defaultContextProperty"; //$NON-NLS-1$
		String DEFAULT_CONTEXT_PROPERTY = "eclipselink.tenant-id"; //$NON-NLS-1$


	// ********** primaryKey **********

	/**
	 * Return the specified primaryKey setting if present, otherwise return the
	 * default primaryKey setting.
	 */
	boolean isPrimaryKey();
	Boolean getSpecifiedPrimaryKey();
		String SPECIFIED_PRIMARY_KEY_PROPERTY = "specifiedPrimaryKey"; //$NON-NLS-1$
	boolean isDefaultPrimaryKey();
		String DEFAULT_PRIMARY_KEY_PROPERTY = "defaultPrimaryKey"; //$NON-NLS-1$
	boolean DEFAULT_PRIMARY_KEY = false;


	// ********** parent adapter **********

	/**
	 * interface allowing tenant discriminator columns to be used in multiple places
	 */
	interface ParentAdapter
		extends NamedDiscriminatorColumn.ParentAdapter, TableColumn.ParentAdapter
	{
		/**
		 * Return the default context property name
		 */
		String getDefaultContextPropertyName();

		/**
		 * Return the default primary key setting
		 */
		boolean getDefaultPrimaryKey();
	}
}
