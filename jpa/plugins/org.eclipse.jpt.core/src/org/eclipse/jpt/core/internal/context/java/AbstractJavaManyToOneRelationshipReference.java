/*******************************************************************************
 *  Copyright (c) 2009, 2010  Oracle. 
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
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.java.JavaJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaJoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.core.jpa2.context.java.JavaManyToOneRelationshipReference2_0;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractJavaManyToOneRelationshipReference
	extends AbstractJavaRelationshipReference
	implements JavaManyToOneRelationshipReference2_0
{
	protected final JavaJoinColumnJoiningStrategy joinColumnJoiningStrategy;

	protected final JavaJoinTableJoiningStrategy joinTableJoiningStrategy;


	protected AbstractJavaManyToOneRelationshipReference(JavaManyToOneMapping parent) {
		super(parent);
		this.joinColumnJoiningStrategy = buildJoinColumnJoiningStrategy();
		this.joinTableJoiningStrategy = buildJoinTableJoiningStrategy();
	}
	
	
	protected JavaJoinColumnJoiningStrategy buildJoinColumnJoiningStrategy() {
		return new GenericJavaJoinColumnJoiningStrategy(this);
	}

	protected abstract JavaJoinTableJoiningStrategy buildJoinTableJoiningStrategy();
	
	@Override
	public JavaManyToOneMapping getRelationshipMapping() {
		return (JavaManyToOneMapping) getParent();
	}
	
	public boolean isRelationshipOwner() {
		return true;
	}
	
	public boolean isOwnedBy(RelationshipMapping mapping) {
		return false;
	}
		
	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result == null) {
			result = this.joinColumnJoiningStrategy.javaCompletionProposals(pos, filter, astRoot);
		}
		if (result == null) {
			result = this.joinTableJoiningStrategy.javaCompletionProposals(pos, filter, astRoot);
		}
		return result;
	}
	
	
	// **************** join columns *******************************************
	
	public JavaJoinColumnJoiningStrategy getJoinColumnJoiningStrategy() {
		return this.joinColumnJoiningStrategy;
	}
	
	public boolean usesJoinColumnJoiningStrategy() {
		return getPredominantJoiningStrategy() == this.joinColumnJoiningStrategy;
	}
	
	public void setJoinColumnJoiningStrategy() {
		// join columns are default (and only, so far) strategy, so no need
		// to add to resource model
		this.joinTableJoiningStrategy.removeStrategy();
		setPredominantJoiningStrategy();
	}
	
	public void unsetJoinColumnJoiningStrategy() {
		this.joinColumnJoiningStrategy.removeStrategy();
		setPredominantJoiningStrategy();
	}
	
	public boolean mayHaveDefaultJoinColumn() {
		return this.getJoinTableJoiningStrategy().getJoinTable() == null;
	}

	
	// **************** join table *********************************************
	
	public JavaJoinTableJoiningStrategy getJoinTableJoiningStrategy() {
		return this.joinTableJoiningStrategy;
	}
	
	public boolean usesJoinTableJoiningStrategy() {
		return getPredominantJoiningStrategy() == this.joinTableJoiningStrategy;
	}
	
	public final void setJoinTableJoiningStrategy() {
		this.joinTableJoiningStrategy.addStrategy();
		this.joinColumnJoiningStrategy.removeStrategy();
		setPredominantJoiningStrategy();
	}
	
	public final void unsetJoinTableJoiningStrategy() {
		unsetJoinTableJoiningStrategy_();
		setPredominantJoiningStrategy();
	}
	
	protected void unsetJoinTableJoiningStrategy_() {
		this.joinTableJoiningStrategy.removeStrategy();
	}
	
	public boolean mayHaveDefaultJoinTable() {
		return false;
	}	
	
	// **************** resource => context ************************************
	
	@Override
	protected void initializeJoiningStrategies() {
		this.joinTableJoiningStrategy.initialize();
		// initialize join column strategy last, as the existence of a default 
		// join column is dependent on the other mechanisms (join table) not 
		// being specified
		this.joinColumnJoiningStrategy.initialize();
	}
	
	@Override
	protected void updateJoiningStrategies() {
		this.joinTableJoiningStrategy.update();
		// update join column strategy last, as the existence of a default join 
		// column is dependent on the other mechanisms (join table) not being 
		// specified
		this.joinColumnJoiningStrategy.update();
	}
	
	
	// **************** Validation *********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.joinColumnJoiningStrategy.validate(messages, reporter, astRoot);
		this.joinTableJoiningStrategy.validate(messages, reporter, astRoot);
	}
}
