/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.jpa2.context.Cascade2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.RelationshipMapping2_0;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractCascadePane;
import org.eclipse.jpt.jpa.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.swt.widgets.Composite;


public class CascadePane2_0
	extends AbstractCascadePane<Cascade2_0>
{
	public CascadePane2_0(
		Pane<? extends RelationshipMapping2_0> parentPane,
		PropertyValueModel<? extends Cascade2_0> cascadeModel,
		Composite parent
	) {
		super(parentPane, cascadeModel, parent);
	}
	
	@Override
	protected Composite addComposite(Composite container) {
		return addTitledGroup(
			container,
			JptUiDetailsMessages.CascadeComposite_cascadeTitle,
			6,
			null);
	}

	@Override
	protected void initializeLayout(Composite container) {		
		addAllCheckBox(container);
		addPersistCheckBox(container);
		addMergeCheckBox(container);
		addRemoveCheckBox(container);
		addRefreshCheckBox(container);
		addDetachCheckBox(container);
	}
	
	protected void addDetachCheckBox(Composite container) {
		addCheckBox(
				container,
				JptUiDetailsMessages2_0.CascadePane2_0_detach,
				buildCascadeTypeDetachModel(),
				null);
	}
	
	protected ModifiablePropertyValueModel<Boolean> buildCascadeTypeDetachModel() {
		return new PropertyAspectAdapter<Cascade2_0, Boolean>(getSubjectHolder(), Cascade2_0.DETACH_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isDetach());
			}
			
			@Override
			protected void setValue_(Boolean value) {
				this.subject.setDetach(value.booleanValue());
			}
		};
	}	
}
