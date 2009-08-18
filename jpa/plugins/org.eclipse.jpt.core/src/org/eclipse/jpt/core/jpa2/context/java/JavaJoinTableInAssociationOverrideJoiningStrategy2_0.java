/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.jpa2.context.java;

import org.eclipse.jpt.core.context.java.JavaJoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.jpa2.resource.java.AssociationOverride2_0Annotation;

public interface JavaJoinTableInAssociationOverrideJoiningStrategy2_0 
	extends 
		JavaJpaContextNode,
		JavaJoinTableJoiningStrategy
{
	
	void initialize(AssociationOverride2_0Annotation associationOverride);
	
	void update(AssociationOverride2_0Annotation associationOverride);

}
