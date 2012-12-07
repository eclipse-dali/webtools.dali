/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.widgets;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

/**
 * This builder is responsible to create a fully initialized
 * <code>NewNameDialog</code> once all the properties have been set.
 */
public final class NewNameDialogBuilder {

	/**
	 * The message to show in the description area.
	 */
	private String description;

	/**
	 * The image of the description area.
	 */
	private Image descriptionImage;

	/**
	 * The title to show in the description area.
	 */
	private String descriptionTitle;

	/**
	 * The title of the new name dialog.
	 */
	private String dialogTitle;

	/**
	 * The text field's label.
	 */
	private String labelText;

	/**
	 * The initial input or <code>null</code> if no initial value can be
	 * specified.
	 */
	private String name;

	/**
	 * The collection of names that can't be used or an empty collection if none
	 * are available.
	 */
	private Collection<String> names = Collections.emptyList();

	/**
	 * The parent shell of the new name dialog.
	 */
	private Shell parentShell;

	/**
	 * The new name dialog's resource manager;
	 */
	private ResourceManager resourceManager;

	/**
	 * Creates a new <code>NewNameDialogBuilder</code>.
	 *
	 * @param parentShell The parent shell of the new name dialog
	 */
	public NewNameDialogBuilder(Shell parentShell) {
		super();
		if (parentShell == null) {
			throw new NullPointerException();
		}
		this.parentShell = parentShell;
	}

	/**
	 * Creates the dialog that will be used to request a new name from the user.
	 *
	 * @return The initialized dialog
	 */
	public NewNameDialog buildDialog() {
		return new NewNameDialog(
			this.parentShell,
			this.dialogTitle,
			this.descriptionTitle,
			this.descriptionImage,
			this.description,
			this.labelText,
			this.name,
			this.names,
			this.resourceManager
		);
	}

	/**
	 * Sets the description to be shown in the description area under the title.
	 *
	 * @param description The message to show in the description area
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Sets the image to be shown to the right side of the description area.
	 *
	 * @param descriptionImage The image of the description area
	 */
	public void setDescriptionImage(Image descriptionImage) {
		this.descriptionImage = descriptionImage;
	}

	/**
	 * Sets the title to be shown in the description area.
	 *
	 * @param descriptionTitle The title to show in the description area
	 */
	public void setDescriptionTitle(String descriptionTitle) {
		this.descriptionTitle = descriptionTitle;
	}

	/**
	 * Sets the dialog's title.
	 *
	 * @param dialogTitle The title of the new name dialog
	 */
	public void setDialogTitle(String dialogTitle) {
		this.dialogTitle = dialogTitle;
	}

	/**
	 * Sets the existing names that will be used to validate the text field's
	 * input and prevent the user from using it.
	 *
	 * @param names The collection of names that can't be used
	 */
	public void setExistingNames(Iterator<String> names) {
		this.names = CollectionTools.collection(names);
	}

	/**
	 * Sets the text to label the text field.
	 *
	 * @param labelText The text field's label
	 */
	public void setLabelText(String labelText) {
		this.labelText = labelText;
	}

	/**
	 * Sets the initial name if one exists. It is valid to leave this
	 * <code>null</code> when the user has to enter something.
	 *
	 * @param name The initial input
	 */
	public void setName(String name) {
		this.name = name;
	}

	public void setResourceManager(ResourceManager resourceManager) {
		this.resourceManager = resourceManager;
	}
}
