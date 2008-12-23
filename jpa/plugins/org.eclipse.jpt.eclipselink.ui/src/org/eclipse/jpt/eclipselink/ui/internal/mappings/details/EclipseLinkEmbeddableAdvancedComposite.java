/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.mappings.details;

import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.eclipselink.core.context.ChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.Customizer;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkEmbeddable;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkEmbeddableAdvancedComposite extends FormPane<Embeddable> {
	
	public EclipseLinkEmbeddableAdvancedComposite(
			FormPane<? extends Embeddable> parentPane,
			Composite parent) {

		super(parentPane, parent, false);
	}
	
	@Override
	protected void initializeLayout(Composite container) {
		container = addCollapsableSection(
			container,
			EclipseLinkUiMappingsMessages.EclipseLinkTypeMappingComposite_advanced
		);
		
		new CustomizerComposite(this, buildCustomizerHolder(), container);
		new ChangeTrackingComposite(this, buildChangeTrackingHolder(), container);
	}
	
	private PropertyValueModel<Customizer> buildCustomizerHolder() {
		return new PropertyAspectAdapter<Embeddable, Customizer>(getSubjectHolder()) {
			@Override
			protected Customizer buildValue_() {
				return ((EclipseLinkEmbeddable) this.subject).getCustomizer();
			}
		};
	}
	
	private PropertyValueModel<ChangeTracking> buildChangeTrackingHolder() {
		return new PropertyAspectAdapter<Embeddable, ChangeTracking>(getSubjectHolder()) {
			@Override
			protected ChangeTracking buildValue_() {
				return ((EclipseLinkEmbeddable) this.subject).getChangeTracking();
			}
		};
	}
}
