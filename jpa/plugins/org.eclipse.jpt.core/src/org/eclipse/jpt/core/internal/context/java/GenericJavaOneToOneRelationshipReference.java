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
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.core.context.java.JavaOneToOneRelationshipReference;
import org.eclipse.jpt.core.resource.java.OwnableRelationshipMappingAnnotation;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaOneToOneRelationshipReference
	extends AbstractJavaRelationshipReference
	implements JavaOneToOneRelationshipReference
{
	protected final JavaMappedByJoiningStrategy mappedByJoiningStrategy;
	
	protected final JavaJoinColumnJoiningStrategy joinColumnJoiningStrategy;
	
	protected final JavaPrimaryKeyJoinColumnJoiningStrategy primaryKeyJoinColumnJoiningStrategy;
	
	
	public GenericJavaOneToOneRelationshipReference(JavaOneToOneMapping parent) {
		super(parent);
		this.mappedByJoiningStrategy = new JavaMappedByJoiningStrategy(this);
		this.joinColumnJoiningStrategy = new JavaJoinColumnJoiningStrategy(this);
		this.primaryKeyJoinColumnJoiningStrategy = new JavaPrimaryKeyJoinColumnJoiningStrategy(this);
	}
	
	
	@Override
	public JavaOneToOneMapping getRelationshipMapping() {
		return (JavaOneToOneMapping) getParent();
	}
	
	public OwnableRelationshipMappingAnnotation getMappingAnnotation() {
		return getRelationshipMapping().getMappingAnnotation();
	}
	
	public boolean isRelationshipOwner() {
		return this.mappedByJoiningStrategy.getMappedByAttribute() == null;
	}
	
	public boolean isOwnedBy(RelationshipMapping mapping) {
		// true if the target entity matches the mapping's entity
		// and this mappedBy value matches the mapping's name
		String targetEntity = 
			(getRelationshipMapping().getResolvedTargetEntity() == null) ?
				null : getRelationshipMapping().getResolvedTargetEntity().getName();
		return StringTools.stringsAreEqual(
				targetEntity,
				mapping.getEntity().getName())
			&& StringTools.stringsAreEqual(
				this.getMappedByJoiningStrategy().getMappedByAttribute(), 
				mapping.getPersistentAttribute().getName());
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
	
	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result == null) {
			result = this.mappedByJoiningStrategy.javaCompletionProposals(pos, filter, astRoot);
		}
		if (result == null) {
			result = this.joinColumnJoiningStrategy.javaCompletionProposals(pos, filter, astRoot);
		}
		return result;
	}
	
	
	// **************** mapped by **********************************************
	
	public JavaMappedByJoiningStrategy getMappedByJoiningStrategy() {
		return this.mappedByJoiningStrategy;
	}
	
	public boolean usesMappedByJoiningStrategy() {
		return getPredominantJoiningStrategy() == this.mappedByJoiningStrategy;
	}
	
	public void setMappedByJoiningStrategy() {
		this.joinColumnJoiningStrategy.removeStrategy();
		this.primaryKeyJoinColumnJoiningStrategy.removeStrategy();
		this.mappedByJoiningStrategy.addStrategy();
	}
	
	public void unsetMappedByJoiningStrategy() {
		this.mappedByJoiningStrategy.removeStrategy();
	}
	
	public boolean mayBeMappedBy(AttributeMapping mappedByMapping) {
		return mappedByMapping.getKey() == MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}
	
	
	// **************** join columns *******************************************
	
	public JavaJoinColumnJoiningStrategy getJoinColumnJoiningStrategy() {
		return this.joinColumnJoiningStrategy;
	}
	
	public boolean usesJoinColumnJoiningStrategy() {
		return getPredominantJoiningStrategy() == this.joinColumnJoiningStrategy;
	}
	
	public void setJoinColumnJoiningStrategy() {
		this.mappedByJoiningStrategy.removeStrategy();
		this.primaryKeyJoinColumnJoiningStrategy.removeStrategy();
		// join columns are default, so no need to add annotations
	}
	
	public void unsetJoinColumnJoiningStrategy() {
		this.joinColumnJoiningStrategy.removeStrategy();
	}
	
	public boolean mayHaveDefaultJoinColumn() {
		return this.getMappedByJoiningStrategy().getMappedByAttribute() == null 
			&& this.getPrimaryKeyJoinColumnJoiningStrategy().primaryKeyJoinColumnsSize() == 0;
	}
	
	
	// **************** primary key join columns *******************************
	
	public JavaPrimaryKeyJoinColumnJoiningStrategy getPrimaryKeyJoinColumnJoiningStrategy() {
		return this.primaryKeyJoinColumnJoiningStrategy;
	}
	
	public boolean usesPrimaryKeyJoinColumnJoiningStrategy() {
		return getPredominantJoiningStrategy() == this.primaryKeyJoinColumnJoiningStrategy;
	}
	
	public void setPrimaryKeyJoinColumnJoiningStrategy() {
		this.mappedByJoiningStrategy.removeStrategy();
		this.joinColumnJoiningStrategy.removeStrategy();
		this.primaryKeyJoinColumnJoiningStrategy.addStrategy();
	}
	
	public void unsetPrimaryKeyJoinColumnJoiningStrategy() {
		this.primaryKeyJoinColumnJoiningStrategy.removeStrategy();
	}
	
	public boolean mayHaveDefaultPrimaryKeyJoinColumn() {
		return false;
	}
	
	
	// **************** resource => context ************************************
	
	@Override
	protected void initializeJoiningStrategies() {
		this.mappedByJoiningStrategy.initialize();
		this.primaryKeyJoinColumnJoiningStrategy.initialize();
		
		// initialize join columns last, as the existence of a default join 
		// column is dependent on the other mechanisms (mappedBy, join table)
		// not being specified
		this.joinColumnJoiningStrategy.initialize();
	}
	
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
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.mappedByJoiningStrategy.validate(messages, reporter, astRoot);
		this.primaryKeyJoinColumnJoiningStrategy.validate(messages, reporter, astRoot);
		this.joinColumnJoiningStrategy.validate(messages, reporter, astRoot);
	}
}
