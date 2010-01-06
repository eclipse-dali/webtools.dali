/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details.orm;

import org.eclipse.jpt.core.context.AccessHolder;
import org.eclipse.jpt.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.details.AccessTypeComposite;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class OrmEmbeddableComposite extends Pane<OrmEmbeddable> implements JpaComposite
{
	public OrmEmbeddableComposite(PropertyValueModel<? extends OrmEmbeddable> subjectHolder,
	                          Composite parent,
	                          WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	protected void initializeLayout(Composite container) {
		new OrmJavaClassChooser(this, getSubjectHolder(), container);
		new AccessTypeComposite(this, buildAccessHolder(), container);
		new MetadataCompleteComposite(this, getSubjectHolder(), container);
	}
	
	protected PropertyValueModel<AccessHolder> buildAccessHolder() {
		return new PropertyAspectAdapter<OrmEmbeddable, AccessHolder>(
			getSubjectHolder())
		{
			@Override
			protected AccessHolder buildValue_() {
				return this.subject.getPersistentType();
			}
		};
	}

}