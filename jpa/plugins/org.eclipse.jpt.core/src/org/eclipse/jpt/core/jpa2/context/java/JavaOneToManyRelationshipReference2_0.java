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
package org.eclipse.jpt.core.jpa2.context.java;

import org.eclipse.jpt.core.context.RelationshipReference;
import org.eclipse.jpt.core.context.java.JavaJoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.context.java.JavaOneToManyRelationshipReference;
import org.eclipse.jpt.core.jpa2.context.OneToManyRelationshipReference2_0;

/**
 * Represents the {@link RelationshipReference} of a {@link JavaOneToManyMapping}
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JavaOneToManyRelationshipReference2_0
	extends 
		JavaOneToManyRelationshipReference, 
		JavaJoinColumnEnabledRelationshipReference, 
		OneToManyRelationshipReference2_0
{
	
}
