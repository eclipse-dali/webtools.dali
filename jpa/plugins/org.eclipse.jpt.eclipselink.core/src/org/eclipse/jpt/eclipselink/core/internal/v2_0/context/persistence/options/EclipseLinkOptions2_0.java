/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_0.context.persistence.options;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.options.EclipseLinkOptions;
import org.eclipse.jpt.eclipselink.core.v2_0.context.persistence.options.Options2_0;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 *  EclipseLinkOptions2_0
 */
public class EclipseLinkOptions2_0 extends EclipseLinkOptions
	implements Options2_0
{
	// ********** GenericConnection properties **********
	private Integer lockTimeout;
	private Integer queryTimeout;
	private List<String> validationGroupPrePersists;
	private List<String> validationGroupPreUpdates;
	private List<String> validationGroupPreRemoves;

	// ********** constructors **********
	public EclipseLinkOptions2_0(PersistenceUnit2_0 parent) {
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

		// ValidationMode is initialized with the persistence unit element
		this.validationGroupPrePersists = this.getCompositeValue(PERSISTENCE_VALIDATION_GROUP_PRE_PERSIST);
		this.validationGroupPreUpdates = this.getCompositeValue(PERSISTENCE_VALIDATION_GROUP_PRE_UPDATE);
		this.validationGroupPreRemoves = this.getCompositeValue(PERSISTENCE_VALIDATION_GROUP_PRE_REMOVE);
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
			this.validationGroupPrePersistsChanged();
		}
		else if (propertyName.equals(PERSISTENCE_VALIDATION_GROUP_PRE_UPDATE)) {
			this.validationGroupPreUpdatesChanged();
		}
		else if (propertyName.equals(PERSISTENCE_VALIDATION_GROUP_PRE_REMOVE)) {
			this.validationGroupPreRemovesChanged();
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
			this.validationGroupPrePersistsChanged();
		}
		else if (propertyName.equals(PERSISTENCE_VALIDATION_GROUP_PRE_UPDATE)) {
			this.validationGroupPreUpdatesChanged();
		}
		else if (propertyName.equals(PERSISTENCE_VALIDATION_GROUP_PRE_REMOVE)) {
			this.validationGroupPreRemovesChanged();
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

	@Override
	public PersistenceUnit2_0 getPersistenceUnit() {
		return (PersistenceUnit2_0) super.getPersistenceUnit();
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

	
	// ********** ValidationGroupPrePersists **********

	public ListIterator<String> validationGroupPrePersists(){
		return new CloneListIterator<String>(this.validationGroupPrePersists);
	}
	
	public int validationGroupPrePersistsSize(){
		return this.validationGroupPrePersists.size();
	}

	public boolean validationGroupPrePersistExists(String validationGroupPrePersistClassName) {

		for (String validationGroupPrePersist : this.validationGroupPrePersists) {
			if(validationGroupPrePersist.equals(validationGroupPrePersistClassName)) {
				return true;
			}
		}
		return false;
	}

	public String addValidationGroupPrePersist(String newPrePersistClassName){

		if( ! this.validationGroupPrePersistExists(newPrePersistClassName)) {
			this.validationGroupPrePersists.add(newPrePersistClassName);
			this.putPropertyCompositeValue(VALIDATION_GROUP_PRE_PERSIST_PROPERTY, newPrePersistClassName);
			this.fireListChanged(VALIDATION_GROUP_PRE_PERSIST_LIST, this.validationGroupPrePersists);
			return newPrePersistClassName;
		}
		return null;
	}
	
	public void removeValidationGroupPrePersist(String className){

		if(this.removeValidationGroupPrePersist_(className) != null) {
			this.removePropertyCompositeValue(VALIDATION_GROUP_PRE_PERSIST_PROPERTY, className);
			this.fireListChanged(VALIDATION_GROUP_PRE_PERSIST_LIST, this.validationGroupPrePersists);
		}
	}
	
	private String removeValidationGroupPrePersist_(String className){

		for ( ListIterator<String> i = this.validationGroupPrePersists(); i.hasNext();) {
			String validationGroupPrePersist = i.next();
			if(validationGroupPrePersist.equals(className)) {
				this.validationGroupPrePersists.remove(validationGroupPrePersist);
				return validationGroupPrePersist;
			}
		}
		return null;
	}

	private void validationGroupPrePersistsChanged() {
		this.validationGroupPrePersists = this.getCompositeValue(PERSISTENCE_VALIDATION_GROUP_PRE_PERSIST);
		this.fireListChanged(VALIDATION_GROUP_PRE_PERSIST_LIST, this.validationGroupPrePersists);
	}

	// ********** ValidationGroupPreUpdates **********

	public ListIterator<String> validationGroupPreUpdates(){
		return new CloneListIterator<String>(this.validationGroupPreUpdates);
	}
	
	public int validationGroupPreUpdatesSize(){
		return this.validationGroupPreUpdates.size();
	}

	public boolean validationGroupPreUpdateExists(String validationGroupPreUpdateClassName) {

		for (String validationGroupPreUpdate : this.validationGroupPreUpdates) {
			if(validationGroupPreUpdate.equals(validationGroupPreUpdateClassName)) {
				return true;
			}
		}
		return false;
	}

	public String addValidationGroupPreUpdate(String newPreUpdateClassName){

		if( ! this.validationGroupPreUpdateExists(newPreUpdateClassName)) {
			this.validationGroupPreUpdates.add(newPreUpdateClassName);
			this.putPropertyCompositeValue(VALIDATION_GROUP_PRE_UPDATE_PROPERTY, newPreUpdateClassName);
			this.fireListChanged(VALIDATION_GROUP_PRE_UPDATE_LIST, this.validationGroupPreUpdates);
			return newPreUpdateClassName;
		}
		return null;
	}
	
	public void removeValidationGroupPreUpdate(String className){

		if(this.removeValidationGroupPreUpdate_(className) != null) {
			this.removePropertyCompositeValue(VALIDATION_GROUP_PRE_UPDATE_PROPERTY, className);
			this.fireListChanged(VALIDATION_GROUP_PRE_UPDATE_LIST, this.validationGroupPreUpdates);
		}
	}
	
	private String removeValidationGroupPreUpdate_(String className){

		for(ListIterator<String> i = this.validationGroupPreUpdates(); i.hasNext();) {
			String validationGroupPreUpdate = i.next();
			if(validationGroupPreUpdate.equals(className)) {
				this.validationGroupPreUpdates.remove(validationGroupPreUpdate);
				return validationGroupPreUpdate;
			}
		}
		return null;
	}

	private void validationGroupPreUpdatesChanged() {
		this.validationGroupPreUpdates = this.getCompositeValue(PERSISTENCE_VALIDATION_GROUP_PRE_UPDATE);
		this.fireListChanged(VALIDATION_GROUP_PRE_UPDATE_LIST, this.validationGroupPreUpdates);
	}

	// ********** ValidationGroupPreRemoves **********

	public ListIterator<String> validationGroupPreRemoves(){
		return new CloneListIterator<String>(this.validationGroupPreRemoves);
	}
	
	public int validationGroupPreRemovesSize(){
		return this.validationGroupPreRemoves.size();
	}

	public boolean validationGroupPreRemoveExists(String validationGroupPreRemoveClassName) {

		for (String validationGroupPreRemove : this.validationGroupPreRemoves) {
			if(validationGroupPreRemove.equals(validationGroupPreRemoveClassName)) {
				return true;
			}
		}
		return false;
	}

	public String addValidationGroupPreRemove(String newPreRemoveClassName){

		if( ! this.validationGroupPreRemoveExists(newPreRemoveClassName)) {
			this.validationGroupPreRemoves.add(newPreRemoveClassName);
			this.putPropertyCompositeValue(VALIDATION_GROUP_PRE_REMOVE_PROPERTY, newPreRemoveClassName);
			this.fireListChanged(VALIDATION_GROUP_PRE_REMOVE_LIST, this.validationGroupPreRemoves);
			return newPreRemoveClassName;
		}
		return null;
	}
	
	public void removeValidationGroupPreRemove(String className){

		if(this.removeValidationGroupPreRemove_(className) != null) {
			this.removePropertyCompositeValue(VALIDATION_GROUP_PRE_REMOVE_PROPERTY, className);
			this.fireListChanged(VALIDATION_GROUP_PRE_REMOVE_LIST, this.validationGroupPreRemoves);
		}
	}
	
	private String removeValidationGroupPreRemove_(String className){

		for(ListIterator<String> i = this.validationGroupPreRemoves(); i.hasNext();) {
			String validationGroupPreRemove = i.next();
			if(validationGroupPreRemove.equals(className)) {
				this.validationGroupPreRemoves.remove(validationGroupPreRemove);
				return validationGroupPreRemove;
			}
		}
		return null;
	}

	private void validationGroupPreRemovesChanged() {
		this.validationGroupPreRemoves = this.getCompositeValue(PERSISTENCE_VALIDATION_GROUP_PRE_REMOVE);
		this.fireListChanged(VALIDATION_GROUP_PRE_REMOVE_LIST, this.validationGroupPreRemoves);
	}

}
