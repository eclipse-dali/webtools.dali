/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.xml.structure;

import java.util.Collection;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.jpt.core.internal.content.orm.EntityMappings;
import org.eclipse.jpt.core.internal.content.orm.OrmPackage;

public class XmlRootContentNodeItemProvider extends ItemProviderAdapter
	implements IEditingDomainItemProvider, 
			IStructuredItemContentProvider,
			ITreeItemContentProvider, 
			IItemLabelProvider
{
	public XmlRootContentNodeItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}
	
	@Override
	public Collection getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(OrmPackage.Literals.XML_ROOT_CONTENT_NODE__ENTITY_MAPPINGS);
		}
		return childrenFeatures;
	}
	
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(EntityMappings.class)) {
		case OrmPackage.XML_ROOT_CONTENT_NODE__ENTITY_MAPPINGS:
			fireNotifyChanged(
				new ViewerNotification(
					notification, notification.getNotifier(), true, false));
			return;
		}
		super.notifyChanged(notification);
	}
}
