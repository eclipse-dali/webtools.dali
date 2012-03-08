/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationDescriptionMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaDerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaMapsIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaSingleRelationshipMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapsId2_0Annotation;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaMapsIdDerivedIdentityStrategy2_0
	extends AbstractJavaJpaContextNode
	implements JavaMapsIdDerivedIdentityStrategy2_0
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
		if (this.valuesAreDifferent(idAttributeName, this.specifiedIdAttributeName)) {
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

	protected JavaResourceAttribute getResourceAttribute() {
		return this.getPersistentAttribute().getResourceAttribute();
	}

	protected Iterable<AttributeMapping> getAllAttributeMappings() {
		return CollectionTools.collection(this.getPersistentAttribute().getOwningTypeMapping().getAllAttributeMappings());
	}

	public Iterable<String> getSortedCandidateIdAttributeNames() {
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
				CollectionTools.collection(embeddable.getAllAttributeMappings())
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
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.getAnnotation().valueTouches(pos)) {
			result = this.getSortedJavaValueChoices(filter);
		}
		return result;
	}

	protected Iterable<String> getSortedJavaValueChoices(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(new FilteringIterable<String>(this.getSortedCandidateIdAttributeNames(), filter));
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
		// test whether id attribute name can be resolved
		AttributeMapping attributeMapping = this.getDerivedIdAttributeMapping();
		if (attributeMapping == null) {
			// if id attribute name is not specified, use that message
			if (this.specifiedIdAttributeName == null) {
				messages.add(this.buildMessage(JpaValidationMessages.MAPS_ID_VALUE_NOT_SPECIFIED, EMPTY_STRING_ARRAY, astRoot));
			} else {
				messages.add(this.buildMessage(JpaValidationMessages.MAPS_ID_VALUE_NOT_RESOLVED, new String[] {this.getIdAttributeName()}, astRoot));
			}
		} else {
			// test whether attribute mapping is allowable
			if ( ! CollectionTools.contains(this.getValidAttributeMappingChoices(), attributeMapping)) {
				messages.add(this.buildMessage(JpaValidationMessages.MAPS_ID_VALUE_INVALID, new String[] {this.getIdAttributeName()}, astRoot));
			}
		}
	}

	protected Iterable<AttributeMapping> getValidAttributeMappingChoices() {
		return this.buildAttributeMappingChoices(this.getIdAttributeMappings());
	}

	protected IMessage buildMessage(String msgID, String[] parms, CompilationUnit astRoot) {
		PersistentAttribute attribute = this.getPersistentAttribute();
		String attributeDescription = attribute.isVirtual() ?
				JpaValidationDescriptionMessages.VIRTUAL_ATTRIBUTE_DESC :
				JpaValidationDescriptionMessages.ATTRIBUTE_DESC;
		attributeDescription = NLS.bind(attributeDescription, attribute.getName());
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
		TextRange textRange = this.getAnnotationTextRange(astRoot);
		return (textRange != null) ? textRange : this.getDerivedIdentity().getValidationTextRange(astRoot);
	}

	protected TextRange getAnnotationTextRange(CompilationUnit astRoot) {
		return this.getAnnotation().getTextRange(astRoot);
	}
}
