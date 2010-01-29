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
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmJoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmJoinTableEnabledRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmMappedByJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToOneRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmOwnableRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmPrimaryKeyJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmRelationshipReference;
import org.eclipse.jpt.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmOneToOneRelationshipReference
	extends AbstractOrmRelationshipReference
	implements OrmOneToOneRelationshipReference
{
	protected OrmMappedByJoiningStrategy mappedByJoiningStrategy;
	
	protected OrmJoinColumnJoiningStrategy joinColumnJoiningStrategy;
	
	protected OrmPrimaryKeyJoinColumnJoiningStrategy primaryKeyJoinColumnJoiningStrategy;
	
	
	public GenericOrmOneToOneRelationshipReference(
			OrmOneToOneMapping parent, XmlOneToOne resource) {
		super(parent, resource);
	}
	
	
	@Override
	protected void initializeJoiningStrategies() {
		this.mappedByJoiningStrategy = buildMappedByJoiningStrategy();
		this.primaryKeyJoinColumnJoiningStrategy = buildPrimaryKeyJoinColumnJoiningStrategy();
		
		// initialize join columns last, as the existence of a default join 
		// column is dependent on the other mechanisms (mappedBy, join table)
		// not being specified
		this.joinColumnJoiningStrategy = buildJoinColumnJoiningStrategy();
	}
	
	protected OrmMappedByJoiningStrategy buildMappedByJoiningStrategy() {
		return new GenericOrmMappedByJoiningStrategy(this, getResourceMapping());
	}
	
	protected OrmJoinColumnJoiningStrategy buildJoinColumnJoiningStrategy() {
		return new GenericOrmJoinColumnJoiningStrategy(this, getResourceMapping());
	}
	
	protected OrmPrimaryKeyJoinColumnJoiningStrategy buildPrimaryKeyJoinColumnJoiningStrategy() {
		return new GenericOrmPrimaryKeyJoinColumnJoiningStrategy(this, getResourceMapping());
	}
	
	public void initializeOn(OrmRelationshipReference newRelationshipReference) {
		newRelationshipReference.initializeFromOwnableRelationshipReference(this);
		newRelationshipReference.initializeFromJoinColumnEnabledRelationshipReference(this);
		// no other primary key reference as of yet, so no initialization based on pk join columns
	}
	
	@Override
	public void initializeFromOwnableRelationshipReference(
			OrmOwnableRelationshipReference oldRelationshipReference) {
		this.mappedByJoiningStrategy.setMappedByAttribute(
			oldRelationshipReference.getMappedByJoiningStrategy().getMappedByAttribute());
	}
	
	@Override
	public void initializeFromJoinColumnEnabledRelationshipReference(
			OrmJoinColumnEnabledRelationshipReference oldRelationshipReference) {
		int index = 0;
		for (JoinColumn joinColumn : 
				CollectionTools.iterable(
					oldRelationshipReference.getJoinColumnJoiningStrategy().specifiedJoinColumns())) {
			OrmJoinColumn newJoinColumn = getJoinColumnJoiningStrategy().addSpecifiedJoinColumn(index++);
			newJoinColumn.initializeFrom(joinColumn);
		}
	}
	
	@Override
	public void initializeFromJoinTableEnabledRelationshipReference(
			OrmJoinTableEnabledRelationshipReference oldRelationshipReference) {
		// TODO no join table context model support as of yet
		
	}
	
	@Override
	public OrmOneToOneMapping getRelationshipMapping() {
		return (OrmOneToOneMapping) getParent();
	}
	
	public XmlOneToOne getResourceMapping() {
		return getRelationshipMapping().getResourceAttributeMapping();
	}
	
	public boolean isRelationshipOwner() {
		return this.mappedByJoiningStrategy.getMappedByAttribute() == null;
	}
	
	public boolean isOwnedBy(RelationshipMapping mapping) {
		return this.mappedByJoiningStrategy.relationshipIsOwnedBy(mapping);
	}
	
	@Override
	protected JoiningStrategy calculatePredominantJoiningStrategy() {
		if (this.mappedByJoiningStrategy.getMappedByAttribute() != null) {
			return this.mappedByJoiningStrategy;
		}
		else if (this.primaryKeyJoinColumnJoiningStrategy.primaryKeyJoinColumnsSize() > 0) {
			return this.primaryKeyJoinColumnJoiningStrategy;
		}
		else {
			return this.joinColumnJoiningStrategy;
		}
	}
	
	
	// **************** mapped by **********************************************
	
	public OrmMappedByJoiningStrategy getMappedByJoiningStrategy() {
		return this.mappedByJoiningStrategy;
	}
	
	public boolean usesMappedByJoiningStrategy() {
		return getPredominantJoiningStrategy() == this.mappedByJoiningStrategy;
	}
	
	public void setMappedByJoiningStrategy() {
		this.mappedByJoiningStrategy.addStrategy();
		this.joinColumnJoiningStrategy.removeStrategy();
		this.primaryKeyJoinColumnJoiningStrategy.removeStrategy();
		setPredominantJoiningStrategy();
	}
	
	public void unsetMappedByJoiningStrategy() {
		this.mappedByJoiningStrategy.removeStrategy();
		setPredominantJoiningStrategy();
	}
	
	public boolean mayBeMappedBy(AttributeMapping mappedByMapping) {
		return mappedByMapping.getKey() == MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}
	
	
	// **************** join columns *******************************************
	
	public OrmJoinColumnJoiningStrategy getJoinColumnJoiningStrategy() {
		return this.joinColumnJoiningStrategy;
	}
	
	public boolean usesJoinColumnJoiningStrategy() {
		return getPredominantJoiningStrategy() == this.joinColumnJoiningStrategy;
	}
	
	public void setJoinColumnJoiningStrategy() {
		this.mappedByJoiningStrategy.removeStrategy();
		this.primaryKeyJoinColumnJoiningStrategy.removeStrategy();
		// join columns are default, so no need to add annotations
		setPredominantJoiningStrategy();
	}
	
	public void unsetJoinColumnJoiningStrategy() {
		this.joinColumnJoiningStrategy.removeStrategy();
		setPredominantJoiningStrategy();
	}
	
	public boolean mayHaveDefaultJoinColumn() {
		return this.getMappedByJoiningStrategy().getMappedByAttribute() == null 
			&& this.getPrimaryKeyJoinColumnJoiningStrategy().primaryKeyJoinColumnsSize() == 0;
	}
	
	
	// **************** primary key join columns *******************************
	
	public OrmPrimaryKeyJoinColumnJoiningStrategy getPrimaryKeyJoinColumnJoiningStrategy() {
		return this.primaryKeyJoinColumnJoiningStrategy;
	}
	
	public boolean usesPrimaryKeyJoinColumnJoiningStrategy() {
		return getPredominantJoiningStrategy() == this.primaryKeyJoinColumnJoiningStrategy;
	}
	
	public void setPrimaryKeyJoinColumnJoiningStrategy() {
		this.primaryKeyJoinColumnJoiningStrategy.addStrategy();
		this.mappedByJoiningStrategy.removeStrategy();
		this.joinColumnJoiningStrategy.removeStrategy();
		setPredominantJoiningStrategy();
	}
	
	public void unsetPrimaryKeyJoinColumnJoiningStrategy() {
		this.primaryKeyJoinColumnJoiningStrategy.removeStrategy();
		setPredominantJoiningStrategy();
	}
	
	public boolean mayHaveDefaultPrimaryKeyJoinColumn() {
		return false;
	}
	
	
	// **************** resource => context ************************************
	
	@Override
	protected void updateJoiningStrategies() {
		this.mappedByJoiningStrategy.update();
		this.primaryKeyJoinColumnJoiningStrategy.update();
		
		// update join columns last, as the existence of a default join 
		// column is dependent on the other mechanisms (mappedBy, join table)
		// not being specified
		this.joinColumnJoiningStrategy.update();
	}
	
	
	// **************** Validation *********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.mappedByJoiningStrategy.validate(messages, reporter);
		this.primaryKeyJoinColumnJoiningStrategy.validate(messages, reporter);
		this.joinColumnJoiningStrategy.validate(messages, reporter);
	}
}
