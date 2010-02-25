/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOneToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOneToOneRelationshipReference2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOrphanRemovable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOrphanRemovalHolder2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmXml2_0ContextNodeFactory;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.XmlOneToOne;


public abstract class AbstractOrmOneToOneMapping<T extends XmlOneToOne>
	extends AbstractOrmSingleRelationshipMapping<T>
	implements OrmOneToOneMapping2_0, OrmOrphanRemovalHolder2_0
{
	protected final OrmOrphanRemovable2_0 orphanRemoval;
	
	// ********** constructor **********
	protected AbstractOrmOneToOneMapping(OrmPersistentAttribute parent, T resourceMapping) {
		super(parent, resourceMapping);
		this.orphanRemoval = ((OrmXml2_0ContextNodeFactory) getXmlContextNodeFactory()).buildOrmOrphanRemoval(this, resourceMapping);
	}

	// ********** update **********
	
	@Override
	public void update() {
		super.update();
		this.getOrphanRemoval().update();
	}
	
	public int getXmlSequence() {
		return 60;
	}
	
	public String getKey() {
		return MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}
	
	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmOneToOneMapping(this);
	}

	public void addToResourceModel(Attributes resourceAttributes) {
		resourceAttributes.getOneToOnes().add(this.resourceAttributeMapping);
	}
	
	public void removeFromResourceModel(Attributes resourceAttributes) {
		resourceAttributes.getOneToOnes().remove(this.resourceAttributeMapping);
	}
	
	@Override
	public OrmOneToOneRelationshipReference2_0 getRelationshipReference() {
		return (OrmOneToOneRelationshipReference2_0) super.getRelationshipReference();
	}

	// ********** OrmOrphanRemovalHolder2_0 implementation **********

	public OrmOrphanRemovable2_0 getOrphanRemoval() {
		return this.orphanRemoval;
	}
}
