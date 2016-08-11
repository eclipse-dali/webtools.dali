/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context.persistence.options;

import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitProperties;

/**
 * JPA 2.0 options
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface Options2_0
	extends PersistenceUnitProperties
{
	Integer getLockTimeout();
	void setLockTimeout(Integer newLockTimeout);
		String LOCK_TIMEOUT_PROPERTY = "lockTimeout"; //$NON-NLS-1$
		// Property key
		String PERSISTENCE_LOCK_TIMEOUT = "javax.persistence.lock.timeout"; //$NON-NLS-1$

	Integer getDefaultLockTimeout();
		Integer DEFAULT_LOCK_TIMEOUT = Integer.valueOf(5);

	Integer getQueryTimeout();
	void setQueryTimeout(Integer newQueryTimeout);
		String QUERY_TIMEOUT_PROPERTY = "queryTimeout"; //$NON-NLS-1$
		// Property key
		String PERSISTENCE_QUERY_TIMEOUT = "javax.persistence.query.timeout"; //$NON-NLS-1$

	Integer getDefaultQueryTimeout();
		Integer DEFAULT_QUERY_TIMEOUT = Integer.valueOf(5);

	ListIterable<String> getValidationGroupPrePersists();
	int getValidationGroupPrePersistsSize();
	boolean validationGroupPrePersistExists(String validationGroupPrePersistClassName);
	String addValidationGroupPrePersist(String newValidationGroupPrePersistClassName);
	void removeValidationGroupPrePersist(String validationGroupPrePersistClassName);
		String VALIDATION_GROUP_PRE_PERSIST_LIST = "validationGroupPrePersists"; //$NON-NLS-1$
		String VALIDATION_GROUP_PRE_PERSIST_PROPERTY = "validationGroupPrePersist"; //$NON-NLS-1$
		// Property key
		String PERSISTENCE_VALIDATION_GROUP_PRE_PERSIST = "javax.persistence.validation.group.pre-persist"; //$NON-NLS-1$

	ListIterable<String> getValidationGroupPreUpdates();
	int getValidationGroupPreUpdatesSize();
	boolean validationGroupPreUpdateExists(String validationGroupPreUpdateClassName);
	String addValidationGroupPreUpdate(String newValidationGroupPreUpdateClassName);
	void removeValidationGroupPreUpdate(String validationGroupPreUpdateClassName);
		String VALIDATION_GROUP_PRE_UPDATE_LIST = "validationGroupPreUpdates"; //$NON-NLS-1$
		String VALIDATION_GROUP_PRE_UPDATE_PROPERTY = "validationGroupPreUpdate"; //$NON-NLS-1$
		// Property key
		String PERSISTENCE_VALIDATION_GROUP_PRE_UPDATE = "javax.persistence.validation.group.pre-update"; //$NON-NLS-1$

	ListIterable<String> getValidationGroupPreRemoves();
	int getValidationGroupPreRemovesSize();
	boolean validationGroupPreRemoveExists(String validationGroupPreRemoveClassName);
	String addValidationGroupPreRemove(String newValidationGroupPreRemoveClassName);
	void removeValidationGroupPreRemove(String validationGroupPreRemoveClassName);
		String VALIDATION_GROUP_PRE_REMOVE_LIST = "validationGroupPreRemoves"; //$NON-NLS-1$
		String VALIDATION_GROUP_PRE_REMOVE_PROPERTY = "validationGroupPreRemove"; //$NON-NLS-1$
		// Property key
		String PERSISTENCE_VALIDATION_GROUP_PRE_REMOVE = "javax.persistence.validation.group.pre-remove"; //$NON-NLS-1$
}
