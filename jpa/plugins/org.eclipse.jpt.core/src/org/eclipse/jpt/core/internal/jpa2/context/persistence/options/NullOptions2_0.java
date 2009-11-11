/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.persistence.options;

import java.util.Map;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.context.persistence.AbstractPersistenceUnitProperties;
import org.eclipse.jpt.core.jpa2.context.persistence.options.JpaOptions2_0;

/**
 * JPA 2.0 options
 */
public class NullOptions2_0 extends AbstractPersistenceUnitProperties
	implements JpaOptions2_0
{
	

	// ********** constructors **********
	public NullOptions2_0(PersistenceUnit parent) {
		super(parent);
	}

	// ********** initialization **********
	/**
	 * Initializes properties with values from the persistence unit.
	 */
	@Override
	protected void initializeProperties() {
		//do nothing
	}

	// ********** behavior **********
	
	public void propertyValueChanged(String propertyName, String newValue) {
		//do nothing
	}

	public void propertyRemoved(String propertyName) {
		//do nothing
	}

	/**
	 * Adds property names key/value pairs, where: 
	 * 		key = PU property key
	 * 		value = property id
	 */
	@Override
	protected void addPropertyNames(Map<String, String> propertyNames) {
		//do nothing
	}

	// ********** LockTimeout **********
	public Integer getLockTimeout() {
		return null;
	}

	public void setLockTimeout(Integer newLockTimeout) {
		throw new UnsupportedOperationException();
	}

	public Integer getDefaultLockTimeout() {
		return DEFAULT_LOCK_TIMEOUT;
	}

	// ********** QueryTimeout **********
	public Integer getQueryTimeout() {
		return null;
	}

	public void setQueryTimeout(Integer newQueryTimeout) {
		throw new UnsupportedOperationException();
	}

	public Integer getDefaultQueryTimeout() {
		return DEFAULT_QUERY_TIMEOUT;
	}


	// ********** ValidationGroupPrePersist **********
	public String getValidationGroupPrePersist() {
		return null;
	}

	public void setValidationGroupPrePersist(String newValidationGroupPrePersist) {
		throw new UnsupportedOperationException();
	}

	public String getDefaultValidationGroupPrePersist() {
		return DEFAULT_VALIDATION_GROUP_PRE_PERSIST;
	}

	// ********** ValidationGroupPreUpdate **********
	public String getValidationGroupPreUpdate() {
		return null;
	}

	public void setValidationGroupPreUpdate(String newValidationGroupPreUpdate) {
		throw new UnsupportedOperationException();
	}

	public String getDefaultValidationGroupPreUpdate() {
		return DEFAULT_VALIDATION_GROUP_PRE_UPDATE;
	}

	// ********** ValidationGroupPreRemove **********
	public String getValidationGroupPreRemove() {
		return null;
	}

	public void setValidationGroupPreRemove(String newValidationGroupPreRemove) {
		throw new UnsupportedOperationException();
	}

	public String getDefaultValidationGroupPreRemove() {
		return DEFAULT_VALIDATION_GROUP_PRE_REMOVE;
	}
}
