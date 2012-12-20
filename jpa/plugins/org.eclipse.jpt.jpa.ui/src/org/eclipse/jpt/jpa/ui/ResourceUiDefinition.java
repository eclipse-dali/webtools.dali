/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.ui.jface.ItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.jpa.ui.editors.JpaEditorPageDefinition;

/**
 * Resource UI definition.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ResourceUiDefinition {
	/**
	 * Return whether the definition provides UI for resource of the specified
	 * type.
	 */
	boolean providesUi(JptResourceType resourceType);

	/**
	 * Return the details providers for this resource ui definition.
	 */
	Iterable<JpaDetailsProvider> getDetailsProviders();

	/**
	 * Return the resource definition's JPA Structure View factory provider.
	 * This is used by the view to build and maintain its tree's content and
	 * labels.
	 */
	ItemTreeStateProviderFactoryProvider getStructureViewFactoryProvider();

	/**
	 * Return the JPA editor page definitions to be displayed
	 * as tabs for an editor for the resource.
	 * <p>
	 * Currently Dali has only a <code>persistence.xml</code> editor.
	 * 
	 * @see org.eclipse.jpt.jpa.ui.internal.editors.JpaXmlEditor
	 */
	ListIterable<JpaEditorPageDefinition> getEditorPageDefinitions();
}
