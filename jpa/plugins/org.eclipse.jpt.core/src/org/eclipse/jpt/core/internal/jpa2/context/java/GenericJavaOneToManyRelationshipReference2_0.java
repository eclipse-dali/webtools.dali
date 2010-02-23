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
import org.eclipse.jpt.core.context.java.JavaJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaOneToManyRelationshipReference;
import org.eclipse.jpt.core.internal.context.java.GenericJavaJoinColumnJoiningStrategy;

public class GenericJavaOneToManyRelationshipReference2_0
	extends AbstractJavaOneToManyRelationshipReference
{	

	public GenericJavaOneToManyRelationshipReference2_0(JavaOneToManyMapping parent) {
		super(parent);
	}	

	@Override
	protected JavaJoinColumnJoiningStrategy buildJoinColumnJoiningStrategy() {
		return new GenericJavaJoinColumnJoiningStrategy(this);
	}

	@Override
	protected JoiningStrategy calculatePredominantJoiningStrategy() {
		if (this.mappedByJoiningStrategy.getMappedByAttribute() != null) {
			return this.mappedByJoiningStrategy;
		}
		else if (this.joinColumnJoiningStrategy.hasSpecifiedJoinColumns()) {
			return this.joinColumnJoiningStrategy;
		}
		else {
			return this.joinTableJoiningStrategy;
		}
	}
}
