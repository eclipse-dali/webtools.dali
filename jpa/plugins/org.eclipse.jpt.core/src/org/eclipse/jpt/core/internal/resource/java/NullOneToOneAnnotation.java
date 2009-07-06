/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.OneToOneAnnotation;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * javax.persistence.OneToOne
 */
public final class NullOneToOneAnnotation
	extends NullOwnableRelationshipMappingAnnotation
	implements OneToOneAnnotation
{
	protected NullOneToOneAnnotation(JavaResourcePersistentAttribute parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	protected OneToOneAnnotation setMappingAnnotation() {
		return (OneToOneAnnotation) super.setMappingAnnotation();
	}

	// ***** optional
	public Boolean getOptional() {
		return null;
	}

	public void setOptional(Boolean optional) {
		if (optional != null) {
			this.setMappingAnnotation().setOptional(optional);
		}
	}

	public TextRange getOptionalTextRange(CompilationUnit astRoot) {
		return null;
	}

}
