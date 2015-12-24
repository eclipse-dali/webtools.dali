/*******************************************************************************
 * Copyright (c) 2006, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.NullOrmOrphanRemoval2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OneToManyMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OrphanRemovable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmOneToManyRelationship2_0;
import org.eclipse.jpt.jpa.core.resource.orm.Attributes;
import org.eclipse.jpt.jpa.core.resource.orm.XmlOneToMany;


public abstract class AbstractOrmOneToManyMapping<X extends XmlOneToMany>
	extends AbstractOrmMultiRelationshipMapping<X>
	implements OneToManyMapping2_0, OrmOneToManyMapping
{
	protected final OrphanRemovable2_0 orphanRemoval;


	protected AbstractOrmOneToManyMapping(OrmSpecifiedPersistentAttribute parent, X xmlMapping) {
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
	protected OrmOneToManyRelationship2_0 buildRelationship() {
		return new GenericOrmOneToManyRelationship(this, this.isJpa2_0Compatible());
	}

	@Override
	public OrmOneToManyRelationship2_0 getRelationship() {
		return (OrmOneToManyRelationship2_0) super.getRelationship();
	}


	// ********** misc **********

	public String getKey() {
		return MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}

	public int getXmlSequence() {
		return 50;
	}

	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmOneToManyMapping(this);
	}

	public void addXmlAttributeMappingTo(Attributes resourceAttributes) {
		resourceAttributes.getOneToManys().add(this.xmlAttributeMapping);
	}

	public void removeXmlAttributeMappingFrom(Attributes resourceAttributes) {
		resourceAttributes.getOneToManys().remove(this.xmlAttributeMapping);
	}
}
