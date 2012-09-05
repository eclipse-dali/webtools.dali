/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.jpa2.context.ManyToOneMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmManyToOneRelationship2_0;
import org.eclipse.jpt.jpa.core.resource.orm.Attributes;
import org.eclipse.jpt.jpa.core.resource.orm.XmlManyToOne;

/**
 * <code>orm.xml</code> 1:1 mapping
 */
public abstract class AbstractOrmManyToOneMapping<X extends XmlManyToOne>
	extends AbstractOrmSingleRelationshipMapping<X>
	implements ManyToOneMapping2_0, OrmManyToOneMapping
{
	protected AbstractOrmManyToOneMapping(OrmPersistentAttribute parent, X xmlMapping) {
		super(parent, xmlMapping);
	}


	// ********** relationship **********

	@Override
	protected OrmManyToOneRelationship2_0 buildRelationship() {
		return new GenericOrmManyToOneRelationship(this);
	}

	@Override
	public OrmManyToOneRelationship2_0 getRelationship() {
		return (OrmManyToOneRelationship2_0) super.getRelationship();
	}


	// ********** misc **********

	public String getKey() {
		return MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}

	public int getXmlSequence() {
		return 40;
	}

	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmManyToOneMapping(this);
	}

	public void addXmlAttributeMappingTo(Attributes resourceAttributes) {
		resourceAttributes.getManyToOnes().add(this.xmlAttributeMapping);
	}

	public void removeXmlAttributeMappingFrom(Attributes resourceAttributes) {
		resourceAttributes.getManyToOnes().remove(this.xmlAttributeMapping);
	}
}
