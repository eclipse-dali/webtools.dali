/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.java.JavaJoinTableEnabledRelationshipReference;
import org.eclipse.jpt.core.context.java.JavaRelationshipMapping;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JoinTableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;

public class GenericJavaJoinTableJoiningStrategy 
	extends AbstractJavaJoinTableJoiningStrategy
{
	protected JavaResourcePersistentAttribute resourcePersistentAttribute;
	
	
	public GenericJavaJoinTableJoiningStrategy(JavaJoinTableEnabledRelationshipReference parent) {
		super(parent);
	}
	
	public boolean isOverridableAssociation() {
		return getJpaPlatformVariation().isJoinTableOverridable();
	}
	
	@Override
	public JavaJoinTableEnabledRelationshipReference getParent() {
		return (JavaJoinTableEnabledRelationshipReference) super.getParent();
	}
	
	@Override
	public JavaJoinTableEnabledRelationshipReference getRelationshipReference() {
		return this.getParent();
	}
	
	@Override
	public JavaRelationshipMapping getRelationshipMapping() {
		return this.getRelationshipReference().getRelationshipMapping();
	}
	
	
	// **************** join table *********************************************
	
	@Override
	protected JoinTableAnnotation addAnnotation() {
		return (JoinTableAnnotation) this.resourcePersistentAttribute.
			addSupportingAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
	}
	
	@Override
	protected void removeAnnotation() {
		this.resourcePersistentAttribute.
			removeSupportingAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
	}
	
	
	// **************** resource => context ************************************

	@Override
	public void initialize() {
		this.resourcePersistentAttribute = 
			getRelationshipReference().getRelationshipMapping().
				getPersistentAttribute().getResourcePersistentAttribute();
		super.initialize();
	}
	
	@Override
	public void update() {
		this.resourcePersistentAttribute = 
			getRelationshipReference().getRelationshipMapping().
				getPersistentAttribute().getResourcePersistentAttribute();
		super.update();
	}
	
	public JoinTableAnnotation getAnnotation() {
		return 	(JoinTableAnnotation) 
			this.resourcePersistentAttribute.getNonNullSupportingAnnotation(
				JoinTableAnnotation.ANNOTATION_NAME);
	}
	
	
	// **************** validation *********************************************
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getRelationshipReference().getValidationTextRange(astRoot);
	}
}
