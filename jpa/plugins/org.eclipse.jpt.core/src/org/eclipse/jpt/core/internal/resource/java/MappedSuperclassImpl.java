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
import org.eclipse.jpt.core.internal.jdtutility.Type;


public class MappedSuperclassImpl extends AbstractAnnotationResource<Type> implements MappedSuperclass
{
	protected MappedSuperclassImpl(JavaPersistentTypeResource parent, Type type) {
		super(parent, type, DECLARATION_ANNOTATION_ADAPTER);
	}
	
	public String getAnnotationName() {
		return JPA.MAPPED_SUPERCLASS;
	}

	public void updateFromJava(@SuppressWarnings("unused") CompilationUnit astRoot) {
		//no annotation members
	}

}
