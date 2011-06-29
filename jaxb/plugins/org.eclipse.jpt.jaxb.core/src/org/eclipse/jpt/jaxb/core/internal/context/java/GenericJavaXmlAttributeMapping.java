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
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.JaxbSchemaComponentRef;
import org.eclipse.jpt.jaxb.core.context.XmlAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlNsForm;
import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;
import org.eclipse.jpt.jaxb.core.internal.JptJaxbCoreMessages;
import org.eclipse.jpt.jaxb.core.resource.java.SchemaComponentRefAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAttributeAnnotation;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaXmlAttributeMapping
		extends GenericJavaContainmentMapping<XmlAttributeAnnotation>
		implements XmlAttributeMapping {
	
	public GenericJavaXmlAttributeMapping(JaxbPersistentAttribute parent) {
		super(parent);
	}
	
	
	@Override
	protected JaxbSchemaComponentRef buildSchemaComponentRef() {
		return new XmlAttributeSchemaComponentRef(this);
	}
	
	public String getKey() {
		return MappingKeys.XML_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected String getAnnotationName() {
		return XmlAttributeAnnotation.ANNOTATION_NAME;
	}
	
	
	protected class XmlAttributeSchemaComponentRef
			extends ContainmentMappingSchemaComponentRef {
		
		protected XmlAttributeSchemaComponentRef(JavaContextNode parent) {
			super(parent);
		}
		
		
		@Override
		protected SchemaComponentRefAnnotation getAnnotation(boolean createIfNull) {
			if (createIfNull) {
				return GenericJavaXmlAttributeMapping.this.getOrCreateAnnotation();
			}
			else {
				return GenericJavaXmlAttributeMapping.this.getAnnotation();
			}
		}
		
		@Override
		public String getDefaultNamespace() {
			return (GenericJavaXmlAttributeMapping.this.getJaxbPackage().getAttributeFormDefault() == XmlNsForm.QUALIFIED) ?
					GenericJavaXmlAttributeMapping.this.getPersistentClass().getNamespace() : "";
		}
		
		@Override
		public Iterable<String> getNameProposals(Filter<String> filter) {
			XsdTypeDefinition xsdType = GenericJavaXmlAttributeMapping.this.getPersistentClass().getXsdTypeDefinition();
			return (xsdType == null) ? EmptyIterable.instance() : xsdType.getAttributeNameProposals(getNamespace(), filter);
		}
		
		@Override
		public String getSchemaComponentTypeDescription() {
			return JptJaxbCoreMessages.XML_ATTRIBUTE__ATTRIBUTE;
		}
		
		@Override
		protected void validateSchemaComponentRef(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
			XsdTypeDefinition type = getPersistentClass().getXsdTypeDefinition();
			if (type != null) {
				if (type.getAttribute(getNamespace(), getName()) == null) {
					messages.add(getUnresolveSchemaComponentMessage(astRoot));
				}
			}
		}
	}
}
