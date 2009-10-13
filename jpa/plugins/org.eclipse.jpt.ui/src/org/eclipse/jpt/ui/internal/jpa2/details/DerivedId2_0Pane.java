/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.details;

import org.eclipse.jpt.core.jpa2.context.DerivedId2_0;
import org.eclipse.jpt.ui.internal.jpa2.details.orm.OrmManyToOneMapping2_0Composite;
import org.eclipse.jpt.ui.internal.jpa2.details.orm.OrmOneToOneMapping2_0Composite;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | [ ]  Derived id
 * -----------------------------------------------------------------------------</pre>
 *
 * @see DerivedId2_0
 * @see JavaOneToOneMapping2_0Composite - A container of this widget
 * @see JavaManyToOneMapping2_0Composite - A container of this widget
 * @see OrmOneToOneMapping2_0Composite - A container of this widget
 * @see OrmManyToOneMapping2_0Composite - A container of this widget
 */
public class DerivedId2_0Pane 
	extends FormPane<DerivedId2_0>
{
	public DerivedId2_0Pane(
			FormPane<?> parentPane,
			PropertyValueModel<? extends DerivedId2_0> subjectHolder,
	        Composite parent) {
		
		super(parentPane, subjectHolder, parent);
	}
	
	
	@Override
	protected void initializeLayout(Composite container) {
		addCheckBox(
			addSubPane(container, 4, 0, 4, 0), 
			JptUiDetailsMessages2_0.DerivedIdPane_derivedIdCheckboxLabel,
			buildDerivedIdValueHolder(),
			null);
	}
	
	protected WritablePropertyValueModel<Boolean> buildDerivedIdValueHolder() {
		return new PropertyAspectAdapter<DerivedId2_0, Boolean>(
				getSubjectHolder(), DerivedId2_0.VALUE_PROPERTY) {
			
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.getValue());
			}
			
			@Override
			protected void setValue_(Boolean value) {
				this.subject.setValue(value.booleanValue());
			}
		};
	}
}
