/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.persistence.general;

import java.util.ListIterator;

import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.PersistenceUnitProperties;

/**
 *  GeneralProperties
 */
public interface GeneralProperties extends PersistenceUnitProperties
{
	String getDefaultName();
	String getName();
	void setName(String newName);
		// PersistenceUnit property
		static final String NAME_PROPERTY = PersistenceUnit.NAME_PROPERTY;
		static final String DEFAULT_NAME = "";

	String getDefaultProvider();
	String getProvider();
	void setProvider(String newProvider);
		// PersistenceUnit property
		static final String PROVIDER_PROPERTY = PersistenceUnit.PROVIDER_PROPERTY;
		static final String DEFAULT_PROVIDER = "";

	String getDefaultDescription();
	String getDescription();
	void setDescription(String newDescription);
		// PersistenceUnit property
		static final String DESCRIPTION_PROPERTY = PersistenceUnit.DESCRIPTION_PROPERTY;
		static final String DEFAULT_DESCRIPTION = "";
		
	Boolean getDefaultExcludeUnlistedClasses();
	Boolean getSpecifiedExcludeUnlistedClasses();
	void setSpecifiedExcludeUnlistedClasses(Boolean newSpecifiedExcludeUnlistedClasses);
		// PersistenceUnit property
		static final String SPECIFIED_EXCLUDE_UNLISTED_CLASSES_PROPERTY = PersistenceUnit.SPECIFIED_EXCLUDE_UNLISTED_CLASSES_PROPERTY;

	ListIterator<ClassRef> specifiedClassRefs();
	int specifiedClassRefsSize();
	ClassRef addSpecifiedClassRef();
	void removeSpecifiedClassRef(ClassRef classRef);
		// PersistenceUnit property
		static final String SPECIFIED_CLASS_REFS_LIST = PersistenceUnit.SPECIFIED_CLASS_REFS_LIST;

	ListIterator<MappingFileRef> specifiedMappingFileRefs();
	int specifiedMappingFileRefsSize();
	MappingFileRef addSpecifiedMappingFileRef();
	MappingFileRef addSpecifiedMappingFileRef(int index);
	void removeSpecifiedMappingFileRef(MappingFileRef mappingFileRef);
		// PersistenceUnit property
		static final String SPECIFIED_MAPPING_FILE_REFS_LIST = PersistenceUnit.SPECIFIED_MAPPING_FILE_REFS_LIST;

	Boolean getDefaultExcludeEclipselinkOrm();
	Boolean getExcludeEclipselinkOrm();
	void setExcludeEclipselinkOrm(Boolean newExcludeEclipselinkOrm);
		static final String EXCLUDE_ECLIPSELINK_ORM_PROPERTY = "excludeEclipselinkOrmProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_EXCLUDE_ECLIPSELINK_ORM = "eclipselink.exclude-eclipselink-orm";
		static final Boolean DEFAULT_EXCLUDE_ECLIPSELINK_ORM = Boolean.FALSE;
		
}
