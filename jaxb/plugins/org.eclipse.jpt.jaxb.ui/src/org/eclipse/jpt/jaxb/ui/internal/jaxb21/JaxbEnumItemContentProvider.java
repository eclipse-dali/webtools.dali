/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.jaxb21;

import org.eclipse.jpt.common.ui.internal.jface.AbstractTreeItemContentProvider;
import org.eclipse.jpt.common.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbEnum;
import org.eclipse.jpt.jaxb.core.context.JaxbEnumConstant;
import org.eclipse.jpt.jaxb.core.context.JaxbEnumMapping;


public class JaxbEnumItemContentProvider
		extends AbstractTreeItemContentProvider<JaxbEnumConstant> {
	
	public JaxbEnumItemContentProvider(
			JaxbEnum jaxbEnum, DelegatingTreeContentAndLabelProvider contentProvider) {
		
		super(jaxbEnum, contentProvider);
	}
	
	
	@Override
	public JaxbEnum getModel() {
		return (JaxbEnum) super.getModel();
	}
	
	@Override
	public JaxbContextRoot getParent() {
		return (JaxbContextRoot) getModel().getParent();
	}
	
	protected PropertyValueModel<JaxbEnumMapping> buildMappingModel() {
		return new PropertyAspectAdapter<JaxbEnum, JaxbEnumMapping>(JaxbEnum.MAPPING_PROPERTY, getModel()) {
			@Override
			protected JaxbEnumMapping buildValue_() {
				return this.subject.getMapping();
			}
		};
	}
	
	@Override
	protected CollectionValueModel<JaxbEnumConstant> buildChildrenModel() {
		return new CollectionAspectAdapter<JaxbEnumMapping, JaxbEnumConstant>(
				buildMappingModel(), JaxbEnumMapping.ENUM_CONSTANTS_COLLECTION) {
			@Override
			protected Iterable<JaxbEnumConstant> getIterable() {
				return this.subject.getEnumConstants();
			}
		};
	}
}
