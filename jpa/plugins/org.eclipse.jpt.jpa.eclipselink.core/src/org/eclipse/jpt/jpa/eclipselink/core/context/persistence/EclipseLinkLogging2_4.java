/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.persistence;

/**
 * EclipseLink 2.0 logging
 */
public interface EclipseLinkLogging2_4
	extends EclipseLinkLogging2_0
{
	static final String METADATA_CATEGORY_LOGGING_PROPERTY = "metadataLoggingLevel"; //$NON-NLS-1$
	static final String ECLIPSELINK_METADATA_CATEGORY_LOGGING_LEVEL = CATEGORY_PREFIX_ + "metadata"; //$NON-NLS-1$

	static final String METAMODEL_CATEGORY_LOGGING_PROPERTY = "metamodelLoggingLevel"; //$NON-NLS-1$
	static final String ECLIPSELINK_METAMODEL_CATEGORY_LOGGING_LEVEL = CATEGORY_PREFIX_ + "metamodel"; //$NON-NLS-1$

	static final String JPA_CATEGORY_LOGGING_PROPERTY = "jpaLoggingLevel"; //$NON-NLS-1$
	final String ECLIPSELINK_JPA_CATEGORY_LOGGING_LEVEL = CATEGORY_PREFIX_ + "jpa"; //$NON-NLS-1$

	static final String DDL_CATEGORY_LOGGING_PROPERTY = "ddlLoggingLevel"; //$NON-NLS-1$
	final String ECLIPSELINK_DDL_CATEGORY_LOGGING_LEVEL = CATEGORY_PREFIX_ + "ddl"; //$NON-NLS-1$
}
