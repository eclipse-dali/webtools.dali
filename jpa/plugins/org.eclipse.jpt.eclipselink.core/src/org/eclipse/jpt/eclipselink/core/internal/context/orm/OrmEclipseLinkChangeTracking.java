/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkChangeTrackingType;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaTypeMapping;
import org.eclipse.jpt.eclipselink.core.context.orm.EclipseLinkOrmTypeMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTracking;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTrackingHolder;

public class OrmEclipseLinkChangeTracking
	extends AbstractOrmXmlContextNode
	implements EclipseLinkChangeTracking
{
	protected EclipseLinkChangeTrackingType specifiedType;
	protected EclipseLinkChangeTrackingType defaultType;


	public OrmEclipseLinkChangeTracking(EclipseLinkOrmTypeMapping parent) {
		super(parent);
		this.specifiedType = this.buildSpecifiedType();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedType_(this.buildSpecifiedType());
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultType(this.buildDefaultType());
	}


	// ********** type **********

	public EclipseLinkChangeTrackingType getType() {
		return (this.specifiedType != null) ? this.specifiedType : this.defaultType;
	}

	public EclipseLinkChangeTrackingType getSpecifiedType() {
		return this.specifiedType;
	}

	public void setSpecifiedType(EclipseLinkChangeTrackingType type) {
		if (this.specifiedType != type) {
			XmlChangeTracking xmlChangeTracking = this.getXmlChangeTrackingForUpdate();
			this.setSpecifiedType_(type);
			xmlChangeTracking.setType(EclipseLinkChangeTrackingType.toOrmResourceModel(type));
			this.removeXmlChangeTrackingIfUnset();
		}
	}

	protected void setSpecifiedType_(EclipseLinkChangeTrackingType type) {
		EclipseLinkChangeTrackingType old = this.specifiedType;
		this.specifiedType = type;
		this.firePropertyChanged(SPECIFIED_TYPE_PROPERTY, old, type);
	}

	protected EclipseLinkChangeTrackingType buildSpecifiedType() {
		XmlChangeTracking xmlChangeTracking = this.getXmlChangeTracking();
		return (xmlChangeTracking == null) ? null : EclipseLinkChangeTrackingType.fromOrmResourceModel(xmlChangeTracking.getType());
	}

	public EclipseLinkChangeTrackingType getDefaultType() {
		return this.defaultType;
	}

	protected void setDefaultType(EclipseLinkChangeTrackingType type) {
		EclipseLinkChangeTrackingType old = this.defaultType;
		this.defaultType = type;
		this.firePropertyChanged(DEFAULT_TYPE_PROPERTY, old, type);
	}

	protected EclipseLinkChangeTrackingType buildDefaultType() {
		EclipseLinkChangeTracking javaChangeTracking = this.getJavaChangeTrackingForDefaults();
		return (javaChangeTracking != null) ? javaChangeTracking.getType() : DEFAULT_TYPE;
	}


	// ********** xml customizer class ref **********

	/**
	 * Return null if the XML change tracking does not exists.
	 */
	protected XmlChangeTracking getXmlChangeTracking() {
		return this.getXmlChangeTrackingHolder().getChangeTracking();
	}

	/**
	 * Build the XML change tracking if it does not exist.
	 */
	protected XmlChangeTracking getXmlChangeTrackingForUpdate() {
		XmlChangeTracking xmlChangeTracking = this.getXmlChangeTracking();
		return (xmlChangeTracking != null) ? xmlChangeTracking : this.buildXmlChangeTracking();
	}

	protected XmlChangeTracking buildXmlChangeTracking() {
		XmlChangeTracking xmlChangeTracking = EclipseLinkOrmFactory.eINSTANCE.createXmlChangeTracking();
		this.getXmlChangeTrackingHolder().setChangeTracking(xmlChangeTracking);
		return xmlChangeTracking;
	}

	protected void removeXmlChangeTrackingIfUnset() {
		if (this.getXmlChangeTracking().isUnset()) {
			this.removeXmlChangeTracking();
		}
	}

	protected void removeXmlChangeTracking() {
		this.getXmlChangeTrackingHolder().setChangeTracking(null);
	}


	// ********** misc **********

	@Override
	public EclipseLinkOrmTypeMapping getParent() {
		return (EclipseLinkOrmTypeMapping) super.getParent();
	}

	protected EclipseLinkOrmTypeMapping getTypeMapping() {
		return this.getParent();
	}

	protected XmlTypeMapping getXmlTypeMapping() {
		return this.getTypeMapping().getXmlTypeMapping();
	}

	protected XmlChangeTrackingHolder getXmlChangeTrackingHolder() {
		return (XmlChangeTrackingHolder) this.getXmlTypeMapping();
	}

	protected EclipseLinkJavaTypeMapping getJavaTypeMappingForDefaults() {
		return this.getTypeMapping().getJavaTypeMappingForDefaults();
	}

	protected EclipseLinkChangeTracking getJavaChangeTrackingForDefaults() {
		EclipseLinkJavaTypeMapping javaTypeMapping = this.getJavaTypeMappingForDefaults();
		return (javaTypeMapping == null) ? null : javaTypeMapping.getChangeTracking();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.getXmlChangeTrackingHolder().getValidationTextRange();
	}
}
