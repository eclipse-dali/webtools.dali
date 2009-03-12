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
import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.core.context.java.JavaManyToOneRelationshipReference;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaManyToOneRelationshipReference
	extends AbstractJavaRelationshipReference
	implements JavaManyToOneRelationshipReference
{
	protected final JavaJoinColumnJoiningStrategy joinColumnJoiningStrategy;
	
	
	public GenericJavaManyToOneRelationshipReference(JavaManyToOneMapping parent) {
		super(parent);
		this.joinColumnJoiningStrategy = new JavaJoinColumnJoiningStrategy(this);
	}
	
	
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
	protected JoiningStrategy calculatePredominantJoiningStrategy() {
		// the only joining strategy
		return this.joinColumnJoiningStrategy;
	}
	
	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result == null) {
			result = this.joinColumnJoiningStrategy.javaCompletionProposals(pos, filter, astRoot);
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
		this.joinColumnJoiningStrategy.addStrategy();
	}
	
	public void unsetJoinColumnJoiningStrategy() {
		this.joinColumnJoiningStrategy.removeStrategy();
	}
	
	public boolean mayHaveDefaultJoinColumn() {
		return true;
	}
	
	
	// **************** resource => context ************************************
	
	@Override
	protected void initializeJoiningStrategies() {
		// initialize join column strategy last, as the existence of a default 
		// join column is dependent on the other mechanisms (join table) not 
		// being specified
		this.joinColumnJoiningStrategy.initialize();
	}
	
	@Override
	protected void updateJoiningStrategies() {
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
	}
}
