/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractEmbeddedIdMappingComposite<T extends EmbeddedIdMapping> 
	extends Pane<T>
	implements JpaComposite
{
	protected AbstractEmbeddedIdMappingComposite(
			PropertyValueModel<? extends T> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}
	
	
	@Override
	protected void initializeLayout(Composite container) {
		initializeEmbeddedIdCollapsibleSection(container);
	}
	
	protected void initializeEmbeddedIdCollapsibleSection(Composite container) {
		container = addCollapsibleSection(
				container,
				JptUiDetailsMessages.EmbeddedIdSection_title,
				new SimplePropertyValueModel<Boolean>(Boolean.TRUE));
		
		this.initializeEmbeddedIdSection(container);
	}
	
	protected abstract void initializeEmbeddedIdSection(Composite container);
}
