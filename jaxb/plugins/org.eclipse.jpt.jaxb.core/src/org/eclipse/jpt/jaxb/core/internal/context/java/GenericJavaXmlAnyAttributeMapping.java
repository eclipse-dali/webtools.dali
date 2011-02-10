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
import java.util.Map;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlAdaptable;
import org.eclipse.jpt.jaxb.core.context.XmlAnyAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlJavaTypeAdapter;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAnyAttributeAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaXmlAnyAttributeMapping
	extends AbstractJavaAttributeMapping<XmlAnyAttributeAnnotation>
	implements XmlAnyAttributeMapping
{

	protected final XmlAdaptable xmlAdaptable;


	public GenericJavaXmlAnyAttributeMapping(JaxbPersistentAttribute parent) {
		super(parent);
		this.xmlAdaptable = buildXmlAdaptable();
	}

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.xmlAdaptable.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.xmlAdaptable.update();
	}

	public String getKey() {
		return MappingKeys.XML_ANY_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected String getAnnotationName() {
		return XmlAnyAttributeAnnotation.ANNOTATION_NAME;
	}

	//****************** XmlJavaTypeAdapter *********************

	public XmlAdaptable buildXmlAdaptable() {
		return new GenericJavaXmlAdaptable(this, new XmlAdaptable.Owner() {
			public JavaResourceAnnotatedElement getResource() {
				return getJavaResourceAttribute();
			}
			public XmlJavaTypeAdapter buildXmlJavaTypeAdapter(XmlJavaTypeAdapterAnnotation adapterAnnotation) {
				return GenericJavaXmlAnyAttributeMapping.this.buildXmlJavaTypeAdapter(adapterAnnotation);
			}
			public void fireXmlAdapterChanged(XmlJavaTypeAdapter oldAdapter, XmlJavaTypeAdapter newAdapter) {
				GenericJavaXmlAnyAttributeMapping.this.firePropertyChanged(XML_JAVA_TYPE_ADAPTER_PROPERTY, oldAdapter, newAdapter);
			}
		});
	}

	public XmlJavaTypeAdapter getXmlJavaTypeAdapter() {
		return this.xmlAdaptable.getXmlJavaTypeAdapter();
	}

	public XmlJavaTypeAdapter addXmlJavaTypeAdapter() {
		return this.xmlAdaptable.addXmlJavaTypeAdapter();
	}

	protected XmlJavaTypeAdapter buildXmlJavaTypeAdapter(XmlJavaTypeAdapterAnnotation xmlJavaTypeAdapterAnnotation) {
		return new GenericJavaAttributeXmlJavaTypeAdapter(this, xmlJavaTypeAdapterAnnotation);
	}

	public void removeXmlJavaTypeAdapter() {
		this.xmlAdaptable.removeXmlJavaTypeAdapter();
	}

	//************* validation ****************
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		if (!getPersistentAttribute().isJavaResourceAttributeTypeSubTypeOf(Map.class.getName())) {
			messages.add(
				DefaultValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JaxbValidationMessages.XML_ANY_ATTRIBUTE_MAPPING_DEFINED_ON_NON_MAP,
					this,
					getValidationTextRange(astRoot)));
		}
	}

}
