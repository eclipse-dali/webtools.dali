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
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.jpa2.context.orm.OrmDerivedId2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.utility.TextRange;

public class NullOrmDerivedId2_0 
	extends AbstractOrmXmlContextNode
	implements OrmDerivedId2_0
{
	
	public NullOrmDerivedId2_0(OrmSingleRelationshipMapping2_0 parent) {
		super(parent);
	}
	
	public boolean getValue() {
		return false;
	}
	
	public void setValue(boolean newValue) {
		//no-op - setValue is called when morphing a mapping, we can't throw an UnsupportedOperationException
	}

	public void update() {
		//no-op
	}

	public TextRange getValidationTextRange() {
		return null;
	}
}
