/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmJoinColumnInAssociationOverrideJoiningStrategy;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.utility.TextRange;

public class GenericOrmAssociationOverrideRelationshipReference extends AbstractXmlContextNode
	implements OrmAssociationOverrideRelationshipReference
{

	protected final OrmJoinColumnInAssociationOverrideJoiningStrategy joinColumnJoiningStrategy;

	public GenericOrmAssociationOverrideRelationshipReference(OrmAssociationOverride parent, XmlAssociationOverride xao) {
		super(parent);
		this.joinColumnJoiningStrategy = buildJoinColumnJoiningStrategy(xao);
	}
	
	protected OrmJoinColumnInAssociationOverrideJoiningStrategy buildJoinColumnJoiningStrategy(XmlAssociationOverride xao) {
		return new GenericOrmJoinColumnInAssociationOverrideJoiningStrategy(this, xao);
	}
	
	@Override
	public OrmAssociationOverride getParent() {
		return (OrmAssociationOverride) super.getParent();
	}
	
	public OrmAssociationOverride getAssociationOverride() {
		return getParent();
	}

	public TypeMapping getTypeMapping() {
		return getAssociationOverride().getOwner().getTypeMapping();
	}
	
	public void update(XmlAssociationOverride xao) {
		this.joinColumnJoiningStrategy.update(xao);
	}	
	
	public boolean isOverridableAssociation() {
		return false;
	}
	
	public boolean isParentVirtual() {
		return getAssociationOverride().isVirtual();
	}
	
	
	// **************** join columns *******************************************
	

	public OrmJoinColumnInAssociationOverrideJoiningStrategy getJoinColumnJoiningStrategy() {
		return this.joinColumnJoiningStrategy;
	}
	
	public boolean usesJoinColumnJoiningStrategy() {
		return getPredominantJoiningStrategy() == this.joinColumnJoiningStrategy;
	}
	
	public void setJoinColumnJoiningStrategy() {
		this.joinColumnJoiningStrategy.addStrategy();
	}
	
	public void unsetJoinColumnJoiningStrategy() {
		this.joinColumnJoiningStrategy.removeStrategy();
	}
	
	public boolean mayHaveDefaultJoinColumn() {
		return false;
	}

	public JoiningStrategy getPredominantJoiningStrategy() {
		return this.joinColumnJoiningStrategy;
	}

	public RelationshipMapping getRelationshipMapping() {
		return getAssociationOverride().getOwner().getRelationshipMapping(getAssociationOverride().getName());
	}

	public boolean isOwnedBy(RelationshipMapping mapping) {
		return getRelationshipMapping().isOwnedBy(mapping);
	}

	public boolean isRelationshipOwner() {
		return getRelationshipMapping().isRelationshipOwner();
	}
	
	// ********** validation **********


	public TextRange getValidationTextRange() {
		return getAssociationOverride().getValidationTextRange();
	}

}
