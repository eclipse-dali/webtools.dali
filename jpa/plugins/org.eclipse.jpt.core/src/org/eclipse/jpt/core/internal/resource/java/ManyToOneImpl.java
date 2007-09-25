/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.content.java.mappings.JPA;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;


public class ManyToOneImpl extends AbstractAnnotationResource<Attribute> implements ManyToOne
{	
	public ManyToOneImpl(Attribute attribute, JpaPlatform jpaPlatform) {
		super(attribute, jpaPlatform, DECLARATION_ANNOTATION_ADAPTER);
	}
	
	public String getAnnotationName() {
		return JPA.MANY_TO_ONE;
	}

	public void updateFromJava(CompilationUnit astRoot) {
	}
}
