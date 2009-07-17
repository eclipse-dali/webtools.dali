/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.resource.java;

import org.eclipse.jpt.core.internal.resource.java.NullJoinTableAnnotation;
import org.eclipse.jpt.core.jpa2.resource.java.AssociationOverride2_0Annotation;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JoinTableAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember.AnnotationInitializer;

/**
 * javax.persistence.JoinTable found in a javax.persistence.AssociationOverride annotation
 */
public class NullAssociationOverrideJoinTableAnnotation
	extends NullJoinTableAnnotation
{
	public NullAssociationOverrideJoinTableAnnotation(AssociationOverride2_0Annotation parent) {
		super(parent);
	}

	private AssociationOverride2_0Annotation getAssociationOverride2_0Annotation() {
		return (AssociationOverride2_0Annotation) this.parent;
	}

	@Override
	protected JoinTableAnnotation addAnnotation() {
		return this.getAssociationOverride2_0Annotation().addJoinTable();
	}
	
	@Override
	protected JoinColumnAnnotation addAnnotation(AnnotationInitializer initializer) {
		return this.getAssociationOverride2_0Annotation().addJoinTable(initializer);
	}

}
