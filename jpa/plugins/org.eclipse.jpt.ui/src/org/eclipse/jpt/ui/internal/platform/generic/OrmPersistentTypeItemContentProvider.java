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
import java.util.ListIterator;
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
		public Object getParent() {
			return ((OrmPersistentType) model()).parent();
		}
		
		@Override
		protected ListValueModel<OrmPersistentAttribute> buildChildrenModel() {
			java.util.List<ListValueModel<OrmPersistentAttribute>> list = new ArrayList<ListValueModel<OrmPersistentAttribute>>();
			list.add(buildSpecifiedPersistentAttributesListHolder());
			list.add(buildVirtualPersistentAttributesListHolder());
			return new CompositeListValueModel<ListValueModel<OrmPersistentAttribute>, OrmPersistentAttribute>(list);
		}
		

		protected ListValueModel<OrmPersistentAttribute> buildSpecifiedPersistentAttributesListHolder() {
			return new ListAspectAdapter<OrmPersistentType, OrmPersistentAttribute>(new String[]{OrmPersistentType.SPECIFIED_ATTRIBUTES_LIST}, (OrmPersistentType) model()) {
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
			return new ListAspectAdapter<OrmPersistentType, OrmPersistentAttribute>(new String[]{OrmPersistentType.VIRTUAL_ATTRIBUTES_LIST}, (OrmPersistentType) model()) {
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