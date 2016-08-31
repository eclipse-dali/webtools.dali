/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.AssociationOverride;
import org.eclipse.jpt.jpa.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.jpa2.context.EmbeddedMapping2_0;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractEmbeddedMappingOverridesComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.PageBook;

public class EmbeddedMappingOverridesComposite2_0
	extends AbstractEmbeddedMappingOverridesComposite<EmbeddedMapping2_0>
{
	public EmbeddedMappingOverridesComposite2_0(
			Pane<? extends EmbeddedMapping2_0> parentPane,
			Composite parent) {
		
		super(parentPane, parent);
	}
	
	
	@Override
	protected boolean supportsAssociationOverrides() {
		return true;
	}	
	
	@Override
	protected PropertyValueModel<AttributeOverrideContainer> buildAttributeOverrideContainerModel() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), m -> m.getAttributeOverrideContainer());
	}
	
	@Override
	protected Pane<AssociationOverride> buildAssociationOverridePane(PageBook pageBook, PropertyValueModel<AssociationOverride> associationOverrideHolder) {
		return new AssociationOverrideComposite2_0(this, associationOverrideHolder, pageBook);
	}
	
	@Override
	protected PropertyValueModel<AssociationOverrideContainer> buildAssociationOverrideContainerModel() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), m -> m.getAssociationOverrideContainer());
	}
}
