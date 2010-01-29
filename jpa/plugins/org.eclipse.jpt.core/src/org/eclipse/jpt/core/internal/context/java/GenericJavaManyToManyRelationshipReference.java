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
import org.eclipse.jpt.core.context.java.JavaJoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.core.context.java.JavaManyToManyRelationshipReference;
import org.eclipse.jpt.core.context.java.JavaMappedByJoiningStrategy;
import org.eclipse.jpt.core.resource.java.OwnableRelationshipMappingAnnotation;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaManyToManyRelationshipReference
	extends AbstractJavaRelationshipReference
	implements JavaManyToManyRelationshipReference
{
	protected final JavaMappedByJoiningStrategy mappedByJoiningStrategy;
	
	protected final JavaJoinTableJoiningStrategy joinTableJoiningStrategy;
	
	
	public GenericJavaManyToManyRelationshipReference(JavaManyToManyMapping parent) {
		super(parent);
		this.mappedByJoiningStrategy = buildMappedByJoiningStrategy();
		this.joinTableJoiningStrategy = buildJoinTableJoiningStrategy();
	}
	
	
	protected JavaMappedByJoiningStrategy buildMappedByJoiningStrategy() {
		return new GenericJavaMappedByJoiningStrategy(this);
	}
	
	protected JavaJoinTableJoiningStrategy buildJoinTableJoiningStrategy() {
		return new GenericJavaJoinTableJoiningStrategy(this);
	}
	
	@Override
	public JavaManyToManyMapping getRelationshipMapping() {
		return (JavaManyToManyMapping) getParent();
	}
	
	public OwnableRelationshipMappingAnnotation getMappingAnnotation() {
		return getRelationshipMapping().getMappingAnnotation();
	}
	
	public boolean isRelationshipOwner() {
		return this.getMappedByJoiningStrategy().getMappedByAttribute() == null;
	}
	
	public boolean isOwnedBy(RelationshipMapping mapping) {
		return this.mappedByJoiningStrategy.relationshipIsOwnedBy(mapping);
	}
	
	@Override
	protected JoiningStrategy calculatePredominantJoiningStrategy() {
		if (this.mappedByJoiningStrategy.getMappedByAttribute() != null) {
			return this.mappedByJoiningStrategy;
		}
		else {
			return this.joinTableJoiningStrategy;
		}
	}
	
	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result == null) {
			result = this.mappedByJoiningStrategy.javaCompletionProposals(pos, filter, astRoot);
		}
		if (result == null) {
			result = this.joinTableJoiningStrategy.javaCompletionProposals(pos, filter, astRoot);
		}
		return result;
	}
	
	
	// **************** mapped by **********************************************
	
	public JavaMappedByJoiningStrategy getMappedByJoiningStrategy() {
		return this.mappedByJoiningStrategy;
	}
	
	public void setMappedByJoiningStrategy() {
		this.mappedByJoiningStrategy.addStrategy();
		this.joinTableJoiningStrategy.removeStrategy();
		setPredominantJoiningStrategy();
	}
	
	public void unsetMappedByJoiningStrategy() {
		this.mappedByJoiningStrategy.removeStrategy();
		setPredominantJoiningStrategy();
	}
	
	public boolean usesMappedByJoiningStrategy() {
		return getPredominantJoiningStrategy() == this.mappedByJoiningStrategy;
	}
	
	public boolean mayBeMappedBy(AttributeMapping mappedByMapping) {
		return mappedByMapping.getKey() == MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}
	
	
	// **************** join table joining strategy  ***************************
	
	public JavaJoinTableJoiningStrategy getJoinTableJoiningStrategy() {
		return this.joinTableJoiningStrategy;
	}
	
	public boolean usesJoinTableJoiningStrategy() {
		return getPredominantJoiningStrategy() == this.joinTableJoiningStrategy;
	}
	
	public void setJoinTableJoiningStrategy() {
		// join table is default option, so no need to add to resource
		this.mappedByJoiningStrategy.removeStrategy();
		setPredominantJoiningStrategy();
	}
	
	public void unsetJoinTableJoiningStrategy() {
		this.joinTableJoiningStrategy.removeStrategy();
		setPredominantJoiningStrategy();
	}
	
	public boolean mayHaveDefaultJoinTable() {
		return getMappedByJoiningStrategy().getMappedByAttribute() == null;
	}
	
	
	// **************** resource => context ************************************
	
	@Override
	protected void initializeJoiningStrategies() {
		this.mappedByJoiningStrategy.initialize();
		this.joinTableJoiningStrategy.initialize();
	}
	
	@Override
	protected void updateJoiningStrategies() {
		this.mappedByJoiningStrategy.update();
		this.joinTableJoiningStrategy.update();
	}
	
	
	// **************** Validation *********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.mappedByJoiningStrategy.validate(messages, reporter, astRoot);
		this.joinTableJoiningStrategy.validate(messages, reporter, astRoot);
	}
}
