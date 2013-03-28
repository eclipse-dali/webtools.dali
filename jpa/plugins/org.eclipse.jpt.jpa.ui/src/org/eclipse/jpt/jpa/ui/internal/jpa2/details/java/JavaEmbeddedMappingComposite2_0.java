/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details.java;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.jpa2.context.EmbeddedMapping2_0;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractEmbeddedMappingComposite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.EmbeddedMappingOverridesComposite2_0;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class JavaEmbeddedMappingComposite2_0
	extends AbstractEmbeddedMappingComposite<EmbeddedMapping2_0>
{
	public JavaEmbeddedMappingComposite2_0(
			PropertyValueModel<? extends EmbeddedMapping2_0> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@Override
	protected Control initializeEmbeddedSection(Composite container) {
		//a Section appears to not like having a Group as its client. EmbeddedMappingOverridesComposite
		//uses a Group as its 'control' so I am adding an extra composite here.
		container = this.addSubPane(container);
		new EmbeddedMappingOverridesComposite2_0(this, container);
		return container;
	}
}
