/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details.orm;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.ManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.ManyToManyRelationship;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractManyToManyMappingComposite;
import org.eclipse.jpt.jpa.ui.internal.details.AccessTypeComposite;
import org.eclipse.jpt.jpa.ui.internal.details.FetchTypeComposite;
import org.eclipse.jpt.jpa.ui.internal.details.TargetEntityComposite;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmMappingNameChooser;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.CascadePane2_0;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.Ordering2_0Composite;
import org.eclipse.swt.widgets.Composite;

public class OrmManyToManyMapping2_0Composite
	extends AbstractManyToManyMappingComposite<ManyToManyMapping, ManyToManyRelationship>
{
	/**
	 * Creates a new <code>ManyToManyMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IManyToManyMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public OrmManyToManyMapping2_0Composite(
			PropertyValueModel<? extends ManyToManyMapping> subjectHolder,
	        Composite parent,
	        WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}
	
	
	@Override
	protected void initializeManyToManySection(Composite container) {
		new TargetEntityComposite(this, container);
		new OrmMappingNameChooser(this, getSubjectHolder(), container);
		new AccessTypeComposite(this, buildAccessHolderHolder(), container);
		new FetchTypeComposite(this, container);
		new CascadePane2_0(this, buildCascadeHolder(), addSubPane(container, 5));
	}
	
	@Override
	protected void initializeOrderingCollapsibleSection(Composite container) {
		new Ordering2_0Composite(this, container);
	}
}