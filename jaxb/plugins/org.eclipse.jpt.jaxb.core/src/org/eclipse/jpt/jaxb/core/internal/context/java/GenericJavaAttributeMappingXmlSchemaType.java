/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaTypeAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaAttributeMappingXmlSchemaType
		extends GenericJavaXmlSchemaType {
	
	public GenericJavaAttributeMappingXmlSchemaType(JaxbAttributeMapping parent, XmlSchemaTypeAnnotation xmlSchemaTypeAnnotation) {
		super(parent, xmlSchemaTypeAnnotation);
	}

	@Override
	public JaxbAttributeMapping getParent() {
		return (JaxbAttributeMapping) super.getParent();
	}

	protected JaxbAttributeMapping getAttributeMapping() {
		return getParent();
	}

	protected JaxbPersistentAttribute getPersistentAttribute() {
		return getAttributeMapping().getPersistentAttribute();
	}

	protected JaxbClassMapping getJaxbClassMapping() {
		return getPersistentAttribute().getClassMapping();
	}

	@Override
	protected JaxbPackage getJaxbPackage() {
		return this.getJaxbClassMapping().getJaxbType().getJaxbPackage();
	}
	
	
	// ***** validation *****
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		String fqType = getFullyQualifiedType();
		if (fqType != null && ! XmlSchemaTypeAnnotation.DEFAULT_TYPE.equals(fqType)) {
			messages.add(
					DefaultValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JaxbValidationMessages.XML_SCHEMA_TYPE__TYPE_SPECIFIED_ON_ATTRIBUTE,
							this,
							getTypeTextRange()));
		}
	}
}
