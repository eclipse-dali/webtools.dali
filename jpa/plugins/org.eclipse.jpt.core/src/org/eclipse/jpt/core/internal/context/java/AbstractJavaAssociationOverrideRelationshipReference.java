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
import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJoinColumnInAssociationOverrideJoiningStrategy;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractJavaAssociationOverrideRelationshipReference extends AbstractJavaJpaContextNode
	implements JavaAssociationOverrideRelationshipReference
{

	// cache the strategy for property change notification
	protected JoiningStrategy cachedPredominantJoiningStrategy;

	protected final JavaJoinColumnInAssociationOverrideJoiningStrategy joinColumnJoiningStrategy;

	protected AbstractJavaAssociationOverrideRelationshipReference(JavaAssociationOverride parent) {
		super(parent);
		this.joinColumnJoiningStrategy = buildJoinColumnJoiningStrategy();
	}
	
	protected JavaJoinColumnInAssociationOverrideJoiningStrategy buildJoinColumnJoiningStrategy() {
		return new GenericJavaJoinColumnInAssociationOverrideJoiningStrategy(this);
	}
	
	@Override
	public JavaAssociationOverride getParent() {
		return (JavaAssociationOverride) super.getParent();
	}
	
	public JavaAssociationOverride getAssociationOverride() {
		return getParent();
	}
	
	public boolean isOverridableAssociation() {
		return false;
	}
	
	public TypeMapping getTypeMapping() {
		return getAssociationOverride().getOwner().getTypeMapping();
	}
	
	public boolean isParentVirtual() {
		return getAssociationOverride().isVirtual();
	}
	
	// **************** predominant joining strategy ***************************
	
	public JoiningStrategy getPredominantJoiningStrategy() {
		return this.cachedPredominantJoiningStrategy;
	}
	
	protected void setPredominantJoiningStrategy(JoiningStrategy newJoiningStrategy) {
		JoiningStrategy oldJoiningStrategy = this.cachedPredominantJoiningStrategy;
		this.cachedPredominantJoiningStrategy = newJoiningStrategy;
		firePropertyChanged(PREDOMINANT_JOINING_STRATEGY_PROPERTY, oldJoiningStrategy, newJoiningStrategy);
	}

	public void initialize(AssociationOverrideAnnotation associationOverride) {
		initializeJoiningStrategies(associationOverride);
		this.cachedPredominantJoiningStrategy = calculatePredominantJoiningStrategy();
	}		
	
	protected void initializeJoiningStrategies(AssociationOverrideAnnotation associationOverride) {
		this.joinColumnJoiningStrategy.initialize(associationOverride);		
	}

	public void update(AssociationOverrideAnnotation associationOverride) {
		updateJoiningStrategies(associationOverride);
		setPredominantJoiningStrategy(calculatePredominantJoiningStrategy());
	}	
		
	protected void updateJoiningStrategies(AssociationOverrideAnnotation associationOverride) {
		this.joinColumnJoiningStrategy.update(associationOverride);
	}
	
	protected abstract JoiningStrategy calculatePredominantJoiningStrategy();

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.joinColumnJoiningStrategy.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return null;
	}

	
	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		if (this.connectionProfileIsActive()) {
			this.validateJoinColumns(messages, astRoot);
		}
	}
	
	protected void validateJoinColumns(List<IMessage> messages, CompilationUnit astRoot) {
		for (Iterator<JavaJoinColumn> stream = this.getJoinColumnJoiningStrategy().joinColumns(); stream.hasNext(); ) {
			this.validateJoinColumn(stream.next(), messages, astRoot);
		}
	}

	protected void validateJoinColumn(JavaJoinColumn joinColumn, List<IMessage> messages, CompilationUnit astRoot) {
		String tableName = joinColumn.getTable();
		if (this.getAssociationOverride().getOwner().getTypeMapping().tableNameIsInvalid(tableName)) {
			if (this.getAssociationOverride().isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_TABLE,
						new String[] {this.getAssociationOverride().getName(), tableName, joinColumn.getName()},
						joinColumn, 
						joinColumn.getTableTextRange(astRoot)
					)
				);
			} else {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.JOIN_COLUMN_UNRESOLVED_TABLE,
						new String[] {tableName, joinColumn.getName()}, 
						joinColumn,
						joinColumn.getTableTextRange(astRoot)
					)
				);
			}
			return;
		}
		
		if ( ! joinColumn.isResolved()) {
			if (this.getAssociationOverride().isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_NAME,
						new String[] {this.getAssociationOverride().getName(), joinColumn.getName()}, 
						joinColumn,
						joinColumn.getNameTextRange(astRoot)
					)
				);
			} else {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.JOIN_COLUMN_UNRESOLVED_NAME,
						new String[] {joinColumn.getName()}, 
						joinColumn, 
						joinColumn.getNameTextRange(astRoot)
					)
				);
			}
		}
		
		if ( ! joinColumn.isReferencedColumnResolved()) {
			if (this.getAssociationOverride().isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME,
						new String[] {this.getAssociationOverride().getName(), joinColumn.getReferencedColumnName(), joinColumn.getName()}, 
						joinColumn,
						joinColumn.getReferencedColumnNameTextRange(astRoot)
					)
				);
			} else {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME,
						new String[] {joinColumn.getReferencedColumnName(), joinColumn.getName()}, 
						joinColumn,
						joinColumn.getReferencedColumnNameTextRange(astRoot)
					)
				);
			}
		}
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return getAssociationOverride().getValidationTextRange(astRoot);
	}

	// **************** join columns *******************************************
	

	public JavaJoinColumnInAssociationOverrideJoiningStrategy getJoinColumnJoiningStrategy() {
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
		return false;
	}

	public RelationshipMapping getRelationshipMapping() {
		return getAssociationOverride().getOwner().getRelationshipMapping(getAssociationOverride().getName());
	}

	public boolean isOwnedBy(RelationshipMapping mapping) {
		return getRelationshipMapping().isOwnedBy(mapping);
	}

	public boolean isRelationshipOwner() {
		return getRelationshipMapping().isRelationshipOwner();
	}

}
