/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.Relationship;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmVirtualAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmVirtualOverrideRelationship;

/**
 * Virtual <code>orm.xml</code> association override
 */
public class GenericOrmVirtualAssociationOverride
	extends AbstractOrmVirtualOverride<OrmAssociationOverrideContainer>
	implements OrmVirtualAssociationOverride
{
	protected final OrmVirtualOverrideRelationship relationship;


	public GenericOrmVirtualAssociationOverride(OrmAssociationOverrideContainer parent, String name) {
		super(parent, name);
		this.relationship = this.buildRelationship();
	}

	@Override
	public void update() {
		super.update();
		this.relationship.update();
	}

	@Override
	public OrmAssociationOverride convertToSpecified() {
		return (OrmAssociationOverride) super.convertToSpecified();
	}

	public RelationshipMapping getMapping() {
		return this.getContainer().getRelationshipMapping(this.name);
	}


	// ********** relationship **********

	public OrmVirtualOverrideRelationship getRelationship() {
		return this.relationship;
	}

	/**
	 * The relationship should be available (since its presence precipitated the
	 * creation of the virtual override).
	 */
	protected OrmVirtualOverrideRelationship buildRelationship() {
		return this.getContextNodeFactory().buildOrmVirtualOverrideRelationship(this);
	}

	public Relationship resolveOverriddenRelationship() {
		return this.getContainer().resolveOverriddenRelationship(this.name);
	}
}
