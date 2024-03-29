/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinTable;
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.RelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.java.JavaMappingJoinTableRelationship;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.InverseJoinColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.JoinColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.JoinTableTableDescriptionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.JoinTableValidator;
import org.eclipse.jpt.jpa.core.jpa2.context.SpecifiedMappingRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OverrideRelationship2_0;
import org.eclipse.jpt.jpa.core.resource.java.JoinTableAnnotation;

public class GenericJavaMappingJoinTableRelationshipStrategy
	extends AbstractJavaJoinTableRelationshipStrategy<JavaMappingJoinTableRelationship>
	implements SpecifiedMappingRelationshipStrategy2_0
{
	public GenericJavaMappingJoinTableRelationshipStrategy(JavaMappingJoinTableRelationship parent) {
		super(parent);
	}


	// ********** join table annotation **********

	public JoinTableAnnotation getJoinTableAnnotation() {
		return 	(JoinTableAnnotation) this.getResourceAttribute().getNonNullAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
	}

	@Override
	protected JoinTableAnnotation addJoinTableAnnotation() {
		return (JoinTableAnnotation) this.getResourceAttribute().addAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
	}

	@Override
	protected void removeJoinTableAnnotation() {
		this.getResourceAttribute().removeAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
	}


	// ********** misc **********

	protected JavaResourceAttribute getResourceAttribute() {
		return this.getRelationship().getMapping().getResourceAttribute();
	}

	@Override
	public JavaMappingJoinTableRelationship getRelationship() {
		return this.parent;
	}

	public boolean isOverridable() {
		return this.getJpaPlatformVariation().isJoinTableOverridable();
	}

	public RelationshipStrategy selectOverrideStrategy(OverrideRelationship2_0 overrideRelationship) {
		return overrideRelationship.getJoinTableStrategy();
	}


	// ********** validation **********

	public boolean validatesAgainstDatabase() {
		return this.getRelationshipMapping().validatesAgainstDatabase();
	}

	protected SpecifiedPersistentAttribute getPersistentAttribute() {
		return this.getRelationshipMapping().getPersistentAttribute();
	}

	public JpaValidator buildTableValidator(Table table) {
		return new JoinTableValidator(this.getPersistentAttribute(), (SpecifiedJoinTable) table);
	}

	public JpaValidator buildJoinTableJoinColumnValidator(JoinColumn column, JoinColumn.ParentAdapter parentAdapter) {
		return new JoinColumnValidator(this.getPersistentAttribute(), column, parentAdapter, new JoinTableTableDescriptionProvider());
	}

	public JpaValidator buildJoinTableInverseJoinColumnValidator(JoinColumn column, JoinColumn.ParentAdapter parentAdapter) {
		return new InverseJoinColumnValidator(this.getPersistentAttribute(), column, parentAdapter, new JoinTableTableDescriptionProvider());
	}
}
