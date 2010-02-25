/*******************************************************************************
 *  Copyright (c) 2009, 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.details;

import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.jpa2.context.DerivedIdentity2_0;
import org.eclipse.jpt.core.jpa2.context.OneToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.context.OneToOneRelationshipReference2_0;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.AbstractOneToOneMappingComposite;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractOneToOneMapping2_0Composite<T extends OneToOneMapping, R extends OneToOneRelationshipReference2_0>
	extends AbstractOneToOneMappingComposite<T, R>
{
	protected AbstractOneToOneMapping2_0Composite(
			PropertyValueModel<? extends T> subjectHolder,
	        Composite parent,
	        WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}
	
	
	protected PropertyValueModel<DerivedIdentity2_0> buildDerivedIdentityHolder() {
		return new PropertyAspectAdapter<T, DerivedIdentity2_0>(getSubjectHolder()) {
			@Override
			protected DerivedIdentity2_0 buildValue_() {
				return ((OneToOneMapping2_0) this.subject).getDerivedIdentity();
			}
		};
	}
}
