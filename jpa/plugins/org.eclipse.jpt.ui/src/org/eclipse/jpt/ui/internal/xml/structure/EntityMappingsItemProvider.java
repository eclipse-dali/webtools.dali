/*******************************************************************************
 *  Copyright (c) 2006 Oracle. All rights reserved. This
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
import org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal;
import org.eclipse.jpt.core.internal.content.orm.OrmPackage;
import org.eclipse.jpt.ui.internal.mappings.JpaUiMappingsImages;

public class EntityMappingsItemProvider extends ItemProviderAdapter
	implements IEditingDomainItemProvider, 
			IStructuredItemContentProvider,
			ITreeItemContentProvider, 
			IItemLabelProvider
{
	public EntityMappingsItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	public Collection getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(OrmPackage.Literals.ENTITY_MAPPINGS_INTERNAL__PERSISTENT_TYPES);
		}
		return childrenFeatures;
	}
	
	@Override
	public Object getParent(Object object) {
		return null;
	}
	
	@Override
	public Object getImage(Object object) {
		return JpaUiMappingsImages.getImage(JpaUiMappingsImages.ENTITY_MAPPINGS);
	}
	
	@Override
	public String getText(Object object) {
		// TODO
		return "EntityMappings";
	}
	
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(EntityMappingsInternal.class)) {
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__PERSISTENT_TYPES:
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__TYPE_MAPPINGS:
			fireNotifyChanged(
				new ViewerNotification(
					notification, notification.getNotifier(), true, false));
			return;
		}
		super.notifyChanged(notification);
	}
}
