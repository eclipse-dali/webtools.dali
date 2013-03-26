/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.List;
import org.eclipse.jpt.jpa.core.context.AssociationOverride;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinTable;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedOverrideRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaSpecifiedOverride;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaSpecifiedAssociationOverride2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaAssociationOverrideContainer2_0;
import org.eclipse.jpt.jpa.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Specified Java association override
 */
public class GenericJavaSpecifiedAssociationOverride
	extends AbstractJavaSpecifiedOverride<JavaAssociationOverrideContainer, AssociationOverrideAnnotation>
	implements JavaSpecifiedAssociationOverride2_0
{
	protected final JavaSpecifiedOverrideRelationship relationship;


	public GenericJavaSpecifiedAssociationOverride(JavaAssociationOverrideContainer parent, AssociationOverrideAnnotation annotation) {
		super(parent, annotation);
		this.relationship = this.buildRelationship();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.relationship.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.relationship.update();
	}


	// ********** specified/virtual **********

	@Override
	public JavaVirtualAssociationOverride convertToVirtual() {
		return (JavaVirtualAssociationOverride) super.convertToVirtual();
	}


	// ********** relationship **********

	public JavaSpecifiedOverrideRelationship getRelationship() {
		return this.relationship;
	}

	protected JavaSpecifiedOverrideRelationship buildRelationship() {
		return this.getJpaFactory().buildJavaOverrideRelationship(this);
	}


	// ********** misc **********

	@Override
	protected JavaAssociationOverrideContainer2_0 getContainer2_0() {
		return (JavaAssociationOverrideContainer2_0) super.getContainer2_0();
	}

	public RelationshipMapping getMapping() {
		return this.getContainer().getRelationshipMapping(this.name);
	}

	public void initializeFrom(AssociationOverride oldOverride) {
		super.initializeFrom(oldOverride);
		this.relationship.initializeFrom(oldOverride.getRelationship());
	}

	public void initializeFromVirtual(AssociationOverride virtualOverride) {
		super.initializeFromVirtual(virtualOverride);
		this.relationship.initializeFromVirtual(virtualOverride.getRelationship());
	}


	// ********** Java completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.relationship.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		return null;
	}

	@Override
	protected Iterable<String> getCandidateNames() {
		return this.getContainer().getAllOverridableNames();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.relationship.validate(messages, reporter);
	}

	public JpaValidator buildJoinTableValidator(JoinTable table) {
		return this.getContainer2_0().buildJoinTableValidator(this, table);
	}

	public JpaValidator buildJoinTableJoinColumnValidator(JoinColumn column, JoinColumn.ParentAdapter parentAdapter) {
		return this.getContainer2_0().buildJoinTableJoinColumnValidator(this, column, parentAdapter);
	}

	public JpaValidator buildJoinTableInverseJoinColumnValidator(JoinColumn column, JoinColumn.ParentAdapter parentAdapter) {
		return this.getContainer2_0().buildJoinTableInverseJoinColumnValidator(this, column, parentAdapter);
	}
}
