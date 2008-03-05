/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.structure;

import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.JptUiIcons;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.jface.AbstractItemLabelProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.ui.internal.jface.ItemLabelProvider;
import org.eclipse.jpt.ui.internal.jface.ItemLabelProviderFactory;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.StaticPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;

public class PersistenceItemLabelProviderFactory
	implements ItemLabelProviderFactory
{
	public ItemLabelProvider buildItemLabelProvider(
			Object item, DelegatingContentAndLabelProvider contentAndLabelProvider) {
		if (item instanceof Persistence) {
			return new PersistenceItemLabelProvider((Persistence) item, contentAndLabelProvider);
		}
		else if (item instanceof PersistenceUnit) {
			return new PersistenceUnitItemLabelProvider((PersistenceUnit) item, contentAndLabelProvider);	
		}
		else if (item instanceof MappingFileRef) {
			return new MappingFileRefItemLabelProvider((MappingFileRef) item, contentAndLabelProvider);	
		}
		else if (item instanceof ClassRef) {
			return new ClassRefItemLabelProvider((ClassRef) item, contentAndLabelProvider);	
		}
		return null;
	}
	
	
	public static class PersistenceItemLabelProvider extends AbstractItemLabelProvider
	{
		public PersistenceItemLabelProvider(
				Persistence persistence, DelegatingContentAndLabelProvider labelProvider) {
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
				PersistenceUnit persistenceUnit, DelegatingContentAndLabelProvider labelProvider) {
			super(persistenceUnit, labelProvider);
		}
		
		@Override
		protected PropertyValueModel<String> buildTextModel() {
			return new PropertyAspectAdapter<PersistenceUnit, String>(PersistenceUnit.NAME_PROPERTY, (PersistenceUnit) model()) {
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
				MappingFileRef mappingFileRef, DelegatingContentAndLabelProvider labelProvider) {
			super(mappingFileRef, labelProvider);
		}
		
		@Override
		protected PropertyValueModel<String> buildTextModel() {
			return new PropertyAspectAdapter<MappingFileRef, String>(MappingFileRef.FILE_NAME_PROPERTY, (MappingFileRef) model()) {
				 @Override
				protected String buildValue_() {
					return subject.getFileName();
				}
			};
		}
		
		@Override
		protected PropertyValueModel<Image> buildImageModel() {
			Image image = JptUiPlugin.getImage(JptUiIcons.MAPPING_FILE_REF);
			if (((MappingFileRef) model()).isVirtual()) {
				image = JptUiIcons.ghost(image);
			}
			return new StaticPropertyValueModel<Image>(image);
		}
	}
	
	
	public static class ClassRefItemLabelProvider extends AbstractItemLabelProvider
	{
		public ClassRefItemLabelProvider(
				ClassRef classRef, DelegatingContentAndLabelProvider labelProvider) {
			super(classRef, labelProvider);
		}
		
		@Override
		protected PropertyValueModel<String> buildTextModel() {
			return new PropertyAspectAdapter<ClassRef, String>(ClassRef.CLASS_NAME_PROPERTY, (ClassRef) model()) {
				 @Override
				protected String buildValue_() {
					return subject.getClassName();
				}
			};
		}
		
		@Override
		protected PropertyValueModel<Image> buildImageModel() {
			Image image = JptUiPlugin.getImage(JptUiIcons.CLASS_REF);
			if (((ClassRef) model()).isVirtual()) {
				image = JptUiIcons.ghost(image);
			}
			return new StaticPropertyValueModel<Image>(image);
		}
	}
}
