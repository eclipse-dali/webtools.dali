/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal;

import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.ui.internal.details.IJpaDetailsProvider;
import org.eclipse.jpt.ui.internal.java.details.IAttributeMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.ITypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.structure.IJpaStructureProvider;


public interface IJpaPlatformUi
{
	//TODO make these Collections IterableIterators(?) once Brian checks in
	//changes to our utility Iterators to make them Iterable
	
	Collection<IJpaStructureProvider> structureProviders();
	
	Collection<IJpaDetailsProvider> detailsProviders();
	
	/**
	 * Return the type mapping UI providers for java.  This will populate
	 * the type mapping combo box in order and displaying ITypeMappingUiProvider.label().
	 * It will also be used to create the appropriate composite given a type mapping. 
	 */
	List<ITypeMappingUiProvider> javaTypeMappingUiProviders();
	
	/**
	 * Return the attribute mapping UI providers for java.  This will populate
	 * the attribute mapping combo box in order and display IAttributeMappingUiProvider.label().
	 * It will also be used to create the appropriate composite given an attribute mapping. 
	 */
	List<IAttributeMappingUiProvider> javaAttributeMappingUiProviders();

	/**
	 * Return the default attribute mapping UI providers for java.  These will be used
	 * to provide a default mapping option if one applies in java.
	 */
	List<IAttributeMappingUiProvider> defaultJavaAttributeMappingUiProviders();

	void generateDDL(IJpaProject project, IStructuredSelection selection);
	
	void generateEntities(IJpaProject project, IStructuredSelection selection);
}
