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
package org.eclipse.jpt.core.jpa2.context.orm;

import org.eclipse.jpt.core.context.orm.OrmJoinTableEnabledRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmManyToOneRelationshipReference;
import org.eclipse.jpt.core.jpa2.context.ManyToOneRelationshipReference2_0;

public interface OrmManyToOneRelationshipReference2_0
	extends 
		OrmManyToOneRelationshipReference,
		ManyToOneRelationshipReference2_0,
		OrmJoinTableEnabledRelationshipReference
{

}
