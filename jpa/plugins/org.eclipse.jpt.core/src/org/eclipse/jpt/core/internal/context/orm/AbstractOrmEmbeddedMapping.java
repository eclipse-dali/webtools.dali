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

import java.util.Iterator;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.RelationshipReference;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.jpa2.context.java.JavaEmbeddedMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmEmbeddedMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmXml2_0ContextNodeFactory;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.XmlEmbedded;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.SubIteratorWrapper;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;


public abstract class AbstractOrmEmbeddedMapping<T extends XmlEmbedded>
	extends AbstractOrmBaseEmbeddedMapping<T> 
	implements OrmEmbeddedMapping2_0
{
	protected OrmAssociationOverrideContainer associationOverrideContainer;

	protected AbstractOrmEmbeddedMapping(OrmPersistentAttribute parent, T resourceMapping) {
		super(parent, resourceMapping);
		this.associationOverrideContainer = 
			((OrmXml2_0ContextNodeFactory) getXmlContextNodeFactory()).
				buildOrmAssociationOverrideContainer(this, new AssociationOverrideContainerOwner(), this.resourceAttributeMapping);
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
	
	//only putting this in EmbeddedMapping since relationship mappings
	//defined within an embedded id class are not supported  by the 2.0 spec.
	@Override
	public Iterator<String> allMappingNames() {
		return this.isJpa2_0Compatible() ?
				new CompositeIterator<String>(this.getName(),this.embeddableAttributeMappingNames()) :
				super.allMappingNames();
	}

	protected Iterator<String> embeddableAttributeMappingNames() {
		return new TransformationIterator<String, String>(
			new CompositeIterator<String>(
				new TransformationIterator<AttributeMapping, Iterator<String>>(this.embeddableAttributeMappings()) {
					@Override
					protected Iterator<String> transform(AttributeMapping mapping) {
						return mapping.allMappingNames();
					}
				}
			)
		) {
			@Override
			protected String transform(String next) {
				return getName() + '.' + next;
			}
		};
	}

	@Override
	public AttributeMapping resolveAttributeMapping(String attributeName) {
		if (getName() == null) {
			return null;
		}
		AttributeMapping resolvedMapping = super.resolveAttributeMapping(attributeName);
		if (resolvedMapping != null) {
			return resolvedMapping;
		}
		if (this.isJpa2_0Compatible()) {
			int dotIndex = attributeName.indexOf('.');
			if (dotIndex != -1) {
				if (getName().equals(attributeName.substring(0, dotIndex))) {
					for (AttributeMapping attributeMapping : CollectionTools.iterable(embeddableAttributeMappings())) {
						resolvedMapping = attributeMapping.resolveAttributeMapping(attributeName.substring(dotIndex + 1));
						if (resolvedMapping != null) {
							return resolvedMapping;
						}
					}
				}
			}
		}
		return null;
	}
	
	@Override
	public RelationshipReference resolveRelationshipReference(String attributeName) {
		if (getName() == null) {
			return null;
		}
		if (this.isJpa2_0Compatible()) {
			int dotIndex = attributeName.indexOf('.');
			if (dotIndex != -1) {
				if (getName().equals(attributeName.substring(0, dotIndex))) {
					attributeName = attributeName.substring(dotIndex + 1);
					AssociationOverride override = getAssociationOverrideContainer().getAssociationOverrideNamed(attributeName);
					if (override != null && !override.isVirtual()) {
						return override.getRelationshipReference();
					}
					if (this.getTargetEmbeddable() == null) {
						return null;
					}
					return this.getTargetEmbeddable().resolveRelationshipReference(attributeName);
				}
			}
		}
		return null;
	}
	
	public OrmAssociationOverrideContainer getAssociationOverrideContainer() {
		return this.associationOverrideContainer;
	}

	protected JavaAssociationOverride getJavaAssociationOverrideNamed(String attributeName) {
		if (getJavaEmbeddedMapping() != null) {
			return getJavaEmbeddedMapping().getAssociationOverrideContainer().getAssociationOverrideNamed(attributeName);
		}
		return null;
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
		return (this.getTargetEmbeddable() == null) ?
				EmptyIterator.<RelationshipMapping>instance() :
				new SubIteratorWrapper<AttributeMapping, RelationshipMapping>(this.allOverridableAssociations_());
	}

	protected Iterator<AttributeMapping> allOverridableAssociations_() {
		return new FilteringIterator<AttributeMapping>(this.getTargetEmbeddable().attributeMappings()) {
			@Override
			protected boolean accept(AttributeMapping o) {
				return o.isOverridableAssociationMapping();
			}
		};
	}

	
	//********** OrmAssociationOverrideContainer.Owner implementation *********	
	
	class AssociationOverrideContainerOwner implements OrmAssociationOverrideContainer.Owner {
		public TypeMapping getOverridableTypeMapping() {
			return AbstractOrmEmbeddedMapping.this.getTargetEmbeddable();
		}
		
		public OrmTypeMapping getTypeMapping() {
			return AbstractOrmEmbeddedMapping.this.getTypeMapping();
		}

		public RelationshipReference resolveRelationshipReference(String associationOverrideName) {
			RelationshipReference relationshipReference = null;
			if (getPersistentAttribute().isVirtual() && !getTypeMapping().isMetadataComplete()) {
				JavaAssociationOverride javaAssociationOverride = getJavaAssociationOverrideNamed(associationOverrideName);
				if (javaAssociationOverride != null && !javaAssociationOverride.isVirtual()) {
					return javaAssociationOverride.getRelationshipReference();
				}
			}
			TypeMapping overridableTypeMapping = getOverridableTypeMapping();
			if (overridableTypeMapping != null) {
				for (TypeMapping typeMapping : CollectionTools.iterable(overridableTypeMapping.inheritanceHierarchy())) {
					relationshipReference = typeMapping.resolveRelationshipReference(associationOverrideName);
					if (relationshipReference != null) {
						return relationshipReference;
					}
				}
			}
			return relationshipReference;
		}
	}
}
