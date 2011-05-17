/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmMappingRelationship;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.NullOrmOrphanRemoval2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmOneToOneMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmOneToOneRelationship2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmOrphanRemovable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmOrphanRemovalHolder2_0;
import org.eclipse.jpt.jpa.core.resource.orm.Attributes;
import org.eclipse.jpt.jpa.core.resource.orm.XmlOneToOne;

/**
 * <code>orm.xml</code> 1:1 mapping
 */
public abstract class AbstractOrmOneToOneMapping<X extends XmlOneToOne>
	extends AbstractOrmSingleRelationshipMapping<X>
	implements OrmOneToOneMapping2_0, OrmOrphanRemovalHolder2_0
{
	protected final OrmOrphanRemovable2_0 orphanRemoval;


	protected AbstractOrmOneToOneMapping(OrmPersistentAttribute parent, X xmlMapping) {
		super(parent, xmlMapping);
		this.orphanRemoval = this.buildOrphanRemoval();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.orphanRemoval.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.orphanRemoval.update();
	}


	// ********** orphan removal **********

	public OrmOrphanRemovable2_0 getOrphanRemoval() {
		return this.orphanRemoval;
	}

	protected OrmOrphanRemovable2_0 buildOrphanRemoval() {
		return this.isOrmXml2_0Compatible() ?
				this.getContextNodeFactory2_0().buildOrmOrphanRemoval(this) :
				new NullOrmOrphanRemoval2_0(this);
	}


	// ********** relationship **********

	@Override
	public OrmOneToOneRelationship2_0 getRelationship() {
		return (OrmOneToOneRelationship2_0) super.getRelationship();
	}

	@Override
	protected OrmMappingRelationship buildRelationship() {
		return new GenericOrmOneToOneRelationship(this);
	}


	// ********** misc **********

	public String getKey() {
		return MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}

	public int getXmlSequence() {
		return 60;
	}

	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmOneToOneMapping(this);
	}

	public void addXmlAttributeMappingTo(Attributes resourceAttributes) {
		resourceAttributes.getOneToOnes().add(this.xmlAttributeMapping);
	}

	public void removeXmlAttributeMappingFrom(Attributes resourceAttributes) {
		resourceAttributes.getOneToOnes().remove(this.xmlAttributeMapping);
	}
}
