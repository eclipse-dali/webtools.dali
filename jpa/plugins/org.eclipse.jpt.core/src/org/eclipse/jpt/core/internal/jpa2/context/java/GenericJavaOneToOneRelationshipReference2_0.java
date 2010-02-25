/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaJoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaOneToOneRelationshipReference;
import org.eclipse.jpt.core.internal.context.java.GenericJavaJoinTableJoiningStrategy;

public class GenericJavaOneToOneRelationshipReference2_0
	extends AbstractJavaOneToOneRelationshipReference
{
	
	public GenericJavaOneToOneRelationshipReference2_0(JavaOneToOneMapping parent) {
		super(parent);
	}

	@Override
	protected JavaJoinTableJoiningStrategy buildJoinTableJoiningStrategy() {
		return new GenericJavaJoinTableJoiningStrategy(this);
	}

	@Override
	protected JoiningStrategy calculatePredominantJoiningStrategy() {
		if (this.mappedByJoiningStrategy.getMappedByAttribute() != null) {
			return this.mappedByJoiningStrategy;
		}
		else if (this.primaryKeyJoinColumnJoiningStrategy.primaryKeyJoinColumnsSize() > 0) {
			return this.primaryKeyJoinColumnJoiningStrategy;
		}
		else if (this.joinTableJoiningStrategy.getJoinTable() != null) {
			return this.joinTableJoiningStrategy;
		}
		return this.joinColumnJoiningStrategy;
	}
}
