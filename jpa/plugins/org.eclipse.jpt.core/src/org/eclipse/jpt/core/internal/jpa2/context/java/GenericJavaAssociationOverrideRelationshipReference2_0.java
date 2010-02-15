/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.AssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaAssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.jpa2.context.AssociationOverrideRelationshipReference2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaAssociationOverrideRelationshipReference2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaJoinTableInAssociationOverrideJoiningStrategy2_0;
import org.eclipse.jpt.core.jpa2.resource.java.AssociationOverride2_0Annotation;
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaAssociationOverrideRelationshipReference2_0 extends AbstractJavaAssociationOverrideRelationshipReference
	implements JavaAssociationOverrideRelationshipReference2_0
{

	protected final JavaJoinTableInAssociationOverrideJoiningStrategy2_0 joinTableJoiningStrategy;

	public GenericJavaAssociationOverrideRelationshipReference2_0(JavaAssociationOverride parent) {
		super(parent);
		this.joinTableJoiningStrategy = buildJoinTableJoiningStrategy();
	}
	
	public void initializeFrom(AssociationOverrideRelationshipReference oldAssociationOverride) {
		if (oldAssociationOverride.getJoinColumnJoiningStrategy().hasSpecifiedJoinColumns()) {
			getJoinColumnJoiningStrategy().initializeFrom(oldAssociationOverride.getJoinColumnJoiningStrategy());
		}
		else {
			getJoinTableJoiningStrategy().initializeFrom(((AssociationOverrideRelationshipReference2_0) oldAssociationOverride).getJoinTableJoiningStrategy());
		}
	}
	
	protected JavaJoinTableInAssociationOverrideJoiningStrategy2_0 buildJoinTableJoiningStrategy() {
		return new GenericJavaJoinTableInAssociationOverrideJoiningStrategy2_0(this);
	}

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.joinTableJoiningStrategy.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return null;
	}

	@Override
	protected void initializeJoiningStrategies(AssociationOverrideAnnotation associationOverride) {
		super.initializeJoiningStrategies(associationOverride);
		this.joinTableJoiningStrategy.initialize((AssociationOverride2_0Annotation) associationOverride);
	}
	
	@Override
	protected void updateJoiningStrategies(AssociationOverrideAnnotation associationOverride) {
		super.updateJoiningStrategies(associationOverride);
		this.joinTableJoiningStrategy.update((AssociationOverride2_0Annotation) associationOverride);
	}
		
	@Override
	protected JoiningStrategy calculatePredominantJoiningStrategy() {
		if (this.joinColumnJoiningStrategy.hasSpecifiedJoinColumns()) {
			return this.joinColumnJoiningStrategy;
		}
		return this.joinTableJoiningStrategy;
	}
	
	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		//this is not yet correctly supported so I am going to comment it out
		//I end up with join-table validation when the association is for join-columns.
		//bug 190319 covers the use case for validation of join tables in association overrides 
		//this.joinTableJoiningStrategy.validate(messages, reporter, astRoot);
	}



	// **************** join columns *******************************************
	

	public JavaJoinTableInAssociationOverrideJoiningStrategy2_0 getJoinTableJoiningStrategy() {
		return this.joinTableJoiningStrategy;
	}
	
	public boolean usesJoinTableJoiningStrategy() {
		return getPredominantJoiningStrategy() == this.joinTableJoiningStrategy;
	}
	
	public void setJoinTableJoiningStrategy() {
		this.joinTableJoiningStrategy.addStrategy();
		this.joinColumnJoiningStrategy.removeStrategy();
	}
	
	public void unsetJoinTableJoiningStrategy() {
		this.joinTableJoiningStrategy.removeStrategy();
	}
	
	public boolean mayHaveDefaultJoinTable() {
		return getAssociationOverride().isVirtual();
	}

	@Override
	public void setJoinColumnJoiningStrategy() {
		super.setJoinColumnJoiningStrategy();
		this.joinTableJoiningStrategy.removeStrategy();
	}
}
