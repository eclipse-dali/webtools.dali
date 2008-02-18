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

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.IJpaStructureNode;
import org.eclipse.jpt.ui.internal.details.IJpaDetailsProvider;
import org.eclipse.ui.navigator.ICommonContentProvider;
import org.eclipse.ui.navigator.ICommonLabelProvider;

/**
 * This interface is to be implemented by a JPA vendor to provide extensions to
 * JPA UI functionality.  This is intended to work in conjunction with a core
 * JPA platform ({@link IJpaPlatform}) implementation with the same ID.
 * <p>
 * Any implementation should be <i>stateless</i> in nature.
 * <p>
 * The "generic" extension supplies UI for the core platform extension with the same
 * ID.
 *
 * @see the org.eclipse.jpt.ui.jpaPlatform extension point
 */
public interface IJpaPlatformUi
{
	/**
	 * Return a *new* navigator content provider.  There will be (at most) one
	 * of these per view that uses it.
	 */
	ICommonContentProvider buildNavigatorContentProvider();

	/**
	 * Return a *new* navigator label provider.  There will be (at most) one
	 * of these per view that uses it.
	 */
	ICommonLabelProvider buildNavigatorLabelProvider();


	void generateDDL(IJpaProject project, IStructuredSelection selection);

	void generateEntities(IJpaProject project, IStructuredSelection selection);


//	Iterator<IJpaStructureProvider> structureProviders();
//
//	IJpaStructureProvider structureProvider(String fileContentType);
//
//	Iterator<IJpaDetailsProvider> detailsProviders();
//
	IJpaDetailsProvider detailsProvider(IJpaStructureNode contextNode);
//
//	/**
//	 * Return the type mapping UI providers for java.  This will populate
//	 * the type mapping combo box in order and displaying ITypeMappingUiProvider.label().
//	 * It will also be used to create the appropriate composite given a type mapping.
//	 */
//	ListIterator<ITypeMappingUiProvider> javaTypeMappingUiProviders();
//
//	/**
//	 * Return the attribute mapping UI providers for java.  This will populate
//	 * the attribute mapping combo box in order and display IAttributeMappingUiProvider.label().
//	 * It will also be used to create the appropriate composite given an attribute mapping.
//	 */
//	ListIterator<IAttributeMappingUiProvider> javaAttributeMappingUiProviders();
//
//	/**
//	 * Return the default attribute mapping UI providers for java.  These will be used
//	 * to provide a default mapping option if one applies in java.
//	 */
//	ListIterator<IAttributeMappingUiProvider> defaultJavaAttributeMappingUiProviders();
//
//	IJpaUiFactory getJpaUiFactory();

}
