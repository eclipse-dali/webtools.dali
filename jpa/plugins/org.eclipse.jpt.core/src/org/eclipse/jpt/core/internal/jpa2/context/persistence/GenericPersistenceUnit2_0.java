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

import java.util.Iterator;

import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.JarFileRef;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.internal.context.persistence.AbstractPersistenceUnit;
import org.eclipse.jpt.core.internal.jpa2.context.persistence.connection.GenericConnection2_0;
import org.eclipse.jpt.core.internal.jpa2.context.persistence.options.GenericOptions2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.ClassRef2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.JarFileRef2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.MappingFileRef2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.Persistence2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.connection.JpaConnection2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.options.JpaOptions2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.options.ValidationMode;
import org.eclipse.jpt.core.jpa2.resource.persistence.XmlPersistenceUnit;

/**
 * JPA 2.0 persistence-unit
 */
public class GenericPersistenceUnit2_0
	extends AbstractPersistenceUnit
	implements PersistenceUnit2_0
{
	protected ValidationMode specifiedValidationMode;
	protected ValidationMode defaultValidationMode;
	
	private JpaConnection2_0 connection;
	private JpaOptions2_0 options;

	// ********** constructors **********
	public GenericPersistenceUnit2_0(Persistence2_0 parent, org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit xmlPersistenceUnit) {
		super(parent, xmlPersistenceUnit);

		this.defaultValidationMode = this.buildDefaultValidationMode();
	}

	// ********** behavior **********

	protected ValidationMode buildSpecifiedValidationMode() {
		return ValidationMode.fromXmlResourceModel(this.getXmlPersistenceUnit().getValidationMode());
	}
	
	protected ValidationMode buildDefaultValidationMode() {
		return JpaOptions2_0.DEFAULT_VALIDATION_MODE; 
	}

	@Override
	protected XmlPersistenceUnit getXmlPersistenceUnit() {
		return (XmlPersistenceUnit) this.xmlPersistenceUnit;
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
	
	// ********** properties **********
	@Override
	protected void initializeProperties() {
		super.initializeProperties();

		// ValidationMode may be specified with an element or a property
		// the element need to be initialized first.
		this.specifiedValidationMode = this.buildSpecifiedValidationMode();
		
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
		this.setSpecifiedValidationMode(this.buildSpecifiedValidationMode());
	}

	// ********** JPA 2.0 Static Metamodel **********

	public void synchronizeStaticMetamodel() {
		for (Iterator<MappingFileRef> stream = this.mappingFileRefs(); stream.hasNext(); ) {
			((MappingFileRef2_0) stream.next()).synchronizeStaticMetamodel();
		}
		for (Iterator<ClassRef> stream = this.classRefs(); stream.hasNext(); ) {
			((ClassRef2_0) stream.next()).synchronizeStaticMetamodel();
		}
		for (Iterator<JarFileRef> stream = this.jarFileRefs(); stream.hasNext(); ) {
			((JarFileRef2_0) stream.next()).synchronizeStaticMetamodel();
		}
	}

}
