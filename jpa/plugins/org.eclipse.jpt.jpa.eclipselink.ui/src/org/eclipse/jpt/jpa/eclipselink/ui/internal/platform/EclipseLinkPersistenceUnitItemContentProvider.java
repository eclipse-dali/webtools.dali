/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.platform;

import java.util.ArrayList;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.ui.internal.platform.generic.PersistenceUnitItemContentProvider;

/**
 * Item content provider for project explorer.
 */
public class EclipseLinkPersistenceUnitItemContentProvider 
	extends PersistenceUnitItemContentProvider
{
	public EclipseLinkPersistenceUnitItemContentProvider(EclipseLinkPersistenceUnit persistenceUnit, Manager manager) {
		super(persistenceUnit, manager);
	}
	
	@Override
	protected void addChildrenModelsTo(ArrayList<CollectionValueModel<? extends JpaContextModel>> list) {
		super.addChildrenModelsTo(list);
		// add after the implied mapping file
		list.add(3, this.buildImpliedEclipseLinkMappingFilesModel());
	}


	// ********** implied EclipseLink mapping file **********

	/**
	 * No need to filter this list model as it will be empty if the wrapped
	 * property model is <code>null</code>.
	 */
	protected CollectionValueModel<MappingFile> buildImpliedEclipseLinkMappingFilesModel() {
		return new PropertyCollectionValueModelAdapter<MappingFile>(this.buildImpliedEclipseLinkMappingFileModel());
	}

	protected PropertyValueModel<MappingFile> buildImpliedEclipseLinkMappingFileModel() {
		return new ImpliedMappingFileModel(this.buildImpliedEclipseLinkMappingFileRefModel());
	}

	protected PropertyValueModel<MappingFileRef> buildImpliedEclipseLinkMappingFileRefModel() {
		return new ImpliedEclipseLinkMappingFileRefModel((EclipseLinkPersistenceUnit) this.item);
	}

	public static class ImpliedEclipseLinkMappingFileRefModel
		extends PropertyAspectAdapter<EclipseLinkPersistenceUnit, MappingFileRef>
	{
		public ImpliedEclipseLinkMappingFileRefModel(EclipseLinkPersistenceUnit persistenceUnit) {
			super(EclipseLinkPersistenceUnit.IMPLIED_ECLIPSELINK_MAPPING_FILE_REF_PROPERTY, persistenceUnit);
		}
		@Override
		protected MappingFileRef buildValue_() {
			return this.subject.getImpliedEclipseLinkMappingFileRef();
		}
	}
}
