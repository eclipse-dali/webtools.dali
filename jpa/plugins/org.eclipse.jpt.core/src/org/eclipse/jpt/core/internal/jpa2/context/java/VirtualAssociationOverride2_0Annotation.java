/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import org.eclipse.jpt.core.context.JoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.internal.context.java.VirtualAssociationOverrideAnnotation;
import org.eclipse.jpt.core.internal.resource.java.NullJoinTableAnnotation;
import org.eclipse.jpt.core.jpa2.resource.java.AssociationOverride2_0Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JoinTableAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember.AnnotationInitializer;

/**
 * javax.persistence.AssociationOverride
 */
public final class VirtualAssociationOverride2_0Annotation
	extends VirtualAssociationOverrideAnnotation
	implements AssociationOverride2_0Annotation
{

	private final JoinTableAnnotation joinTable;
	
	public VirtualAssociationOverride2_0Annotation(JavaResourcePersistentType parent, String name, JoiningStrategy joiningStrategy) {
		super(parent, name, joiningStrategy);
		this.joinTable = this.buildJoinTable();
	}
	
	protected JoinTableAnnotation buildJoinTable() {
		if (this.joiningStrategy instanceof JoinTableJoiningStrategy) {
			return new VirtualAssociationOverrideJoinTableAnnotation(this, ((JoinTableJoiningStrategy)this.joiningStrategy).getJoinTable());
		}
		return new NullJoinTableAnnotation(this);
	}

	// ***** join table
	
	public JoinTableAnnotation getJoinTable() {
		return this.joinTable;
	}

	public JoinTableAnnotation getNonNullJoinTable() {
		return this.joinTable;
	}

	public JoinTableAnnotation addJoinTable() {
		throw new UnsupportedOperationException();
	}
	
	public void removeJoinTable() {
		throw new UnsupportedOperationException();
	}

	public JoinColumnAnnotation addJoinTable(AnnotationInitializer initializer) {
		throw new UnsupportedOperationException();
	}
}
