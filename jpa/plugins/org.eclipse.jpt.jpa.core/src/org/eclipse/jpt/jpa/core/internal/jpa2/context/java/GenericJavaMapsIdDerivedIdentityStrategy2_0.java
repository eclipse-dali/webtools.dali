/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.java;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementIterable;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;
import org.eclipse.jpt.jpa.core.jpa2.context.MapsIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaDerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaSingleRelationshipMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapsIdAnnotation2_0;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationArgumentMessages;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaMapsIdDerivedIdentityStrategy2_0
	extends AbstractJavaContextModel<JavaDerivedIdentity2_0>
	implements MapsIdDerivedIdentityStrategy2_0
{
	protected String specifiedIdAttributeName;
	protected String defaultIdAttributeName;


	public GenericJavaMapsIdDerivedIdentityStrategy2_0(JavaDerivedIdentity2_0 parent) {
		super(parent);
		this.specifiedIdAttributeName = this.buildSpecifiedIdAttributeName();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedIdAttributeName_(this.buildSpecifiedIdAttributeName());
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultIdAttributeName(this.buildDefaultIdAttributeName());
	}


	// ********** ID attribute name **********

	public String getIdAttributeName() {
		return (this.specifiedIdAttributeName != null) ? this.specifiedIdAttributeName : this.defaultIdAttributeName;
	}

	public String getSpecifiedIdAttributeName() {
		return this.specifiedIdAttributeName;
	}

	public void setSpecifiedIdAttributeName(String idAttributeName) {
		if (ObjectTools.notEquals(idAttributeName, this.specifiedIdAttributeName)) {
			this.getAnnotation().setValue(idAttributeName);
			this.setSpecifiedIdAttributeName_(idAttributeName);
		}
	}

	protected void setSpecifiedIdAttributeName_(String idAttributeName) {
		String old = this.specifiedIdAttributeName;
		this.specifiedIdAttributeName = idAttributeName;
		this.firePropertyChanged(SPECIFIED_ID_ATTRIBUTE_NAME_PROPERTY, old, idAttributeName);
	}

	protected String buildSpecifiedIdAttributeName() {
		return this.getAnnotation().getValue();
	}

	public String getDefaultIdAttributeName() {
		return this.defaultIdAttributeName;
	}

	protected void setDefaultIdAttributeName(String idAttributeName) {
		String old = this.defaultIdAttributeName;
		this.defaultIdAttributeName = idAttributeName;
		this.firePropertyChanged(DEFAULT_ID_ATTRIBUTE_NAME_PROPERTY, old, idAttributeName);
	}

	protected String buildDefaultIdAttributeName() {
		Iterator<AttributeMapping> stream = this.getIdAttributeMappings().iterator();
		if (stream.hasNext()) {
			AttributeMapping mapping = stream.next();
			// return null if we have more than one id mapping
			return stream.hasNext() ? null : mapping.getName();
		}
		return null;  // empty
	}

	public boolean defaultIdAttributeNameIsPossible() {
		return true;
	}


	// ********** annotation **********

	/**
	 * Do <em>not</em> return <code>null</code>.
	 */
	protected MapsIdAnnotation2_0 getAnnotation() {
		return (MapsIdAnnotation2_0) this.getResourceAttribute().getNonNullAnnotation(this.getAnnotationName());
	}

	/**
	 * Return <code>null</code> if the annotation is not present.
	 */
	protected MapsIdAnnotation2_0 getAnnotationOrNull() {
		return (MapsIdAnnotation2_0) this.getResourceAttribute().getAnnotation(this.getAnnotationName());
	}

	protected void addAnnotation() {
		this.getResourceAttribute().addAnnotation(this.getAnnotationName());
	}

	protected void removeAnnotation() {
		this.getResourceAttribute().removeAnnotation(this.getAnnotationName());
	}

	protected String getAnnotationName() {
		return MapsIdAnnotation2_0.ANNOTATION_NAME;
	}


	// ********** misc **********

	protected JavaDerivedIdentity2_0 getDerivedIdentity() {
		return this.parent;
	}

	protected JavaSingleRelationshipMapping2_0 getMapping() {
		return this.getDerivedIdentity().getMapping();
	}

	protected JavaSpecifiedPersistentAttribute getPersistentAttribute() {
		return this.getMapping().getPersistentAttribute();
	}

	protected JavaResourceAttribute getResourceAttribute() {
		return this.getPersistentAttribute().getResourceAttribute();
	}

	protected Iterable<AttributeMapping> getAllAttributeMappings() {
		return this.getPersistentAttribute().getDeclaringTypeMapping().getAllAttributeMappings();
	}

	public Iterable<String> getSortedCandidateIdAttributeNames() {
		return IterableTools.sort(this.getAllAttributeMappingChoiceNames());
	}

	protected Iterable<String> getAllAttributeMappingChoiceNames() {
		return IterableTools.transform(this.getAllAttributeMappingChoices(), AttributeMapping.NAME_TRANSFORMER);
	}

	protected Iterable<AttributeMapping> getAllAttributeMappingChoices() {
		return this.buildAttributeMappingChoices(this.getAllAttributeMappings());
	}

	/**
	 * @see #getEmbeddedIdMappingChoiceIterable(EmbeddedIdMapping)
	 */
	protected Iterable<AttributeMapping> buildAttributeMappingChoices(Iterable<AttributeMapping> attributeMappings) {
		return IterableTools.children(attributeMappings, new AttributeMappingTransformer());
	}

	public class AttributeMappingTransformer
		extends TransformerAdapter<AttributeMapping, Iterable<AttributeMapping>>
	{
		@Override
		public Iterable<AttributeMapping> transform(AttributeMapping mapping) {
			return (ObjectTools.equals(mapping.getKey(), MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY)) ?
					GenericJavaMapsIdDerivedIdentityStrategy2_0.this.getEmbeddedIdMappingChoiceIterable((EmbeddedIdMapping) mapping) :
					new SingleElementIterable<AttributeMapping>(mapping);
		}
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
		return IterableTools.insert(
				mapping,
				embeddable.getAllAttributeMappings()
			);
	}

	public AttributeMapping getDerivedIdAttributeMapping() {
		String idAttributeName = this.getIdAttributeName();
		if (idAttributeName != null) {
			for (AttributeMapping mapping : this.getAllAttributeMappingChoices()) {
				if (idAttributeName.equals(mapping.getName())) {
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
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.getAnnotation().valueTouches(pos)) {
			result = this.getSortedJavaValueChoices();
		}
		return result;
	}

	protected Iterable<String> getSortedJavaValueChoices() {
		return new TransformationIterable<String, String>(this.getSortedCandidateIdAttributeNames(),
				StringTools.JAVA_STRING_LITERAL_CONTENT_TRANSFORMER);
	}


	// ********** ID mappings **********

	protected Iterable<AttributeMapping> getIdAttributeMappings() {
		return IterableTools.filter(this.getAllAttributeMappings(), new MappingIsIdMapping());
	}

	public class MappingIsIdMapping
		extends PredicateAdapter<AttributeMapping>
	{
		@Override
		public boolean evaluate(AttributeMapping mapping) {
			return GenericJavaMapsIdDerivedIdentityStrategy2_0.this.mappingIsIdMapping(mapping);
		}
	}

	protected boolean mappingIsIdMapping(AttributeMapping mapping) {
		return IterableTools.contains(this.getIdMappingKeys(), mapping.getKey());
	}

	protected Iterable<String> getIdMappingKeys() {
		return ID_MAPPING_KEYS;
	}

	protected static final String[] ID_MAPPING_KEYS_ARRAY = new String[] {
		MappingKeys.ID_ATTRIBUTE_MAPPING_KEY,
		MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY
	};
	
	protected static final Iterable<String> ID_MAPPING_KEYS = IterableTools.iterable(ID_MAPPING_KEYS_ARRAY);


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateMapsId(messages);
	}

	protected void validateMapsId(List<IMessage> messages) {
		if (this.getDerivedIdentity().usesMapsIdDerivedIdentityStrategy()) {
			this.validateMapsId_(messages);
		}
	}

	protected void validateMapsId_(List<IMessage> messages) {
		// test whether id attribute name can be resolved
		AttributeMapping attributeMapping = this.getDerivedIdAttributeMapping();
		if (attributeMapping == null) {
			// if id attribute name is not specified, use that message
			if (this.specifiedIdAttributeName == null) {
				messages.add(this.buildMessage(JptJpaCoreValidationMessages.MAPS_ID_VALUE_NOT_SPECIFIED));
			} else {
				messages.add(this.buildMessage(JptJpaCoreValidationMessages.MAPS_ID_VALUE_NOT_RESOLVED, this.getIdAttributeName()));
			}
		} else {
			// test whether attribute mapping is allowable
			if ( ! IterableTools.contains(this.getValidAttributeMappingChoices(), attributeMapping)) {
				messages.add(this.buildMessage(JptJpaCoreValidationMessages.MAPS_ID_VALUE_INVALID, this.getIdAttributeName()));
			}
		}
	}

	protected Iterable<AttributeMapping> getValidAttributeMappingChoices() {
		return this.buildAttributeMappingChoices(this.getIdAttributeMappings());
	}

	protected IMessage buildMessage(ValidationMessage msg, Object... args) {
		SpecifiedPersistentAttribute attribute = this.getPersistentAttribute();
		String attributeDescription = attribute.isVirtual() ?
				JptJpaCoreValidationArgumentMessages.VIRTUAL_ATTRIBUTE_DESC :
				JptJpaCoreValidationArgumentMessages.ATTRIBUTE_DESC;
		attributeDescription = NLS.bind(attributeDescription, attribute.getName());
		args = ArrayTools.add(args, 0, attributeDescription);
		TextRange textRange = attribute.isVirtual() ? 
				attribute.getValidationTextRange() :
				this.getValidationTextRange();
		return this.buildValidationMessage(textRange, msg, args);
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getAnnotationTextRange();
		return (textRange != null) ? textRange : this.getDerivedIdentity().getValidationTextRange();
	}

	protected TextRange getAnnotationTextRange() {
		return this.getAnnotation().getTextRange();
	}
}
