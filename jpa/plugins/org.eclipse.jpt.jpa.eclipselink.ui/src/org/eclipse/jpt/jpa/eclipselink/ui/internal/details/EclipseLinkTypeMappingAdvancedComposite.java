/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkChangeTracking;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTypeMapping;
import org.eclipse.swt.widgets.Composite;

public abstract class EclipseLinkTypeMappingAdvancedComposite<T extends TypeMapping> extends Pane<T> {
	
	protected EclipseLinkTypeMappingAdvancedComposite(
			Pane<? extends T> parentPane,
			Composite parent) {

		super(parentPane, parent);
	}

	protected PropertyValueModel<EclipseLinkCustomizer> buildCustomizerHolder() {
		return new PropertyAspectAdapter<TypeMapping, EclipseLinkCustomizer>(getSubjectHolder()) {
			@Override
			protected EclipseLinkCustomizer buildValue_() {
				return ((EclipseLinkTypeMapping) this.subject).getCustomizer();
			}
		};
	}

	protected PropertyValueModel<EclipseLinkChangeTracking> buildChangeTrackingHolder() {
		return new PropertyAspectAdapter<TypeMapping, EclipseLinkChangeTracking>(getSubjectHolder()) {
			@Override
			protected EclipseLinkChangeTracking buildValue_() {
				return ((EclipseLinkTypeMapping) this.subject).getChangeTracking();
			}
		};
	}
}
