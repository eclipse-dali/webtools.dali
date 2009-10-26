/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.orm.OrmOneToOneRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmRelationshipReference;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOneToOneMapping2_0;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.XmlOneToOne;


public abstract class AbstractOrmOneToOneMapping<T extends XmlOneToOne>
	extends AbstractOrmSingleRelationshipMapping<T>
	implements OrmOneToOneMapping2_0
{
	protected AbstractOrmOneToOneMapping(OrmPersistentAttribute parent, T resourceMapping) {
		super(parent, resourceMapping);
	}
	
	
	@Override
	protected OrmRelationshipReference buildRelationshipReference() {
		return new GenericOrmOneToOneRelationshipReference(this, this.resourceAttributeMapping);
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
	public OrmOneToOneRelationshipReference getRelationshipReference() {
		return (OrmOneToOneRelationshipReference) super.getRelationshipReference();
	}
	
	// ********** JPA 2.0 behavior **********

	public boolean isOrphanRemoval() {
		throw new UnsupportedOperationException("operation not supported in JPA 1.0"); //$NON-NLS-1$
	}

	public Boolean getSpecifiedOrphanRemoval() {
		throw new UnsupportedOperationException("operation not supported in JPA 1.0"); //$NON-NLS-1$
	}

	public void setSpecifiedOrphanRemoval(Boolean newOrphanRemoval) {
		throw new UnsupportedOperationException("operation not supported in JPA 1.0"); //$NON-NLS-1$
	}

	public boolean isDefaultOrphanRemoval() {
		throw new UnsupportedOperationException("operation not supported in JPA 1.0"); //$NON-NLS-1$
	}
}
