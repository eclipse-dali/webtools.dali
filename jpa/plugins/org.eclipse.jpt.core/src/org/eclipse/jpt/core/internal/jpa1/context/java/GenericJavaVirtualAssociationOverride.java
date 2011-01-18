/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.RelationshipReference;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaVirtualAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaVirtualAssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaVirtualOverride;

/**
 * Virtual Java association override
 */
public class GenericJavaVirtualAssociationOverride
	extends AbstractJavaVirtualOverride<JavaAssociationOverrideContainer>
	implements JavaVirtualAssociationOverride
{
	protected final JavaVirtualAssociationOverrideRelationshipReference relationship;


	public GenericJavaVirtualAssociationOverride(JavaAssociationOverrideContainer parent, String name) {
		super(parent, name);
		this.relationship = this.buildRelationshipReference();
	}

	@Override
	public void update() {
		super.update();
		this.relationship.update();
	}

	@Override
	public JavaAssociationOverride convertToSpecified() {
		return (JavaAssociationOverride) super.convertToSpecified();
	}

	public RelationshipMapping getMapping() {
		return this.getContainer().getRelationshipMapping(this.name);
	}


	// ********** relationship **********

	public JavaVirtualAssociationOverrideRelationshipReference getRelationshipReference() {
		return this.relationship;
	}

	/**
	 * The relationship should be available (since its presence precipitated the
	 * creation of the virtual override).
	 */
	protected JavaVirtualAssociationOverrideRelationshipReference buildRelationshipReference() {
		return this.getJpaFactory().buildJavaVirtualAssociationOverrideRelationshipReference(this);
	}

	public RelationshipReference resolveOverriddenRelationship() {
		return this.getContainer().resolveOverriddenRelationship(this.name);
	}
}
