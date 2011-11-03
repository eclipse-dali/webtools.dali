/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.context.JaxbBasicMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.JaxbType;
import org.eclipse.jpt.jaxb.core.context.JaxbTypeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlList;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.XmlListAnnotation;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;
import org.eclipse.jpt.jaxb.core.xsd.XsdSimpleTypeDefinition;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.jpt.jaxb.core.xsd.XsdUtil;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.eclipse.xsd.XSDVariety;

public class GenericJavaXmlList
		extends AbstractJavaContextNode
		implements XmlList {
	
	protected final XmlListAnnotation resourceXmlList;
	
	
	public GenericJavaXmlList(JaxbBasicMapping parent, XmlListAnnotation resource) {
		super(parent);
		this.resourceXmlList = resource;
	}
	
	
	public JaxbBasicMapping getMapping() {
		return (JaxbBasicMapping) getParent();
	}
	
	protected JaxbPersistentAttribute getPersistentAttribute() {
		return getMapping().getPersistentAttribute();
	}


	//************* validation ****************

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		if (! getPersistentAttribute().isJavaResourceAttributeCollectionType()) {
			messages.add(
				DefaultValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JaxbValidationMessages.XML_LIST__ATTRIBUTE_NOT_COLLECTION_TYPE,
					this,
					getValidationTextRange(astRoot)));
		}
		else {
			String dataType = getMapping().getDataTypeName();
			boolean noTypeAssociated = false;
			XsdTypeDefinition itemXsdType = null;
			
			JaxbType jaxbType = getContextRoot().getType(dataType);
			if (jaxbType != null) {
				JaxbTypeMapping typeMapping = jaxbType.getMapping();
				if (typeMapping != null) {
					itemXsdType = typeMapping.getXsdTypeDefinition();
				}
			}
			else {
				String builtInType = getJaxbProject().getPlatform().getDefinition().getSchemaTypeMapping(dataType);
				if (builtInType != null) {
					XsdSchema xsdSchema = XsdUtil.getSchemaForSchema();
					itemXsdType = xsdSchema.getTypeDefinition(builtInType);
				}
				else {
					noTypeAssociated = true;
				}
			}
			
			if (noTypeAssociated 
					|| itemXsdType == null
					|| itemXsdType.getKind() != XsdTypeDefinition.Kind.SIMPLE
					|| ((XsdSimpleTypeDefinition) itemXsdType).getXSDComponent().getVariety() == XSDVariety.LIST_LITERAL) {
				
				messages.add(
						DefaultValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JaxbValidationMessages.XML_LIST__ITEM_TYPE_NOT_MAPPED_TO_VALID_SCHEMA_TYPE,
								new String[] { dataType },
								this,
								getValidationTextRange(astRoot)));
			}
		}
		
	}

	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.resourceXmlList.getTextRange(astRoot);
	}
}
