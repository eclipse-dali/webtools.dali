/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.java;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementIterable;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.Entity;
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
		implements MapsIdDerivedIdentityStrategy2_0 {
			
	protected String specifiedIdAttributeName;
	
	protected String defaultIdAttributeName;
	
	
	public GenericJavaMapsIdDerivedIdentityStrategy2_0(JavaDerivedIdentity2_0 parent) {
		super(parent);
		this.specifiedIdAttributeName = buildSpecifiedIdAttributeName();
	}
	
	
	// ********** synchronize/update **********
	
	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setSpecifiedIdAttributeName_(this.buildSpecifiedIdAttributeName());
	}
	
	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
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
			getAnnotation().setValue(idAttributeName);
			setSpecifiedIdAttributeName_(idAttributeName);
		}
	}
	
	protected void setSpecifiedIdAttributeName_(String idAttributeName) {
		String old = this.specifiedIdAttributeName;
		this.specifiedIdAttributeName = idAttributeName;
		firePropertyChanged(SPECIFIED_ID_ATTRIBUTE_NAME_PROPERTY, old, idAttributeName);
	}
	
	protected String buildSpecifiedIdAttributeName() {
		return getAnnotation().getValue();
	}
	
	public String getDefaultIdAttributeName() {
		return this.defaultIdAttributeName;
	}
	
	protected void setDefaultIdAttributeName(String idAttributeName) {
		String old = this.defaultIdAttributeName;
		this.defaultIdAttributeName = idAttributeName;
		firePropertyChanged(DEFAULT_ID_ATTRIBUTE_NAME_PROPERTY, old, idAttributeName);
	}
	
	protected String buildDefaultIdAttributeName() {
		AttributeMapping mapping = getPersistentAttribute().getDeclaringTypeMapping().getIdAttributeMapping();
		if (mapping == null) {
			return null;
		}
		
		// if id mapping is embedded id ...
		if (ObjectTools.equals(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, mapping.getKey())) {
			// ... if embedded id and target entity have same primary key return embedded id name
			EmbeddedIdMapping embeddedId = (EmbeddedIdMapping) mapping;
			Entity targetEntity = getMapping().getResolvedTargetEntity();
			if (targetEntity != null &&
					ObjectTools.equals(embeddedId.getTargetEmbeddableName(), targetEntity.getPrimaryKeyClassName())) {
				return embeddedId.getName();
			}
			// ... otherwise use name of the mapping itself
			else {
				return getMapping().getName();
			}
		}
		// if id mapping is simple id, return its name
		else if (ObjectTools.equals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, mapping.getKey())) {
			return mapping.getName();
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
		return (MapsIdAnnotation2_0) getResourceAttribute().getNonNullAnnotation(getAnnotationName());
	}
	
	/**
	 * Return <code>null</code> if the annotation is not present.
	 */
	protected MapsIdAnnotation2_0 getAnnotationOrNull() {
		return (MapsIdAnnotation2_0) getResourceAttribute().getAnnotation(getAnnotationName());
	}
	
	protected void addAnnotation() {
		getResourceAttribute().addAnnotation(getAnnotationName());
	}
	
	protected void removeAnnotation() {
		getResourceAttribute().removeAnnotation(getAnnotationName());
	}
	
	protected String getAnnotationName() {
		return MapsIdAnnotation2_0.ANNOTATION_NAME;
	}
	
	
	// ********** misc **********
	
	protected JavaDerivedIdentity2_0 getDerivedIdentity() {
		return this.parent;
	}
	
	protected JavaSingleRelationshipMapping2_0 getMapping() {
		return getDerivedIdentity().getMapping();
	}
	
	protected JavaSpecifiedPersistentAttribute getPersistentAttribute() {
		return getMapping().getPersistentAttribute();
	}
	
	protected JavaResourceAttribute getResourceAttribute() {
		return getPersistentAttribute().getResourceAttribute();
	}
	
	protected Iterable<AttributeMapping> getAllAttributeMappings() {
		return getPersistentAttribute().getDeclaringTypeMapping().getAllAttributeMappings();
	}
	
	public Iterable<String> getSortedCandidateIdAttributeNames() {
		return IterableTools.sort(getAllAttributeMappingChoiceNames());
	}
	
	protected Iterable<String> getAllAttributeMappingChoiceNames() {
		return IterableTools.transform(getAllAttributeMappingChoices(), AttributeMapping.NAME_TRANSFORMER);
	}
	
	protected Iterable<AttributeMapping> getAllAttributeMappingChoices() {
		return buildAttributeMappingChoices(getAllAttributeMappings());
	}
	
	/**
	 * @see #getEmbeddedIdMappingChoiceIterable(EmbeddedIdMapping)
	 */
	protected Iterable<AttributeMapping> buildAttributeMappingChoices(Iterable<AttributeMapping> attributeMappings) {
		return IterableTools.children(attributeMappings, new AttributeMappingTransformer());
	}
	
	public class AttributeMappingTransformer
			extends TransformerAdapter<AttributeMapping, Iterable<AttributeMapping>> {
		
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
		String idAttributeName = getIdAttributeName();
		if (idAttributeName != null) {
			for (AttributeMapping mapping : getAllAttributeMappingChoices()) {
				if (idAttributeName.equals(mapping.getName())) {
					return mapping;
				}
			}
		}
		return null;
	}
	
	public boolean isSpecified() {
		return getAnnotationOrNull() != null;
	}
	
	public void addStrategy() {
		if (getAnnotationOrNull() == null) {
			addAnnotation();
		}
	}
	
	public void removeStrategy() {
		if (getAnnotationOrNull() != null) {
			removeAnnotation();
		}
	}
	
	
	// ********** Java completion proposals **********
	
	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (getAnnotation().valueTouches(pos)) {
			result = getSortedJavaValueChoices();
		}
		return result;
	}
	
	protected Iterable<String> getSortedJavaValueChoices() {
		return new TransformationIterable<String, String>(getSortedCandidateIdAttributeNames(),
				StringTools.JAVA_STRING_LITERAL_CONTENT_TRANSFORMER);
	}
	
	
	// ********** validation **********
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		validateMapsId(messages);
	}
	
	protected void validateMapsId(List<IMessage> messages) {
		if (getDerivedIdentity().usesMapsIdDerivedIdentityStrategy()) {
			validateMapsId_(messages);
		}
	}
	
	protected void validateMapsId_(List<IMessage> messages) {
		// test whether id attribute name can be resolved
		AttributeMapping attributeMapping = getDerivedIdAttributeMapping();
		if (attributeMapping == null) {
			// if id attribute name is not specified, use that message
			if (this.specifiedIdAttributeName == null) {
				messages.add(buildMessage(JptJpaCoreValidationMessages.MAPS_ID_VALUE_NOT_SPECIFIED));
			} else {
				messages.add(buildMessage(JptJpaCoreValidationMessages.MAPS_ID_VALUE_NOT_RESOLVED, getIdAttributeName()));
			}
		} else {
			// test whether attribute mapping is allowable
			if ( ! IterableTools.contains(getValidAttributeMappingChoices(), attributeMapping)) {
				messages.add(buildMessage(JptJpaCoreValidationMessages.MAPS_ID_VALUE_INVALID, getIdAttributeName()));
			}
		}
	}
	
	protected Iterable<AttributeMapping> getValidAttributeMappingChoices() {
		return this.buildAttributeMappingChoices(getPersistentAttribute().getDeclaringTypeMapping().getIdAttributeMappings());
	}
	
	protected IMessage buildMessage(ValidationMessage msg, Object... args) {
		SpecifiedPersistentAttribute attribute = getPersistentAttribute();
		String attributeDescription = attribute.isVirtual() ?
				JptJpaCoreValidationArgumentMessages.VIRTUAL_ATTRIBUTE_DESC :
				JptJpaCoreValidationArgumentMessages.ATTRIBUTE_DESC;
		attributeDescription = NLS.bind(attributeDescription, attribute.getName());
		args = ArrayTools.add(args, 0, attributeDescription);
		TextRange textRange = attribute.isVirtual() ? 
				attribute.getValidationTextRange() :
				getValidationTextRange();
		return buildValidationMessage(textRange, msg, args);
	}
	
	public TextRange getValidationTextRange() {
		TextRange textRange = getAnnotationTextRange();
		return (textRange != null) ? textRange : getDerivedIdentity().getValidationTextRange();
	}
	
	protected TextRange getAnnotationTextRange() {
		return getAnnotation().getTextRange();
	}
}
