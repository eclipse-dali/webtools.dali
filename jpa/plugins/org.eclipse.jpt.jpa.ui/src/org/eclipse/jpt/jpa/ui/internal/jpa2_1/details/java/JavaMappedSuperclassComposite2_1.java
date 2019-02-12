/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2_1.details.java;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.java.JavaMappedSuperclassComposite2_0;
import org.eclipse.jpt.jpa.ui.internal.jpa2_1.details.QueriesComposite2_1;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class JavaMappedSuperclassComposite2_1
	extends JavaMappedSuperclassComposite2_0
{
	public JavaMappedSuperclassComposite2_1(
			PropertyValueModel<? extends JavaMappedSuperclass> mappedSuperclassModel,
			Composite parentComposite, WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(mappedSuperclassModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected Control initializeQueriesSection(Composite container) {
		return new QueriesComposite2_1(this, this.buildQueryContainerModel(), container).getControl();
	}
}
