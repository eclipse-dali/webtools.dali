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

import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.resource.orm.XmlId;

public interface OrmIdMapping extends IdMapping, OrmColumnMapping, OrmAttributeMapping 
{
	OrmGeneratedValue getGeneratedValue();
	OrmGeneratedValue addGeneratedValue();	
	
	OrmSequenceGenerator getSequenceGenerator();
	OrmSequenceGenerator addSequenceGenerator();
	
	OrmTableGenerator getTableGenerator();
	OrmTableGenerator addTableGenerator();

	void initialize(XmlId id);

	void update(XmlId id);
}