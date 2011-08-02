/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaBaseEmbeddedMapping;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.jpa2.context.EmbeddedIdMapping2_0;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddedIdAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java embedded ID mapping
 */
public class GenericJavaEmbeddedIdMapping
	extends AbstractJavaBaseEmbeddedMapping<EmbeddedIdAnnotation>
	implements EmbeddedIdMapping2_0, JavaEmbeddedIdMapping
{
	/* JPA 2.0 - the embedded id may be derived from a relationship */
	protected boolean derived;


	public GenericJavaEmbeddedIdMapping(JavaPersistentAttribute parent) {
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
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);

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
					this.attributeOverrideContainer.getValidationTextRange(astRoot)
				)
			);
		}
	}


	// ********** attribute override container owner *********

	protected class AttributeOverrideContainerOwner
		extends AbstractJavaBaseEmbeddedMapping<EmbeddedIdAnnotation>.AttributeOverrideContainerOwner
	{
		@Override
		public Iterable<String> getAllOverridableNames() {
			return GenericJavaEmbeddedIdMapping.this.isDerived() ?
					EmptyIterable.<String>instance() :
					super.getAllOverridableNames();
		}

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
