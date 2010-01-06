/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.persistence.options;

import org.eclipse.jpt.core.jpa2.context.persistence.options.JpaOptions2_0;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaPageComposite;
import org.eclipse.jpt.ui.internal.jpa2.Jpt2_0UiMessages;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 *  GenericPersistenceUnit2_0OptionsTab
 */
public class GenericPersistenceUnit2_0OptionsTab extends Pane<JpaOptions2_0>
	implements JpaPageComposite
{
	// ********** constructors/initialization **********
	/**
	 * Creates a new <code>GenericPersistenceUnit2_0OptionsTab</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public GenericPersistenceUnit2_0OptionsTab(
											PropertyValueModel<JpaOptions2_0> subjectHolder,
	                                        Composite parent,
	                                        WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite parent) {
		new GenericPersistenceUnit2_0OptionsComposite(this, parent);

	}

	// ********** Layout **********
	@Override
	protected Composite addContainer(Composite parent) {
		GridLayout layout = new GridLayout(1, true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.marginTop = 0;
		layout.marginLeft = 0;
		layout.marginBottom = 0;
		layout.marginRight = 0;
		layout.verticalSpacing = 15;
		Composite container = this.addPane(parent, layout);
		updateGridData(container);
		return container;
	}

	private void updateGridData(Composite container) {
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		container.setLayoutData(gridData);
	}

	// ********** JpaPageComposite implementation **********

	public String getHelpID() {
		return null;						// TODO - Review for JPA 2.0
	}
	
	public Image getPageImage() {
		return null;
	}

	public String getPageText() {
		return Jpt2_0UiMessages.GenericPersistenceUnit2_0OptionsTab_title;
	}
}
