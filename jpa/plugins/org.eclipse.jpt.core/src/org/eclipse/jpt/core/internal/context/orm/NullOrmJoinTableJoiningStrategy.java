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
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinColumn.Owner;
import org.eclipse.jpt.core.context.orm.OrmJoinTableEnabledRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmRelationshipMapping;
import org.eclipse.jpt.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.utility.TextRange;

public class NullOrmJoinTableJoiningStrategy 
	extends AbstractOrmJoinTableJoiningStrategy
{

	
	public NullOrmJoinTableJoiningStrategy(OrmJoinTableEnabledRelationshipReference parent) {
		super(parent);
		this.initialize();
	}
	
	
	@Override
	public OrmJoinTableEnabledRelationshipReference getParent() {
		return (OrmJoinTableEnabledRelationshipReference) super.getParent();
	}
	
	@Override
	public OrmJoinTableEnabledRelationshipReference getRelationshipReference() {
		return this.getParent();
	}
	
	@Override
	public OrmRelationshipMapping getRelationshipMapping() {
		return this.getRelationshipReference().getRelationshipMapping();
	}

	@Override
	protected void setResourceJoinTable(XmlJoinTable resourceJoinTable) {
		throw new UnsupportedOperationException();
	}
	
	public boolean isOverridableAssociation() {
		return false;
	}
	
	public boolean shouldValidateAgainstDatabase() {
		return false;
	}
	
	// **************** join table *********************************************

	
	public void removeResourceJoinTable() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	protected boolean mayHaveJoinTable() {
		return false;
	}
	
	public XmlJoinTable getResourceJoinTable() {
		return null;
	}
	
	
	// **************** validation *********************************************
	
	public TextRange getValidationTextRange() {
		return null;
	}

	public JptValidator buildJoinTableJoinColumnValidator(JoinColumn column, Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
		throw new UnsupportedOperationException();
	}

	public JptValidator buildJoinTableInverseJoinColumnValidator(JoinColumn column, Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
		throw new UnsupportedOperationException();
	}
}
