/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details;

import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.OneToOneRelationshipReference;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkPrivateOwned;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.details.AbstractOneToOneMappingComposite;
import org.eclipse.jpt.ui.internal.details.CascadeComposite;
import org.eclipse.jpt.ui.internal.details.FetchTypeComposite;
import org.eclipse.jpt.ui.internal.details.OptionalComposite;
import org.eclipse.jpt.ui.internal.details.TargetEntityComposite;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;


public class EclipseLinkOneToOneMappingComposite<T extends OneToOneMapping>
	extends AbstractOneToOneMappingComposite<T, OneToOneRelationshipReference>
	implements JpaComposite
{
	/**
	 * Creates a new <code>EclipselinkOneToOneMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IOneToOneMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public EclipseLinkOneToOneMappingComposite(PropertyValueModel<? extends T> subjectHolder,
	                                Composite parent,
	                                WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeOneToOneSection(Composite container) {
		new TargetEntityComposite(this, container);
		new FetchTypeComposite(this, container);
		new EclipseLinkJoinFetchComposite(this, buildJoinFetchableHolder(), container);
		new OptionalComposite(this, container);
		new EclipseLinkPrivateOwnedComposite(this, buildPrivateOwnableHolder(), container);
		new CascadeComposite(this, buildCascadeHolder(), addSubPane(container, 5));
	}
	
	protected PropertyValueModel<EclipseLinkJoinFetch> buildJoinFetchableHolder() {
		return new PropertyAspectAdapter<T, EclipseLinkJoinFetch>(getSubjectHolder()) {
			@Override
			protected EclipseLinkJoinFetch buildValue_() {
				return ((EclipseLinkOneToOneMapping)this.subject).getJoinFetch();
			}
		};
	}
	
	protected PropertyValueModel<EclipseLinkPrivateOwned> buildPrivateOwnableHolder() {
		return new PropertyAspectAdapter<T, EclipseLinkPrivateOwned>(getSubjectHolder()) {
			@Override
			protected EclipseLinkPrivateOwned buildValue_() {
				return ((EclipseLinkOneToOneMapping)this.subject).getPrivateOwned();
			}
		};
	}
}