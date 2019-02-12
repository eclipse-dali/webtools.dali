/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmMappingRelationship;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.NullOrmOrphanRemoval2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OneToOneMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OrphanRemovable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmOneToOneRelationship2_0;
import org.eclipse.jpt.jpa.core.resource.orm.Attributes;
import org.eclipse.jpt.jpa.core.resource.orm.XmlOneToOne;

/**
 * <code>orm.xml</code> 1:1 mapping
 */
public abstract class AbstractOrmOneToOneMapping<X extends XmlOneToOne>
	extends AbstractOrmSingleRelationshipMapping<X>
	implements OneToOneMapping2_0, OrmOneToOneMapping
{
	protected final OrphanRemovable2_0 orphanRemoval;


	protected AbstractOrmOneToOneMapping(OrmSpecifiedPersistentAttribute parent, X xmlMapping) {
		super(parent, xmlMapping);
		this.orphanRemoval = this.buildOrphanRemoval();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.orphanRemoval.synchronizeWithResourceModel(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.orphanRemoval.update(monitor);
	}


	// ********** orphan removal **********

	public OrphanRemovable2_0 getOrphanRemoval() {
		return this.orphanRemoval;
	}

	protected OrphanRemovable2_0 buildOrphanRemoval() {
		return this.isOrmXml2_0Compatible() ?
				this.getContextModelFactory2_0().buildOrmOrphanRemoval(this) :
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
