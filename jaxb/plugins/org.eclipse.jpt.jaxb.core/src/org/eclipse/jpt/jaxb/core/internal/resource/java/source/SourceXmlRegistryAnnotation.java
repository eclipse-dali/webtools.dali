/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlRegistryAnnotation;

/**
 * javax.xml.bind.annotation.XmlRegistry
 */
public final class SourceXmlRegistryAnnotation
	extends SourceAnnotation
	implements XmlRegistryAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JAXB.XML_REGISTRY);

	public SourceXmlRegistryAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		super(parent, annotatedElement, DECLARATION_ANNOTATION_ADAPTER);
	}

	public String getAnnotationName() {
		return JAXB.XML_REGISTRY;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		//no-op
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		//no-op
	}
}
