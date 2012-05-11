/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaJoinColumnEnabledRelationshipReference;

public class GenericJavaJoinColumnJoiningStrategy 
	extends AbstractJavaJoinColumnInRelationshipMappingJoiningStrategy
{

	public GenericJavaJoinColumnJoiningStrategy(JavaJoinColumnEnabledRelationshipReference parent) {
		super(parent);
	}

	public TypeMapping getRelationshipSource() {
		return getRelationshipMapping().getTypeMapping();
	}

	public TypeMapping getRelationshipTarget() {
		return getRelationshipTargetEntity();
	}

	@Override
	protected Entity getRelationshipTargetEntity() {
		return getRelationshipMapping().getResolvedTargetEntity();
	}

	public boolean isTargetForeignKeyRelationship() {
		return false;
	}
}
