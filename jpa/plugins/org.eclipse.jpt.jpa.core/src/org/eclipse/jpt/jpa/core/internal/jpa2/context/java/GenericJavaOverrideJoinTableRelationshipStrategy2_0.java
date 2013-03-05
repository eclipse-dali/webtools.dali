/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.java;

import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinTable;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaSpecifiedOverrideRelationship2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.AssociationOverride2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.JoinTableAnnotation;

public class GenericJavaOverrideJoinTableRelationshipStrategy2_0
	extends AbstractJavaJoinTableRelationshipStrategy<JavaSpecifiedOverrideRelationship2_0>
{
	public GenericJavaOverrideJoinTableRelationshipStrategy2_0(JavaSpecifiedOverrideRelationship2_0 parent) {
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
	public JavaSpecifiedOverrideRelationship2_0 getRelationship() {
		return this.parent;
	}

	public boolean isOverridable() {
		return false;
	}


	// ********** validation **********

	public boolean validatesAgainstDatabase() {
		return this.getRelationship().getTypeMapping().validatesAgainstDatabase();
	}

	public JptValidator buildTableValidator(Table table) {
		return this.getRelationship().buildJoinTableValidator((JoinTable) table);
	}

	public JptValidator buildJoinTableJoinColumnValidator(JoinColumn column, JoinColumn.ParentAdapter parentAdapter) {
		return this.getRelationship().buildJoinTableJoinColumnValidator(column, parentAdapter);
	}

	public JptValidator buildJoinTableInverseJoinColumnValidator(JoinColumn column, JoinColumn.ParentAdapter parentAdapter) {
		return this.getRelationship().buildJoinTableInverseJoinColumnValidator(column, parentAdapter);
	}
}
