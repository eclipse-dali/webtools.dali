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

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jpt.utility.internal.CommandExecutorProvider;

public interface JpaPlatform
{
	ListIterator<MappingAnnotationProvider> javaTypeMappingAnnotationProviders();

	Iterator<AnnotationProvider> javaTypeAnnotationProviders();

	ListIterator<MappingAnnotationProvider> javaAttributeMappingAnnotationProviders();

	Iterator<AnnotationProvider> javaAttributeAnnotationProviders();
		
	//TODO get this from IJpaProject
	CommandExecutorProvider modifySharedDocumentCommandExecutorProvider();

}
