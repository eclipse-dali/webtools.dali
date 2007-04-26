/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.java.structure;

import java.util.Collection;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.JpaCorePackage;
import org.eclipse.jpt.core.internal.content.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.content.java.JpaJavaPackage;
import org.eclipse.jpt.core.internal.mappings.IEmbeddable;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.IMappedSuperclass;
import org.eclipse.jpt.ui.internal.mappings.JpaUiMappingsImages;

public class JavaPersistentTypeItemProvider extends ItemProviderAdapter
	implements IEditingDomainItemProvider, 
		IStructuredItemContentProvider,
		ITreeItemContentProvider, 
		IItemLabelProvider
{
	public JavaPersistentTypeItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}
	
	
	@Override
	protected Collection getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(JpaJavaPackage.Literals.JAVA_PERSISTENT_TYPE__ATTRIBUTES);
		}
		return childrenFeatures;
	}
	
	@Override
	public Object getImage(Object object) {
		ITypeMapping mapping = ((IPersistentType) object).getMapping();
		
		
		if (mapping instanceof IEntity) {
			return JpaUiMappingsImages.getImage(JpaUiMappingsImages.ENTITY);
		}
		else if (mapping instanceof IEmbeddable) {
			return JpaUiMappingsImages.getImage(JpaUiMappingsImages.EMBEDDABLE);
		}
		else if (mapping instanceof IMappedSuperclass) {
			return JpaUiMappingsImages.getImage(JpaUiMappingsImages.MAPPED_SUPERCLASS);
		}
		else {
			return null;
		}
	}
	
	@Override
	public String getText(Object object) {
		IType type = ((IPersistentType) object).findJdtType();
		return (type == null) ? "" : type.getElementName();
	}
	
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(JavaPersistentType.class)) {
			case JpaCorePackage.IPERSISTENT_TYPE__MAPPING_KEY:
				fireNotifyChanged(
					new ViewerNotification(
						notification, notification.getNotifier(), false, true));
				return;
			
			case JpaJavaPackage.JAVA_PERSISTENT_TYPE__ATTRIBUTES:
				fireNotifyChanged(
					new ViewerNotification(
						notification, notification.getNotifier(), true, false));
			return;
		}
		
		super.notifyChanged(notification);
	}
}