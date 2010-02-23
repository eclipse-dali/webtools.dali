/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaOneToManyRelationshipReference;

public class GenericJavaOneToManyRelationshipReference
	extends AbstractJavaOneToManyRelationshipReference
{	
	protected final JavaJoinColumnJoiningStrategy joinColumnJoiningStrategy;

	public GenericJavaOneToManyRelationshipReference(JavaOneToManyMapping parent) {
		super(parent);
		this.joinColumnJoiningStrategy = buildJoinColumnJoiningStrategy();
	}	

	protected JavaJoinColumnJoiningStrategy buildJoinColumnJoiningStrategy() {
		return new NullJavaJoinColumnJoiningStrategy(this);
	}

	// **************** join columns *******************************************

	public JavaJoinColumnJoiningStrategy getJoinColumnJoiningStrategy() {
		return this.joinColumnJoiningStrategy;
	}

	public boolean usesJoinColumnJoiningStrategy() {
		return false;
	}

	public void setJoinColumnJoiningStrategy() {
		throw new UnsupportedOperationException("join column joining strategy not supported on a 1.0 1-m mapping"); //$NON-NLS-1$
	}

	public void unsetJoinColumnJoiningStrategy() {
		throw new UnsupportedOperationException("join column joining strategy not supported on a 1.0 1-m mapping"); //$NON-NLS-1$
	}

	public boolean mayHaveDefaultJoinColumn() {
		return false;
	}


	@Override
	protected JoiningStrategy calculatePredominantJoiningStrategy() {
		if (this.mappedByJoiningStrategy.getMappedByAttribute() != null) {
			return this.mappedByJoiningStrategy;
		}
		return this.joinTableJoiningStrategy;
	}
}
