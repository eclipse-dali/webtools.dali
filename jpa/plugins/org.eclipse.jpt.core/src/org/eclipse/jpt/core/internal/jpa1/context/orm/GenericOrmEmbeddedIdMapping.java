/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedIdMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmBaseEmbeddedMapping;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.context.EmbeddedIdMapping2_0;
import org.eclipse.jpt.core.jpa2.context.SingleRelationshipMapping2_0;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddedId;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.SubIterableWrapper;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class GenericOrmEmbeddedIdMapping
	extends AbstractOrmBaseEmbeddedMapping<XmlEmbeddedId> 
	implements EmbeddedIdMapping2_0, OrmEmbeddedIdMapping
{
	/* 2.0 feature - a relationship may map this embedded id */
	protected boolean mappedByRelationship;
	
	
	public GenericOrmEmbeddedIdMapping(OrmPersistentAttribute parent, XmlEmbeddedId resourceMapping) {
		super(parent, resourceMapping);
		this.mappedByRelationship = calculateMappedByRelationship();
	}
	
	
	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmEmbeddedIdMapping(this);
	}
	
	public int getXmlSequence() {
		return 10;
	}
	
	
	//*************** AttributeMapping implementation *********************
	
	public String getKey() {
		return MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY;
	}
	
	public void addToResourceModel(Attributes resourceAttributes) {
		resourceAttributes.getEmbeddedIds().add(this.resourceAttributeMapping);
	}
	
	public void removeFromResourceModel(Attributes resourceAttributes) {
		resourceAttributes.getEmbeddedIds().remove(this.resourceAttributeMapping);
	}
	
	
	// **************** EmbeddedId2_0 impl ************************************
	
	public boolean isMappedByRelationship() {
		return this.mappedByRelationship;
	}
	
	protected void setMappedByRelationship(boolean newValue) {
		boolean oldValue = this.mappedByRelationship;
		this.mappedByRelationship = newValue;
		firePropertyChanged(MAPPED_BY_RELATIONSHIP_PROPERTY, oldValue, newValue);
	}
	
	protected boolean calculateMappedByRelationship() {
		for (SingleRelationshipMapping2_0 each : getMapsIdRelationships()) {
			if (getName().equals(each.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getValue())) {
				return true;
			}
		}
		return false;
	}
	
	protected Iterable<SingleRelationshipMapping2_0> getMapsIdRelationships() {
		return new FilteringIterable<SingleRelationshipMapping2_0>(
				new SubIterableWrapper<AttributeMapping, SingleRelationshipMapping2_0>(
					new CompositeIterable<AttributeMapping>(
						getTypeMapping().getAllAttributeMappings(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY),
						getTypeMapping().getAllAttributeMappings(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY)))) {
			@Override
			protected boolean accept(SingleRelationshipMapping2_0 o) {
				return o.getDerivedIdentity().usesMapsIdDerivedIdentityStrategy();
			}
		};
	}
	
	
	// **************** overrides *********************************************
	
	@Override
	protected Iterator<String> embeddableOverrideableAttributeMappingNames() {
		return (isMappedByRelationship()) ?
			EmptyIterator.<String>instance()
			: super.embeddableOverrideableAttributeMappingNames();
	}
	
	@Override
	protected OrmAttributeOverrideContainer.Owner buildAttributeOverrideContainerOwner() {
		return new AttributeOverrideContainerOwner();
	}
	
	
	protected class AttributeOverrideContainerOwner
		extends AbstractOrmBaseEmbeddedMapping.AttributeOverrideContainerOwner
	{
		@Override
		public Iterator allOverridableNames() {
			return (GenericOrmEmbeddedIdMapping.this.isMappedByRelationship()) ?
					EmptyIterator.<String>instance()
					: super.allOverridableNames();
		}
		
		@Override
		protected Iterator allOverridableAttributeNames_(TypeMapping typeMapping) {
			final Collection mappedByRelationshipAttributes = CollectionTools.collection(
					new TransformationIterator<SingleRelationshipMapping2_0, String>(getMapsIdRelationships()) {
						@Override
						protected String transform(SingleRelationshipMapping2_0 next) {
							return next.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getValue();
						}
					});
			return new FilteringIterator<String>(super.allOverridableAttributeNames_(typeMapping)) {
				@Override
				protected boolean accept(String o) {
					if (mappedByRelationshipAttributes.isEmpty()) {
						return true;
					}
					// overrideable names are (usually?) qualified with a container mapping, 
					// which may also be the one mapped by a relationship
					String qualifier = 
							(o.indexOf('.') > 0) ?
								o.substring(0, o.indexOf('.'))
								: o;
					return ! mappedByRelationshipAttributes.contains(qualifier);
				}
			};
		}
	}
	
	
	// **************** resource -> context ***********************************
	
	@Override
	public void update() {
		super.update();
		setMappedByRelationship(calculateMappedByRelationship());
	}
	
	
	// **************** validation ********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		// [JPA 2.0] if the embedded id is mapped by a relationship, then any specified 
		// attribute overrides are in error
		// (in JPA 1.0, this will obviously never be reached)
		if (isMappedByRelationship()
				&& getAttributeOverrideContainer().specifiedAttributeOverridesSize() > 0) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.EMBEDDED_ID_MAPPING_MAPPED_BY_RELATIONSHIP_AND_ATTRIBUTE_OVERRIDES_SPECIFIED,
						new String[] {},
						getAttributeOverrideContainer(),
						getAttributeOverrideContainer().getValidationTextRange()));
		}
	}
}
