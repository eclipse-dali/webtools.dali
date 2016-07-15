/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.swt.widgets.ComboTools;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.internal.model.value.StaticListValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.OrderBy;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class OrderByComposite
		extends Pane<OrderBy> {
	
	public OrderByComposite(
			Pane<?> parentPane, 
			PropertyValueModel<OrderBy> subjectHolder,
			PropertyValueModel<Boolean> enabledModel,
	        Composite parentComposite) {
			
		super(parentPane, subjectHolder, enabledModel, parentComposite);
	}
	
	@Override
	protected void initializeLayout(Composite container) {
		// key combo
		Combo keyCombo = addEditableCombo(
			container,
			buildDefaultKeyListHolder(),
			buildKeyHolder(),
			TransformerTools.<String>objectToStringTransformer(),
			JpaHelpContextIds.MAPPING_ORDER_BY_ORDERING);
		
		ComboTools.handleDefaultValue(keyCombo);
	}
	
	protected ModifiablePropertyValueModel<String> buildKeyHolder() {
		return new PropertyAspectAdapterXXXX<OrderBy, String>(getSubjectHolder(), OrderBy.KEY_PROPERTY) {
			@Override
			protected String buildValue_() {
				String key = this.subject.getKey();
				if (StringTools.isBlank(key)) {
					key = defaultKey();
				}
				return key;
			}
			
			@Override
			protected void setValue_(String value) {
				if (defaultKey().equals(value)) {
					value = null;
				}
				this.subject.setKey(value);
			}
		};
	}
	
	private ListValueModel<String> buildDefaultKeyListHolder() {
		return new StaticListValueModel(defaultKey());
	}
	
	private String defaultKey() {
		return JptJpaUiDetailsMessages.ORDERING_COMPOSITE_PRIMARY_KEY;
	}
}
