/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.details.orm;

import org.eclipse.jpt.core.context.AccessHolder;
import org.eclipse.jpt.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.AbstractBasicMappingComposite;
import org.eclipse.jpt.ui.internal.details.AccessTypeComposite;
import org.eclipse.jpt.ui.internal.details.ColumnComposite;
import org.eclipse.jpt.ui.internal.details.FetchTypeComposite;
import org.eclipse.jpt.ui.internal.details.OptionalComposite;
import org.eclipse.jpt.ui.internal.details.orm.OrmMappingNameChooser;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class OrmBasicMapping2_0Composite extends AbstractBasicMappingComposite<OrmBasicMapping>
{
	/**
	 * Creates a new <code>EclipseLink1_1OrmBasicMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>BasicMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public OrmBasicMapping2_0Composite(PropertyValueModel<? extends OrmBasicMapping> subjectHolder,
	                               Composite parent,
	                               WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeBasicSection(Composite container) {
		new ColumnComposite(this, buildColumnHolder(), container);
		new OrmMappingNameChooser(this, getSubjectHolder(), container);
		new AccessTypeComposite(this, buildAccessHolderHolder(), container);
		new FetchTypeComposite(this, container);
		new OptionalComposite(this, addSubPane(container, 4));
	}
	
	protected PropertyValueModel<AccessHolder> buildAccessHolderHolder() {
		return new PropertyAspectAdapter<OrmBasicMapping, AccessHolder>(getSubjectHolder()) {
			@Override
			protected AccessHolder buildValue_() {
				return this.subject.getPersistentAttribute();
			}
		};
	}
}