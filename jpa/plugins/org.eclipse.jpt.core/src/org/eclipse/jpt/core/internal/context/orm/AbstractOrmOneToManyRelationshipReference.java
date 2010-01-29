/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.List;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinTable;
import org.eclipse.jpt.core.context.orm.OrmJoinTableEnabledRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmJoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmMappedByJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToManyRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmOwnableRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmRelationshipReference;
import org.eclipse.jpt.core.resource.orm.XmlOneToMany;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractOrmOneToManyRelationshipReference
	extends AbstractOrmRelationshipReference
	implements OrmOneToManyRelationshipReference
{
	protected OrmMappedByJoiningStrategy mappedByJoiningStrategy;
	
	protected OrmJoinTableJoiningStrategy joinTableJoiningStrategy;
	
	
	protected AbstractOrmOneToManyRelationshipReference(
			OrmOneToManyMapping parent, XmlOneToMany resource) {
		super(parent, resource);
	}
	
	
	@Override
	protected void initializeJoiningStrategies() {
		this.mappedByJoiningStrategy = buildMappedByJoiningStrategy();
		
		// initialize join table last, as the existence of a default join 
		// table is dependent on the other mechanisms (mappedBy)
		// not being specified
		this.joinTableJoiningStrategy = buildJoinTableJoiningStrategy();
	}
	
	protected OrmMappedByJoiningStrategy buildMappedByJoiningStrategy() {
		return new GenericOrmMappedByJoiningStrategy(this, getResourceMapping());
	}
	
	protected OrmJoinTableJoiningStrategy buildJoinTableJoiningStrategy() {
		return 	new GenericOrmJoinTableJoiningStrategy(this, getResourceMapping());
	}
	
	public void initializeOn(OrmRelationshipReference newRelationshipReference) {
		newRelationshipReference.initializeFromOwnableRelationshipReference(this);
		newRelationshipReference.initializeFromJoinTableEnabledRelationshipReference(this);
	}
	
	@Override
	public void initializeFromOwnableRelationshipReference(
			OrmOwnableRelationshipReference oldRelationshipReference) {
		super.initializeFromOwnableRelationshipReference(oldRelationshipReference);
		this.mappedByJoiningStrategy.setMappedByAttribute(
			oldRelationshipReference.getMappedByJoiningStrategy().getMappedByAttribute());
	}
	
	@Override
	public void initializeFromJoinTableEnabledRelationshipReference(
			OrmJoinTableEnabledRelationshipReference oldRelationshipReference) {
		super.initializeFromJoinTableEnabledRelationshipReference(oldRelationshipReference);
		OrmJoinTable oldTable = 
			oldRelationshipReference.getJoinTableJoiningStrategy().getJoinTable();
		if (oldTable != null) {
			this.joinTableJoiningStrategy.addJoinTable().initializeFrom(oldTable);
		}
	}
	
	@Override
	public OrmOneToManyMapping getRelationshipMapping() {
		return (OrmOneToManyMapping) getParent();
	}
	
	public XmlOneToMany getResourceMapping() {
		return getRelationshipMapping().getResourceAttributeMapping();
	}
	
	public boolean isRelationshipOwner() {
		return this.getMappedByJoiningStrategy().getMappedByAttribute() == null;
	}
	
	public boolean isOwnedBy(RelationshipMapping mapping) {
		return this.mappedByJoiningStrategy.relationshipIsOwnedBy(mapping);
	}
	
	
	// **************** mapped by **********************************************
	
	public OrmMappedByJoiningStrategy getMappedByJoiningStrategy() {
		return this.mappedByJoiningStrategy;
	}
	
	public final void setMappedByJoiningStrategy() {
		setMappedByJoiningStrategy_();
		setPredominantJoiningStrategy();
	}
	
	protected void setMappedByJoiningStrategy_() {
		this.mappedByJoiningStrategy.addStrategy();
		this.joinTableJoiningStrategy.removeStrategy();
	}
	
	public final void unsetMappedByJoiningStrategy() {
		unsetMappedByJoiningStrategy_();
		setPredominantJoiningStrategy();
	}
	
	protected void unsetMappedByJoiningStrategy_() {
		this.mappedByJoiningStrategy.removeStrategy();
	}
	
	public boolean usesMappedByJoiningStrategy() {
		return this.getPredominantJoiningStrategy() == this.mappedByJoiningStrategy;
	}
	
	public boolean mayBeMappedBy(AttributeMapping mappedByMapping) {
		return mappedByMapping.getKey() == MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}
	
	
	// **************** join table *********************************************
	
	public OrmJoinTableJoiningStrategy getJoinTableJoiningStrategy() {
		return this.joinTableJoiningStrategy;
	}
	
	public boolean usesJoinTableJoiningStrategy() {
		return getPredominantJoiningStrategy() == this.joinTableJoiningStrategy;
	}
	
	public final void setJoinTableJoiningStrategy() {
		setJoinTableJoiningStrategy_();
		setPredominantJoiningStrategy();
	}
	
	protected void setJoinTableJoiningStrategy_() {
		// join table is default, so no need to add to resource
		this.mappedByJoiningStrategy.removeStrategy();
	}
	
	public final void unsetJoinTableJoiningStrategy() {
		unsetJoinTableJoiningStrategy_();
		setPredominantJoiningStrategy();
	}
	
	protected void unsetJoinTableJoiningStrategy_() {
		this.joinTableJoiningStrategy.removeStrategy();
	}
	
	public boolean mayHaveDefaultJoinTable() {
		return this.mappedByJoiningStrategy.getMappedByAttribute() == null;
	}
	
	
	// **************** resource => context ************************************
	
	@Override
	protected void updateJoiningStrategies() {
		this.mappedByJoiningStrategy.update();
		
		// update join table last, as the existence of a default join 
		// table is dependent on the other mechanisms (mappedBy)
		// not being specified
		this.joinTableJoiningStrategy.update();
	}
	
	
	// **************** Validation *********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.mappedByJoiningStrategy.validate(messages, reporter);
		this.joinTableJoiningStrategy.validate(messages, reporter);
	}
}
