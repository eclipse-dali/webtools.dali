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

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.Cascade;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.Cascade2_0;
import org.eclipse.jpt.jpa.ui.internal.details.CascadeComposite;
import org.eclipse.jpt.jpa.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.swt.widgets.Composite;

public class CascadePane2_0
	extends CascadeComposite
{
	public CascadePane2_0(
			Pane<? extends RelationshipMapping> parentPane,
	        PropertyValueModel<? extends Cascade> subjectHolder,
	        Composite parent) {
		
		super(parentPane, subjectHolder, parent);
	}
	
	public CascadePane2_0(
			PropertyValueModel<? extends Cascade> subjectHolder,
			Composite parent,
		    WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
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
				buildCascadeTypeDetachHolder(),
				null);
	}
	
	protected ModifiablePropertyValueModel<Boolean> buildCascadeTypeDetachHolder() {
		return new PropertyAspectAdapter<Cascade, Boolean>(getSubjectHolder(), Cascade2_0.DETACH_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(((Cascade2_0) this.subject).isDetach());
			}
			
			@Override
			protected void setValue_(Boolean value) {
				((Cascade2_0) this.subject).setDetach(value.booleanValue());
			}
		};
	}	
}
