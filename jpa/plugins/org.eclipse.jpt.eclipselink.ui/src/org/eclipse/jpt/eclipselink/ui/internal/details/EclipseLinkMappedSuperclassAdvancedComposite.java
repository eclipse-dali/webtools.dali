/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details;

import org.eclipse.jpt.core.context.MappedSuperclass;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkReadOnly;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkMappedSuperclassAdvancedComposite extends FormPane<MappedSuperclass> {
	
	public EclipseLinkMappedSuperclassAdvancedComposite(
			FormPane<? extends MappedSuperclass> parentPane,
			Composite parent) {

		super(parentPane, parent, false);
	}
	
	@Override
	protected void initializeLayout(Composite container) {
		container = addCollapsibleSection(
			container,
			EclipseLinkUiDetailsMessages.EclipseLinkTypeMappingComposite_advanced
		);
		
		new EclipseLinkReadOnlyComposite(this, buildReadOnlyHolder(), container);
		new EclipseLinkCustomizerComposite(this, buildCustomizerHolder(), container);
		new EclipseLinkChangeTrackingComposite(this, buildChangeTrackingHolder(), container);
	}
	
	private PropertyValueModel<EclipseLinkReadOnly> buildReadOnlyHolder() {
		return new PropertyAspectAdapter<MappedSuperclass, EclipseLinkReadOnly>(getSubjectHolder()) {
			@Override
			protected EclipseLinkReadOnly buildValue_() {
				return ((EclipseLinkMappedSuperclass) this.subject).getReadOnly();
			}
		};
	}
	
	private PropertyValueModel<EclipseLinkCustomizer> buildCustomizerHolder() {
		return new PropertyAspectAdapter<MappedSuperclass, EclipseLinkCustomizer>(getSubjectHolder()) {
			@Override
			protected EclipseLinkCustomizer buildValue_() {
				return ((EclipseLinkMappedSuperclass) this.subject).getCustomizer();
			}
		};
	}
	
	private PropertyValueModel<EclipseLinkChangeTracking> buildChangeTrackingHolder() {
		return new PropertyAspectAdapter<MappedSuperclass, EclipseLinkChangeTracking>(getSubjectHolder()) {
			@Override
			protected EclipseLinkChangeTracking buildValue_() {
				return ((EclipseLinkMappedSuperclass) this.subject).getChangeTracking();
			}
		};
	}
}
