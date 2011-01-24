/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.JoinColumn.Owner;
import org.eclipse.jpt.core.context.java.JavaMappingJoinTableRelationship;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJoinTableJoiningStrategy;
import org.eclipse.jpt.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.core.internal.jpa1.context.InverseJoinColumnValidator;
import org.eclipse.jpt.core.internal.jpa1.context.JoinColumnValidator;
import org.eclipse.jpt.core.internal.jpa1.context.JoinTableTableDescriptionProvider;
import org.eclipse.jpt.core.internal.jpa1.context.JoinTableValidator;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JoinTableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;

public class GenericJavaMappingJoinTableJoiningStrategy
	extends AbstractJavaJoinTableJoiningStrategy
{
	public GenericJavaMappingJoinTableJoiningStrategy(JavaMappingJoinTableRelationship parent) {
		super(parent);
	}


	// ********** join table annotation **********

	public JoinTableAnnotation getJoinTableAnnotation() {
		return 	(JoinTableAnnotation) this.getResourcePersistentAttribute().getNonNullAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
	}

	@Override
	protected JoinTableAnnotation addJoinTableAnnotation() {
		return (JoinTableAnnotation) this.getResourcePersistentAttribute().addAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
	}

	@Override
	protected void removeJoinTableAnnotation() {
		this.getResourcePersistentAttribute().removeAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
	}


	// ********** misc **********

	protected JavaResourcePersistentAttribute getResourcePersistentAttribute() {
		return this.getRelationship().getMapping().getResourcePersistentAttribute();
	}

	@Override
	public JavaMappingJoinTableRelationship getParent() {
		return (JavaMappingJoinTableRelationship) super.getParent();
	}

	@Override
	public JavaMappingJoinTableRelationship getRelationship() {
		return this.getParent();
	}

	public boolean isOverridable() {
		return this.getJpaPlatformVariation().isJoinTableOverridable();
	}


	// ********** validation **********

	public boolean validatesAgainstDatabase() {
		return this.getRelationshipMapping().validatesAgainstDatabase();
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getRelationship().getValidationTextRange(astRoot);
	}

	public JptValidator buildTableValidator(Table table, TableTextRangeResolver textRangeResolver) {
		return new JoinTableValidator((JoinTable) table, textRangeResolver);
	}

	public JptValidator buildJoinTableJoinColumnValidator(JoinColumn column, JoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
		return new JoinColumnValidator(column, owner, textRangeResolver, new JoinTableTableDescriptionProvider());
	}

	public JptValidator buildJoinTableInverseJoinColumnValidator(JoinColumn column, Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
		return new InverseJoinColumnValidator(column, owner, textRangeResolver, new JoinTableTableDescriptionProvider());
	}
}
