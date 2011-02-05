/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.core.context.ReadOnlyAssociationOverride;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaOverrideRelationship;
import org.eclipse.jpt.core.context.java.JavaVirtualAssociationOverride;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaOverride;
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Specified Java association override
 */
public class GenericJavaAssociationOverride
	extends AbstractJavaOverride<JavaAssociationOverrideContainer, AssociationOverrideAnnotation>
	implements JavaAssociationOverride
{
	protected final JavaOverrideRelationship relationship;


	public GenericJavaAssociationOverride(JavaAssociationOverrideContainer parent, AssociationOverrideAnnotation annotation) {
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

	public JavaOverrideRelationship getRelationship() {
		return this.relationship;
	}

	protected JavaOverrideRelationship buildRelationship() {
		return this.getJpaFactory().buildJavaOverrideRelationship(this);
	}


	// ********** misc **********

	public RelationshipMapping getMapping() {
		return this.getContainer().getRelationshipMapping(this.name);
	}

	public void initializeFrom(ReadOnlyAssociationOverride oldOverride) {
		super.initializeFrom(oldOverride);
		this.relationship.initializeFrom(oldOverride.getRelationship());
	}

	public void initializeFromVirtual(ReadOnlyAssociationOverride virtualOverride) {
		super.initializeFromVirtual(virtualOverride);
		this.relationship.initializeFromVirtual(virtualOverride.getRelationship());
	}


	// ********** Java completion proposals **********

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.relationship.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return null;
	}

	@Override
	protected Iterator<String> candidateNames() {
		return this.getContainer().allOverridableNames();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.relationship.validate(messages, reporter, astRoot);
	}
}
