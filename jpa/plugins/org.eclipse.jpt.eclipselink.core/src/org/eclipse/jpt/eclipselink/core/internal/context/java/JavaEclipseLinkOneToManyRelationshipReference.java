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
package org.eclipse.jpt.eclipselink.core.internal.context.java;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaOneToManyRelationshipReference;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaTargetForiegnKeyJoinColumnJoiningStrategy;
import org.eclipse.jpt.eclipselink.core.v2_0.context.EclipseLinkOneToManyRelationshipReference2_0;

public class JavaEclipseLinkOneToManyRelationshipReference
	extends AbstractJavaOneToManyRelationshipReference
	implements EclipseLinkOneToManyRelationshipReference2_0
{
	public JavaEclipseLinkOneToManyRelationshipReference(
			JavaEclipseLinkOneToManyMapping parent) {
		super(parent);
	}
	
	
	@Override
	protected JavaJoinColumnJoiningStrategy buildJoinColumnJoiningStrategy() {
		return new GenericJavaTargetForiegnKeyJoinColumnJoiningStrategy(this);
	}
	
	@Override
	public JavaEclipseLinkOneToManyMapping getRelationshipMapping() {
		return (JavaEclipseLinkOneToManyMapping) getParent();
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
	
	
	// **************** mapped by **********************************************
	
	@Override
	public boolean mayBeMappedBy(AttributeMapping mappedByMapping) {
		return super.mayBeMappedBy(mappedByMapping) ||
			mappedByMapping.getKey() == MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}
}
