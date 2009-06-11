/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.Nullable;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmSingleRelationshipMapping;
import org.eclipse.jpt.core.resource.orm.AbstractXmlSingleRelationshipMapping;


public abstract class AbstractOrmSingleRelationshipMapping<T extends AbstractXmlSingleRelationshipMapping>
	extends AbstractOrmRelationshipMapping<T>
	implements OrmSingleRelationshipMapping
{
	protected Boolean specifiedOptional;
	
	
	protected AbstractOrmSingleRelationshipMapping(OrmPersistentAttribute parent, T resourceMapping) {
		super(parent, resourceMapping);
		this.specifiedOptional = this.getResourceOptional();
		//TODO defaultOptional
	}
	
	
	@Override
	protected String getResourceDefaultTargetEntity() {
		return this.getJavaPersistentAttribute().getSingleReferenceEntityTypeName();
	}
	
	public FetchType getDefaultFetch() {
		return DEFAULT_FETCH_TYPE;
	}
	
	
	// **************** optional ***********************************************

	public boolean isOptional() {
		return (this.specifiedOptional != null) ? this.specifiedOptional.booleanValue() : this.isDefaultOptional();
	}
	
	public Boolean getSpecifiedOptional() {
		return this.specifiedOptional;
	}
	
	public void setSpecifiedOptional(Boolean optional) {
		Boolean old = this.specifiedOptional;
		this.specifiedOptional = optional;
		this.resourceAttributeMapping.setOptional(optional);
		this.firePropertyChanged(Nullable.SPECIFIED_OPTIONAL_PROPERTY, old, optional);
	}
	
	protected void setSpecifiedOptional_(Boolean optional) {
		Boolean old = this.specifiedOptional;
		this.specifiedOptional = optional;
		this.firePropertyChanged(Nullable.SPECIFIED_OPTIONAL_PROPERTY, old, optional);
	}
	
	public boolean isDefaultOptional() {
		return Nullable.DEFAULT_OPTIONAL;
	}


	// **************** resource => context ************************************
	
	@Override
	public void update() {
		super.update();
		this.setSpecifiedOptional_(this.getResourceOptional());
	}
	
	protected Boolean getResourceOptional() {
		return this.resourceAttributeMapping.getOptional();
	}
}
