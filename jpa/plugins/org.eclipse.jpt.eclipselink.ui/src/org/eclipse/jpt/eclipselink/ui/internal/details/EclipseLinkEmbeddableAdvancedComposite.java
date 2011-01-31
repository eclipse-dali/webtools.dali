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

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkEmbeddable;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkEmbeddableAdvancedComposite extends Pane<Embeddable> {
	
	public EclipseLinkEmbeddableAdvancedComposite(
			Pane<? extends Embeddable> parentPane,
			Composite parent) {

		super(parentPane, parent, false);
	}
	
	@Override
	protected void initializeLayout(Composite container) {
		container = addCollapsibleSection(
			container,
			EclipseLinkUiDetailsMessages.EclipseLinkTypeMappingComposite_advanced
		);
		
		new EclipseLinkCustomizerComposite(this, buildCustomizerHolder(), container);
		new EclipseLinkChangeTrackingComposite(this, buildChangeTrackingHolder(), container);
	}
	
	private PropertyValueModel<EclipseLinkCustomizer> buildCustomizerHolder() {
		return new PropertyAspectAdapter<Embeddable, EclipseLinkCustomizer>(getSubjectHolder()) {
			@Override
			protected EclipseLinkCustomizer buildValue_() {
				return ((EclipseLinkEmbeddable) this.subject).getCustomizer();
			}
		};
	}
	
	private PropertyValueModel<EclipseLinkChangeTracking> buildChangeTrackingHolder() {
		return new PropertyAspectAdapter<Embeddable, EclipseLinkChangeTracking>(getSubjectHolder()) {
			@Override
			protected EclipseLinkChangeTracking buildValue_() {
				return ((EclipseLinkEmbeddable) this.subject).getChangeTracking();
			}
		};
	}
}
