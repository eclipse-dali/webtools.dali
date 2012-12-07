/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.orm;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractEmbeddableComposite;
import org.eclipse.jpt.jpa.ui.internal.details.AccessTypeComboViewer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;

public abstract class AbstractOrmEmbeddableComposite
	extends AbstractEmbeddableComposite<OrmEmbeddable>
{
	protected AbstractOrmEmbeddableComposite(
			PropertyValueModel<? extends OrmEmbeddable> embeddableModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(embeddableModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected Control buildEmbeddableSectionClient(Section embeddableSection) {
		Composite container = this.addSubPane(embeddableSection, 2, 0, 0, 0, 0);

		// Java class widgets
		Hyperlink javaClassHyperlink = this.addHyperlink(container, JptUiDetailsOrmMessages.OrmJavaClassChooser_javaClass);
		new OrmJavaClassChooser(this, getSubjectHolder(), container, javaClassHyperlink);

		// Access type widgets
		this.addLabel(container, JptUiMessages.AccessTypeComposite_access);
		new AccessTypeComboViewer(this, this.buildAccessReferenceModel(), container);

		// Metadata complete widgets
		MetadataCompleteTriStateCheckBox metadataCompleteCheckBox = new MetadataCompleteTriStateCheckBox(this, getSubjectHolder(), container);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		metadataCompleteCheckBox.getControl().setLayoutData(gridData);

		return container;
	}
}
