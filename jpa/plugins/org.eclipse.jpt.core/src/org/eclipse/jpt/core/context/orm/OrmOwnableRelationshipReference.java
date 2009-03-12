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
package org.eclipse.jpt.core.context.orm;

import org.eclipse.jpt.core.context.OwnableRelationshipReference;
import org.eclipse.jpt.core.internal.context.orm.OrmMappedByJoiningStrategy;
import org.eclipse.jpt.core.resource.orm.XmlMappedByMapping;

public interface OrmOwnableRelationshipReference
	extends OrmRelationshipReference, OwnableRelationshipReference
{
	XmlMappedByMapping getResourceMapping();
	
	OrmMappedByJoiningStrategy getMappedByJoiningStrategy();
}
