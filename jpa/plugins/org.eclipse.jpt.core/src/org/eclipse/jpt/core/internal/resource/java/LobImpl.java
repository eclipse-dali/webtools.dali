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
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;

public class LobImpl extends AbstractAnnotationResource<Attribute> implements Lob
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.LOB);

	protected LobImpl(JavaResource parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
	}

	public String getAnnotationName() {
		return JPA.LOB;
	}
}
