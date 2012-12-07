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
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.ui.forms.IManagedForm;

/**
 * A JPA editor page definition defines an editor page's:<ul>
 * <li>title image
 * <li>title text
 * <li>help ID
 * <li>content
 * </ul>
 * We take advantage of the {@link org.eclipse.ui.forms.editor.FormEditor FormEditor}
 * behavior by not building the content
 * of any particular editor page until its tab is selected. When the tab
 * <em>is</em> selected we call
 * {@link #buildContent(IManagedForm, WidgetFactory, ResourceManager, PropertyValueModel)}.
 * 
 * @see org.eclipse.jpt.jpa.ui.ResourceUiDefinition
 * @see org.eclipse.jpt.jpa.ui.internal.editors.JpaXmlEditor
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaEditorPageDefinition {

	/**
	 * Return the descriptor for the image to be displayed alongside the page's
	 * title {@link #getTitleText() text}.
	 * @see org.eclipse.ui.forms.widgets.ScrolledForm#getImage()
	 */
	ImageDescriptor getTitleImageDescriptor();

	/**
	 * Return the text to be displayed in the page's title.
	 * @see org.eclipse.ui.forms.widgets.ScrolledForm#getText()
	 */
	String getTitleText();

	/**
	 * Return the page's help ID.
	 * This ID will be used if the help button is invoked.
	 */
	String getHelpID();

	/**
	 * Build the page's content in the specified form, using the specified
	 * widget factory, resource manager, and JPA structure node model.
	 */
	void buildContent(IManagedForm form, WidgetFactory widgetFactory, ResourceManager resourceManager, PropertyValueModel<JpaStructureNode> rootStructureNodeModel);
}
