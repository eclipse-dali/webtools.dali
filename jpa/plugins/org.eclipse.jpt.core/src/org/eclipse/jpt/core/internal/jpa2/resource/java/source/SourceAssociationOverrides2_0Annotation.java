/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.resource.java.source;

import org.eclipse.jpt.core.internal.resource.java.source.SourceAssociationOverridesAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableAssociationOverrideAnnotation;
import org.eclipse.jpt.core.utility.jdt.Member;

/**
 * javax.persistence.AssociationOverrides
 */
public final class SourceAssociationOverrides2_0Annotation
	extends SourceAssociationOverridesAnnotation
{

	public SourceAssociationOverrides2_0Annotation(JavaResourceNode parent, Member member) {
		super(parent, member);
	}

	@Override
	protected NestableAssociationOverrideAnnotation buildAssociationOverride(int index) {
		return SourceAssociationOverride2_0Annotation.buildNestedAssociationOverride(this, this.member, index, this.daa);
	}

}
