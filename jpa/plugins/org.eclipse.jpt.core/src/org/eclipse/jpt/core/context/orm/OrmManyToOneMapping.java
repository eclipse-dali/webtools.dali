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

import java.util.ListIterator;
import org.eclipse.jpt.core.context.ManyToOneMapping;
import org.eclipse.jpt.core.resource.orm.XmlManyToOne;

public interface OrmManyToOneMapping extends ManyToOneMapping, OrmSingleRelationshipMapping
{
	ListIterator<OrmJoinColumn> joinColumns();
	
	ListIterator<OrmJoinColumn> specifiedJoinColumns();

	void initialize(XmlManyToOne xmlManyToOne);

	void update(XmlManyToOne xmlManyToOne);

}