/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaOneToManyRelationshipReference;
import org.eclipse.jpt.core.internal.context.java.GenericJavaJoinColumnJoiningStrategy;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaOneToManyRelationshipReference2_0
	extends AbstractJavaOneToManyRelationshipReference
{	
	protected final JavaJoinColumnJoiningStrategy joinColumnJoiningStrategy;

	public GenericJavaOneToManyRelationshipReference2_0(JavaOneToManyMapping parent) {
		super(parent);
		this.joinColumnJoiningStrategy = buildJoinColumnJoiningStrategy();
	}	

	protected JavaJoinColumnJoiningStrategy buildJoinColumnJoiningStrategy() {
		return new GenericJavaJoinColumnJoiningStrategy(this);
	}
	
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
	protected void setMappedByJoiningStrategy_() {
		super.setMappedByJoiningStrategy_();
		this.joinColumnJoiningStrategy.removeStrategy();
	}

	// **************** join table *********************************************

	@Override
	protected void setJoinTableJoiningStrategy_() {
		super.setJoinTableJoiningStrategy_();
		this.joinColumnJoiningStrategy.removeStrategy();
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
		this.joinColumnJoiningStrategy.addStrategy();
		this.mappedByJoiningStrategy.removeStrategy();
		this.joinTableJoiningStrategy.removeStrategy();
		setPredominantJoiningStrategy();
	}

	public void unsetJoinColumnJoiningStrategy() {
		this.joinColumnJoiningStrategy.removeStrategy();
		setPredominantJoiningStrategy();
	}

	public boolean mayHaveDefaultJoinColumn() {
		return this.mappedByJoiningStrategy.getMappedByAttribute() == null 
			&& this.joinTableJoiningStrategy.getJoinTable() == null;
	}
	
	// **************** Validation *********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.joinColumnJoiningStrategy.validate(messages, reporter, astRoot);
	}
}
