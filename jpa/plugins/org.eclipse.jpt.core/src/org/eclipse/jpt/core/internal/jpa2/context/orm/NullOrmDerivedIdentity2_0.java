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

import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.jpa2.context.DerivedIdentityStrategy2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmDerivedIdentity2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmMapsIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.utility.TextRange;

public class NullOrmDerivedIdentity2_0 extends AbstractXmlContextNode
	implements OrmDerivedIdentity2_0
{
	public NullOrmDerivedIdentity2_0(OrmSingleRelationshipMapping2_0 parent) {
		super(parent);
	}
	
	
	public OrmSingleRelationshipMapping2_0 getMapping() {
		return (OrmSingleRelationshipMapping2_0) getParent();
	}
	
	public void update() {
		// no op
	}
	
	public DerivedIdentityStrategy2_0 getPredominantDerivedIdentityStrategy() {
		return null;
	}
	
	public OrmMapsIdDerivedIdentityStrategy2_0 getMapsIdDerivedIdentityStrategy() {
		return null;
	}
	
	public boolean usesMapsIdDerivedIdentityStrategy() {
		return false;
	}
	
	public void setMapsIdDerivedIdentityStrategy() {
		// no op
	}
	
	public void unsetMapsIdDerivedIdentityStrategy() {
		// no op
	}
	
	public OrmIdDerivedIdentityStrategy2_0 getIdDerivedIdentityStrategy() {
		return null;
	}
	
	public boolean usesIdDerivedIdentityStrategy() {
		return false;
	}
	
	public void setIdDerivedIdentityStrategy() {
		// no op
	}
	
	public void unsetIdDerivedIdentityStrategy() {
		// no op
	}
	
	public boolean usesNullDerivedIdentityStrategy() {
		return true;
	}
	
	public void setNullDerivedIdentityStrategy() {
		// no op
	}
	
	public void initializeFrom(OrmDerivedIdentity2_0 oldDerivedIdentity) {
		// no op
	}
	
	public TextRange getValidationTextRange() {
		return null;
	}
}
