/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context.persistence.options;

import java.util.ListIterator;

import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitProperties;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface JpaOptions2_0
	extends PersistenceUnitProperties
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

	ListIterator<String> validationGroupPrePersists();
	int validationGroupPrePersistsSize();
	boolean validationGroupPrePersistExists(String validationGroupPrePersistClassName);
	String addValidationGroupPrePersist(String newValidationGroupPrePersistClassName);
	void removeValidationGroupPrePersist(String validationGroupPrePersistClassName);
		static final String VALIDATION_GROUP_PRE_PERSIST_LIST = "validationGroupPrePersists"; //$NON-NLS-1$
		static final String VALIDATION_GROUP_PRE_PERSIST_PROPERTY = "validationGroupPrePersist"; //$NON-NLS-1$
		// Property key
		static final String PERSISTENCE_VALIDATION_GROUP_PRE_PERSIST = "javax.persistence.validation.group.pre-persist"; //$NON-NLS-1$

	ListIterator<String> validationGroupPreUpdates();
	int validationGroupPreUpdatesSize();
	boolean validationGroupPreUpdateExists(String validationGroupPreUpdateClassName);
	String addValidationGroupPreUpdate(String newValidationGroupPreUpdateClassName);
	void removeValidationGroupPreUpdate(String validationGroupPreUpdateClassName);
		static final String VALIDATION_GROUP_PRE_UPDATE_LIST = "validationGroupPreUpdates"; //$NON-NLS-1$
		static final String VALIDATION_GROUP_PRE_UPDATE_PROPERTY = "validationGroupPreUpdate"; //$NON-NLS-1$
		// Property key
		static final String PERSISTENCE_VALIDATION_GROUP_PRE_UPDATE = "javax.persistence.validation.group.pre-update"; //$NON-NLS-1$

	ListIterator<String> validationGroupPreRemoves();
	int validationGroupPreRemovesSize();
	boolean validationGroupPreRemoveExists(String validationGroupPreRemoveClassName);
	String addValidationGroupPreRemove(String newValidationGroupPreRemoveClassName);
	void removeValidationGroupPreRemove(String validationGroupPreRemoveClassName);
		static final String VALIDATION_GROUP_PRE_REMOVE_LIST = "validationGroupPreRemoves"; //$NON-NLS-1$
		static final String VALIDATION_GROUP_PRE_REMOVE_PROPERTY = "validationGroupPreRemove"; //$NON-NLS-1$
		// Property key
		static final String PERSISTENCE_VALIDATION_GROUP_PRE_REMOVE = "javax.persistence.validation.group.pre-remove"; //$NON-NLS-1$
}
