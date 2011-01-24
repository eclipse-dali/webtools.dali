/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.orm.OrmManyToManyRelationship;
import org.eclipse.jpt.core.context.orm.OrmMappingRelationship;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.jpa2.context.orm.OrmManyToManyMapping2_0;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.XmlManyToMany;

public abstract class AbstractOrmManyToManyMapping<X extends XmlManyToMany>
	extends AbstractOrmMultiRelationshipMapping<X>
	implements OrmManyToManyMapping2_0
{
	protected AbstractOrmManyToManyMapping(OrmPersistentAttribute parent, X xmlMapping) {
		super(parent, xmlMapping);
	}


	// ********** relationship **********

	@Override
	public OrmManyToManyRelationship getRelationship() {
		return (OrmManyToManyRelationship) super.getRelationship();
	}

	@Override
	protected OrmMappingRelationship buildRelationship() {
		return new GenericOrmManyToManyRelationship(this);
	}


	// ********** misc **********

	public String getKey() {
		return MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}

	public int getXmlSequence() {
		return 70;
	}

	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmManyToManyMapping(this);
	}

	public void addXmlAttributeMappingTo(Attributes resourceAttributes) {
		resourceAttributes.getManyToManys().add(this.xmlAttributeMapping);
	}

	public void removeXmlAttributeMappingFrom(Attributes resourceAttributes) {
		resourceAttributes.getManyToManys().remove(this.xmlAttributeMapping);
	}
}
