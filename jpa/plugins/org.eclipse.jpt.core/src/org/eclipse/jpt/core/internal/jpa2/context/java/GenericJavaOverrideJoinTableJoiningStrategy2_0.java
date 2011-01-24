/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJoinTableJoiningStrategy;
import org.eclipse.jpt.core.internal.jpa1.context.AssociationOverrideInverseJoinColumnValidator;
import org.eclipse.jpt.core.internal.jpa1.context.AssociationOverrideJoinColumnValidator;
import org.eclipse.jpt.core.internal.jpa1.context.AssociationOverrideJoinTableValidator;
import org.eclipse.jpt.core.internal.jpa1.context.JoinTableTableDescriptionProvider;
import org.eclipse.jpt.core.jpa2.context.java.JavaOverrideRelationship2_0;
import org.eclipse.jpt.core.jpa2.resource.java.AssociationOverride2_0Annotation;
import org.eclipse.jpt.core.resource.java.JoinTableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;

public class GenericJavaOverrideJoinTableJoiningStrategy2_0
	extends AbstractJavaJoinTableJoiningStrategy
{
	public GenericJavaOverrideJoinTableJoiningStrategy2_0(JavaOverrideRelationship2_0 parent) {
		super(parent);
	}


	// ********** join table annotation **********

	public JoinTableAnnotation getJoinTableAnnotation() {
		return this.getOverrideAnnotation().getNonNullJoinTable();
	}

	@Override
	protected JoinTableAnnotation addJoinTableAnnotation() {
		return this.getOverrideAnnotation().addJoinTable();
	}

	@Override
	protected void removeJoinTableAnnotation() {
		this.getOverrideAnnotation().removeJoinTable();
	}

	protected AssociationOverride2_0Annotation getOverrideAnnotation() {
		// only JPA 2.0 association overrides can have a join table (join strategy)
		return (AssociationOverride2_0Annotation) this.getAssociationOverride().getOverrideAnnotation();
	}


	// ********** misc **********

	@Override
	public JavaOverrideRelationship2_0 getParent() {
		return (JavaOverrideRelationship2_0) super.getParent();
	}

	@Override
	public JavaOverrideRelationship2_0 getRelationship() {
		return this.getParent();
	}

	protected JavaAssociationOverride getAssociationOverride() {
		return this.getRelationship().getAssociationOverride();
	}

	public boolean isOverridable() {
		return false;
	}


	// ********** validation **********

	public boolean validatesAgainstDatabase() {
		return this.getRelationship().getTypeMapping().validatesAgainstDatabase();
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getRelationship().getValidationTextRange(astRoot);
	}

	public JptValidator buildTableValidator(Table table, TableTextRangeResolver textRangeResolver) {
		return new AssociationOverrideJoinTableValidator(this.getRelationship().getAssociationOverride(), (JoinTable) table, textRangeResolver);
	}

	public JptValidator buildJoinTableJoinColumnValidator(JoinColumn column, JoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
		return new AssociationOverrideJoinColumnValidator(this.getRelationship().getAssociationOverride(), column, owner, textRangeResolver, new JoinTableTableDescriptionProvider());
	}

	public JptValidator buildJoinTableInverseJoinColumnValidator(JoinColumn column, JoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
		return new  AssociationOverrideInverseJoinColumnValidator(this.getRelationship().getAssociationOverride(), column, owner, textRangeResolver, new JoinTableTableDescriptionProvider());
	}
}
