/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_0.details.java;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.eclipselink.core.v2_0.context.EclipseLinkElementCollectionMapping2_0;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkJoinFetchComposite;
import org.eclipse.jpt.ui.internal.details.FetchTypeComposite;
import org.eclipse.jpt.ui.internal.jpa2.details.AbstractElementCollectionMapping2_0Composite;
import org.eclipse.jpt.ui.internal.jpa2.details.CollectionTable2_0Composite;
import org.eclipse.jpt.ui.internal.jpa2.details.TargetClassComposite;
import org.eclipse.swt.widgets.Composite;

public class JavaEclipseLinkElementCollectionMapping2_0Composite extends AbstractElementCollectionMapping2_0Composite<JavaElementCollectionMapping2_0>
{
	/**
	 * Creates a new <code>EclipseLink1_1OrmBasicMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>BasicMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public JavaEclipseLinkElementCollectionMapping2_0Composite(PropertyValueModel<? extends JavaElementCollectionMapping2_0> subjectHolder,
	                               Composite parent,
	                               WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeElementCollectionSection(Composite container) {
		new TargetClassComposite(this, container);
		new FetchTypeComposite(this, container);
		new EclipseLinkJoinFetchComposite(this, buildJoinFetchHolder(), container);
		new CollectionTable2_0Composite(this, buildCollectionTableHolder(), container);
	}

	protected PropertyValueModel<EclipseLinkJoinFetch> buildJoinFetchHolder() {
		return new PropertyAspectAdapter<JavaElementCollectionMapping2_0, EclipseLinkJoinFetch>(getSubjectHolder()) {
			@Override
			protected EclipseLinkJoinFetch buildValue_() {
				return ((EclipseLinkElementCollectionMapping2_0) this.subject).getJoinFetch();
			}
		};
	}
}