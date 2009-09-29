/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_0.context.persistence;

import java.util.Iterator;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.JarFileRef;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.jpa2.context.persistence.ClassRef2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.JarFileRef2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.MappingFileRef2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.options.ValidationMode;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.internal.v2_0.context.persistence.options.EclipseLinkOptions2_0;
import org.eclipse.jpt.eclipselink.core.v2_0.context.persistence.connection.Connection2_0;
import org.eclipse.jpt.eclipselink.core.v2_0.context.persistence.options.Options2_0;

/**
 *  EclipseLinkPersistenceUnit2_0
 */
public class EclipseLinkPersistenceUnit2_0
	extends EclipseLinkPersistenceUnit
	implements PersistenceUnit2_0
{
	protected ValidationMode specifiedValidationMode;
	protected ValidationMode defaultValidationMode;

	public EclipseLinkPersistenceUnit2_0(Persistence parent, org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit xmlPersistenceUnit) {
		super(parent, xmlPersistenceUnit);

		this.specifiedValidationMode = this.buildSpecifiedValidationMode();
		this.defaultValidationMode = this.buildDefaultValidationMode();
	}

	// ********** behavior **********

	protected ValidationMode buildSpecifiedValidationMode() {
		return ValidationMode.fromXmlResourceModel(this.getXmlPersistenceUnit().getValidationMode());
	}
	
	protected ValidationMode buildDefaultValidationMode() {
		return Options2_0.DEFAULT_VALIDATION_MODE; 
	}

	@Override
	public XmlPersistenceUnit getXmlPersistenceUnit() {
		return (XmlPersistenceUnit) this.xmlPersistenceUnit;
	}
	
	// **************** factory methods *********************************************

	@Override
	protected Options2_0 buildEclipseLinkOptions() {
		return new EclipseLinkOptions2_0(this);
	}

	// **************** properties *********************************************

	@Override
	public Connection2_0 getConnection() {
		return (Connection2_0) super.getConnection();
	}
	
	@Override
	public Options2_0 getOptions() {
		return (Options2_0) this.options;
	}

	// ********** updating **********

	@Override
	public void update(org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit xpu) {
		super.update(xpu);
		
		this.xmlPersistenceUnit = xpu;
		this.updateValidationMode();
	}
	
	private void updateValidationMode() {
		ValidationMode newValidationMode = this.buildSpecifiedValidationMode();

		// update with the property definition if the element is null, and discard the property
		if(newValidationMode == null && this.getOptions().getValidationMode() != null) {
			newValidationMode = this.getOptions().getValidationMode();
			this.getOptions().removeValidationMode();
		}
		this.setSpecifiedValidationMode(newValidationMode);
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
