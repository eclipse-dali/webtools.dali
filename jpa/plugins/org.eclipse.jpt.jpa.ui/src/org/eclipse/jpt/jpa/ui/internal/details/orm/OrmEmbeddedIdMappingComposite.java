/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.orm;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractEmbeddedIdMappingComposite;
import org.eclipse.jpt.jpa.ui.internal.details.EmbeddedMappingOverridesComposite;
import org.eclipse.swt.widgets.Composite;

public class OrmEmbeddedIdMappingComposite
	extends AbstractEmbeddedIdMappingComposite<EmbeddedIdMapping>
{
	public OrmEmbeddedIdMappingComposite(
			PropertyValueModel<? extends EmbeddedIdMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}
	
	
	@Override
	protected void initializeEmbeddedIdSection(Composite container) {
		new OrmMappingNameChooser(this, getSubjectHolder(), container);

		new EmbeddedMappingOverridesComposite(
				this,
				container);
	}
}
