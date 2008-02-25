/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.platform.JpaPlatform;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.ui.internal.structure.JpaStructureProvider;
import org.eclipse.ui.navigator.ICommonContentProvider;
import org.eclipse.ui.navigator.ICommonLabelProvider;

/**
 * This interface is to be implemented by a JPA vendor to provide extensions to
 * JPA UI functionality.  This is intended to work in conjunction with a core
 * JPA platform ({@link JpaPlatform}) implementation with the same ID.
 * <p>
 * Any implementation should be <i>stateless</i> in nature.
 * <p>
 * The "generic" extension supplies UI for the core platform extension with the same
 * ID.
 *
 * @see the org.eclipse.jpt.ui.jpaPlatform extension point
 */
public interface JpaPlatformUi
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
	
	/**
	 * Return a *new* structure provider for the given JPA file
	 */
	// TODO - binary java type support
	JpaStructureProvider buildStructureProvider(JpaFile jpaFile);
	
	JpaDetailsProvider detailsProvider(JpaStructureNode contextNode);

	void generateDDL(JpaProject project, IStructuredSelection selection);

	void generateEntities(JpaProject project, IStructuredSelection selection);
}
