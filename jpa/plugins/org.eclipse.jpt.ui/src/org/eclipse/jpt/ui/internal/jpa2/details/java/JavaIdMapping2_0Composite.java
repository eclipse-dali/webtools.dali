/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.details.java;

import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.AbstractIdMappingComposite;
import org.eclipse.jpt.ui.internal.details.ColumnComposite;
import org.eclipse.jpt.ui.internal.jpa2.details.IdMapping2_0MappedByRelationshipPane;
import org.eclipse.jpt.ui.internal.jpa2.details.IdMappingGeneration2_0Composite;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class JavaIdMapping2_0Composite
	extends AbstractIdMappingComposite<IdMapping>
{
	public JavaIdMapping2_0Composite(
			PropertyValueModel<? extends IdMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}
	
	
	@Override
	protected void initializeIdSection(Composite container) {
		new IdMapping2_0MappedByRelationshipPane(this, getSubjectHolder(), container);
		new ColumnComposite(this, buildColumnHolder(), container);
	}
	
	@Override
	protected void initializeGenerationCollapsibleSection(Composite container) {
		new IdMappingGeneration2_0Composite(this, container);
	}
}
