/*******************************************************************************
* Copyright (c) 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence;

import java.util.Map;

import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Logging2_4;

/**
 *  EclipseLinkLogging2_4
 */
public class EclipseLinkLogging2_4 extends AbstractEclipseLinkLogging2_0 implements Logging2_4 {

	// ********** constructors **********
	public EclipseLinkLogging2_4(PersistenceUnit2_0 parent) {
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
