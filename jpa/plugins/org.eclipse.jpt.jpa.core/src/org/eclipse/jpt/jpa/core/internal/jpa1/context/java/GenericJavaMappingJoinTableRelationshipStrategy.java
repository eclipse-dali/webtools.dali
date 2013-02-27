/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.jpa.core.context.JoinTable;
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.context.java.JavaMappingJoinTableRelationship;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.InverseJoinColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.JoinColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.JoinTableTableDescriptionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.JoinTableValidator;
import org.eclipse.jpt.jpa.core.jpa2.context.MappingRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ReadOnlyOverrideRelationship2_0;
import org.eclipse.jpt.jpa.core.resource.java.JoinTableAnnotation;

public class GenericJavaMappingJoinTableRelationshipStrategy
	extends AbstractJavaJoinTableRelationshipStrategy<JavaMappingJoinTableRelationship>
	implements MappingRelationshipStrategy2_0
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

	public ReadOnlyRelationshipStrategy selectOverrideStrategy(ReadOnlyOverrideRelationship2_0 overrideRelationship) {
		return overrideRelationship.getJoinTableStrategy();
	}


	// ********** validation **********

	public boolean validatesAgainstDatabase() {
		return this.getRelationshipMapping().validatesAgainstDatabase();
	}

	protected SpecifiedPersistentAttribute getPersistentAttribute() {
		return this.getRelationshipMapping().getPersistentAttribute();
	}

	public JptValidator buildTableValidator(ReadOnlyTable table) {
		return new JoinTableValidator(this.getPersistentAttribute(), (JoinTable) table);
	}

	public JptValidator buildJoinTableJoinColumnValidator(JoinColumn column, JoinColumn.Owner owner) {
		return new JoinColumnValidator(this.getPersistentAttribute(), column, owner, new JoinTableTableDescriptionProvider());
	}

	public JptValidator buildJoinTableInverseJoinColumnValidator(JoinColumn column, JoinColumn.Owner owner) {
		return new InverseJoinColumnValidator(this.getPersistentAttribute(), column, owner, new JoinTableTableDescriptionProvider());
	}
}
