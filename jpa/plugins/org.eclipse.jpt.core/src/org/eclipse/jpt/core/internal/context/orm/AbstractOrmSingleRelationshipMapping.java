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

import java.util.List;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.Nullable;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmSingleRelationshipMapping;
import org.eclipse.jpt.core.jpa2.context.orm.OrmDerivedIdentity2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmXml2_0ContextNodeFactory;
import org.eclipse.jpt.core.resource.orm.AbstractXmlSingleRelationshipMapping;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlSingleRelationshipMapping_2_0;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public abstract class AbstractOrmSingleRelationshipMapping<T extends AbstractXmlSingleRelationshipMapping>
	extends AbstractOrmRelationshipMapping<T>
	implements OrmSingleRelationshipMapping2_0
{
	protected Boolean specifiedOptional;
	
	protected final OrmDerivedIdentity2_0 derivedIdentity;
	
	
	protected AbstractOrmSingleRelationshipMapping(OrmPersistentAttribute parent, T resourceMapping) {
		super(parent, resourceMapping);
		this.specifiedOptional = this.getResourceOptional();
		//TODO defaultOptional
		this.derivedIdentity = buildDerivedIdentity();
	}
	
	
	@Override
	public void initializeFromOrmSingleRelationshipMapping(OrmSingleRelationshipMapping oldMapping) {
		super.initializeFromOrmSingleRelationshipMapping(oldMapping);
		getDerivedIdentity().initializeFrom(((OrmSingleRelationshipMapping2_0) oldMapping).getDerivedIdentity());
	}
	
	@Override
	protected String getResourceDefaultTargetEntity() {
		return this.getJavaPersistentAttribute().getSingleReferenceTargetTypeName();
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
	
	
	// ********** 2.0 derived identity **********
	
	protected OrmDerivedIdentity2_0 buildDerivedIdentity() {
		return ((OrmXml2_0ContextNodeFactory) getXmlContextNodeFactory()).
				buildOrmDerivedIdentity(this, (XmlSingleRelationshipMapping_2_0) this.resourceAttributeMapping);
	}
	
	public OrmDerivedIdentity2_0 getDerivedIdentity() {
		return this.derivedIdentity;
	}
	
	@Override
	public boolean isIdMapping() {
		return this.derivedIdentity.usesIdDerivedIdentityStrategy();
	}
	
	
	// **************** resource => context ************************************
	
	@Override
	public void update() {
		super.update();
		this.setSpecifiedOptional_(this.getResourceOptional());
		this.derivedIdentity.update();
	}
	
	protected Boolean getResourceOptional() {
		return this.resourceAttributeMapping.getOptional();
	}
	
	
	// **************** validation ************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.derivedIdentity.validate(messages, reporter);
	}
}
