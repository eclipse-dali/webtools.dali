/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.widgets;

import java.util.Collection;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * The dialog used to requests a name from the user.
 *
 * @version 2.0
 * @since 2.0
 */
public class NewNameDialog extends ValidatingDialog<NewNameStateObject>
{
	private String description;
	private Image descriptionImage;
	private String descriptionTitle;
	private String labelText;
	private String name;
	private Collection<String> names;

	/**
	 * Creates a new <code>NewNameDialog</code>.
	 *
	 * @param parentShell
	 * @param dialogTitle
	 * @param descriptionTitle
	 * @param descriptionImage
	 * @param description
	 * @param labelText
	 * @param name
	 * @param names
	 */
	NewNameDialog(Shell parentShell,
	              String dialogTitle,
	              String descriptionTitle,
	              Image descriptionImage,
	              String description,
	              String labelText,
	              String name,
	              Collection<String> names)
	{
		super(parentShell, dialogTitle);

		this.name             = name;
		this.names            = names;
		this.labelText        = labelText;
		this.description      = description;
		this.descriptionImage = descriptionImage;
		this.descriptionTitle = descriptionTitle;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected DialogPane<NewNameStateObject> buildLayout(Composite container) {
		return new NewNameDialogPane(container);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected NewNameStateObject buildStateObject() {
		return new NewNameStateObject(name, names);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void create() {
		super.create();

		NewNameDialogPane pane = (NewNameDialogPane) getPane();
		pane.selectAll();

		getButton(OK).setEnabled(false);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected String getDescription() {
		return description;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected Image getDescriptionImage() {
		return descriptionImage;
	}

	/* (non-Javadoc)
	 */
	@Override
	protected String getDescriptionTitle() {
		return descriptionTitle;
	}

	/**
	 * Returns the text field's input, which is the new name the user entered.
	 *
	 * @return The name the user entered
	 */
	public String getName() {
		return getSubject().getName();
	}

	private class NewNameDialogPane extends DialogPane<NewNameStateObject> {

		private Text text;

		NewNameDialogPane(Composite parent) {
			super(NewNameDialog.this.getSubjectHolder(), parent);
		}

		private WritablePropertyValueModel<String> buildNameHolder() {
			return new PropertyAspectAdapter<NewNameStateObject, String>(getSubjectHolder(), NewNameStateObject.NAME_PROPERTY) {
				@Override
				protected String buildValue_() {
					return subject.getName();
				}

				@Override
				protected void setValue_(String value) {
					subject.setName(value);
				}
			};
		}

		/*
		 * (non-Javadoc)
		 */
		@Override
		protected void initializeLayout(Composite container) {

			text = addLabeledText(
				container,
				labelText,
				buildNameHolder()
			);
		}

		void selectAll() {
			text.selectAll();
		}
	}
}
