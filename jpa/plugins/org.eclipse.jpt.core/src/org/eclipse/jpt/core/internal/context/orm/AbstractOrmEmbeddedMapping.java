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

import java.util.Iterator;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.RelationshipReference;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmBaseEmbeddedMapping;
import org.eclipse.jpt.core.jpa2.context.java.JavaEmbeddedMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmEmbeddedMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmXml2_0ContextNodeFactory;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.XmlEmbedded;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;


public abstract class AbstractOrmEmbeddedMapping<T extends XmlEmbedded>
	extends AbstractOrmBaseEmbeddedMapping<T> 
	implements OrmEmbeddedMapping2_0
{
	protected OrmAssociationOverrideContainer associationOverrideContainer;

	protected AbstractOrmEmbeddedMapping(OrmPersistentAttribute parent, T resourceMapping) {
		super(parent, resourceMapping);
		if (this.resourceAttributeMapping instanceof org.eclipse.jpt.core.jpa2.resource.orm.XmlEmbedded) {
			this.associationOverrideContainer = ((OrmXml2_0ContextNodeFactory) getXmlContextNodeFactory()).buildOrmAssociationOverrideContainer(this, this, (org.eclipse.jpt.core.jpa2.resource.orm.XmlEmbedded) this.resourceAttributeMapping);
		}
		else {
			this.associationOverrideContainer = ((OrmXml2_0ContextNodeFactory) getXmlContextNodeFactory()).buildOrmAssociationOverrideContainer(this, this, null);
		}
	}
	
	@Override
	public void update() {
		super.update();
		getAssociationOverrideContainer().update();
	}
	
	@Override
	public void postUpdate() {
		super.postUpdate();
		getAssociationOverrideContainer().postUpdate();
	}

	@Override
	public JavaEmbeddedMapping2_0 getJavaEmbeddedMapping() {
		return (JavaEmbeddedMapping2_0) super.getJavaEmbeddedMapping();
	}

	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmEmbeddedMapping(this);
	}

	public int getXmlSequence() {
		return 80;
	}

	public String getKey() {
		return MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY;
	}

	public void addToResourceModel(Attributes resourceAttributes) {
		resourceAttributes.getEmbeddeds().add(this.resourceAttributeMapping);
	}

	public void removeFromResourceModel(Attributes resourceAttributes) {
		resourceAttributes.getEmbeddeds().remove(this.resourceAttributeMapping);
	}
	
	

	public OrmAssociationOverrideContainer getAssociationOverrideContainer() {
		return this.associationOverrideContainer;
	}
	
	
	//************* AttributeOverrideContainer.Owner implementation ********************
	
	public Iterator<String> allOverridableAssociationNames() {
		return new TransformationIterator<RelationshipMapping, String>(this.allOverridableAssociations()) {
			@Override
			protected String transform(RelationshipMapping overridableAssociation) {
				return overridableAssociation.getName();
			}
		};
	}

	public Iterator<RelationshipMapping> allOverridableAssociations() {
		if (this.getEmbeddable() == null) {
			return EmptyIterator.instance();
		}
		return new FilteringIterator<AttributeMapping, RelationshipMapping>(this.getEmbeddable().attributeMappings()) {
			@Override
			protected boolean accept(AttributeMapping o) {
				return o.isOverridableAssociationMapping();
			}
		};
	}

	//********** OrmAssociationOverrideContainer.Owner implementation *********

	public RelationshipReference getOverridableRelationshipReference(RelationshipMapping overridableAssociation) {
		if (getPersistentAttribute().isVirtual()) {
			JavaAssociationOverride javaAssociationOverride = getJavaAssociationOverrideNamed(overridableAssociation.getName());
			if (javaAssociationOverride != null && !javaAssociationOverride.isVirtual()) {
				return javaAssociationOverride.getRelationshipReference();
			}
		}
		return overridableAssociation.getRelationshipReference();
	}

	protected JavaAssociationOverride getJavaAssociationOverrideNamed(String attributeName) {
		if (getJavaEmbeddedMapping() != null) {
			return getJavaEmbeddedMapping().getAssociationOverrideContainer().getAssociationOverrideNamed(attributeName);
		}
		return null;
	}


}
