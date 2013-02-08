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
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkMappedSuperclass;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.details.AccessTypeComboViewer;
import org.eclipse.jpt.jpa.ui.internal.details.IdClassChooser;
import org.eclipse.jpt.jpa.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.Hyperlink;

public class JavaEclipseLinkMappedSuperclass1_2Composite
	extends AbstractJavaEclipseLinkMappedSuperclassComposite<JavaEclipseLinkMappedSuperclass>
{
	/**
	 * Creates a new <code>JavaEclipseLinkMappedSuperclass1_2Composite</code>.
	 *
	 * @param subjectModel The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public JavaEclipseLinkMappedSuperclass1_2Composite(
			PropertyValueModel<? extends JavaEclipseLinkMappedSuperclass> mappedSuperclassModel,
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
		Hyperlink hyperlink = this.addHyperlink(container,JptUiDetailsMessages.IdClassComposite_label);
		new IdClassChooser(this, this.buildIdClassReferenceModel(), container, hyperlink);

		return container;
	}
}
