/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.AssociationOverride;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractEntityOverridesComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.PageBook;

public class EntityOverridesComposite2_0
	extends AbstractEntityOverridesComposite
{
	public EntityOverridesComposite2_0(
			Pane<? extends Entity> parentPane,
			Composite parent) {
		
		super(parentPane, parent);
	}
	
	
	@Override
	protected Pane<AssociationOverride> buildAssociationOverridePane(PageBook pageBook, PropertyValueModel<AssociationOverride> associationOverrideHolder) {
		return new AssociationOverrideComposite2_0(this, associationOverrideHolder, pageBook);
	}
}
