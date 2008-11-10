/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.core.context.MappingFile;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.ui.internal.platform.generic.GenericNavigatorItemContentProviderFactory;
import org.eclipse.jpt.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.ui.jface.TreeItemContentProvider;
import org.eclipse.jpt.ui.jface.TreeItemContentProviderFactory;
import org.eclipse.jpt.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.utility.model.value.ListValueModel;

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
		public EclipseLinkPersistenceUnit model() {
			return (EclipseLinkPersistenceUnit) super.model();
		}
		
		@Override
		protected ListValueModel<JpaContextNode> buildChildrenModel() {
			List<ListValueModel<? extends JpaContextNode>> list = new ArrayList<ListValueModel<? extends JpaContextNode>>();
			list.add(buildSpecifiedOrmXmlLvm());
			list.add(buildImpliedMappingFileLvm());
			list.add(buildImpliedEclipseLinkMappingFileLvm());
			list.add(buildPersistentTypeLvm());
			return new CompositeListValueModel<ListValueModel<? extends JpaContextNode>, JpaContextNode>(list);
		}
		
		private ListValueModel<MappingFile> buildImpliedEclipseLinkMappingFileLvm() {
			return new PropertyListValueModelAdapter<MappingFile>(
				new PropertyAspectAdapter<MappingFileRef, MappingFile>(
						new PropertyAspectAdapter<EclipseLinkPersistenceUnit, MappingFileRef>(
								EclipseLinkPersistenceUnit.IMPLIED_ECLIPSELINK_MAPPING_FILE_REF_PROPERTY,
								model()) {
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
