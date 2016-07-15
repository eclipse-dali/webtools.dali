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
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class NewNameDialog
	extends ValidatingDialog<NewNameStateObject>
{
	private String description;
	private Image descriptionImage;
	private String descriptionTitle;
	private String labelText;
	private String name;
	private Collection<String> names;


	NewNameDialog(
			Shell parentShell,
			String dialogTitle,
			String descriptionTitle,
			Image descriptionImage,
			String description,
			String labelText,
			String name,
			Collection<String> names,
			ResourceManager resourceManager) {
		super(parentShell, resourceManager, dialogTitle);
		this.name             = name;
		this.names            = names;
		this.labelText        = labelText;
		this.description      = description;
		this.descriptionImage = descriptionImage;
		this.descriptionTitle = descriptionTitle;
	}

	@Override
	protected DialogPane<NewNameStateObject> buildLayout(Composite container) {
		return new NewNameDialogPane(this.labelText, this.getSubjectHolder(), container, this.resourceManager);
	}

	@Override
	protected NewNameStateObject buildStateObject() {
		return new NewNameStateObject(this.name, this.names);
	}

	@Override
	public void create() {
		super.create();
		this.getPane().selectAll();
		this.getButton(OK).setEnabled(false);
	}

	@Override
	protected NewNameDialogPane getPane() {
		return (NewNameDialogPane) super.getPane();
	}

	@Override
	protected String getDescription() {
		return this.description;
	}

	@Override
	protected Image getDescriptionImage() {
		return this.descriptionImage;
	}

	@Override
	protected String getDescriptionTitle() {
		return this.descriptionTitle;
	}

	/**
	 * Return the text field's input, which is the new name the user entered.
	 */
	public String getName() {
		return this.getSubject().getName();
	}


	static class NewNameDialogPane
		extends DialogPane<NewNameStateObject>
	{
		private final String labelText;
		private Text text;

		NewNameDialogPane(
				String labelText,
				PropertyValueModel<NewNameStateObject> subjectModel,
				Composite parentComposite,
				ResourceManager resourceManager) {
			super(subjectModel, parentComposite, resourceManager);
			this.labelText = labelText;
		}

		@Override
		protected Composite addComposite(Composite container) {
			return this.addSubPane(container, 2, 0, 0, 0, 0);
		}

		@Override
		protected void initializeLayout(Composite container) {
			this.addLabel(container, this.labelText);
			this.text = this.addText(container, this.buildNameModel());
		}

		private ModifiablePropertyValueModel<String> buildNameModel() {
			return new PropertyAspectAdapterXXXX<NewNameStateObject, String>(getSubjectHolder(), NewNameStateObject.NAME_PROPERTY) {
				@Override
				protected String buildValue_() {
					return this.subject.getName();
				}

				@Override
				protected void setValue_(String value) {
					this.subject.setName(value);
				}
			};
		}

		void selectAll() {
			this.text.selectAll();
		}
	}
}
