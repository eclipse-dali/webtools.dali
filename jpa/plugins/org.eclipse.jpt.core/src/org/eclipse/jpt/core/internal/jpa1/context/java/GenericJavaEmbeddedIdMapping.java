/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaEmbeddedIdMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaBaseEmbeddedMapping;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.context.EmbeddedIdMapping2_0;
import org.eclipse.jpt.core.jpa2.context.SingleRelationshipMapping2_0;
import org.eclipse.jpt.core.resource.java.EmbeddedIdAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.SubIterableWrapper;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaEmbeddedIdMapping
	extends AbstractJavaBaseEmbeddedMapping<EmbeddedIdAnnotation>
	implements EmbeddedIdMapping2_0, JavaEmbeddedIdMapping
{
	/* 2.0 feature - a relationship may map this embedded id */
	protected boolean mappedByRelationship;
	
	
	public GenericJavaEmbeddedIdMapping(JavaPersistentAttribute parent) {
		super(parent);
	}

	
	//****************** JavaAttributeMapping implementation *******************

	public String getKey() {
		return MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String getAnnotationName() {
		return EmbeddedIdAnnotation.ANNOTATION_NAME;
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
	protected JavaAttributeOverrideContainer.Owner buildAttributeOverrideContainerOwner() {
		return new AttributeOverrideContainerOwner();
	}
	
	
	protected class AttributeOverrideContainerOwner
		extends AbstractJavaBaseEmbeddedMapping.AttributeOverrideContainerOwner
	{
		@Override
		public Iterator<String> allOverridableNames() {
			return (GenericJavaEmbeddedIdMapping.this.isMappedByRelationship()) ?
					EmptyIterator.<String>instance()
					: super.allOverridableNames();
		}
		
		@Override
		protected Iterator<String> allOverridableAttributeNames_(TypeMapping typeMapping) {
			final Collection<String> mappedByRelationshipAttributes = CollectionTools.collection(
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
	protected void initialize() {
		super.initialize();
		this.mappedByRelationship = calculateMappedByRelationship();
	}
	
	
	@Override
	protected void update() {
		super.update();
		setMappedByRelationship(calculateMappedByRelationship());
	}
	
	
	// **************** validation ********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
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
						getAttributeOverrideContainer().getValidationTextRange(astRoot)));
		}
	}
}