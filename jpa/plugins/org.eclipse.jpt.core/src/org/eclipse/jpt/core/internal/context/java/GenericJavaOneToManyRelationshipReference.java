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
import org.eclipse.jpt.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.core.context.java.JavaOneToManyRelationshipReference;
import org.eclipse.jpt.core.resource.java.OneToManyAnnotation;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaOneToManyRelationshipReference
	extends AbstractJavaRelationshipReference
	implements JavaOneToManyRelationshipReference
{
	protected final JavaMappedByJoiningStrategy mappedByJoiningStrategy;
	
	protected final JavaJoinTableJoiningStrategy joinTableJoiningStrategy;
	
	
	public GenericJavaOneToManyRelationshipReference(JavaOneToManyMapping parent) {
		super(parent);
		this.mappedByJoiningStrategy = new JavaMappedByJoiningStrategy(this);
		this.joinTableJoiningStrategy = new JavaJoinTableJoiningStrategy(this);
	}
	
	
	@Override
	public JavaOneToManyMapping getRelationshipMapping() {
		return (JavaOneToManyMapping) getParent();
	}
	
	public OneToManyAnnotation getMappingAnnotation() {
		return getRelationshipMapping().getMappingAnnotation();
	}
	
	public boolean isRelationshipOwner() {
		return this.getMappedByJoiningStrategy().getMappedByAttribute() == null;
	}
	
	public boolean isOwnedBy(RelationshipMapping mapping) {
		// true if the target entity matches the mapping's entity
		// and this mappedByJoiningStrategy value matches the mapping's name
		return StringTools.stringsAreEqual(
				this.getRelationshipMapping().getResolvedTargetEntity().getName(),
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
		this.joinTableJoiningStrategy.removeStrategy();
		this.mappedByJoiningStrategy.addStrategy();
	}
	
	public void unsetMappedByJoiningStrategy() {
		this.mappedByJoiningStrategy.removeStrategy();
	}
	
	public boolean usesMappedByJoiningStrategy() {
		return this.getPredominantJoiningStrategy() == this.mappedByJoiningStrategy;
	}
	
	public boolean mayBeMappedBy(AttributeMapping mappedByMapping) {
		return mappedByMapping.getKey() == MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}
	
	
	// **************** join table *********************************************
	
	public JavaJoinTableJoiningStrategy getJoinTableJoiningStrategy() {
		return this.joinTableJoiningStrategy;
	}
	
	public boolean usesJoinTableJoiningStrategy() {
		return getPredominantJoiningStrategy() == this.joinTableJoiningStrategy;
	}
	
	public void setJoinTableJoiningStrategy() {
		this.mappedByJoiningStrategy.removeStrategy();
		// join table is default, so no need to add annotation
	}
	
	public void unsetJoinTableJoiningStrategy() {
		this.joinTableJoiningStrategy.removeStrategy();
	}
	
	public boolean mayHaveDefaultJoinTable() {
		return this.mappedByJoiningStrategy.getMappedByAttribute() == null;
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
	}
}
