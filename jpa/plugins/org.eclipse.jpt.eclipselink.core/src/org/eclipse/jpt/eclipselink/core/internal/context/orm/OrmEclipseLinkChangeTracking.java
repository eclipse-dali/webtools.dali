/*******************************************************************************
 *  Copyright (c) 2008, 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkChangeTrackingType;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTracking;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTrackingHolder;

public class OrmEclipseLinkChangeTracking extends AbstractOrmXmlContextNode
	implements EclipseLinkChangeTracking
{
	protected final XmlChangeTrackingHolder resource;
	
	protected EclipseLinkChangeTrackingType defaultType;
	
	protected EclipseLinkChangeTrackingType specifiedType;
	
	
	public OrmEclipseLinkChangeTracking(OrmTypeMapping parent, XmlChangeTrackingHolder resource, EclipseLinkChangeTracking javaChangeTracking) {
		super(parent);
		this.resource = resource;
		this.defaultType = calculateDefaultType(javaChangeTracking);
		this.specifiedType = getResourceChangeTracking();
	}
	
	
	public EclipseLinkChangeTrackingType getType() {
		return (getSpecifiedType() != null) ? getSpecifiedType() : getDefaultType();
	}
	
	public EclipseLinkChangeTrackingType getDefaultType() {
		return this.defaultType;
	}
	
	protected void setDefaultType_(EclipseLinkChangeTrackingType newDefaultType) {
		EclipseLinkChangeTrackingType oldDefaultType = this.defaultType;
		this.defaultType = newDefaultType;
		firePropertyChanged(DEFAULT_TYPE_PROPERTY, oldDefaultType, newDefaultType);
	}
	
	public EclipseLinkChangeTrackingType getSpecifiedType() {
		return this.specifiedType;
	}
	
	public void setSpecifiedType(EclipseLinkChangeTrackingType newSpecifiedType) {
		EclipseLinkChangeTrackingType oldSpecifiedType = this.specifiedType;
		this.specifiedType = newSpecifiedType;
		
		if (newSpecifiedType == null) {
			this.resource.setChangeTracking(null);
		}
		else {
			if (this.resource.getChangeTracking() == null) {
				this.resource.setChangeTracking(EclipseLinkOrmFactory.eINSTANCE.createXmlChangeTracking());
			}
			this.resource.getChangeTracking().setType(EclipseLinkChangeTrackingType.toOrmResourceModel(newSpecifiedType));
		}
		
		firePropertyChanged(SPECIFIED_TYPE_PROPERTY, oldSpecifiedType, newSpecifiedType);
	}
	
	protected void setSpecifiedType_(EclipseLinkChangeTrackingType newSpecifiedType) {
		EclipseLinkChangeTrackingType oldSpecifiedType = this.specifiedType;
		this.specifiedType = newSpecifiedType;
		firePropertyChanged(SPECIFIED_TYPE_PROPERTY, oldSpecifiedType, newSpecifiedType);
	}
	
	
	// **************** updating **************************************
	
	protected void update(EclipseLinkChangeTracking javaChangeTracking) {
		setDefaultType_(calculateDefaultType(javaChangeTracking));
		setSpecifiedType_(getResourceChangeTracking());
	}
	
	protected EclipseLinkChangeTrackingType calculateDefaultType(EclipseLinkChangeTracking javaChangeTracking) {
		return (javaChangeTracking != null) ? javaChangeTracking.getType() : EclipseLinkChangeTracking.DEFAULT_TYPE;
	}
	
	protected EclipseLinkChangeTrackingType getResourceChangeTracking() {
		XmlChangeTracking xmlChangeTracking = this.resource.getChangeTracking();
		return (xmlChangeTracking != null) ? EclipseLinkChangeTrackingType.fromOrmResourceModel(xmlChangeTracking.getType()) : null;
	}
	
	
	// **************** validation **************************************
	
	public TextRange getValidationTextRange() {
		return this.resource.getValidationTextRange();
	}
}
