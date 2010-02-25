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

import org.eclipse.jpt.core.context.java.JavaJoinTableEnabledRelationshipReference;
import org.eclipse.jpt.core.context.java.JavaManyToOneRelationshipReference;
import org.eclipse.jpt.core.jpa2.context.ManyToOneRelationshipReference2_0;

public interface JavaManyToOneRelationshipReference2_0
	extends 
		JavaManyToOneRelationshipReference,
		ManyToOneRelationshipReference2_0,
		JavaJoinTableEnabledRelationshipReference
{

}
