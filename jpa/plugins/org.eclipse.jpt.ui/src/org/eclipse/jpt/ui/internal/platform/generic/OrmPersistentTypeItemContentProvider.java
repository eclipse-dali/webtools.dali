/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.platform.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.ui.internal.jface.AbstractTreeItemContentProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.model.value.ListValueModel;

public class OrmPersistentTypeItemContentProvider extends AbstractTreeItemContentProvider<OrmPersistentAttribute>
	{
		public OrmPersistentTypeItemContentProvider(
				OrmPersistentType persistentType, DelegatingTreeContentAndLabelProvider contentProvider) {
			super(persistentType, contentProvider);
		}
		
		@Override
		public OrmPersistentType getModel() {
			return (OrmPersistentType) super.getModel();
		}
		
		@Override
		public Object getParent() {
			return getModel().getParent();
		}
		
		@Override
		protected ListValueModel<OrmPersistentAttribute> buildChildrenModel() {
			List<ListValueModel<OrmPersistentAttribute>> list = new ArrayList<ListValueModel<OrmPersistentAttribute>>(2);
			list.add(buildSpecifiedPersistentAttributesListHolder());
			list.add(buildVirtualPersistentAttributesListHolder());
			return new CompositeListValueModel<ListValueModel<OrmPersistentAttribute>, OrmPersistentAttribute>(list);
		}
		

		protected ListValueModel<OrmPersistentAttribute> buildSpecifiedPersistentAttributesListHolder() {
			return new ListAspectAdapter<OrmPersistentType, OrmPersistentAttribute>(PersistentType.SPECIFIED_ATTRIBUTES_LIST, getModel()) {
				@Override
				protected ListIterator<OrmPersistentAttribute> listIterator_() {
					return subject.specifiedAttributes();
				}
				@Override
				protected int size_() {
					return subject.specifiedAttributesSize();
				}
			};
		}
		
		protected ListValueModel<OrmPersistentAttribute> buildVirtualPersistentAttributesListHolder() {
			return new ListAspectAdapter<OrmPersistentType, OrmPersistentAttribute>(OrmPersistentType.VIRTUAL_ATTRIBUTES_LIST, getModel()) {
				@Override
				protected ListIterator<OrmPersistentAttribute> listIterator_() {
					return subject.virtualAttributes();
				}
				@Override
				protected int size_() {
					return subject.virtualAttributesSize();
				}
			};
		}
}