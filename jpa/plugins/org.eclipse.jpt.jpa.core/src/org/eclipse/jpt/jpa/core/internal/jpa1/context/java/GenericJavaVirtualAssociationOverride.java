/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualOverrideRelationship;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaVirtualOverride;

/**
 * Virtual Java association override
 */
public class GenericJavaVirtualAssociationOverride
	extends AbstractJavaVirtualOverride<JavaAssociationOverrideContainer>
	implements JavaVirtualAssociationOverride
{
	protected final JavaVirtualOverrideRelationship relationship;


	public GenericJavaVirtualAssociationOverride(JavaAssociationOverrideContainer parent, String name) {
		super(parent, name);
		this.relationship = this.buildRelationship();
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

	public JavaVirtualOverrideRelationship getRelationship() {
		return this.relationship;
	}

	/**
	 * The relationship should be available (since its presence precipitated the
	 * creation of the virtual override).
	 */
	protected JavaVirtualOverrideRelationship buildRelationship() {
		return this.getJpaFactory().buildJavaVirtualOverrideRelationship(this);
	}

	public Relationship resolveOverriddenRelationship() {
		return this.getContainer().resolveOverriddenRelationship(this.name);
	}
}
