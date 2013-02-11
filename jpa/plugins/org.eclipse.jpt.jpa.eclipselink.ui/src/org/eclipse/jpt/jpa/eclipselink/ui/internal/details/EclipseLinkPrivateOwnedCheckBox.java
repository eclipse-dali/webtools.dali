/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkPrivateOwned;
import org.eclipse.jpt.jpa.eclipselink.ui.details.JptJpaEclipseLinkUiDetailsMessages;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * This composite simply shows the Lob check box.
 *
 * @see BasicMapping
 *
 * @version 2.1
 * @since 2.1
 */
public class EclipseLinkPrivateOwnedCheckBox extends Pane<EclipseLinkPrivateOwned>
{
	private Button checkBox;

	/**
	 * Creates a new <code>PrivateOwnedComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public EclipseLinkPrivateOwnedCheckBox(Pane<?> parentPane, 
		PropertyValueModel<? extends EclipseLinkPrivateOwned> subjectHolder,
		Composite parent) {

			super(parentPane, subjectHolder, parent);
	}

	@Override
	protected boolean addsComposite() {
		return false;
	}

	@Override
	public Control getControl() {
		return this.checkBox;
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.checkBox = addCheckBox(
			container,
			JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_PRIVATE_OWNED_COMPOSITE_PRIVATE_OWNED_LABEL,
			buildPrivateOwnedHolder(),
			null
		);
	}

	private PropertyAspectAdapter<EclipseLinkPrivateOwned, Boolean> buildPrivateOwnedHolder() {

		return new PropertyAspectAdapter<EclipseLinkPrivateOwned, Boolean>(getSubjectHolder(), EclipseLinkPrivateOwned.PRIVATE_OWNED_PROPERTY) {

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
}
