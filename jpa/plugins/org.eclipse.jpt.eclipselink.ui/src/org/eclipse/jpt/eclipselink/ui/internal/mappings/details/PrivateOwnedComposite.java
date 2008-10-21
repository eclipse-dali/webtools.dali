/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.mappings.details;

import org.eclipse.jpt.eclipselink.core.context.PrivateOwned;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * This composite simply shows the Lob check box.
 *
 * @see BasicMapping
 *
 * @version 2.1
 * @since 2.1
 */
public class PrivateOwnedComposite extends FormPane<PrivateOwned>
{
	/**
	 * Creates a new <code>PrivateOwnedComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public PrivateOwnedComposite(FormPane<?> parentPane, 
		PropertyValueModel<? extends PrivateOwned> subjectHolder,
		Composite parent) {

			super(parentPane, subjectHolder, parent);
	}
	
	private PropertyAspectAdapter<PrivateOwned, Boolean> buildPrivateOwnedHolder() {

		return new PropertyAspectAdapter<PrivateOwned, Boolean>(getSubjectHolder(), PrivateOwned.PRIVATE_OWNED_PROPERTY) {

			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isPrivateOwned());
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setPrivateOwned(value.booleanValue());
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {
		addCheckBox(
			container,
			EclipseLinkUiMappingsMessages.PrivateOwnedComposite_privateOwnedLabel,
			buildPrivateOwnedHolder(),
			null
		);
	}
}
