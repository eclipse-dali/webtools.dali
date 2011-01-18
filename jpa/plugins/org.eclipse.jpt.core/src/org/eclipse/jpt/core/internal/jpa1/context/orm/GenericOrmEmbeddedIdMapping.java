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

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedIdMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.context.TypeMappingTools;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmBaseEmbeddedMapping;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.context.EmbeddedIdMapping2_0;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddedId;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> embedded ID mapping
 */
public class GenericOrmEmbeddedIdMapping
	extends AbstractOrmBaseEmbeddedMapping<XmlEmbeddedId>
	implements EmbeddedIdMapping2_0, OrmEmbeddedIdMapping
{
	/* JPA 2.0 feature - a relationship may map this embedded ID */
	protected boolean mappedByRelationship;


	public GenericOrmEmbeddedIdMapping(OrmPersistentAttribute parent, XmlEmbeddedId xmlMapping) {
		super(parent, xmlMapping);
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();
		this.setMappedByRelationship(this.buildMappedByRelationship());
	}


	// ********** mapped by relationship **********

	public boolean isMappedByRelationship() {
		return this.mappedByRelationship;
	}

	protected void setMappedByRelationship(boolean value) {
		boolean old = this.mappedByRelationship;
		this.mappedByRelationship = value;
		this.firePropertyChanged(MAPPED_BY_RELATIONSHIP_PROPERTY, old, value);
	}

	protected boolean buildMappedByRelationship() {
		return this.isJpa2_0Compatible() && this.buildMappedByRelationship_();
	}

	protected boolean buildMappedByRelationship_() {
		return CollectionTools.contains(this.getMappedByRelationshipAttributeNames(), this.name);
	}

	protected Iterable<String> getMappedByRelationshipAttributeNames() {
		return TypeMappingTools.getMappedByRelationshipAttributeNames(this.getTypeMapping());
	}


	// ********** misc **********

	public String getKey() {
		return MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY;
	}

	public int getXmlSequence() {
		return 10;
	}

	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmEmbeddedIdMapping(this);
	}

	public void addXmlAttributeMappingTo(Attributes xmlAttributes) {
		xmlAttributes.getEmbeddedIds().add(this.xmlAttributeMapping);
	}

	public void removeXmlAttributeMappingFrom(Attributes xmlAttributes) {
		xmlAttributes.getEmbeddedIds().remove(this.xmlAttributeMapping);
	}

	@Override
	protected Iterator<String> embeddableOverridableAttributeMappingNames() {
		return this.mappedByRelationship ?
			EmptyIterator.<String>instance() :
			super.embeddableOverridableAttributeMappingNames();
	}

	@Override
	protected OrmAttributeOverrideContainer.Owner buildAttributeOverrideContainerOwner() {
		return new AttributeOverrideContainerOwner();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);

		// [JPA 2.0] if the embedded id is mapped by a relationship, then any specified
		// attribute overrides are in error
		// (in JPA 1.0, this will obviously never be reached)
		if (this.mappedByRelationship
				&& (this.attributeOverrideContainer.specifiedOverridesSize() > 0)) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.EMBEDDED_ID_MAPPING_MAPPED_BY_RELATIONSHIP_AND_ATTRIBUTE_OVERRIDES_SPECIFIED,
					EMPTY_STRING_ARRAY,
					this.attributeOverrideContainer,
					this.attributeOverrideContainer.getValidationTextRange()
				)
			);
		}
	}


	// ********** attribute override container owner *********

	protected class AttributeOverrideContainerOwner
		extends AbstractOrmBaseEmbeddedMapping<XmlEmbeddedId>.AttributeOverrideContainerOwner
	{
		@Override
		public Iterator<String> allOverridableNames() {
			return GenericOrmEmbeddedIdMapping.this.isMappedByRelationship() ?
					EmptyIterator.<String>instance() :
					super.allOverridableNames();
		}

		/**
		 * pre-condition: type mapping is not <code>null</code>
		 */
		@Override
		protected Iterator<String> allOverridableAttributeNames_(TypeMapping typeMapping) {
			final Set<String> mappedByRelationshipAttributeNames = this.buildMappedByRelationshipAttributeNames();
			if (mappedByRelationshipAttributeNames.isEmpty()) {
				return super.allOverridableAttributeNames_(typeMapping);
			}
			return new FilteringIterator<String>(super.allOverridableAttributeNames_(typeMapping)) {
				@Override
				protected boolean accept(String attributeName) {
					// overridable names are (usually?) qualified with a container mapping,
					// which may also be the one mapped by a relationship
					int dotIndex = attributeName.indexOf('.');
					String qualifier = (dotIndex > 0) ? attributeName.substring(0, dotIndex) : attributeName;
					return ! mappedByRelationshipAttributeNames.contains(qualifier);
				}
			};
		}

		protected Set<String> buildMappedByRelationshipAttributeNames() {
			return CollectionTools.set(GenericOrmEmbeddedIdMapping.this.getMappedByRelationshipAttributeNames());
		}
	}
}
