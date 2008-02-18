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
package org.eclipse.jpt.ui.internal.structure;

import org.eclipse.jpt.core.internal.context.persistence.IClassRef;
import org.eclipse.jpt.core.internal.context.persistence.IMappingFileRef;
import org.eclipse.jpt.core.internal.context.persistence.IPersistence;
import org.eclipse.jpt.core.internal.context.persistence.IPersistenceUnit;
import org.eclipse.jpt.ui.internal.JptUiIcons;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.JptUiPlugin;
import org.eclipse.jpt.ui.internal.jface.AbstractItemLabelProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.ui.internal.jface.IItemLabelProvider;
import org.eclipse.jpt.ui.internal.jface.IItemLabelProviderFactory;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.StaticPropertyValueModel;
import org.eclipse.swt.graphics.Image;

public class PersistenceItemLabelProviderFactory
	implements IItemLabelProviderFactory
{
	public IItemLabelProvider buildItemLabelProvider(
			Object item, DelegatingContentAndLabelProvider contentAndLabelProvider) {
		if (item instanceof IPersistence) {
			return new PersistenceItemLabelProvider((IPersistence) item, contentAndLabelProvider);
		}
		else if (item instanceof IPersistenceUnit) {
			return new PersistenceUnitItemLabelProvider((IPersistenceUnit) item, contentAndLabelProvider);	
		}
		else if (item instanceof IMappingFileRef) {
			return new MappingFileRefItemLabelProvider((IMappingFileRef) item, contentAndLabelProvider);	
		}
		else if (item instanceof IClassRef) {
			return new ClassRefItemLabelProvider((IClassRef) item, contentAndLabelProvider);	
		}
		return null;
	}
	
	
	public static class PersistenceItemLabelProvider extends AbstractItemLabelProvider
	{
		public PersistenceItemLabelProvider(
				IPersistence persistence, DelegatingContentAndLabelProvider labelProvider) {
			super(persistence, labelProvider);
		}
		
		@Override
		protected PropertyValueModel<String> buildTextModel() {
			return new StaticPropertyValueModel<String>(JptUiMessages.PersistenceItemLabelProviderFactory_persistenceLabel);
		}
		
		@Override
		protected PropertyValueModel<Image> buildImageModel() {
			return new StaticPropertyValueModel<Image>(JptUiPlugin.getImage(JptUiIcons.PERSISTENCE));
		}	
	}
	
	
	public static class PersistenceUnitItemLabelProvider extends AbstractItemLabelProvider
	{
		public PersistenceUnitItemLabelProvider(
				IPersistenceUnit persistenceUnit, DelegatingContentAndLabelProvider labelProvider) {
			super(persistenceUnit, labelProvider);
		}
		
		@Override
		protected PropertyValueModel<String> buildTextModel() {
			return new PropertyAspectAdapter<IPersistenceUnit, String>(IPersistenceUnit.NAME_PROPERTY, (IPersistenceUnit) model()) {
				 @Override
				protected String buildValue_() {
					return subject.getName();
				}
			};
		}
		
		@Override
		protected PropertyValueModel<Image> buildImageModel() {
			return new StaticPropertyValueModel<Image>(JptUiPlugin.getImage(JptUiIcons.PERSISTENCE_UNIT));
		}
	}
	
	
	public static class MappingFileRefItemLabelProvider extends AbstractItemLabelProvider
	{
		public MappingFileRefItemLabelProvider(
				IMappingFileRef mappingFileRef, DelegatingContentAndLabelProvider labelProvider) {
			super(mappingFileRef, labelProvider);
		}
		
		@Override
		protected PropertyValueModel<String> buildTextModel() {
			return new PropertyAspectAdapter<IMappingFileRef, String>(IMappingFileRef.FILE_NAME_PROPERTY, (IMappingFileRef) model()) {
				 @Override
				protected String buildValue_() {
					return subject.getFileName();
				}
			};
		}
		
		@Override
		protected PropertyValueModel<Image> buildImageModel() {
			Image image = JptUiPlugin.getImage(JptUiIcons.MAPPING_FILE_REF);
			if (((IMappingFileRef) model()).isVirtual()) {
				image = JptUiIcons.ghost(image);
			}
			return new StaticPropertyValueModel<Image>(image);
		}
	}
	
	
	public static class ClassRefItemLabelProvider extends AbstractItemLabelProvider
	{
		public ClassRefItemLabelProvider(
				IClassRef classRef, DelegatingContentAndLabelProvider labelProvider) {
			super(classRef, labelProvider);
		}
		
		@Override
		protected PropertyValueModel<String> buildTextModel() {
			return new PropertyAspectAdapter<IClassRef, String>(IClassRef.CLASS_NAME_PROPERTY, (IClassRef) model()) {
				 @Override
				protected String buildValue_() {
					return subject.getClassName();
				}
			};
		}
		
		@Override
		protected PropertyValueModel<Image> buildImageModel() {
			Image image = JptUiPlugin.getImage(JptUiIcons.CLASS_REF);
			if (((IClassRef) model()).isVirtual()) {
				image = JptUiIcons.ghost(image);
			}
			return new StaticPropertyValueModel<Image>(image);
		}
	}
}
