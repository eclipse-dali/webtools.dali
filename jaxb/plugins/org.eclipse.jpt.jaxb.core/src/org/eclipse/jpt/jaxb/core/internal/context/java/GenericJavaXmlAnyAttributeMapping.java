/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.List;
import java.util.Map;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.java.JavaXmlAnyAttributeMapping;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAnyAttributeAnnotation;
import org.eclipse.jpt.jaxb.core.validation.JptJaxbCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaXmlAnyAttributeMapping
		extends AbstractJavaAdaptableAttributeMapping<XmlAnyAttributeAnnotation>
		implements JavaXmlAnyAttributeMapping {
	
	public GenericJavaXmlAnyAttributeMapping(JavaPersistentAttribute parent) {
		super(parent);
		initializeXmlJavaTypeAdapter();
	}
	
	
	public String getKey() {
		return MappingKeys.XML_ANY_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	protected String getAnnotationName() {
		return JAXB.XML_ANY_ATTRIBUTE;
	}
		
	
	// ***** validation *****
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		if (! getPersistentAttribute().isJavaResourceAttributeTypeSubTypeOf(Map.class.getName())) {
			messages.add(
				this.buildValidationMessage(
					getValidationTextRange(),
					JptJaxbCoreValidationMessages.XML_ANY_ATTRIBUTE__NON_MAP_TYPE
				));
		}
	}
}
