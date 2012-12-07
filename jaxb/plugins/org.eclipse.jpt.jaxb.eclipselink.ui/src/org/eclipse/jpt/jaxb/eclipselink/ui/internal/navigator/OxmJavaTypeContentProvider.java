/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.ui.internal.navigator;

import org.eclipse.jpt.common.ui.internal.jface.AbstractItemTreeContentProvider;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaAttribute;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaType;

public class OxmJavaTypeContentProvider
		extends AbstractItemTreeContentProvider<OxmJavaType, OxmJavaAttribute> {
	
	public OxmJavaTypeContentProvider(OxmJavaType item, Manager manager) {
		super(item, manager);
	}
	
	
	public Object getParent() {
		return this.item.getParent();
	}
	
	@Override
	protected CollectionValueModel<OxmJavaAttribute> buildChildrenModel() {
		return new ListCollectionValueModelAdapter<OxmJavaAttribute>(
				new ItemPropertyListValueModelAdapter<OxmJavaAttribute>(
						new ListAspectAdapter<OxmJavaType, OxmJavaAttribute>(OxmJavaType.SPECIFIED_ATTRIBUTES_LIST, this.item) {
							@Override
							protected ListIterable<OxmJavaAttribute> getListIterable() {
								return this.subject.getSpecifiedAttributes();
							}
							@Override
							protected int size_() {
								return this.subject.getSpecifiedAttributesSize();
							}
						}));
	}
}
