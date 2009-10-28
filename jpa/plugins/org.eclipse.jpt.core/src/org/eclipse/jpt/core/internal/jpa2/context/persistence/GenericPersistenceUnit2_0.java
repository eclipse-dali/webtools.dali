/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.persistence;

import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.internal.context.persistence.AbstractPersistenceUnit;
import org.eclipse.jpt.core.internal.jpa2.context.persistence.connection.GenericConnection2_0;
import org.eclipse.jpt.core.internal.jpa2.context.persistence.options.GenericOptions2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.connection.JpaConnection2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.options.JpaOptions2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.options.SharedCacheMode;
import org.eclipse.jpt.core.jpa2.context.persistence.options.ValidationMode;

/**
 * JPA 2.0 persistence-unit
 */
public class GenericPersistenceUnit2_0
	extends AbstractPersistenceUnit
	implements PersistenceUnit2_0
{
	protected SharedCacheMode specifiedSharedCacheMode;
	protected SharedCacheMode defaultSharedCacheMode;
	
	protected ValidationMode specifiedValidationMode;
	protected ValidationMode defaultValidationMode;
	
	private JpaConnection2_0 connection;
	private JpaOptions2_0 options;

	// ********** constructors **********
	public GenericPersistenceUnit2_0(Persistence parent, org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit xmlPersistenceUnit) {
		super(parent, xmlPersistenceUnit);

		this.specifiedSharedCacheMode = this.buildSpecifiedSharedCacheMode();
		this.defaultSharedCacheMode = this.buildDefaultSharedCacheMode();

		this.specifiedValidationMode = this.buildSpecifiedValidationMode();
		this.defaultValidationMode = this.buildDefaultValidationMode();
	}

	// ********** behavior **********

	protected SharedCacheMode buildSpecifiedSharedCacheMode() {
		return SharedCacheMode.fromXmlResourceModel(this.getXmlPersistenceUnit().getSharedCacheMode());
	}
	
	protected SharedCacheMode buildDefaultSharedCacheMode() {
		return SharedCacheMode.UNSPECIFIED;
	}

	protected ValidationMode buildSpecifiedValidationMode() {
		return ValidationMode.fromXmlResourceModel(this.getXmlPersistenceUnit().getValidationMode());
	}
	
	protected ValidationMode buildDefaultValidationMode() {
		return JpaOptions2_0.DEFAULT_VALIDATION_MODE; 
	}
	
	// ********** properties **********
	@Override
	protected void initializeProperties() {
		super.initializeProperties();
		
		this.connection = new GenericConnection2_0(this);
		this.options = new GenericOptions2_0(this);
	}
	
	@Override
	public void propertyValueChanged(String propertyName, String newValue) {
		super.propertyValueChanged(propertyName, newValue);
		this.connection.propertyValueChanged(propertyName, newValue);
		this.options.propertyValueChanged(propertyName, newValue);
	}
	
	@Override
	public void propertyRemoved(String propertyName) {
		super.propertyRemoved(propertyName);
		this.connection.propertyRemoved(propertyName);
		this.options.propertyRemoved(propertyName);
	}

	public JpaConnection2_0 getConnection() {
		return this.connection;
	}

	public JpaOptions2_0 getOptions() {
		return this.options;
	}

	// ********** updating **********

	@Override
	public void update(org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit xpu) {
		super.update(xpu);
		
		this.xmlPersistenceUnit = xpu;
		this.setSpecifiedSharedCacheMode(this.buildSpecifiedSharedCacheMode());
		this.setDefaultSharedCacheMode(this.buildDefaultSharedCacheMode());
		
		this.updateValidationMode();
	}
	
	private void updateValidationMode() {
		ValidationMode newValidationMode = this.buildSpecifiedValidationMode();

		// update with the property definition if the element is null, and discard the property
		if(newValidationMode == null && this.options.getValidationMode() != null) {
			newValidationMode = this.options.getValidationMode();
			this.options.removeValidationMode();
		}
		this.setSpecifiedValidationMode(newValidationMode);
	}


	// ********** shared cache mode **********

	public SharedCacheMode getSharedCacheMode() {
		return (this.specifiedSharedCacheMode != null) ? this.specifiedSharedCacheMode : this.defaultSharedCacheMode;
	}

	public SharedCacheMode getSpecifiedSharedCacheMode() {
		return this.specifiedSharedCacheMode;
	}

	public void setSpecifiedSharedCacheMode(SharedCacheMode specifiedSharedCacheMode) {
		SharedCacheMode old = this.specifiedSharedCacheMode;
		this.specifiedSharedCacheMode = specifiedSharedCacheMode;
		this.getXmlPersistenceUnit().setSharedCacheMode(SharedCacheMode.toXmlResourceModel(specifiedSharedCacheMode));
		this.firePropertyChanged(SPECIFIED_SHARED_CACHE_MODE_PROPERTY, old, specifiedSharedCacheMode);
	}

	public SharedCacheMode getDefaultSharedCacheMode() {
		return this.defaultSharedCacheMode;
	}

	protected void setDefaultSharedCacheMode(SharedCacheMode defaultSharedCacheMode) {
		SharedCacheMode old = this.defaultSharedCacheMode;
		this.defaultSharedCacheMode = defaultSharedCacheMode;
		this.firePropertyChanged(DEFAULT_SHARED_CACHE_MODE_PROPERTY, old, defaultSharedCacheMode);
	}
	
	public boolean calculateDefaultCacheable() {
		switch (getSharedCacheMode()) {
			case NONE:
			case ENABLE_SELECTIVE:
			case UNSPECIFIED:
				return false;
			case ALL:
			case DISABLE_SELECTIVE:
				return true;
		}
		return false;//null
	}
	
	
	// ********** validation mode **********

	public ValidationMode getValidationMode() {
		return (this.specifiedValidationMode != null) ? this.specifiedValidationMode : this.defaultValidationMode;
	}

	public ValidationMode getSpecifiedValidationMode() {
		return this.specifiedValidationMode;
	}

	public void setSpecifiedValidationMode(ValidationMode specifiedValidationMode) {
		ValidationMode old = this.specifiedValidationMode;
		this.specifiedValidationMode = specifiedValidationMode;
		this.getXmlPersistenceUnit().setValidationMode(ValidationMode.toXmlResourceModel(specifiedValidationMode));
		this.firePropertyChanged(SPECIFIED_VALIDATION_MODE_PROPERTY, old, specifiedValidationMode);
	}

	public ValidationMode getDefaultValidationMode() {
		return this.defaultValidationMode;
	}

	protected void setDefaultValidationMode(ValidationMode defaultValidationMode) {
		ValidationMode old = this.defaultValidationMode;
		this.defaultValidationMode = defaultValidationMode;
		this.firePropertyChanged(DEFAULT_VALIDATION_MODE_PROPERTY, old, defaultValidationMode);
	}
}
