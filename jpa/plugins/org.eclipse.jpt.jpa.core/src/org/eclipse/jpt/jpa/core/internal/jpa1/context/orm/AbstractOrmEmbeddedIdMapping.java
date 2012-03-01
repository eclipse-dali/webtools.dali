/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.io.Serializable;
import java.util.List;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmEmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmBaseEmbeddedMapping;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.jpa2.context.EmbeddedIdMapping2_0;
import org.eclipse.jpt.jpa.core.resource.orm.Attributes;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEmbeddedId;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> embedded ID mapping
 */
public abstract class AbstractOrmEmbeddedIdMapping<X extends XmlEmbeddedId>
	extends AbstractOrmBaseEmbeddedMapping<X>
	implements EmbeddedIdMapping2_0, OrmEmbeddedIdMapping
{
	/* JPA 2.0 - the embedded id may be derived from a relationship */
	protected boolean derived;


	protected AbstractOrmEmbeddedIdMapping(OrmPersistentAttribute parent, X xmlMapping) {
		super(parent, xmlMapping);
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();
		this.setMappedByRelationship(this.buildDerived());
	}


	// ********** derived **********

	public boolean isDerived() {
		return this.derived;
	}

	protected void setMappedByRelationship(boolean derived) {
		boolean old = this.derived;
		this.derived = derived;
		this.firePropertyChanged(DERIVED_PROPERTY, old, derived);
	}

	protected boolean buildDerived() {
		return this.isJpa2_0Compatible() && this.buildDerived_();
	}

	protected boolean buildDerived_() {
		return this.getTypeMapping().attributeIsDerivedId(this.name);
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
	protected Iterable<String> getEmbeddableOverridableAttributeMappingNames() {
		return this.derived ?
			EmptyIterable.<String>instance() :
			super.getEmbeddableOverridableAttributeMappingNames();
	}

	@Override
	protected OrmAttributeOverrideContainer.Owner buildAttributeOverrideContainerOwner() {
		return new AttributeOverrideContainerOwner();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		validateMappedByRelationshipAndAttributeOverridesSpecified(messages, reporter);
	}

	@Override
	protected boolean validateTargetEmbeddable(List<IMessage> messages) {
		boolean continueValidating = super.validateTargetEmbeddable(messages);
		if (continueValidating) { //targetEmbeddable != null
			if (this.getTargetEmbeddable().getJavaResourceType() != null) {
				this.validateTargetEmbeddableClass(messages);
			}
			this.validateNoRelationshipMappingsOnTargetEmbeddable(messages);			
		}
		return true;
	}

	/**
	 * preconditions: 
	 * 	{@link #getTargetEmbeddable()} is not null
	 * 	{@link #getTargetEmbeddable#getJavaResourceType()} is not null
	 */
	protected void validateTargetEmbeddableClass(List<IMessage> messages) {
		this.validateTargetEmbeddableImplementsEqualsAndHashcode(messages);
		this.validateTargetEmbeddableIsPublic(messages);
		this.validateTargetEmbeddableImplementsSerializable(messages);
		this.validateTargetEmbeddableImplementsNoArgConstructor(messages);
	}

	/**
	 * preconditions: 
	 * 	{@link #getTargetEmbeddable()} is not null
	 */
	protected void validateNoRelationshipMappingsOnTargetEmbeddable(List<IMessage> messages) {
		TypeMapping targetEmbeddableTypeMapping = this.getTargetEmbeddable().getPersistentType().getMapping();
		if (targetEmbeddableTypeMapping.getAllAttributeMappings(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY).iterator().hasNext()
				|| targetEmbeddableTypeMapping.getAllAttributeMappings(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY).iterator().hasNext()
				|| targetEmbeddableTypeMapping.getAllAttributeMappings(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY).iterator().hasNext()
				|| targetEmbeddableTypeMapping.getAllAttributeMappings(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY).iterator().hasNext()) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.EMBEDDED_ID_CLASS_SHOULD_NOT_CONTAIN_RELATIONSHIP_MAPPINGS,
							EMPTY_STRING_ARRAY,
							this,
							this.getValidationTextRange()
					)
			);
		}
	}
	
	/**
	 * preconditions: 
	 * 	{@link #getTargetEmbeddable()} is not null
	 * 	{@link #getTargetEmbeddable#getJavaResourceType()} is not null
	 */
	protected void validateTargetEmbeddableImplementsSerializable(List<IMessage> messages) {
		String targetEmbeddableClassName = this.getTargetEmbeddable().getPersistentType().getName();
		IJavaProject javaProject = getJpaProject().getJavaProject();
		if (!JDTTools.typeIsSubType(javaProject, targetEmbeddableClassName, Serializable.class.getName())) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_SERIALIZABLE,
							EMPTY_STRING_ARRAY,
							this,
							this.getValidationTextRange()
					)
			);
		}
	}
	
	/**
	 * preconditions: 
	 * 	{@link #getTargetEmbeddable()} is not null
	 * 	{@link #getTargetEmbeddable#getJavaResourceType()} is not null
	 */
	protected void validateTargetEmbeddableIsPublic(List<IMessage> messages) {
		if (!this.getTargetEmbeddable().getJavaResourceType().isPublic()) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.EMBEDDED_ID_CLASS_SHOULD_BE_PUBLIC,
							EMPTY_STRING_ARRAY,
							this,
							this.getValidationTextRange()
					)
			);
		}
	}

	/**
	 * preconditions: 
	 * 	{@link #getTargetEmbeddable()} is not null
	 * 	{@link #getTargetEmbeddable#getJavaResourceType()} is not null
	 */
	protected void validateTargetEmbeddableImplementsEqualsAndHashcode(List<IMessage> messages) {
		JavaResourceType resourceType = this.getTargetEmbeddable().getJavaResourceType();
		if (!resourceType.hasHashCodeMethod() || !resourceType.hasEqualsMethod()) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_EQUALS_HASHCODE,
							EMPTY_STRING_ARRAY,
							this,
							this.getValidationTextRange()
					)
			);
		}
	}
	
	/**
	 * preconditions: 
	 * 	{@link #getTargetEmbeddable()} is not null
	 * 	{@link #getTargetEmbeddable#getJavaResourceType()} is not null
	 */
	protected void validateTargetEmbeddableImplementsNoArgConstructor(List<IMessage> messages) {
		String targetEmbeddableClassName = this.getTargetEmbeddable().getPersistentType().getName();
		IJavaProject javaProject = getJpaProject().getJavaProject();
		if (!JDTTools.classHasPublicZeroArgConstructor(javaProject, targetEmbeddableClassName)) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_NO_ARG_CONSTRUCTOR,
							EMPTY_STRING_ARRAY,
							this,
							this.getValidationTextRange()
					)
			);
		}
	}
	
	protected void validateMappedByRelationshipAndAttributeOverridesSpecified(List<IMessage> messages, IReporter reporter) {
		// [JPA 2.0] if the embedded id is mapped by a relationship, then any specified
		// attribute overrides are in error
		// (in JPA 1.0, this will obviously never be reached)
		if (this.derived
				&& (this.attributeOverrideContainer.getSpecifiedOverridesSize() > 0)) {
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
		public Iterable<String> getAllOverridableNames() {
			return AbstractOrmEmbeddedIdMapping.this.isDerived() ?
					EmptyIterable.<String>instance() :
					super.getAllOverridableNames();
		}

		/**
		 * pre-condition: type mapping is not <code>null</code>
		 */
		@Override
		protected Iterable<String> getAllOverridableAttributeNames_(TypeMapping overriddenTypeMapping) {
			return new FilteringIterable<String>(super.getAllOverridableAttributeNames_(overriddenTypeMapping)) {
				@Override
				protected boolean accept(String attributeName) {
					return ! AttributeOverrideContainerOwner.this.getTypeMapping().attributeIsDerivedId(attributeName);
				}
			};
		}
	}
}
