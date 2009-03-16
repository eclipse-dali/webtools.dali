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
package org.eclipse.jpt.eclipselink.core.internal.context.java;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaJoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.internal.context.java.GenericJavaOneToManyRelationshipReference;
import org.eclipse.jpt.core.internal.context.java.JavaJoinColumnJoiningStrategy;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToManyRelationshipReference;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkJavaOneToManyRelationshipReference
	extends GenericJavaOneToManyRelationshipReference
	implements EclipseLinkOneToManyRelationshipReference,
		JavaJoinColumnEnabledRelationshipReference
{
	protected final JavaJoinColumnJoiningStrategy joinColumnJoiningStrategy;
	
	
	public EclipseLinkJavaOneToManyRelationshipReference(
			EclipseLinkJavaOneToManyMapping parent) {
		super(parent);
		this.joinColumnJoiningStrategy = new JavaJoinColumnJoiningStrategy(this);
	}
	
	
	@Override
	public EclipseLinkJavaOneToManyMapping getRelationshipMapping() {
		return (EclipseLinkJavaOneToManyMapping) getParent();
	}
	
	
	@Override
	protected JoiningStrategy calculatePredominantJoiningStrategy() {
		if (this.mappedByJoiningStrategy.getMappedByAttribute() != null) {
			return this.mappedByJoiningStrategy;
		}
		else if (this.joinColumnJoiningStrategy.hasSpecifiedJoinColumns()) {
			return this.joinColumnJoiningStrategy;
		}
		else {
			return this.joinTableJoiningStrategy;
		}
	}
	
	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result == null) {
			result = this.joinColumnJoiningStrategy.javaCompletionProposals(pos, filter, astRoot);
		}
		return result;
	}
	
	
	// **************** mapped by **********************************************
	
	@Override
	public void setMappedByJoiningStrategy() {
		super.setMappedByJoiningStrategy();
		this.joinColumnJoiningStrategy.removeStrategy();
	}
	
	@Override
	public boolean mayBeMappedBy(AttributeMapping mappedByMapping) {
		return super.mayBeMappedBy(mappedByMapping) ||
			mappedByMapping.getKey() == MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}
	
	
	// **************** join table *********************************************
	
	@Override
	public void setJoinTableJoiningStrategy() {
		this.joinColumnJoiningStrategy.removeStrategy();
		super.setJoinTableJoiningStrategy();
	}
	
	@Override
	public boolean mayHaveDefaultJoinTable() {
		return super.mayHaveDefaultJoinTable()
			&& ! this.joinColumnJoiningStrategy.hasSpecifiedJoinColumns();
	}
	
	
	// **************** join columns *******************************************
	
	public JavaJoinColumnJoiningStrategy getJoinColumnJoiningStrategy() {
		return this.joinColumnJoiningStrategy;
	}
	
	public boolean usesJoinColumnJoiningStrategy() {
		return getPredominantJoiningStrategy() == this.joinColumnJoiningStrategy;
	}
	
	public void setJoinColumnJoiningStrategy() {
		//add the join-column strategy to prevent the update from changing the model out from under us
		this.joinColumnJoiningStrategy.addStrategy();
		this.mappedByJoiningStrategy.removeStrategy();
		this.joinTableJoiningStrategy.removeStrategy();
	}
	
	public void unsetJoinColumnJoiningStrategy() {
		this.joinColumnJoiningStrategy.removeStrategy();
	}
	
	public boolean mayHaveDefaultJoinColumn() {
		return this.mappedByJoiningStrategy.getMappedByAttribute() == null 
			&& this.joinTableJoiningStrategy.getJoinTable() == null;
	}
	
	
	
	// **************** resource => context ************************************
	
	@Override
	protected void initializeJoiningStrategies() {
		this.joinColumnJoiningStrategy.initialize();
		super.initializeJoiningStrategies();
	}
	
	@Override
	protected void updateJoiningStrategies() {
		this.joinColumnJoiningStrategy.update();
		super.updateJoiningStrategies();
	}
	
	
	// **************** Validation *********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.joinColumnJoiningStrategy.validate(messages, reporter, astRoot);
	}
}
