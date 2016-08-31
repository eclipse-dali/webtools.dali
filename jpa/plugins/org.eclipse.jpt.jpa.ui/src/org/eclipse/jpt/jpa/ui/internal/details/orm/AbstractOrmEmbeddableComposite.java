/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.details.orm.JptJpaUiDetailsOrmMessages;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractEmbeddableComposite;
import org.eclipse.jpt.jpa.ui.internal.details.AccessTypeComboViewer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;

public abstract class AbstractOrmEmbeddableComposite<T extends OrmEmbeddable>
	extends AbstractEmbeddableComposite<T>
{
	protected AbstractOrmEmbeddableComposite(
			PropertyValueModel<? extends T> embeddableModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(embeddableModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected Control buildEmbeddableSectionClient(Section embeddableSection) {
		Composite container = this.addSubPane(embeddableSection, 2, 0, 0, 0, 0);

		// Java class widgets
		Hyperlink javaClassHyperlink = this.addHyperlink(container, JptJpaUiDetailsOrmMessages.ORM_JAVA_CLASS_CHOOSER_JAVA_CLASS);
		new OrmJavaClassChooser(this, this.buildPersistentTypeReferenceModel(), container, javaClassHyperlink);

		// Access type widgets
		this.addLabel(container, JptJpaUiMessages.ACCESS_TYPE_COMPOSITE_ACCESS);
		new AccessTypeComboViewer(this, this.buildAccessReferenceModel(), container);

		// Metadata complete widgets
		MetadataCompleteTriStateCheckBox metadataCompleteCheckBox = new MetadataCompleteTriStateCheckBox(this, getSubjectHolder(), container);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		metadataCompleteCheckBox.getControl().setLayoutData(gridData);

		return container;
	}

	protected PropertyValueModel<OrmPersistentType> buildPersistentTypeReferenceModel() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), m -> m.getPersistentType());
	}
}
