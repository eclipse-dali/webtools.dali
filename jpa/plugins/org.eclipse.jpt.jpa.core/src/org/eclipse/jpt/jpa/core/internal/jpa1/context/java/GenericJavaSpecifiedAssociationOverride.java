/*******************************************************************************
 * Copyright (c) 2008, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinTable;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedOverrideRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaSpecifiedOverride;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaAssociationOverrideContainer2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaSpecifiedAssociationOverride2_0;
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
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.relationship.synchronizeWithResourceModel(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.relationship.update(monitor);
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

	public void initializeFrom(JavaVirtualAssociationOverride virtualOverride) {
		super.initializeFrom(virtualOverride);
		this.relationship.initializeFrom(virtualOverride.getRelationship());
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
