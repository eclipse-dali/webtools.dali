/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt2_0.core.internal.resource.java;

import org.eclipse.jpt.core.internal.resource.java.NullJoinTableAnnotation;
import org.eclipse.jpt.core.resource.java.JoinTableAnnotation;
import org.eclipse.jpt2_0.core.resource.java.AssociationOverride2_0Annotation;

/**
 * javax.persistence.Column
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

}
