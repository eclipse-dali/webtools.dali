/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaAssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaOverride;
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaAssociationOverride extends AbstractJavaOverride
	implements JavaAssociationOverride
{

	protected final JavaAssociationOverrideRelationshipReference relationshipReference;

	public GenericJavaAssociationOverride(JavaAssociationOverrideContainer parent, JavaAssociationOverride.Owner owner) {
		super(parent, owner);
		this.relationshipReference = buildRelationshipReference();
	}
	
	public void initializeFrom(AssociationOverride oldAssociationOverride) {
		this.setName(oldAssociationOverride.getName());
		this.relationshipReference.initializeFrom(oldAssociationOverride.getRelationshipReference());
	}
	
	protected JavaAssociationOverrideRelationshipReference buildRelationshipReference() {
		return getJpaFactory().buildJavaAssociationOverrideRelationshipReference(this);
	}
	
	public JavaAssociationOverrideRelationshipReference getRelationshipReference() {
		return this.relationshipReference;
	}
	
	@Override
	public JavaAssociationOverride setVirtual(boolean virtual) {
		return (JavaAssociationOverride) super.setVirtual(virtual);
	}

	@Override
	protected AssociationOverrideAnnotation getOverrideAnnotation() {
		return (AssociationOverrideAnnotation) super.getOverrideAnnotation();
	}
	
	@Override
	public JavaAssociationOverride.Owner getOwner() {
		return (JavaAssociationOverride.Owner) super.getOwner();
	}

	@Override
	protected Iterator<String> candidateNames() {
		return this.getOwner().allOverridableAttributeNames();
	}

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.relationshipReference.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return null;
	}

	public void initialize(AssociationOverrideAnnotation associationOverride) {
		super.initialize(associationOverride);
		this.relationshipReference.initialize(associationOverride);
	}		

	public void update(AssociationOverrideAnnotation associationOverride) {
		super.update(associationOverride);
		this.relationshipReference.update(associationOverride);
	}
	
	
	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.relationshipReference.validate(messages, reporter, astRoot);
	}
}
