/*******************************************************************************
 *  Copyright (c) 2008 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.context.orm;

import org.eclipse.jpt.core.context.QueryHint;
import org.eclipse.jpt.core.resource.orm.XmlQueryHint;

public interface OrmQueryHint extends QueryHint, OrmJpaContextNode
{
	void initialize(XmlQueryHint queryHint);

	void update(XmlQueryHint queryHint);
}