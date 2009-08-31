/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import org.eclipse.jpt.core.context.AssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaAssociationOverrideRelationshipReference;

public class GenericJavaAssociationOverrideRelationshipReference extends AbstractJavaAssociationOverrideRelationshipReference
{

	public GenericJavaAssociationOverrideRelationshipReference(JavaAssociationOverride parent) {
		super(parent);
	}
	
	public void initializeFrom(AssociationOverrideRelationshipReference oldAssociationOverride) {
		if (oldAssociationOverride.getJoinColumnJoiningStrategy().hasSpecifiedJoinColumns()) {
			getJoinColumnJoiningStrategy().initializeFrom(oldAssociationOverride.getJoinColumnJoiningStrategy());
		}
	}

	@Override
	protected JoiningStrategy calculatePredominantJoiningStrategy() {
		return this.joinColumnJoiningStrategy;
	}

}
