/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.v2_1.details.orm;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.AccessHolder;
import org.eclipse.jpt.jpa.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.jpa.eclipselink.core.v2_0.context.EclipseLinkElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkJoinFetchComposite;
import org.eclipse.jpt.jpa.ui.internal.details.AccessTypeComposite;
import org.eclipse.jpt.jpa.ui.internal.details.FetchTypeComposite;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmMappingNameChooser;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.AbstractElementCollectionMapping2_0Composite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.CollectionTable2_0Composite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.TargetClassComposite;
import org.eclipse.swt.widgets.Composite;

public class OrmEclipseLinkElementCollectionMapping2_1Composite
	extends AbstractElementCollectionMapping2_0Composite<ElementCollectionMapping2_0>
{
	/**
	 * Creates a new <code>EclipseLink1_1OrmBasicMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>BasicMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public OrmEclipseLinkElementCollectionMapping2_1Composite(PropertyValueModel<? extends ElementCollectionMapping2_0> subjectHolder,
	                               Composite parent,
	                               WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeElementCollectionSection(Composite container) {
		new TargetClassComposite(this, container);
		new OrmMappingNameChooser(this, getSubjectHolder(), container);
		new AccessTypeComposite(this, buildAccessHolderHolder(), container);
		new FetchTypeComposite(this, container);
		new EclipseLinkJoinFetchComposite(this, buildJoinFetchHolder(), container);
		new CollectionTable2_0Composite(this, buildCollectionTableHolder(), container);
	}
	
	protected PropertyValueModel<AccessHolder> buildAccessHolderHolder() {
		return new PropertyAspectAdapter<ElementCollectionMapping2_0, AccessHolder>(getSubjectHolder()) {
			@Override
			protected AccessHolder buildValue_() {
				return this.subject.getPersistentAttribute();
			}
		};
	}

	protected PropertyValueModel<EclipseLinkJoinFetch> buildJoinFetchHolder() {
		return new PropertyAspectAdapter<ElementCollectionMapping2_0, EclipseLinkJoinFetch>(getSubjectHolder()) {
			@Override
			protected EclipseLinkJoinFetch buildValue_() {
				return ((EclipseLinkElementCollectionMapping2_0) this.subject).getJoinFetch();
			}
		};
	}
}