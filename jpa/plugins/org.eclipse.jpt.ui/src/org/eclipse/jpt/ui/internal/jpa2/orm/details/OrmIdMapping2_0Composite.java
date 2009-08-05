/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.orm.details;

import org.eclipse.jpt.core.context.AccessHolder;
import org.eclipse.jpt.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.AccessTypeComposite;
import org.eclipse.jpt.ui.internal.jpa2.java.details.Generation2_0Composite;
import org.eclipse.jpt.ui.internal.mappings.details.AbstractIdMappingComposite;
import org.eclipse.jpt.ui.internal.mappings.details.ColumnComposite;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class OrmIdMapping2_0Composite extends AbstractIdMappingComposite<OrmIdMapping>
{
	public OrmIdMapping2_0Composite(PropertyValueModel<? extends OrmIdMapping> subjectHolder,
	                               Composite parent,
	                               WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		
		// Column widgets
		new ColumnComposite(this, buildColumnHolder(), container);
		new AccessTypeComposite(this, buildAccessHolderHolder(), container);

		initializeTypePane(container);

		// Generation pane
		new Generation2_0Composite(this, addSubPane(container, 10));
	}
	
	protected PropertyValueModel<AccessHolder> buildAccessHolderHolder() {
		return new PropertyAspectAdapter<OrmIdMapping, AccessHolder>(getSubjectHolder()) {
			@Override
			protected AccessHolder buildValue_() {
				return this.subject.getPersistentAttribute();
			}
		};
	}
}