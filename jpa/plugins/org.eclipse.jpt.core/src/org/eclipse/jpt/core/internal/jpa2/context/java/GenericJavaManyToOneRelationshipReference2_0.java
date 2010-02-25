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
import org.eclipse.jpt.core.internal.context.java.AbstractJavaManyToOneRelationshipReference;
import org.eclipse.jpt.core.internal.context.java.GenericJavaJoinTableJoiningStrategy;
import org.eclipse.jpt.core.jpa2.context.java.JavaManyToOneMapping2_0;

public class GenericJavaManyToOneRelationshipReference2_0
	extends AbstractJavaManyToOneRelationshipReference
{

	public GenericJavaManyToOneRelationshipReference2_0(JavaManyToOneMapping2_0 parent) {
		super(parent);
	}

	@Override
	protected JavaJoinTableJoiningStrategy buildJoinTableJoiningStrategy() {
		return new GenericJavaJoinTableJoiningStrategy(this);
	}

	@Override
	protected JoiningStrategy calculatePredominantJoiningStrategy() {
		if (this.joinTableJoiningStrategy.getJoinTable() != null) {
			return this.joinTableJoiningStrategy;
		}
		return this.joinColumnJoiningStrategy;
	}

}
