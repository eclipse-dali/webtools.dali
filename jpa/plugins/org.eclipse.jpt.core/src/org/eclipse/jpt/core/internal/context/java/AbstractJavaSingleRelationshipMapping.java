/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.Nullable;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaSingleRelationshipMapping;
import org.eclipse.jpt.core.resource.java.RelationshipMappingAnnotation;


public abstract class AbstractJavaSingleRelationshipMapping<T extends RelationshipMappingAnnotation>
	extends AbstractJavaRelationshipMapping<T>
	implements JavaSingleRelationshipMapping
{
	protected Boolean specifiedOptional;


	protected AbstractJavaSingleRelationshipMapping(JavaPersistentAttribute parent) {
		super(parent);
	}
	
	
	@Override
	public boolean isOverridableAssociationMapping() {
		return true;
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
		this.setOptionalOnResourceModel(optional);
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
	
	protected abstract void setOptionalOnResourceModel(Boolean newOptional);


	// **************** resource => context ************************************

	@Override
	protected void initialize() {
		super.initialize();
		this.specifiedOptional = this.getResourceOptional();
	}
	
	@Override
	protected void update() {
		super.update();
		this.setSpecifiedOptional_(this.getResourceOptional());
	}
	
	protected abstract Boolean getResourceOptional();
	
	@Override
	protected String buildDefaultTargetEntity() {
		return this.getPersistentAttribute().getSingleReferenceEntityTypeName();
	}


	// **************** Fetchable implementation *******************************
	
	public FetchType getDefaultFetch() {
		return DEFAULT_FETCH_TYPE;
	}
}
