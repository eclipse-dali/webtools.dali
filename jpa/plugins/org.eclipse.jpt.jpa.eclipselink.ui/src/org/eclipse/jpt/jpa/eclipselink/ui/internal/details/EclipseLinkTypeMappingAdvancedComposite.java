/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkChangeTracking;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTypeMapping;
import org.eclipse.swt.widgets.Composite;

public abstract class EclipseLinkTypeMappingAdvancedComposite<T extends TypeMapping>
	extends Pane<T>
{
	protected EclipseLinkTypeMappingAdvancedComposite(
			Pane<? extends T> parentPane,
			Composite parent) {

		super(parentPane, parent);
	}

	protected PropertyValueModel<EclipseLinkCustomizer> buildCustomizerHolder() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), m -> ((EclipseLinkTypeMapping) m).getCustomizer());
	}

	protected PropertyValueModel<EclipseLinkChangeTracking> buildChangeTrackingHolder() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), m -> ((EclipseLinkTypeMapping) m).getChangeTracking());
	}
}
