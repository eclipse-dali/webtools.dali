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
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.ChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.ChangeTrackingType;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTracking;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTrackingHolder;

public class EclipseLinkOrmChangeTracking extends AbstractXmlContextNode
	implements ChangeTracking
{
	protected final XmlChangeTrackingHolder resource;
	
	protected ChangeTrackingType defaultType;
	
	protected ChangeTrackingType specifiedType;
	
	
	public EclipseLinkOrmChangeTracking(OrmTypeMapping parent, XmlChangeTrackingHolder resource, ChangeTracking javaChangeTracking) {
		super(parent);
		this.resource = resource;
		this.defaultType = calculateDefaultType(javaChangeTracking);
		this.specifiedType = getResourceChangeTracking();
	}
	
	
	public ChangeTrackingType getType() {
		return (getSpecifiedType() != null) ? getSpecifiedType() : getDefaultType();
	}
	
	public ChangeTrackingType getDefaultType() {
		return this.defaultType;
	}
	
	protected void setDefaultType_(ChangeTrackingType newDefaultType) {
		ChangeTrackingType oldDefaultType = this.defaultType;
		this.defaultType = newDefaultType;
		firePropertyChanged(DEFAULT_TYPE_PROPERTY, oldDefaultType, newDefaultType);
	}
	
	public ChangeTrackingType getSpecifiedType() {
		return this.specifiedType;
	}
	
	public void setSpecifiedType(ChangeTrackingType newSpecifiedType) {
		ChangeTrackingType oldSpecifiedType = this.specifiedType;
		this.specifiedType = newSpecifiedType;
		
		if (newSpecifiedType == null) {
			this.resource.setChangeTracking(null);
		}
		else {
			if (this.resource.getChangeTracking() == null) {
				this.resource.setChangeTracking(EclipseLinkOrmFactory.eINSTANCE.createXmlChangeTracking());
			}
			this.resource.getChangeTracking().setType(ChangeTrackingType.toOrmResourceModel(newSpecifiedType));
		}
		
		firePropertyChanged(SPECIFIED_TYPE_PROPERTY, oldSpecifiedType, newSpecifiedType);
	}
	
	protected void setSpecifiedType_(ChangeTrackingType newSpecifiedType) {
		ChangeTrackingType oldSpecifiedType = this.specifiedType;
		this.specifiedType = newSpecifiedType;
		firePropertyChanged(SPECIFIED_TYPE_PROPERTY, oldSpecifiedType, newSpecifiedType);
	}
	
	
	// **************** updating **************************************
	
	protected void update(ChangeTracking javaChangeTracking) {
		setDefaultType_(calculateDefaultType(javaChangeTracking));
		setSpecifiedType_(getResourceChangeTracking());
	}
	
	protected ChangeTrackingType calculateDefaultType(ChangeTracking javaChangeTracking) {
		return (javaChangeTracking != null) ? javaChangeTracking.getType() : ChangeTracking.DEFAULT_TYPE;
	}
	
	protected ChangeTrackingType getResourceChangeTracking() {
		XmlChangeTracking xmlChangeTracking = this.resource.getChangeTracking();
		return (xmlChangeTracking != null) ? ChangeTrackingType.fromOrmResourceModel(xmlChangeTracking.getType()) : null;
	}
	
	
	// **************** validation **************************************
	
	public TextRange getValidationTextRange() {
		return this.resource.getValidationTextRange();
	}
}
