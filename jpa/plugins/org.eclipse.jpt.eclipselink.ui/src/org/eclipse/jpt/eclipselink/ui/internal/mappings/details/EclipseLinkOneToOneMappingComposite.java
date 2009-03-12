/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.mappings.details;

import org.eclipse.jpt.core.context.Cascade;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.OneToOneRelationshipReference;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.context.JoinFetch;
import org.eclipse.jpt.eclipselink.core.context.PrivateOwned;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.mappings.details.CascadeComposite;
import org.eclipse.jpt.ui.internal.mappings.details.FetchTypeComposite;
import org.eclipse.jpt.ui.internal.mappings.details.OneToOneJoiningStrategyPane;
import org.eclipse.jpt.ui.internal.mappings.details.OptionalComposite;
import org.eclipse.jpt.ui.internal.mappings.details.TargetEntityComposite;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;


public class EclipseLinkOneToOneMappingComposite extends FormPane<OneToOneMapping>
                                      implements JpaComposite
{
	/**
	 * Creates a new <code>EclipselinkOneToOneMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IOneToOneMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public EclipseLinkOneToOneMappingComposite(PropertyValueModel<? extends OneToOneMapping> subjectHolder,
	                                Composite parent,
	                                WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		int groupBoxMargin = getGroupBoxMargin();
		Composite subPane = addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin);

		new TargetEntityComposite(this, subPane);
		new OneToOneJoiningStrategyPane(this, buildJoiningHolder(), container);
		new FetchTypeComposite(this, subPane);
		new JoinFetchComposite(this, buildJoinFetchableHolder(), subPane);
		new OptionalComposite(this, addSubPane(subPane, 4));
		new PrivateOwnedComposite(this, buildPrivateOwnableHolder(), subPane);
		new CascadeComposite(this, buildCascadeHolder(), container);
	}
	
	protected PropertyValueModel<OneToOneRelationshipReference> buildJoiningHolder() {
		return new TransformationPropertyValueModel<OneToOneMapping, OneToOneRelationshipReference>(
				getSubjectHolder()) {
			@Override
			protected OneToOneRelationshipReference transform_(OneToOneMapping value) {
				return value.getRelationshipReference();
			}
		};
	}
	
	protected PropertyValueModel<JoinFetch> buildJoinFetchableHolder() {
		return new PropertyAspectAdapter<OneToOneMapping, JoinFetch>(getSubjectHolder()) {
			@Override
			protected JoinFetch buildValue_() {
				return ((EclipseLinkOneToOneMapping) this.subject).getJoinFetch();
			}
		};
	}
	
	protected PropertyValueModel<PrivateOwned> buildPrivateOwnableHolder() {
		return new PropertyAspectAdapter<OneToOneMapping, PrivateOwned>(getSubjectHolder()) {
			@Override
			protected PrivateOwned buildValue_() {
				return ((EclipseLinkOneToOneMapping) this.subject).getPrivateOwned();
			}
		};
	}

	protected PropertyValueModel<Cascade> buildCascadeHolder() {
		return new TransformationPropertyValueModel<OneToOneMapping, Cascade>(getSubjectHolder()) {
		
			@Override
			protected Cascade transform_(OneToOneMapping value) {
				return value.getCascade();
			}
		};
	}
}