/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java;

import org.eclipse.jpt.jpa.core.internal.resource.java.NullJoinTableAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.AssociationOverrideAnnotation2_0;
import org.eclipse.jpt.jpa.core.resource.java.JoinTableAnnotation;

/**
 * <code>javax.persistence.JoinTable</code> found in a
 * <code>javax.persistence.AssociationOverride</code> annotation
 */
public final class NullAssociationOverrideJoinTableAnnotation2_0
	extends NullJoinTableAnnotation
{
	public NullAssociationOverrideJoinTableAnnotation2_0(AssociationOverrideAnnotation2_0 parent) {
		super(parent);
	}

	private AssociationOverrideAnnotation2_0 getAssociationOverride2_0Annotation() {
		return (AssociationOverrideAnnotation2_0) this.parent;
	}

	@Override
	protected JoinTableAnnotation addAnnotation() {
		return this.getAssociationOverride2_0Annotation().addJoinTable();
	}
}
