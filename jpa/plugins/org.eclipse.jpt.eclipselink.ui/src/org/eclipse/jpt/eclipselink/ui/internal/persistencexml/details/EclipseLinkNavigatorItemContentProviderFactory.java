/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistencexml.details;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jpt.common.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.common.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.common.ui.jface.TreeItemContentProvider;
import org.eclipse.jpt.common.ui.jface.TreeItemContentProviderFactory;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.core.context.MappingFile;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.ui.internal.platform.generic.GenericNavigatorItemContentProviderFactory;

/**
 * EclipseLinkNavigatorItemContentProviderFactory
 */
public class EclipseLinkNavigatorItemContentProviderFactory
	extends GenericNavigatorItemContentProviderFactory
	implements TreeItemContentProviderFactory
{
	@Override
	public TreeItemContentProvider buildItemContentProvider(
			Object item, DelegatingContentAndLabelProvider contentAndLabelProvider) {
		DelegatingTreeContentAndLabelProvider treeContentAndLabelProvider = 
			(DelegatingTreeContentAndLabelProvider) contentAndLabelProvider;
		
		if (item instanceof EclipseLinkPersistenceUnit) {
			return new EclipseLinkPersistenceUnitItemContentProvider(
				(EclipseLinkPersistenceUnit) item, treeContentAndLabelProvider);	
		}
		
		return super.buildItemContentProvider(item, contentAndLabelProvider);
	}
	
	
	public static class EclipseLinkPersistenceUnitItemContentProvider 
		extends PersistenceUnitItemContentProvider
	{
		public EclipseLinkPersistenceUnitItemContentProvider(
				EclipseLinkPersistenceUnit persistenceUnit, DelegatingTreeContentAndLabelProvider contentProvider) {
			super(persistenceUnit, contentProvider);
		}
		
		@Override
		public EclipseLinkPersistenceUnit getModel() {
			return (EclipseLinkPersistenceUnit) super.getModel();
		}
		
		@Override
		protected CollectionValueModel<JpaContextNode> buildChildrenModel() {
			List<CollectionValueModel<? extends JpaContextNode>> list = new ArrayList<CollectionValueModel<? extends JpaContextNode>>();
			list.add(buildSpecifiedOrmXmlCvm());
			list.add(buildImpliedMappingFileCvm());
			list.add(buildImpliedEclipseLinkMappingFileCvm());
			list.add(buildPersistentTypeCvm());
			list.add(buildJarFileCvm());
			return new CompositeCollectionValueModel<CollectionValueModel<? extends JpaContextNode>, JpaContextNode>(list);
		}
		
		private CollectionValueModel<MappingFile> buildImpliedEclipseLinkMappingFileCvm() {
			return new PropertyCollectionValueModelAdapter<MappingFile>(
				new PropertyAspectAdapter<MappingFileRef, MappingFile>(
						new PropertyAspectAdapter<EclipseLinkPersistenceUnit, MappingFileRef>(
								EclipseLinkPersistenceUnit.IMPLIED_ECLIPSELINK_MAPPING_FILE_REF_PROPERTY,
								getModel()) {
							@Override
							protected MappingFileRef buildValue_() {
								return subject.getImpliedEclipseLinkMappingFileRef();
							}
						},
						MappingFileRef.MAPPING_FILE_PROPERTY) {
					@Override
					protected MappingFile buildValue_() {
						return subject.getMappingFile();
					}
				}
			);
		}
	}
}
