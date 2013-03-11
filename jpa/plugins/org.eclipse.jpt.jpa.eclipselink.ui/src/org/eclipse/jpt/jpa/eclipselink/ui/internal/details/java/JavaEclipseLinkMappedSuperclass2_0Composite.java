/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaMappedSuperclass;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.AccessTypeComboViewer;
import org.eclipse.jpt.jpa.ui.internal.details.IdClassChooser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.Hyperlink;

public class JavaEclipseLinkMappedSuperclass2_0Composite
	extends AbstractJavaEclipseLinkMappedSuperclassComposite<EclipseLinkJavaMappedSuperclass>
{
	public JavaEclipseLinkMappedSuperclass2_0Composite(
			PropertyValueModel<? extends EclipseLinkJavaMappedSuperclass> mappedSuperclassModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(mappedSuperclassModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected Control initializeMappedSuperclassSection(Composite container) {
		container = this.addSubPane(container, 2, 0, 0, 0, 0);

		// Access type widgets
		this.addLabel(container, JptJpaUiMessages.AccessTypeComposite_access);
		new AccessTypeComboViewer(this, this.buildAccessReferenceModel(), container);

		// Id class widgets
		Hyperlink hyperlink = this.addHyperlink(container,JptJpaUiDetailsMessages.IdClassComposite_label);
		new IdClassChooser(this, this.buildIdClassReferenceModel(), container, hyperlink);

		return container;
	}

	@Override
	protected Control initializeCachingSection(Composite container) {
		return new JavaEclipseLinkCaching2_0Composite(this, this.buildCachingModel(), container).getControl();
	}
}
