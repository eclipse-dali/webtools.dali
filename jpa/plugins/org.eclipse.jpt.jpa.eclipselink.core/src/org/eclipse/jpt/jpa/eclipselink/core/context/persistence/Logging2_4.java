/*******************************************************************************
* Copyright (c) 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.persistence;

/**
 *  Logging2_4
 */
public interface Logging2_4 extends Logging2_0 {

	static final String METADATA_CATEGORY_LOGGING_PROPERTY = "metadataLoggingLevel"; //$NON-NLS-1$
	static final String ECLIPSELINK_METADATA_CATEGORY_LOGGING_LEVEL = CATEGORY_PREFIX_ + "metadata"; //$NON-NLS-1$

	static final String METAMODEL_CATEGORY_LOGGING_PROPERTY = "metamodelLoggingLevel"; //$NON-NLS-1$
	static final String ECLIPSELINK_METAMODEL_CATEGORY_LOGGING_LEVEL = CATEGORY_PREFIX_ + "metamodel"; //$NON-NLS-1$

	static final String JPA_CATEGORY_LOGGING_PROPERTY = "jpaLoggingLevel"; //$NON-NLS-1$
	final String ECLIPSELINK_JPA_CATEGORY_LOGGING_LEVEL = CATEGORY_PREFIX_ + "jpa"; //$NON-NLS-1$

	static final String DDL_CATEGORY_LOGGING_PROPERTY = "ddlLoggingLevel"; //$NON-NLS-1$
	final String ECLIPSELINK_DDL_CATEGORY_LOGGING_LEVEL = CATEGORY_PREFIX_ + "ddl"; //$NON-NLS-1$

}
