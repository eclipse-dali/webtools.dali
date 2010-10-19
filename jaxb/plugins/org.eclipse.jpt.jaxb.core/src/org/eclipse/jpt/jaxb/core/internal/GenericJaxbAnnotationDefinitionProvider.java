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
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlEnumAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlInlineBinaryDataAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlJavaTypeAdapterAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlJavaTypeAdaptersAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlRegistryAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlRootElementAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlSeeAlsoAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlTransientAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.internal.resource.java.XmlTypeAnnotationDefinition;

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
	}

	@Override
	protected void addTypeAnnotationDefinitionsTo(List<AnnotationDefinition> definitions) {
		definitions.add(XmlAccessorOrderAnnotationDefinition.instance());
		definitions.add(XmlAccessorTypeAnnotationDefinition.instance());
		definitions.add(XmlEnumAnnotationDefinition.instance());
		definitions.add(XmlInlineBinaryDataAnnotationDefinition.instance());
		definitions.add(XmlJavaTypeAdapterAnnotationDefinition.instance());
		definitions.add(XmlJavaTypeAdaptersAnnotationDefinition.instance());
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
		definitions.add(XmlInlineBinaryDataAnnotationDefinition.instance());
		definitions.add(XmlJavaTypeAdapterAnnotationDefinition.instance());
		definitions.add(XmlJavaTypeAdaptersAnnotationDefinition.instance());
		definitions.add(XmlTransientAnnotationDefinition.instance());
	}
}
