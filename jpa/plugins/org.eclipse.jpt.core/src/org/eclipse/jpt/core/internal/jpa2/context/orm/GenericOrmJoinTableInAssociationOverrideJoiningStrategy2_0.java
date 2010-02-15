/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.internal.context.orm.AbstractOrmJoinTableJoiningStrategy;
import org.eclipse.jpt.core.jpa2.context.orm.OrmAssociationOverrideRelationshipReference2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmJoinTableInAssociationOverrideJoiningStrategy2_0;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.utility.TextRange;

public class GenericOrmJoinTableInAssociationOverrideJoiningStrategy2_0 
	extends AbstractOrmJoinTableJoiningStrategy
	implements OrmJoinTableInAssociationOverrideJoiningStrategy2_0
{
	protected transient XmlAssociationOverride resourceAssociationOverride;
	
	public GenericOrmJoinTableInAssociationOverrideJoiningStrategy2_0(OrmAssociationOverrideRelationshipReference2_0 parent, XmlAssociationOverride xao) {
		super(parent);
		this.resourceAssociationOverride = xao;
		this.initialize();
	}
	
	public boolean isOverridableAssociation() {
		return false;
	}
	
	@Override
	public OrmAssociationOverrideRelationshipReference2_0 getRelationshipReference() {
		return (OrmAssociationOverrideRelationshipReference2_0) super.getRelationshipReference();
	}

	public boolean shouldValidateAgainstDatabase() {
		return getRelationshipReference().getTypeMapping().shouldValidateAgainstDatabase();
	}

	// **************** join table *********************************************

	@Override
	protected void setResourceJoinTable(XmlJoinTable resourceJoinTable) {
		this.resourceAssociationOverride.setJoinTable(resourceJoinTable);
	}

	public XmlJoinTable getResourceJoinTable() {
		return this.resourceAssociationOverride.getJoinTable();
	}

	public void removeResourceJoinTable() {
		this.resourceAssociationOverride.setJoinTable(null);
	}

	public void update(XmlAssociationOverride resourceAssociationOverride) {
		this.resourceAssociationOverride = resourceAssociationOverride;
		super.update();
	}

	public TextRange getValidationTextRange() {
		return this.resourceAssociationOverride.getValidationTextRange();
	}

}
