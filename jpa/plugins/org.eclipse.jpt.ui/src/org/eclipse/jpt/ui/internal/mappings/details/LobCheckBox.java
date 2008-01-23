/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.context.base.IBasicMapping;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * This composite simply shows the Lob check box.
 *
 * @see IBasicMapping
 *
 * @version 2.0
 * @since 1.0
 */
public class LobCheckBox extends BaseJpaController<IBasicMapping>
{
	/**
	 * Creates a new <code>LobCheckBox</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param parent The parent container
	 */
	public LobCheckBox(BaseJpaController<? extends IBasicMapping> parentController,
	                   Composite parent) {

		super(parentController, parent);
	}

	/**
	 * Creates a new <code>LobCheckBox</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IBasicMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public LobCheckBox(PropertyValueModel<? extends IBasicMapping> subjectHolder,
	                   Composite parent,
	                   TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private PropertyAspectAdapter<IBasicMapping, Boolean> buildLobHolder() {

		return new PropertyAspectAdapter<IBasicMapping, Boolean>(getSubjectHolder(), IBasicMapping.LOB_PROPERTY) {

			@Override
			protected Boolean buildValue_() {
				return subject.isLob();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setLob(value);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		buildCheckBox(
			container,
			JptUiMappingsMessages.BasicGeneralSection_lobLabel,
			buildLobHolder(),
			IJpaHelpContextIds.MAPPING_LOB
		);
	}
}