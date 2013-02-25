/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.OneToManyMapping;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.JpaUiFactory;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractOneToManyMappingUiDefinition
	extends AbstractMappingUiDefinition
{
	protected AbstractOneToManyMappingUiDefinition() {
		super();
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return JptJpaUiImages.ONE_TO_MANY;
	}

	@SuppressWarnings("unchecked")
	public JpaComposite buildMappingComposite(JpaUiFactory factory, PropertyValueModel<? extends JpaContextModel> mappingModel, PropertyValueModel<Boolean> enabledModel, Composite parentComposite, WidgetFactory widgetFactory, ResourceManager resourceManager) {
		return factory.createOneToManyMappingComposite((PropertyValueModel<OneToManyMapping>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
}
