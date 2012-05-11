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
import org.eclipse.jpt.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.core.internal.jpa1.context.java.NullJavaJoinTableJoiningStrategy;

public class GenericJavaManyToOneRelationshipReference
	extends AbstractJavaManyToOneRelationshipReference
{

	public GenericJavaManyToOneRelationshipReference(JavaManyToOneMapping parent) {
		super(parent);
	}

	@Override
	protected JavaJoinTableJoiningStrategy buildJoinTableJoiningStrategy() {
		return new NullJavaJoinTableJoiningStrategy(this);
	}

	@Override
	protected JoiningStrategy calculatePredominantJoiningStrategy() {
		// the only joining strategy
		return this.joinColumnJoiningStrategy;
	}

}
