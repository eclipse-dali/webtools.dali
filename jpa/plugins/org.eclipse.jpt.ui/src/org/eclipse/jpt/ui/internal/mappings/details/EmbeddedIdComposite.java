/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.context.base.IEmbeddedIdMapping;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class EmbeddedIdComposite extends BaseJpaComposite<IEmbeddedIdMapping>
{
	public EmbeddedIdComposite(PropertyValueModel<? extends IEmbeddedIdMapping> subjectHolder,
	                           Composite parent,
	                           TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, SWT.NULL, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void disengageListeners() {
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void doPopulate() {
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void engageListeners() {
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite composite) {
	}
}