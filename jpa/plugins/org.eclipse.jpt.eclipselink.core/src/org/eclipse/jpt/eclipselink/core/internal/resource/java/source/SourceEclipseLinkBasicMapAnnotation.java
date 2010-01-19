/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkBasicMapAnnotation;

/**
 * org.eclipse.persistence.annotations.BasicMap
 */
public final class SourceEclipseLinkBasicMapAnnotation
	extends SourceAnnotation<Attribute>
	implements EclipseLinkBasicMapAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	
	public SourceEclipseLinkBasicMapAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public void initialize(CompilationUnit astRoot) {
		// do nothing
	}
	
	public void synchronizeWith(CompilationUnit astRoot) {
		// do nothing
	}

}
