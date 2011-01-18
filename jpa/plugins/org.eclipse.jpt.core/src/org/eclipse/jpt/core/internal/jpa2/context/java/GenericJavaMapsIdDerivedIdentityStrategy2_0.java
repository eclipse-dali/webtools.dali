/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationDescriptionMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.context.java.JavaDerivedIdentity2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaMapsIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.jpa2.resource.java.MapsId2_0Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.Tools;
import org.eclipse.jpt.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.SingleElementIterable;
import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaMapsIdDerivedIdentityStrategy2_0
	extends AbstractJavaJpaContextNode
	implements JavaMapsIdDerivedIdentityStrategy2_0
{
	protected String specifiedValue;
	protected String defaultValue;


	public GenericJavaMapsIdDerivedIdentityStrategy2_0(JavaDerivedIdentity2_0 parent) {
		super(parent);
		this.specifiedValue = this.buildSpecifiedValue();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedValue_(this.buildSpecifiedValue());
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultValue(this.buildDefaultValue());
	}


	// ********** value **********

	public String getValue() {
		return (this.specifiedValue != null) ? this.specifiedValue : this.defaultValue;
	}

	public String getSpecifiedValue() {
		return this.specifiedValue;
	}

	public void setSpecifiedValue(String value) {
		if (this.valuesAreDifferent(value, this.specifiedValue)) {
			this.getAnnotation().setValue(value);
			this.setSpecifiedValue_(value);
		}
	}

	protected void setSpecifiedValue_(String value) {
		String old = this.specifiedValue;
		this.specifiedValue = value;
		this.firePropertyChanged(SPECIFIED_VALUE_PROPERTY, old, value);
	}

	protected String buildSpecifiedValue() {
		return this.getAnnotation().getValue();
	}

	public String getDefaultValue() {
		return this.defaultValue;
	}

	protected void setDefaultValue(String value) {
		String old = this.defaultValue;
		this.defaultValue = value;
		this.firePropertyChanged(SPECIFIED_VALUE_PROPERTY, old, value);
	}

	protected String buildDefaultValue() {
		Iterator<AttributeMapping> stream = this.getIdAttributeMappings().iterator();
		if (stream.hasNext()) {
			AttributeMapping mapping = stream.next();
			// return null if we have more than one id mapping
			return stream.hasNext() ? null : mapping.getName();
		}
		return null;  // empty
	}

	public boolean usesDefaultValue() {
		return true;
	}


	// ********** annotation **********

	/**
	 * Do <em>not</em> return <code>null</code>.
	 */
	protected MapsId2_0Annotation getAnnotation() {
		return (MapsId2_0Annotation) this.getResourceAttribute().getNonNullAnnotation(this.getAnnotationName());
	}

	/**
	 * Return <code>null</code> if the annotation is not present.
	 */
	protected MapsId2_0Annotation getAnnotationOrNull() {
		return (MapsId2_0Annotation) this.getResourceAttribute().getAnnotation(this.getAnnotationName());
	}

	protected void addAnnotation() {
		this.getResourceAttribute().addAnnotation(this.getAnnotationName());
	}

	protected void removeAnnotation() {
		this.getResourceAttribute().removeAnnotation(this.getAnnotationName());
	}

	protected String getAnnotationName() {
		return MapsId2_0Annotation.ANNOTATION_NAME;
	}


	// ********** misc **********

	@Override
	public JavaDerivedIdentity2_0 getParent() {
		return (JavaDerivedIdentity2_0) super.getParent();
	}

	protected JavaDerivedIdentity2_0 getDerivedIdentity() {
		return this.getParent();
	}

	protected JavaSingleRelationshipMapping2_0 getMapping() {
		return this.getDerivedIdentity().getMapping();
	}

	protected JavaPersistentAttribute getPersistentAttribute() {
		return this.getMapping().getPersistentAttribute();
	}

	protected JavaResourcePersistentAttribute getResourceAttribute() {
		return this.getPersistentAttribute().getResourcePersistentAttribute();
	}

	protected Iterable<AttributeMapping> getAllAttributeMappings() {
		return CollectionTools.collection(this.getPersistentAttribute().getOwningTypeMapping().allAttributeMappings());
	}

	public Iterable<String> getSortedValueChoices() {
		return CollectionTools.sort(this.getAllAttributeMappingChoiceNames());
	}

	protected Iterable<String> getAllAttributeMappingChoiceNames() {
		return new TransformationIterable<AttributeMapping, String>(this.getAllAttributeMappingChoices()) {
				@Override
				protected String transform(AttributeMapping mapping) {
					return mapping.getName();
				}
			};
	}

	protected Iterable<AttributeMapping> getAllAttributeMappingChoices() {
		return this.buildAttributeMappingChoices(this.getAllAttributeMappings());
	}

	protected Iterable<AttributeMapping> buildAttributeMappingChoices(Iterable<AttributeMapping> attributeMappings) {
		return new CompositeIterable<AttributeMapping>(this.getAttributeMappingChoiceIterables(attributeMappings));
	}

	/**
	 * @see #getEmbeddedIdMappingChoiceIterable(EmbeddedIdMapping)
	 */
	protected Iterable<Iterable<AttributeMapping>> getAttributeMappingChoiceIterables(Iterable<AttributeMapping> availableMappings) {
		return new TransformationIterable<AttributeMapping, Iterable<AttributeMapping>>(availableMappings) {
			@Override
			protected Iterable<AttributeMapping> transform(AttributeMapping mapping) {
				if (Tools.valuesAreEqual(mapping.getKey(), MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY)) {
					return GenericJavaMapsIdDerivedIdentityStrategy2_0.this.getEmbeddedIdMappingChoiceIterable((EmbeddedIdMapping) mapping);
				}
				return new SingleElementIterable<AttributeMapping>(mapping);
			}
		};
	}

	/**
	 * Convert the specified mapping into a collection of its "mappings".
	 * Typically, this collection will include just the mapping itself;
	 * but, if the mapping is an embedded ID, this collection will include
	 * the mapping itself plus all the mappings of its target embeddable.
	 */
	protected Iterable<AttributeMapping> getEmbeddedIdMappingChoiceIterable(EmbeddedIdMapping mapping) {
		Embeddable embeddable = mapping.getTargetEmbeddable();
		if (embeddable == null) {
			return new SingleElementIterable<AttributeMapping>(mapping);
		}
		return new CompositeIterable<AttributeMapping>(
				mapping,
				CollectionTools.collection(embeddable.allAttributeMappings())
			);
	}

	public AttributeMapping getResolvedAttributeMappingValue() {
		String value = this.getValue();
		if (value != null) {
			for (AttributeMapping mapping : this.getAllAttributeMappingChoices()) {
				if (value.equals(mapping.getName())) {
					return mapping;
				}
			}
		}
		return null;
	}

	public boolean isSpecified() {
		return this.getAnnotationOrNull() != null;
	}

	public void addStrategy() {
		if (this.getAnnotationOrNull() == null) {
			this.addAnnotation();
		}
	}

	public void removeStrategy() {
		if (this.getAnnotationOrNull() != null) {
			this.removeAnnotation();
		}
	}


	// ********** Java completion proposals **********

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.getAnnotation().valueTouches(pos, astRoot)) {
			result = this.sortedJavaValueChoices(filter);
		}
		return result;
	}

	protected Iterator<String> sortedJavaValueChoices(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(new FilteringIterator<String>(this.getSortedValueChoices(), filter));
	}


	// ********** ID mappings **********

	protected Iterable<AttributeMapping> getIdAttributeMappings() {
		return new FilteringIterable<AttributeMapping>(this.getAllAttributeMappings()) {
			@Override
			protected boolean accept(AttributeMapping mapping) {
				return GenericJavaMapsIdDerivedIdentityStrategy2_0.this.mappingIsIdMapping(mapping);
			}
		};
	}

	protected boolean mappingIsIdMapping(AttributeMapping mapping) {
		return CollectionTools.contains(this.getIdMappingKeys(), mapping.getKey());
	}

	protected Iterable<String> getIdMappingKeys() {
		return ID_MAPPING_KEYS;
	}

	protected static final String[] ID_MAPPING_KEYS_ARRAY = new String[] {
		MappingKeys.ID_ATTRIBUTE_MAPPING_KEY,
		MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY
	};
	
	protected static final Iterable<String> ID_MAPPING_KEYS = new ArrayIterable<String>(ID_MAPPING_KEYS_ARRAY);


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.validateMapsId(messages, astRoot);
	}

	protected void validateMapsId(List<IMessage> messages, CompilationUnit astRoot) {
		if (this.getDerivedIdentity().usesMapsIdDerivedIdentityStrategy()) {
			this.validateMapsId_(messages, astRoot);
		}
	}

	protected void validateMapsId_(List<IMessage> messages, CompilationUnit astRoot) {
		// test whether value can be resolved
		AttributeMapping attributeMapping = this.getResolvedAttributeMappingValue();
		if (attributeMapping == null) {
			// if value is not specified, use that message
			if (this.specifiedValue == null) {
				messages.add(this.buildMessage(JpaValidationMessages.MAPS_ID_VALUE_NOT_SPECIFIED, EMPTY_STRING_ARRAY, astRoot));
			} else {
				messages.add(this.buildMessage(JpaValidationMessages.MAPS_ID_VALUE_NOT_RESOLVED, new String[] {this.getValue()}, astRoot));
			}
		} else {
			// test whether attribute mapping is allowable
			if ( ! CollectionTools.contains(this.getValidAttributeMappingChoices(), attributeMapping)) {
				messages.add(this.buildMessage(JpaValidationMessages.MAPS_ID_VALUE_INVALID, new String[] {this.getValue()}, astRoot));
			}
		}
	}

	protected Iterable<AttributeMapping> getValidAttributeMappingChoices() {
		return this.buildAttributeMappingChoices(this.getIdAttributeMappings());
	}

	protected IMessage buildMessage(String msgID, String[] parms, CompilationUnit astRoot) {
		String attributeDescription = NLS.bind(JpaValidationDescriptionMessages.ATTRIBUTE_DESC, this.getPersistentAttribute().getName());
		parms = ArrayTools.add(parms, 0, attributeDescription);
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				msgID,
				parms,
				this,
				this.getValidationTextRange(astRoot)
			);
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getAnnotation().getTextRange(astRoot);
	}
}
