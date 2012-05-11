/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.jpa2.resource.java.OneToMany2_0Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * javax.persistence.OneToMany
 */
public final class NullOneToManyAnnotation
	extends NullOwnableRelationshipMappingAnnotation
	implements OneToMany2_0Annotation
{
	public NullOneToManyAnnotation(JavaResourcePersistentAttribute parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}


	// ********** JPA 2.0 - OrphanRemovable2_0 implementation **********
	public Boolean getOrphanRemoval() {
		return null;
	}

	public void setOrphanRemoval(Boolean orphanRemoval) {
		if (orphanRemoval != null) {
			this.addAnnotation().setOrphanRemoval(orphanRemoval);
		}
	}

	public TextRange getOrphanRemovalTextRange(CompilationUnit astRoot) {
		return null;
	}

	@Override
	protected OneToMany2_0Annotation addAnnotation() {
		return (OneToMany2_0Annotation) super.addAnnotation();
	}
	
	// ***** cascade detach - JPA 2.0
	
	public boolean isCascadeDetach() {
		return false;
	}
	
	public void setCascadeDetach(boolean detach) {
		this.addAnnotation().setCascadeDetach(detach);
	}
}
