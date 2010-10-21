/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal;

import java.util.List;
import org.eclipse.jpt.core.JpaAnnotationDefinitionProvider;
import org.eclipse.jpt.core.internal.AbstractJpaAnnotationDefintionProvider;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlAccessorOrderAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlAccessorTypeAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlAnyAttributeAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlAnyElementAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlAttachmentRefAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlAttributeAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlElementAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlElementDeclAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlElementRefAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlElementRefsAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlElementWrapperAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlElementsAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlEnumAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlEnumValueAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlIDAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlIDREFAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlInlineBinaryDataAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlJavaTypeAdapterAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlJavaTypeAdaptersAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlListAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlMimeTypeAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlMixedAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlRegistryAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlRootElementAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlSchemaAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlSeeAlsoAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlTransientAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlTypeAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlValueAnnotationDefinition;

/**
 * Support for JAXB annotations
 */
public class GenericJaxbAnnotationDefinitionProvider
	extends AbstractJpaAnnotationDefintionProvider
{
	// singleton
	private static final JpaAnnotationDefinitionProvider INSTANCE = 
			new GenericJaxbAnnotationDefinitionProvider();


	/**
	 * Return the singleton
	 */
	public static JpaAnnotationDefinitionProvider instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private GenericJaxbAnnotationDefinitionProvider() {
		super();
	}


	@Override
	protected void addPackageAnnotationDefinitionsTo(List<AnnotationDefinition> definitions) {
		definitions.add(XmlAccessorOrderAnnotationDefinition.instance());
		definitions.add(XmlAccessorTypeAnnotationDefinition.instance());
		definitions.add(XmlJavaTypeAdapterAnnotationDefinition.instance());
		definitions.add(XmlJavaTypeAdaptersAnnotationDefinition.instance());
		definitions.add(XmlSchemaAnnotationDefinition.instance());
	}

	@Override
	protected void addTypeAnnotationDefinitionsTo(List<AnnotationDefinition> definitions) {
		definitions.add(XmlAccessorOrderAnnotationDefinition.instance());
		definitions.add(XmlAccessorTypeAnnotationDefinition.instance());
		definitions.add(XmlEnumAnnotationDefinition.instance());
		definitions.add(XmlInlineBinaryDataAnnotationDefinition.instance());
		definitions.add(XmlJavaTypeAdapterAnnotationDefinition.instance());
		definitions.add(XmlRegistryAnnotationDefinition.instance());
		definitions.add(XmlRootElementAnnotationDefinition.instance());
		definitions.add(XmlSeeAlsoAnnotationDefinition.instance());
		definitions.add(XmlTransientAnnotationDefinition.instance());
		definitions.add(XmlTypeAnnotationDefinition.instance());
	}

	@Override
	protected void addTypeMappingAnnotationDefinitionsTo(List<AnnotationDefinition> definitions) {
		definitions.add(XmlRegistryAnnotationDefinition.instance());
		definitions.add(XmlTransientAnnotationDefinition.instance());
		definitions.add(XmlTypeAnnotationDefinition.instance());
	}

	@Override
	protected void addAttributeAnnotationDefinitionsTo(List<AnnotationDefinition> definitions) {
		definitions.add(XmlAnyAttributeAnnotationDefinition.instance());
		definitions.add(XmlAnyElementAnnotationDefinition.instance());
		definitions.add(XmlAttachmentRefAnnotationDefinition.instance());
		definitions.add(XmlAttributeAnnotationDefinition.instance());
		definitions.add(XmlElementAnnotationDefinition.instance());
		definitions.add(XmlElementDeclAnnotationDefinition.instance());
		definitions.add(XmlElementsAnnotationDefinition.instance());
		definitions.add(XmlElementRefAnnotationDefinition.instance());
		definitions.add(XmlElementRefsAnnotationDefinition.instance());
		definitions.add(XmlElementWrapperAnnotationDefinition.instance());
		definitions.add(XmlEnumValueAnnotationDefinition.instance());
		definitions.add(XmlIDAnnotationDefinition.instance());
		definitions.add(XmlIDREFAnnotationDefinition.instance());
		definitions.add(XmlInlineBinaryDataAnnotationDefinition.instance());
		definitions.add(XmlJavaTypeAdapterAnnotationDefinition.instance());
		definitions.add(XmlListAnnotationDefinition.instance());
		definitions.add(XmlMimeTypeAnnotationDefinition.instance());
		definitions.add(XmlMixedAnnotationDefinition.instance());
		definitions.add(XmlTransientAnnotationDefinition.instance());
		definitions.add(XmlValueAnnotationDefinition.instance());
	}
}
