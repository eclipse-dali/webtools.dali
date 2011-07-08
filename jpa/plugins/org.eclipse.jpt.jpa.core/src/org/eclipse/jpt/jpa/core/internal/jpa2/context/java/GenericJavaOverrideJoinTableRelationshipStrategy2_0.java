/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaOverrideRelationship2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.AssociationOverride2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.JoinTableAnnotation;

public class GenericJavaOverrideJoinTableRelationshipStrategy2_0
	extends AbstractJavaJoinTableRelationshipStrategy
{
	public GenericJavaOverrideJoinTableRelationshipStrategy2_0(JavaOverrideRelationship2_0 parent) {
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
		return (AssociationOverride2_0Annotation) this.getRelationship().getOverrideAnnotation();
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

	public JptValidator buildTableValidator(ReadOnlyTable table, TableTextRangeResolver textRangeResolver) {
		return this.getRelationship().buildJoinTableValidator((ReadOnlyJoinTable) table, textRangeResolver);
	}

	public JptValidator buildJoinTableJoinColumnValidator(ReadOnlyJoinColumn column, ReadOnlyJoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
		return this.getRelationship().buildJoinTableJoinColumnValidator(column, owner, textRangeResolver);
	}

	public JptValidator buildJoinTableInverseJoinColumnValidator(ReadOnlyJoinColumn column, ReadOnlyJoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
		return this.getRelationship().buildJoinTableInverseJoinColumnValidator(column, owner, textRangeResolver);
	}
}
