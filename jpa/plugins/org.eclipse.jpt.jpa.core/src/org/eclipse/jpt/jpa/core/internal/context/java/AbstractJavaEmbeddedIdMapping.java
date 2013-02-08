/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.io.Serializable;
import java.util.List;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.jpa2.context.EmbeddedIdMapping2_0;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddedIdAnnotation;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java embedded ID mapping
 */
public abstract class AbstractJavaEmbeddedIdMapping
	extends AbstractJavaBaseEmbeddedMapping<EmbeddedIdAnnotation>
	implements EmbeddedIdMapping2_0, JavaEmbeddedIdMapping
{
	/* JPA 2.0 - the embedded id may be derived from a relationship */
	protected boolean derived;


	protected AbstractJavaEmbeddedIdMapping(JavaPersistentAttribute parent) {
		super(parent);
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();
		this.setDerived(this.buildDerived());
	}


	// ********** derived **********

	public boolean isDerived() {
		return this.derived;
	}

	protected void setDerived(boolean derived) {
		boolean old = this.derived;
		this.derived = derived;
		this.firePropertyChanged(DERIVED_PROPERTY, old, derived);
	}

	protected boolean buildDerived() {
		return this.isJpa2_0Compatible() && this.buildDerived_();
	}

	protected boolean buildDerived_() {
		return this.getTypeMapping().attributeIsDerivedId(this.getName());
	}


	// ********** misc **********

	public String getKey() {
		return MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected String getAnnotationName() {
		return EmbeddedIdAnnotation.ANNOTATION_NAME;
	}

	@Override
	protected Iterable<String> getEmbeddableOverridableAttributeMappingNames() {
		return (this.derived) ?
			EmptyIterable.<String>instance() :
			super.getEmbeddableOverridableAttributeMappingNames();
	}

	@Override
	protected JavaAttributeOverrideContainer.Owner buildAttributeOverrideContainerOwner() {
		return new AttributeOverrideContainerOwner();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);

		// [JPA 2.0] if the embedded id is mapped by a relationship, then any specified
		// attribute overrides are in error
		// (in JPA 1.0, this will obviously never be reached)
		if (this.derived
				&& (this.attributeOverrideContainer.getSpecifiedOverridesSize() > 0)) {
			if (this.getPersistentAttribute().isVirtual()) {
				messages.add(
						DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_EMBEDDED_ID_MAPPING_MAPPED_BY_RELATIONSHIP_AND_ATTRIBUTE_OVERRIDES_SPECIFIED,
								new String[] {this.getName()},
								this.attributeOverrideContainer,
								this.getVirtualPersistentAttributeTextRange()
						)
				);				
			} else {
				messages.add(
						DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JptJpaCoreValidationMessages.EMBEDDED_ID_MAPPING_MAPPED_BY_RELATIONSHIP_AND_ATTRIBUTE_OVERRIDES_SPECIFIED,
								EMPTY_STRING_ARRAY,
								this.attributeOverrideContainer,
								this.attributeOverrideContainer.getValidationTextRange()
						)
				);
			}
		}
		
		validateTargetEmbeddableImplementsSerializable(messages, reporter);
		validateNoRelationshipMappingsOnTargetEmbeddable(messages, reporter);
		validateTargetEmbeddableImplementsEqualsAndHashcode(messages, reporter);
		validateTargetEmbeddableIsPublic(messages, reporter);
		validateTargetEmbeddableImplementsZeroArgConstructor(messages, reporter);
	}

	protected void validateTargetEmbeddableImplementsZeroArgConstructor(List<IMessage> messages, IReporter reporter) {
		if (this.getTargetEmbeddable() != null) {
			String targetEmbeddableClassName = this.getTargetEmbeddable().getPersistentType().getName();
			if (!JDTTools.classHasPublicZeroArgConstructor(this.getJavaProject(), targetEmbeddableClassName)){
				if (this.getPersistentAttribute().isVirtual()) {
					messages.add(
							DefaultJpaValidationMessages.buildMessage(
									IMessage.HIGH_SEVERITY,
									JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_NO_ARG_CONSTRUCTOR,
									new String[] {this.getName()},
									this,
									this.getVirtualPersistentAttributeTextRange()
							)
					);						
				} else {
					messages.add(
							DefaultJpaValidationMessages.buildMessage(
									IMessage.HIGH_SEVERITY,
									JptJpaCoreValidationMessages.EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_NO_ARG_CONSTRUCTOR,
									EMPTY_STRING_ARRAY,
									this,
									this.getValidationTextRange()
							)
					);
				}
			}
		}
	}

	protected void validateTargetEmbeddableImplementsEqualsAndHashcode(List<IMessage> messages, IReporter reporter) {
		if (this.getTargetEmbeddable() != null) {
			JavaResourceType resourceType = getTargetEmbeddable().getJavaResourceType();
			if (resourceType != null
				&& (!resourceType.hasHashCodeMethod() || !resourceType.hasEqualsMethod())) {

					if (this.getPersistentAttribute().isVirtual()) {
						messages.add(
								DefaultJpaValidationMessages.buildMessage(
										IMessage.HIGH_SEVERITY,
										JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_EQUALS_HASHCODE,
										new String[] {this.getName()},
										this,
										this.getVirtualPersistentAttributeTextRange()
								)
						);
					} else {
						messages.add(
								DefaultJpaValidationMessages.buildMessage(
										IMessage.HIGH_SEVERITY,
										JptJpaCoreValidationMessages.EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_EQUALS_HASHCODE,
										EMPTY_STRING_ARRAY,
										this,
										this.getValidationTextRange()
								)
						);
					}
			}
		}
	}

	protected void validateTargetEmbeddableIsPublic(List<IMessage> messages, IReporter reporter) {
		if (this.getTargetEmbeddable() != null) {
			if (!this.getTargetEmbeddable().getJavaResourceType().isPublic()) {
				if (this.getPersistentAttribute().isVirtual()) {
					messages.add(
							DefaultJpaValidationMessages.buildMessage(
									IMessage.HIGH_SEVERITY,
									JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_EMBEDDED_ID_CLASS_SHOULD_BE_PUBLIC,
									new String[] {this.getName()},
									this,
									this.getVirtualPersistentAttributeTextRange()
							)
					);					
				} else {
					messages.add(
							DefaultJpaValidationMessages.buildMessage(
									IMessage.HIGH_SEVERITY,
									JptJpaCoreValidationMessages.EMBEDDED_ID_CLASS_SHOULD_BE_PUBLIC,
									EMPTY_STRING_ARRAY,
									this,
									this.getValidationTextRange()
							)
					);
				}
			}
		}
	}

	protected void validateTargetEmbeddableImplementsSerializable(List<IMessage> messages, IReporter reporter) {
		if (this.getTargetEmbeddable() != null) {
			String targetEmbeddableClassName = this.getTargetEmbeddable().getPersistentType().getName();
			if (!JDTTools.typeIsSubType(this.getJavaProject(), targetEmbeddableClassName, Serializable.class.getName())) {
				if (this.getPersistentAttribute().isVirtual()) {
					messages.add(
							DefaultJpaValidationMessages.buildMessage(
									IMessage.HIGH_SEVERITY,
									JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_SERIALIZABLE,
									new String[] {this.getName()},
									this,
									this.getVirtualPersistentAttributeTextRange()
							)
					);					
				} else {
					messages.add(
							DefaultJpaValidationMessages.buildMessage(
									IMessage.HIGH_SEVERITY,
									JptJpaCoreValidationMessages.EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_SERIALIZABLE,
									EMPTY_STRING_ARRAY,
									this,
									this.getValidationTextRange()
							)
					);
				}
			}
		}
	}

	protected void validateNoRelationshipMappingsOnTargetEmbeddable(List<IMessage> messages, IReporter reporter) {
		if (this.getTargetEmbeddable() != null) {
			TypeMapping targetEmbeddableTypeMapping = this.getTargetEmbeddable().getPersistentType().getMapping();
			if (targetEmbeddableTypeMapping.getAllAttributeMappings(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY).iterator().hasNext()
					|| targetEmbeddableTypeMapping.getAllAttributeMappings(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY).iterator().hasNext()
					|| targetEmbeddableTypeMapping.getAllAttributeMappings(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY).iterator().hasNext()
					|| targetEmbeddableTypeMapping.getAllAttributeMappings(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY).iterator().hasNext()) {
				if (this.getPersistentAttribute().isVirtual()) {
					messages.add(
							DefaultJpaValidationMessages.buildMessage(
									IMessage.HIGH_SEVERITY,
									JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_EMBEDDED_ID_CLASS_SHOULD_NOT_CONTAIN_RELATIONSHIP_MAPPINGS,
									new String[] {this.getName()},
									this,
									this.getVirtualPersistentAttributeTextRange()
							)
					);
				} else {
					messages.add(
							DefaultJpaValidationMessages.buildMessage(
									IMessage.HIGH_SEVERITY,
									JptJpaCoreValidationMessages.EMBEDDED_ID_CLASS_SHOULD_NOT_CONTAIN_RELATIONSHIP_MAPPINGS,
									EMPTY_STRING_ARRAY,
									this,
									this.getValidationTextRange()
							)
					);
				}
			}
		}
	}


	// ********** attribute override container owner *********

	protected class AttributeOverrideContainerOwner
		extends AbstractJavaBaseEmbeddedMapping<EmbeddedIdAnnotation>.AttributeOverrideContainerOwner
	{
		@Override
		public Iterable<String> getAllOverridableNames() {
			return AbstractJavaEmbeddedIdMapping.this.isDerived() ?
					EmptyIterable.<String>instance() :
					super.getAllOverridableNames();
		}

		@Override
		protected Iterable<String> getAllOverridableAttributeNames_(TypeMapping overriddenTypeMapping) {
			return IterableTools.filter(super.getAllOverridableAttributeNames_(overriddenTypeMapping), new AttributeIsOverridable(this));
		}
	}
}
