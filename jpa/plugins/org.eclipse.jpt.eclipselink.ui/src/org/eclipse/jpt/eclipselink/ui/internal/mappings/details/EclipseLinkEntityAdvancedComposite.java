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

import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.eclipselink.core.context.ChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.Customizer;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkEntity;
import org.eclipse.jpt.eclipselink.core.context.ReadOnly;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkEntityAdvancedComposite extends FormPane<Entity> {
	
	public EclipseLinkEntityAdvancedComposite(
			FormPane<? extends Entity> parentPane,
			Composite parent) {

		super(parentPane, parent, false);
	}
	
	@Override
	protected void initializeLayout(Composite container) {
		container = addCollapsableSection(
			container,
			EclipseLinkUiMappingsMessages.EclipseLinkTypeMappingComposite_advanced
		);
		
		new ReadOnlyComposite(this, buildReadOnlyHolder(), container);
		new CustomizerComposite(this, buildCustomizerHolder(), container);
		new ChangeTrackingComposite(this, buildChangeTrackingHolder(), container);
	}
	
	private PropertyValueModel<ReadOnly> buildReadOnlyHolder() {
		return new PropertyAspectAdapter<Entity, ReadOnly>(getSubjectHolder()) {
			@Override
			protected ReadOnly buildValue_() {
				return ((EclipseLinkEntity) this.subject).getReadOnly();
			}
		};
	}
	
	private PropertyValueModel<Customizer> buildCustomizerHolder() {
		return new PropertyAspectAdapter<Entity, Customizer>(getSubjectHolder()) {
			@Override
			protected Customizer buildValue_() {
				return ((EclipseLinkEntity) this.subject).getCustomizer();
			}
		};
	}
	
	private PropertyValueModel<ChangeTracking> buildChangeTrackingHolder() {
		return new PropertyAspectAdapter<Entity, ChangeTracking>(getSubjectHolder()) {
			@Override
			protected ChangeTracking buildValue_() {
				return ((EclipseLinkEntity) this.subject).getChangeTracking();
			}
		};
	}
}
