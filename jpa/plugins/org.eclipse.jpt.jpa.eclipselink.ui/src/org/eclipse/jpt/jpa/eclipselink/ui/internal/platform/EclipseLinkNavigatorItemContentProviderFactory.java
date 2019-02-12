/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.platform;

import java.util.ArrayList;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.ui.internal.platform.base.AbstractNavigatorItemContentProviderFactory;

/**
 * This factory builds item content providers for the JPA content in the
 * Project Explorer. It is to be used by
 * {@link org.eclipse.jpt.jpa.ui.internal.navigator.JpaNavigatorItemContentProviderFactory}
 * as a delegate for <em>EclipseLink</em> JPA projects.
 */
public class EclipseLinkNavigatorItemContentProviderFactory
	extends AbstractNavigatorItemContentProviderFactory
{
	// singleton
	private static final ItemTreeContentProvider.Factory INSTANCE = new EclipseLinkNavigatorItemContentProviderFactory();

	/**
	 * Return the singleton
	 */
	public static ItemTreeContentProvider.Factory instance() {
		return INSTANCE;
	}


	private EclipseLinkNavigatorItemContentProviderFactory() {
		super();
	}


	// ********** persistence unit **********

	@Override
	protected void addPersistenceUnitChildrenModelsTo(PersistenceUnit persistenceUnit, ArrayList<CollectionValueModel<? extends JpaContextModel>> list) {
		super.addPersistenceUnitChildrenModelsTo(persistenceUnit, list);
		// add after the implied mapping file
		list.add(3, this.buildPersistenceUnitImpliedEclipseLinkMappingFilesModel((EclipseLinkPersistenceUnit) persistenceUnit));
	}


	// ********** persistence unit - implied EclipseLink mapping file **********

	/**
	 * No need to filter this list model as it will be empty if the wrapped
	 * property model is <code>null</code>.
	 */
	protected CollectionValueModel<MappingFile> buildPersistenceUnitImpliedEclipseLinkMappingFilesModel(EclipseLinkPersistenceUnit item) {
		return new PropertyCollectionValueModelAdapter<MappingFile>(this.buildPersistenceUnitImpliedEclipseLinkMappingFileModel(item));
	}

	protected PropertyValueModel<MappingFile> buildPersistenceUnitImpliedEclipseLinkMappingFileModel(EclipseLinkPersistenceUnit item) {
		return new PersistenceUnitImpliedMappingFileModel(this.buildPersistenceUnitImpliedEclipseLinkMappingFileRefModel(item));
	}

	protected PropertyValueModel<MappingFileRef> buildPersistenceUnitImpliedEclipseLinkMappingFileRefModel(EclipseLinkPersistenceUnit item) {
		return new PersistenceUnitImpliedEclipseLinkMappingFileRefModel(item);
	}

	public static class PersistenceUnitImpliedEclipseLinkMappingFileRefModel
		extends PropertyAspectAdapter<EclipseLinkPersistenceUnit, MappingFileRef>
	{
		public PersistenceUnitImpliedEclipseLinkMappingFileRefModel(EclipseLinkPersistenceUnit persistenceUnit) {
			super(EclipseLinkPersistenceUnit.IMPLIED_ECLIPSELINK_MAPPING_FILE_REF_PROPERTY, persistenceUnit);
		}
		@Override
		protected MappingFileRef buildValue_() {
			return this.subject.getImpliedEclipseLinkMappingFileRef();
		}
	}
}
