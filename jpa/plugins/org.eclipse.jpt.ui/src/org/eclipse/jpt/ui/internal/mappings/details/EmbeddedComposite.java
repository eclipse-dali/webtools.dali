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

import org.eclipse.jpt.core.internal.context.base.IEmbeddedMapping;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class EmbeddedComposite extends BaseJpaComposite<IEmbeddedMapping>
{
	private EmbeddedAttributeOverridesComposite attributeOverridesComposite;

	public EmbeddedComposite(PropertyValueModel<? extends IEmbeddedMapping> subjectHolder,
	                         Composite parent,
	                         TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, SWT.NULL, widgetFactory);
	}

	@Override
	protected void disengageListeners() {
	}


	@Override
	public void dispose() {
		this.attributeOverridesComposite.dispose();
	}

	@Override
	public void doPopulate() {
		this.attributeOverridesComposite.populate();
	}

	@Override
	protected void engageListeners() {
	}

	@Override
	protected void initializeLayout(Composite composite) {
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		composite.setLayout(layout);

		this.attributeOverridesComposite = new EmbeddedAttributeOverridesComposite(getSubjectHolder(), composite, getWidgetFactory());
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		this.attributeOverridesComposite.getControl().setLayoutData(gridData);
	}
}