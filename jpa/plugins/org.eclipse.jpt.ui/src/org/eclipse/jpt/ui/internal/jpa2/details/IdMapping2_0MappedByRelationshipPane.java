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

import org.eclipse.jpt.core.jpa2.context.IdMapping2_0;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;


public class IdMapping2_0MappedByRelationshipPane<T extends IdMapping2_0>
	extends Pane<T>
{
	public IdMapping2_0MappedByRelationshipPane(
			Pane<?> parentPane,
			PropertyValueModel<T> subjectHolder,
			Composite parent) {
		
		super(parentPane, subjectHolder, parent);
	}
	
	
	@Override
	protected void initializeLayout(Composite container) {
		Composite subContainer = addSubPane(container, 2, 0, 0, 0, 0);
		Button checkBox = addCheckBox(
				subContainer,
				null,
				buildIsMappedByRelationshipHolder(),
				null);
		checkBox.setEnabled(false);
		addLabel(subContainer, JptUiDetailsMessages2_0.IdMapping2_0MappedByRelationshipPane_label);
	}
	
	protected WritablePropertyValueModel<Boolean> buildIsMappedByRelationshipHolder() {
		return new PropertyAspectAdapter<T, Boolean>(getSubjectHolder(), IdMapping2_0.MAPPED_BY_RELATIONSHIP_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.isMappedByRelationship();
			}
		};
	}
}
