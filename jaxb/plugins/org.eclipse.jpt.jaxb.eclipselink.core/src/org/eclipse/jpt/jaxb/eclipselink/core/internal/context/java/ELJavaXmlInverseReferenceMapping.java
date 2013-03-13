/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jaxb.core.internal.context.java.AbstractJavaAttributeMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.ELJaxbMappingKeys;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELXmlInverseReferenceMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlInverseReferenceAnnotation;
import org.eclipse.jpt.jaxb.eclipselink.core.validation.JptJaxbEclipseLinkCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class ELJavaXmlInverseReferenceMapping
		extends AbstractJavaAttributeMapping<XmlInverseReferenceAnnotation>
		implements ELXmlInverseReferenceMapping {
	
	protected String mappedBy;
	
	
	public ELJavaXmlInverseReferenceMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.mappedBy = getResourceMappedBy();
	}
	
	
	public String getKey() {
		return ELJaxbMappingKeys.XML_INVERSE_REFERENCE_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	protected String getAnnotationName() {
		return ELJaxb.XML_INVERSE_REFERENCE;
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		
		setMappedBy_(getResourceMappedBy());
	}
	
	
	// ***** mappedBy *****
	
	public String getMappedBy() {
		return mappedBy;
	}
	
	public void setMappedBy(String mappedBy) {
		getXmlInverseReferenceAnnotation().setMappedBy(mappedBy);
		setMappedBy_(mappedBy);
	}
	
	protected void setMappedBy_(String mappedBy) {
		String old = this.mappedBy;
		this.mappedBy = mappedBy;
		firePropertyChanged(MAPPED_BY_PROPERTY, old, mappedBy);
	}
	
	public XmlInverseReferenceAnnotation getXmlInverseReferenceAnnotation() {
		return (XmlInverseReferenceAnnotation) getJavaResourceAttribute().getAnnotation(ELJaxb.XML_INVERSE_REFERENCE);
	}
	
	public String getResourceMappedBy() {
		return getXmlInverseReferenceAnnotation().getMappedBy();
	}
	
	
	// ***** misc *****
	
	protected JaxbClassMapping getReferencedClassMapping() {
		String referencedClassName = getValueTypeName();
		return (referencedClassName == null) ? null : getContextRoot().getClassMapping(referencedClassName);
	}
	
	protected JaxbPersistentAttribute getReferencedAttribute() {
		String mappedBy = getMappedBy();
		if (StringTools.isBlank(mappedBy)) {
			return null;
		}
		JaxbClassMapping referencedClassMapping = getReferencedClassMapping();
		if (referencedClassMapping == null) {
			return null;
		}
		JaxbPersistentAttribute tempResult = null;
		for (JaxbPersistentAttribute attribute : referencedClassMapping.getAllLocallyDefinedAttributes()) {
			if (attribute.getName().equals(mappedBy)) {
				if (attribute.getMappingKey() == MappingKeys.XML_TRANSIENT_ATTRIBUTE_MAPPING_KEY) {
					tempResult = attribute;
				}
				else {
					return attribute;
				}
			}
		}
		return tempResult;
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (! IterableTools.isEmpty(result)) {
			return result;
		}
		
		if (getXmlInverseReferenceAnnotation().mappedByTouches(pos)) {
			result = getMappedByProposals();
		}
		if (! IterableTools.isEmpty(result)) {
			return result;
		}
		
		return EmptyIterable.instance();
	}
	
	protected Iterable<String> getMappedByProposals() {
		JaxbClassMapping referencedClassMapping = getReferencedClassMapping();
		
		if (referencedClassMapping == null) {
			return EmptyIterable.instance();
		}
		
		return IterableTools.transform(
				IterableTools.transform(
						referencedClassMapping.getAllLocallyDefinedAttributes(),
						JaxbPersistentAttribute.NAME_TRANSFORMER),
				StringTools.JAVA_STRING_LITERAL_CONTENT_TRANSFORMER);
	}
	
	
	// ***** validation *****
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		validateMappedBy(messages, reporter);
	}
	
	protected void validateMappedBy(List<IMessage> messages, IReporter reporter) {
		if (StringTools.isBlank(mappedBy)) {
			messages.add(
					this.buildValidationMessage(
							getMappedByTextRange(),
							JptJaxbEclipseLinkCoreValidationMessages.XML_INVERSE_REFERENCE__MAPPED_BY_NOT_SPECIFIED
						));
			return;
		}
		
		JaxbClassMapping referencedClassMapping = getReferencedClassMapping();
		if (referencedClassMapping == null) {
			// TODO validate?
			return;
		}
		
		JaxbPersistentAttribute referencedAttribute = getReferencedAttribute();
		if (referencedAttribute == null) {
			messages.add(
					this.buildValidationMessage(
							getMappedByTextRange(),
							JptJaxbEclipseLinkCoreValidationMessages.XML_INVERSE_REFERENCE__MAPPED_BY_NOT_RESOLVED,
							mappedBy,
							referencedClassMapping.getTypeName().getFullyQualifiedName()));
		}
		else if (
				! ArrayTools.contains(
						getValidReferencedAttributeMappingKeys(),
						referencedAttribute.getMappingKey())) {
			messages.add(
					this.buildValidationMessage(
							getMappedByTextRange(),
							JptJaxbEclipseLinkCoreValidationMessages.XML_INVERSE_REFERENCE__MAPPED_BY_ILLEGAL_MAPPING_TYPE,
							mappedBy,
							referencedClassMapping.getTypeName().getFullyQualifiedName()));
		}
	}

	protected String[] getValidReferencedAttributeMappingKeys() {
		return VALID_REFERENCED_ATTRIBUTE_MAPPING_KEYS;
	}

	protected static final String[] VALID_REFERENCED_ATTRIBUTE_MAPPING_KEYS = new String[] {
		MappingKeys.XML_ELEMENT_ATTRIBUTE_MAPPING_KEY,
		MappingKeys.XML_ELEMENTS_ATTRIBUTE_MAPPING_KEY,
		MappingKeys.XML_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY,
		MappingKeys.XML_VALUE_ATTRIBUTE_MAPPING_KEY
	};
	
	protected TextRange getMappedByTextRange() {
		TextRange textRange = getXmlInverseReferenceAnnotation().getMappedByTextRange();
		return (textRange != null) ? textRange : getValidationTextRange();
	}
}
