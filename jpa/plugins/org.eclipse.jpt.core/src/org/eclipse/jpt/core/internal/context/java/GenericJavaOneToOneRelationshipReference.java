/*******************************************************************************
 *  Copyright (c) 2009, 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaJoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.core.internal.jpa1.context.java.NullJavaJoinTableJoiningStrategy;

public class GenericJavaOneToOneRelationshipReference
	extends AbstractJavaOneToOneRelationshipReference
{
	
	public GenericJavaOneToOneRelationshipReference(JavaOneToOneMapping parent) {
		super(parent);
	}

	@Override
	protected JavaJoinTableJoiningStrategy buildJoinTableJoiningStrategy() {
		return new NullJavaJoinTableJoiningStrategy(this);
	}

	@Override
	protected JoiningStrategy calculatePredominantJoiningStrategy() {
		if (this.mappedByJoiningStrategy.getMappedByAttribute() != null) {
			return this.mappedByJoiningStrategy;
		}
		else if (this.primaryKeyJoinColumnJoiningStrategy.primaryKeyJoinColumnsSize() > 0) {
			return this.primaryKeyJoinColumnJoiningStrategy;
		}
		return this.joinColumnJoiningStrategy;
	}
}
