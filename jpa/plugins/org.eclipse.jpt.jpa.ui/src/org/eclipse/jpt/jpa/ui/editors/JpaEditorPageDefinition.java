/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.editors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.ui.forms.IManagedForm;

/**
 * A {@link JpaEditorPageDefinition} defines the content of an editor page.
 * We take advantage of the FormEditor behavior by not building the content
 * of any particular editor page until that tab is selected. At this point
 * {@link #buildEditorPageContent(IManagedForm, WidgetFactory, PropertyValueModel)}
 * will be called.
 * 
 * @see org.eclipse.jpt.jpa.ui.ResourceUiDefinition
 * @see org.eclipse.jpt.jpa.ui.internal.editors.JpaXmlEditor
 * 
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaEditorPageDefinition {

	/**
	 * Returns the help ID. This ID will be used if the help button is invoked.
	 *
	 * @return Either the help ID of this page or <code>null</code> if no help
	 * is required
	 */
	String getHelpID();

	/**
	 * The image descriptor of the tab showing this page.
	 *
	 * @return The page's image
	 */
	ImageDescriptor getPageImageDescriptor();

	/**
	 * The text of the tab showing this page.
	 *
	 * @return The page's text
	 */
	String getPageText();

	/**
	 * Build the content of this editor page using the given WidgetFactory
	 * and the JpaStructureNode model.
	 */
	void buildEditorPageContent(IManagedForm form, WidgetFactory widgetFactory, PropertyValueModel<JpaStructureNode> rootStructureNodeModel);
}
