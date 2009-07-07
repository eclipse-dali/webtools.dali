/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.java.JavaJoinTable;
import org.eclipse.jpt.core.context.java.JavaJoinTableEnabledRelationshipReference;
import org.eclipse.jpt.core.context.java.JavaJoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaRelationshipMapping;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JoinTableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaJoinTableJoiningStrategy 
	extends AbstractJavaJpaContextNode
	implements JavaJoinTableJoiningStrategy
{
	protected JavaResourcePersistentAttribute resourcePersistentAttribute;
	
	protected JavaJoinTable joinTable;
	
	
	public GenericJavaJoinTableJoiningStrategy(JavaJoinTableEnabledRelationshipReference parent) {
		super(parent);
	}
	
	
	@Override
	public JavaJoinTableEnabledRelationshipReference getParent() {
		return (JavaJoinTableEnabledRelationshipReference) super.getParent();
	}
	
	public JavaJoinTableEnabledRelationshipReference getRelationshipReference() {
		return this.getParent();
	}
	
	public JavaRelationshipMapping getRelationshipMapping() {
		return this.getRelationshipReference().getRelationshipMapping();
	}
	
	public void addStrategy() {
		if (this.joinTable == null) {
			JavaJoinTable oldJoinTable = this.joinTable;
			this.joinTable = getJpaFactory().buildJavaJoinTable(this);
			addAnnotation();
			this.firePropertyChanged(JOIN_TABLE_PROPERTY, oldJoinTable, this.joinTable);
		}
	}
	
	public void removeStrategy() {
		if (this.joinTable != null) {
			JavaJoinTable oldJoinTable = this.joinTable;
			this.joinTable = null;
			removeAnnotation();
			this.firePropertyChanged(JOIN_TABLE_PROPERTY, oldJoinTable, this.joinTable);
		}
	}
	
	
	// **************** join table *********************************************
	
	public JavaJoinTable getJoinTable() {
		return this.joinTable;
	}
	
	protected void setJoinTable_(JavaJoinTable newJoinTable) {
		JavaJoinTable oldJoinTable = this.joinTable;
		this.joinTable = newJoinTable;
		this.firePropertyChanged(JOIN_TABLE_PROPERTY, oldJoinTable, newJoinTable);
	}
	
	protected JoinTableAnnotation addAnnotation() {
		return (JoinTableAnnotation) this.resourcePersistentAttribute.
			addSupportingAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
	}
	
	protected void removeAnnotation() {
		this.resourcePersistentAttribute.
			removeSupportingAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
	}
	
	
	// **************** resource => context ************************************

	public void initialize() {
		this.resourcePersistentAttribute = 
			getRelationshipReference().getRelationshipMapping().
				getPersistentAttribute().getResourcePersistentAttribute();
		JoinTableAnnotation annotation = getAnnotation();
		if (annotation.isSpecified() || getRelationshipReference().mayHaveDefaultJoinTable()) {
			this.joinTable = getJpaFactory().buildJavaJoinTable(this);
			this.joinTable.initialize(annotation);
		}
	}
	
	public void update() {
		this.resourcePersistentAttribute = 
			getRelationshipReference().getRelationshipMapping().
				getPersistentAttribute().getResourcePersistentAttribute();
		JoinTableAnnotation annotation = getAnnotation();
		if (annotation.isSpecified() || getRelationshipReference().mayHaveDefaultJoinTable()) {
			if (this.joinTable == null) {
				setJoinTable_(getJpaFactory().buildJavaJoinTable(this));
			}
			this.joinTable.update(annotation);
		}
		else {
			if (this.joinTable != null) {
				// no annotation, so no clean up
				setJoinTable_(null);
			}
		}
	}
	
	public JoinTableAnnotation getAnnotation() {
		return 	(JoinTableAnnotation) 
			this.resourcePersistentAttribute.getNonNullSupportingAnnotation(
				JoinTableAnnotation.ANNOTATION_NAME);
	}
	
	// **************** Java completion proposals ******************************
	
	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result == null && this.joinTable != null) {
			result = this.joinTable.javaCompletionProposals(pos, filter, astRoot);
		}
		return result;
	}
	
	
	// **************** validation *********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		if (this.joinTable != null && getRelationshipMapping().shouldValidateAgainstDatabase()) {
			this.joinTable.validate(messages, reporter, astRoot);
		}
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getRelationshipReference().getValidationTextRange(astRoot);
	}
}
