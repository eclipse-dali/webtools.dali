/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.context;

import org.eclipse.jpt.core.JpaNode;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;

public interface JpaContextNode extends JpaNode
{
	PersistenceUnit persistenceUnit();
	
	/**
	 * Return the EntityMappings if this contextNode is within an orm.xml context
	 * Return null otherwise.
	 */
	EntityMappings entityMappings();

	OrmPersistentType ormPersistentType();
}
