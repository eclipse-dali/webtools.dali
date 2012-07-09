/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class IdMappingComposite
	extends AbstractIdMappingComposite<IdMapping>
{
	public IdMappingComposite(
			PropertyValueModel<? extends IdMapping> subjectHolder,
			PropertyValueModel<Boolean> enabledModel,
			Composite parent,
	        WidgetFactory widgetFactory) {
		
		super(subjectHolder, enabledModel, parent, widgetFactory);
	}
	
	
	@Override
	protected Control initializeIdSection(Composite container) {
		container = this.addSubPane(container);

		new ColumnComposite(this, buildColumnHolder(), container);

		return container;
	}
}
