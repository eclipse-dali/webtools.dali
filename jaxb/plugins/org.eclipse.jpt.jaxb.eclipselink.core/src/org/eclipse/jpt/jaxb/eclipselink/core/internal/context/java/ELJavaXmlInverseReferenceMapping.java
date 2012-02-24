/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.internal.context.java.AbstractJavaAttributeMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.ELJaxbMappingKeys;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlInverseReferenceMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.validation.ELJaxbValidationMessageBuilder;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.validation.ELJaxbValidationMessages;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlInverseReferenceAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class ELJavaXmlInverseReferenceMapping
		extends AbstractJavaAttributeMapping<XmlInverseReferenceAnnotation>
		implements ELXmlInverseReferenceMapping {
	
	protected String mappedBy;
	
	
	public ELJavaXmlInverseReferenceMapping(JaxbPersistentAttribute parent) {
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
		if (StringTools.stringIsEmpty(mappedBy)) {
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
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		if (getXmlInverseReferenceAnnotation().mappedByTouches(pos, astRoot)) {
			result = getMappedByProposals(filter, astRoot);
		}
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		return EmptyIterable.instance();
	}
	
	protected Iterable<String> getMappedByProposals(Filter<String> filter, CompilationUnit astRoot) {
		JaxbClassMapping referencedClassMapping = getReferencedClassMapping();
		
		if (referencedClassMapping == null) {
			return EmptyIterable.instance();
		}
		
		return StringTools.convertToJavaStringLiterals(
				new TransformationIterable<JaxbPersistentAttribute, String>(referencedClassMapping.getAllLocallyDefinedAttributes()) {
					@Override
					protected String transform(JaxbPersistentAttribute o) {
						return o.getName();
					}
				});
	}
	
	
	// ***** validation *****
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		validateMappedBy(messages, reporter, astRoot);
	}
	
	protected void validateMappedBy(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		String mappedBy = getMappedBy();
		if (StringTools.stringIsEmpty(mappedBy)) {
			messages.add(
					ELJaxbValidationMessageBuilder.buildMessage(
							IMessage.HIGH_SEVERITY,
							ELJaxbValidationMessages.XML_INVERSE_REFERENCE__MAPPED_BY_NOT_SPECIFIED,
							this,
							getMappedByTextRange(astRoot)));
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
					ELJaxbValidationMessageBuilder.buildMessage(
							IMessage.HIGH_SEVERITY,
							ELJaxbValidationMessages.XML_INVERSE_REFERENCE__MAPPED_BY_NOT_RESOLVED,
							new String[] { mappedBy, referencedClassMapping.getJaxbType().getFullyQualifiedName() },
							this,
							getMappedByTextRange(astRoot)));
		}
		else if (
				! Tools.valueIsEqualToOneOf(
						referencedAttribute.getMappingKey(), 
						MappingKeys.XML_ELEMENT_ATTRIBUTE_MAPPING_KEY,
						MappingKeys.XML_ELEMENTS_ATTRIBUTE_MAPPING_KEY,
						MappingKeys.XML_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY,
						MappingKeys.XML_VALUE_ATTRIBUTE_MAPPING_KEY)) {
			messages.add(
					ELJaxbValidationMessageBuilder.buildMessage(
							IMessage.HIGH_SEVERITY,
							ELJaxbValidationMessages.XML_INVERSE_REFERENCE__MAPPED_BY_ILLEGAL_MAPPING_TYPE,
							new String[] { mappedBy, referencedClassMapping.getJaxbType().getFullyQualifiedName() },
							this,
							getMappedByTextRange(astRoot)));
		}
	}
	
	protected TextRange getMappedByTextRange(CompilationUnit astRoot) {
		TextRange textRange = getXmlInverseReferenceAnnotation().getMappedByTextRange(astRoot);
		return (textRange != null) ? textRange : getValidationTextRange(astRoot);
	}
}
