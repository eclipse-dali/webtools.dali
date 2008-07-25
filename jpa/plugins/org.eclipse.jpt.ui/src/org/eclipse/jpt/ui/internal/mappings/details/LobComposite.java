/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.swt.widgets.Composite;

/**
 * This composite simply shows the Lob check box.
 *
 * @see BasicMapping
 *
 * @version 2.0
 * @since 1.0
 */
public class LobComposite extends AbstractFormPane<BasicMapping>
{
	/**
	 * Creates a new <code>LobComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public LobComposite(AbstractFormPane<? extends BasicMapping> parentPane,
	                    Composite parent) {

		super(parentPane, parent);
	}

	private PropertyAspectAdapter<BasicMapping, Boolean> buildLobHolder() {

		return new PropertyAspectAdapter<BasicMapping, Boolean>(getSubjectHolder(), BasicMapping.LOB_PROPERTY) {

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
			JpaHelpContextIds.MAPPING_LOB
		);
	}
}
