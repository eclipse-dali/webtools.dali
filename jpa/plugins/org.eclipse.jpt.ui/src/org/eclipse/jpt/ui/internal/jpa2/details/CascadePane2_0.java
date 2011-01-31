/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.details;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.jpa2.context.Cascade2_0;
import org.eclipse.jpt.ui.internal.details.CascadeComposite;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class CascadePane2_0<T extends Cascade2_0>
	extends CascadeComposite<T>
{
	public CascadePane2_0(
			Pane<? extends RelationshipMapping> parentPane,
	        PropertyValueModel<T> subjectHolder,
	        Composite parent) {
		
		super(parentPane, subjectHolder, parent);
	}
	
	public CascadePane2_0(
			PropertyValueModel<T> subjectHolder,
			Composite parent,
		    WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}
	
	
	@Override
	protected void initializeLayout(Composite container) {
		// Cascade group
		Group cascadeGroup = addCascadeGroup(container);
		
		// Container of the check boxes
		container = addSubPane(cascadeGroup, 6, 8, 0, 0, 0);
		
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
	
	protected WritablePropertyValueModel<Boolean> buildCascadeTypeDetachHolder() {
		return new PropertyAspectAdapter<Cascade2_0, Boolean>(getSubjectHolder(), Cascade2_0.DETACH_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.isDetach();
			}
			
			@Override
			protected void setValue_(Boolean value) {
				subject.setDetach(value);
			}
		};
	}	
}
