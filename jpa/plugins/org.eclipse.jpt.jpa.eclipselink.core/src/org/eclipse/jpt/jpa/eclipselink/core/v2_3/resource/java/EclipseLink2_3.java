/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java;

/**
 * EclipseLink Java-related stuff (annotations etc.)
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.1
 * @since 3.1
 */
@SuppressWarnings("nls")
public interface EclipseLink2_3 {

	// EclipseLink package
	String PACKAGE = "org.eclipse.persistence.annotations"; //$NON-NLS-1$
	String PACKAGE_ = PACKAGE + ".";


	// ********** API **********

	// EclispeLink 2.3 annotations
	String MULTITENANT = PACKAGE_ + "Multitenant";
		String CLASS_EXTRACTOR__VALUE = "value";

	String MULTITENANT_TYPE = PACKAGE_ + "MultitenantType";
		String MULTITENANT_TYPE_ = MULTITENANT_TYPE + ".";
		String MULTITENANT_TYPE__SINGLE_TABLE = MULTITENANT_TYPE_ + "SINGLE_TABLE";
		String MULTITENANT_TYPE__TABLE_PER_TENANT = MULTITENANT_TYPE_ + "TABLE_PER_TENANT";

	String TENANT_DISCRIMINATOR_COLUMN = PACKAGE_ + "TenantDiscriminatorColumn";
		String TENANT_DISCRIMINATOR_COLUMN__NAME = "name";
		String TENANT_DISCRIMINATOR_COLUMN__CONTEXT_PROPERTY = "contextProperty";
		String TENANT_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE = "discriminatorType";
		String TENANT_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION = "columnDefinition";
		String TENANT_DISCRIMINATOR_COLUMN__LENGTH = "length";
		String TENANT_DISCRIMINATOR_COLUMN__TABLE = "table";
		String TENANT_DISCRIMINATOR_COLUMN__PRIMARY_KEY = "primaryKey";

	String TENANT_DISCRIMINATOR_COLUMNS = PACKAGE_ + "TenantDiscriminatorColumns";
		String TENANT_DISCRIMINATOR_COLUMNS__VALUE = "value";

}
