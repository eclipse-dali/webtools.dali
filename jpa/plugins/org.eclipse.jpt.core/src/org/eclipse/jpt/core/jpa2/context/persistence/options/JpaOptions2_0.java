/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.jpa2.context.persistence.options;

import org.eclipse.jpt.core.context.persistence.PersistenceUnitProperties;

/**
 *  JpaOptions2_0
 */
public interface JpaOptions2_0 extends PersistenceUnitProperties
{
	Integer getDefaultLockTimeout();
	Integer getLockTimeout();
	void setLockTimeout(Integer newLockTimeout);
		static final String LOCK_TIMEOUT_PROPERTY = "lockTimeout"; //$NON-NLS-1$
		// Property key
		static final String PERSISTENCE_LOCK_TIMEOUT = "javax.persistence.lock.timeout"; //$NON-NLS-1$
		static final Integer DEFAULT_LOCK_TIMEOUT = Integer.valueOf(5);

	Integer getDefaultQueryTimeout();
	Integer getQueryTimeout();
	void setQueryTimeout(Integer newQueryTimeout);
		static final String QUERY_TIMEOUT_PROPERTY = "queryTimeout"; //$NON-NLS-1$
		// Property key
		static final String PERSISTENCE_QUERY_TIMEOUT = "javax.persistence.query.timeout"; //$NON-NLS-1$
		static final Integer DEFAULT_QUERY_TIMEOUT = Integer.valueOf(5);

	ValidationMode getDefaultValidationMode();
	ValidationMode getValidationMode();
	void setValidationMode(ValidationMode validationMode);
		static final String VALIDATION_MODE_PROPERTY = "validationMode"; //$NON-NLS-1$
		// Property key
		static final String PERSISTENCE_VALIDATION_MODE = "javax.persistence.validation.mode"; //$NON-NLS-1$
		static final ValidationMode DEFAULT_VALIDATION_MODE = ValidationMode.auto;

	String getDefaultValidationGroupPrePersist();
	String getValidationGroupPrePersist();
	void setValidationGroupPrePersist(String newValidationGroupPrePersist);
		static final String VALIDATION_GROUP_PRE_PERSIST_PROPERTY = "validationGroupPrePersist"; //$NON-NLS-1$
		// Property key
		static final String PERSISTENCE_VALIDATION_GROUP_PRE_PERSIST = "javax.persistence.validation.group.pre-persist"; //$NON-NLS-1$
		static final String DEFAULT_VALIDATION_GROUP_PRE_PERSIST = "Default"; //$NON-NLS-1$

	String getDefaultValidationGroupPreUpdate();
	String getValidationGroupPreUpdate();
	void setValidationGroupPreUpdate(String newValidationGroupPreUpdate);
		static final String VALIDATION_GROUP_PRE_UPDATE_PROPERTY = "validationGroupPreUpdate"; //$NON-NLS-1$
		// Property key
		static final String PERSISTENCE_VALIDATION_GROUP_PRE_UPDATE = "javax.persistence.validation.group.pre-update"; //$NON-NLS-1$
		static final String DEFAULT_VALIDATION_GROUP_PRE_UPDATE = "Default"; //$NON-NLS-1$

	String getDefaultValidationGroupPreRemove();
	String getValidationGroupPreRemove();
	void setValidationGroupPreRemove(String newValidationGroupPreRemove);
		static final String VALIDATION_GROUP_PRE_REMOVE_PROPERTY = "validationGroupPreRemove"; //$NON-NLS-1$
		// Property key
		static final String PERSISTENCE_VALIDATION_GROUP_PRE_REMOVE = "javax.persistence.validation.group.pre-remove"; //$NON-NLS-1$
		static final String DEFAULT_VALIDATION_GROUP_PRE_REMOVE = ""; //$NON-NLS-1$

}
