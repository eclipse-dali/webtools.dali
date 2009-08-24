/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_0.context.persistence.options;

import java.util.Map;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.options.EclipseLinkOptions;
import org.eclipse.jpt.eclipselink.core.v2_0.context.persistence.options.Options2_0;

/**
 *  EclipseLinkOptions2_0
 */
public class EclipseLinkOptions2_0 extends EclipseLinkOptions
	implements Options2_0
{
	// ********** GenericConnection properties **********
	private Integer lockTimeout;
	private Integer queryTimeout;
	private String validationGroupPrePersist;
	private String validationGroupPreUpdate;
	private String validationGroupPreRemove;
	

	// ********** constructors **********
	public EclipseLinkOptions2_0(PersistenceUnit parent) {
		super(parent);
	}

	// ********** initialization **********
	/**
	 * Initializes properties with values from the persistence unit.
	 */
	@Override
	protected void initializeProperties() {
		super.initializeProperties();
		
		this.lockTimeout = 
			this.getIntegerValue(PERSISTENCE_LOCK_TIMEOUT);
		this.queryTimeout = 
			this.getIntegerValue(PERSISTENCE_QUERY_TIMEOUT);
		this.validationGroupPrePersist = 
			this.getStringValue(PERSISTENCE_VALIDATION_GROUP_PRE_PERSIST);
		this.validationGroupPreUpdate = 
			this.getStringValue(PERSISTENCE_VALIDATION_GROUP_PRE_UPDATE);
		this.validationGroupPreRemove = 
			this.getStringValue(PERSISTENCE_VALIDATION_GROUP_PRE_REMOVE);
	}

	// ********** behavior **********

	@Override
	public void propertyValueChanged(String propertyName, String newValue) {
		super.propertyValueChanged(propertyName, newValue);
		
		if (propertyName.equals(PERSISTENCE_LOCK_TIMEOUT)) {
			this.lockTimeoutChanged(newValue);
		}
		else if (propertyName.equals(PERSISTENCE_QUERY_TIMEOUT)) {
			this.queryTimeoutChanged(newValue);
		}
		else if (propertyName.equals(PERSISTENCE_VALIDATION_GROUP_PRE_PERSIST)) {
			this.validationGroupPrePersistChanged(newValue);
		}
		else if (propertyName.equals(PERSISTENCE_VALIDATION_GROUP_PRE_UPDATE)) {
			this.validationGroupPreUpdateChanged(newValue);
		}
		else if (propertyName.equals(PERSISTENCE_VALIDATION_GROUP_PRE_REMOVE)) {
			this.validationGroupPreRemoveChanged(newValue);
		}
	}

	@Override
	public void propertyRemoved(String propertyName) {
		super.propertyRemoved(propertyName);
		
		if (propertyName.equals(PERSISTENCE_LOCK_TIMEOUT)) {
			this.lockTimeoutChanged(null);
		}
		else if (propertyName.equals(PERSISTENCE_QUERY_TIMEOUT)) {
			this.queryTimeoutChanged(null);
		}
		else if (propertyName.equals(PERSISTENCE_VALIDATION_GROUP_PRE_PERSIST)) {
			this.validationGroupPrePersistChanged(null);
		}
		else if (propertyName.equals(PERSISTENCE_VALIDATION_GROUP_PRE_UPDATE)) {
			this.validationGroupPreUpdateChanged(null);
		}
		else if (propertyName.equals(PERSISTENCE_VALIDATION_GROUP_PRE_REMOVE)) {
			this.validationGroupPreRemoveChanged(null);
		}
	}

	/**
	 * Adds property names key/value pairs, where: 
	 * 		key = PU property key
	 * 		value = property id
	 */
	@Override
	protected void addPropertyNames(Map<String, String> propertyNames) {
		super.addPropertyNames(propertyNames);
		
		propertyNames.put(
			PERSISTENCE_LOCK_TIMEOUT,
			LOCK_TIMEOUT_PROPERTY);
		propertyNames.put(
			PERSISTENCE_QUERY_TIMEOUT,
			QUERY_TIMEOUT_PROPERTY);
		propertyNames.put(
			PERSISTENCE_VALIDATION_GROUP_PRE_PERSIST,
			VALIDATION_GROUP_PRE_PERSIST_PROPERTY);
		propertyNames.put(
			PERSISTENCE_VALIDATION_GROUP_PRE_UPDATE,
			VALIDATION_GROUP_PRE_UPDATE_PROPERTY);
		propertyNames.put(
			PERSISTENCE_VALIDATION_GROUP_PRE_REMOVE,
			VALIDATION_GROUP_PRE_REMOVE_PROPERTY);
	}

	// ********** LockTimeout **********
	public Integer getLockTimeout() {
		return this.lockTimeout;
	}

	public void setLockTimeout(Integer newLockTimeout) {
		Integer old = this.lockTimeout;
		this.lockTimeout = newLockTimeout;
		this.putProperty(LOCK_TIMEOUT_PROPERTY, newLockTimeout);
		this.firePropertyChanged(LOCK_TIMEOUT_PROPERTY, old, newLockTimeout);
	}

	private void lockTimeoutChanged(String stringValue) {
		Integer newValue = getIntegerValueOf(stringValue);
		
		Integer old = this.lockTimeout;
		this.lockTimeout = newValue;
		this.firePropertyChanged(LOCK_TIMEOUT_PROPERTY, old, newValue);
	}

	public Integer getDefaultLockTimeout() {
		return DEFAULT_LOCK_TIMEOUT;
	}

	// ********** QueryTimeout **********
	public Integer getQueryTimeout() {
		return this.queryTimeout;
	}

	public void setQueryTimeout(Integer newQueryTimeout) {
		Integer old = this.queryTimeout;
		this.queryTimeout = newQueryTimeout;
		this.putProperty(QUERY_TIMEOUT_PROPERTY, newQueryTimeout);
		this.firePropertyChanged(QUERY_TIMEOUT_PROPERTY, old, newQueryTimeout);
	}

	private void queryTimeoutChanged(String stringValue) {
		Integer newValue = getIntegerValueOf(stringValue);
		
		Integer old = this.queryTimeout;
		this.queryTimeout = newValue;
		this.firePropertyChanged(QUERY_TIMEOUT_PROPERTY, old, newValue);
	}

	public Integer getDefaultQueryTimeout() {
		return DEFAULT_QUERY_TIMEOUT;
	}

	// ********** ValidationGroupPrePersist **********
	public String getValidationGroupPrePersist() {
		return this.validationGroupPrePersist;
	}

	public void setValidationGroupPrePersist(String newValidationGroupPrePersist) {
		String old = this.validationGroupPrePersist;
		this.validationGroupPrePersist = newValidationGroupPrePersist;
		this.putProperty(VALIDATION_GROUP_PRE_PERSIST_PROPERTY, newValidationGroupPrePersist);
		this.firePropertyChanged(VALIDATION_GROUP_PRE_PERSIST_PROPERTY, old, newValidationGroupPrePersist);
	}

	private void validationGroupPrePersistChanged(String newValue) {
		String old = this.validationGroupPrePersist;
		this.validationGroupPrePersist = newValue;
		this.firePropertyChanged(VALIDATION_GROUP_PRE_PERSIST_PROPERTY, old, newValue);
	}

	public String getDefaultValidationGroupPrePersist() {
		return DEFAULT_VALIDATION_GROUP_PRE_PERSIST;
	}

	// ********** ValidationGroupPreUpdate **********
	public String getValidationGroupPreUpdate() {
		return this.validationGroupPreUpdate;
	}

	public void setValidationGroupPreUpdate(String newValidationGroupPreUpdate) {
		String old = this.validationGroupPreUpdate;
		this.validationGroupPreUpdate = newValidationGroupPreUpdate;
		this.putProperty(VALIDATION_GROUP_PRE_UPDATE_PROPERTY, newValidationGroupPreUpdate);
		this.firePropertyChanged(VALIDATION_GROUP_PRE_UPDATE_PROPERTY, old, newValidationGroupPreUpdate);
	}

	private void validationGroupPreUpdateChanged(String newValue) {
		String old = this.validationGroupPreUpdate;
		this.validationGroupPreUpdate = newValue;
		this.firePropertyChanged(VALIDATION_GROUP_PRE_UPDATE_PROPERTY, old, newValue);
	}

	public String getDefaultValidationGroupPreUpdate() {
		return DEFAULT_VALIDATION_GROUP_PRE_UPDATE;
	}

	// ********** ValidationGroupPreRemove **********
	public String getValidationGroupPreRemove() {
		return this.validationGroupPreRemove;
	}

	public void setValidationGroupPreRemove(String newValidationGroupPreRemove) {
		String old = this.validationGroupPreRemove;
		this.validationGroupPreRemove = newValidationGroupPreRemove;
		this.putProperty(VALIDATION_GROUP_PRE_REMOVE_PROPERTY, newValidationGroupPreRemove);
		this.firePropertyChanged(VALIDATION_GROUP_PRE_REMOVE_PROPERTY, old, newValidationGroupPreRemove);
	}

	private void validationGroupPreRemoveChanged(String newValue) {
		String old = this.validationGroupPreRemove;
		this.validationGroupPreRemove = newValue;
		this.firePropertyChanged(VALIDATION_GROUP_PRE_REMOVE_PROPERTY, old, newValue);
	}

	public String getDefaultValidationGroupPreRemove() {
		return DEFAULT_VALIDATION_GROUP_PRE_REMOVE;
	}
	
}
