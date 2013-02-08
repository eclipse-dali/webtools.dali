/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkEntity;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.details.AccessTypeComboViewer;
import org.eclipse.jpt.jpa.ui.internal.details.EntityNameCombo;
import org.eclipse.jpt.jpa.ui.internal.details.IdClassChooser;
import org.eclipse.jpt.jpa.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.TableComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.Hyperlink;

public class JavaEclipseLinkEntity1_2Composite
	extends AbstractJavaEclipseLinkEntityComposite<JavaEclipseLinkEntity>
{
	public JavaEclipseLinkEntity1_2Composite(
			PropertyValueModel<? extends JavaEclipseLinkEntity> entityModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(entityModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected Control initializeEntitySection(Composite container) {
		container = this.addSubPane(container, 2, 0, 0, 0, 0);

		// Table widgets
		TableComposite tableComposite = new TableComposite(this, container);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		tableComposite.getControl().setLayoutData(gridData);

		// Entity name widgets
		this.addLabel(container, JptUiDetailsMessages.EntityNameComposite_name);
		new EntityNameCombo(this, container);

		// Access type widgets
		this.addLabel(container, JptJpaUiMessages.AccessTypeComposite_access);
		new AccessTypeComboViewer(this, this.buildAccessReferenceModel(), container);	

		// Id class widgets
		Hyperlink hyperlink = this.addHyperlink(container,JptUiDetailsMessages.IdClassComposite_label);
		new IdClassChooser(this, this.buildIdClassReferenceModel(), container, hyperlink);

		return container;
	}
}
