/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence;

import java.util.Map;

import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkLogging2_4;

/**
 * EclipseLink 2.4 logging
 */
public class EclipseLinkLoggingImpl2_4
	extends EclipseLinkAbstractLogging2_0
	implements EclipseLinkLogging2_4
{
	// ********** constructors **********
	public EclipseLinkLoggingImpl2_4(PersistenceUnit2_0 parent) {
		super(parent);
	}

	// ********** initialization **********

	@Override
	protected void initializeProperties() {
		super.initializeProperties();
		
		this.initializeCategory(METADATA_CATEGORY_LOGGING_PROPERTY);
		this.initializeCategory(METAMODEL_CATEGORY_LOGGING_PROPERTY);
		this.initializeCategory(JPA_CATEGORY_LOGGING_PROPERTY);
		this.initializeCategory(DDL_CATEGORY_LOGGING_PROPERTY);

	}

	// ********** behavior **********
	
	@Override
	public void propertyValueChanged(String propertyName, String newValue) {
		super.propertyValueChanged(propertyName, newValue);
		
		if (propertyName.equals(ECLIPSELINK_METADATA_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_METADATA_CATEGORY_LOGGING_LEVEL, newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_METAMODEL_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_METAMODEL_CATEGORY_LOGGING_LEVEL, newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_JPA_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_JPA_CATEGORY_LOGGING_LEVEL, newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_DDL_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_DDL_CATEGORY_LOGGING_LEVEL, newValue);
		}
	}
	
	@Override
	public void propertyRemoved(String propertyName) {
		super.propertyRemoved(propertyName);

		if (propertyName.equals(ECLIPSELINK_METADATA_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_METADATA_CATEGORY_LOGGING_LEVEL, null);
		}
		else if (propertyName.equals(ECLIPSELINK_METAMODEL_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_METAMODEL_CATEGORY_LOGGING_LEVEL, null);
		}
		else if (propertyName.equals(ECLIPSELINK_JPA_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_JPA_CATEGORY_LOGGING_LEVEL, null);
		}
		else if (propertyName.equals(ECLIPSELINK_DDL_CATEGORY_LOGGING_LEVEL)) {
			this.categoryLoggingChanged_(ECLIPSELINK_DDL_CATEGORY_LOGGING_LEVEL, null);
		}
	}

	@Override
	protected void addPropertyNames(Map<String, String> propertyNames) {
		super.addPropertyNames(propertyNames);

		propertyNames.put(
			ECLIPSELINK_METADATA_CATEGORY_LOGGING_LEVEL,
			METADATA_CATEGORY_LOGGING_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_METAMODEL_CATEGORY_LOGGING_LEVEL,
			METAMODEL_CATEGORY_LOGGING_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_JPA_CATEGORY_LOGGING_LEVEL,
			JPA_CATEGORY_LOGGING_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_DDL_CATEGORY_LOGGING_LEVEL,
			DDL_CATEGORY_LOGGING_PROPERTY);
	}
}
