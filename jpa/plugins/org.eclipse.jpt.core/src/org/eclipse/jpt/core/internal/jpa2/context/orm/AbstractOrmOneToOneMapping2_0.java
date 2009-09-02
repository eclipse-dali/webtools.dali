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

import java.util.List;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmSingleRelationshipMapping;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmOneToOneMapping;
import org.eclipse.jpt.core.jpa2.context.orm.OrmDerivedId2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOneToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToOne;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractOrmOneToOneMapping2_0<T extends XmlOneToOne>
	extends AbstractOrmOneToOneMapping<T> 
	implements OrmOneToOneMapping2_0
{
	protected OrmDerivedId2_0 derivedId;
	
	
	protected AbstractOrmOneToOneMapping2_0(OrmPersistentAttribute parent, T resourceMapping) {
		super(parent, resourceMapping);
		this.derivedId = buildDerivedId();
	}
	
	
	protected OrmDerivedId2_0 buildDerivedId() {
		return new GenericOrmDerivedId2_0(this, this.resourceAttributeMapping);
	}
	
	public OrmDerivedId2_0 getDerivedId() {
		return this.derivedId;
	}
	
	@Override
	public void initializeFromOrmSingleRelationshipMapping(OrmSingleRelationshipMapping oldMapping) {
		super.initializeFromOrmSingleRelationshipMapping(oldMapping);
		getDerivedId().setValue(((OrmSingleRelationshipMapping2_0) oldMapping).getDerivedId().getValue());
	}
	
	@Override
	public void update() {
		super.update();
		this.derivedId.update();
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		// TODO derived id validation
	}
}
