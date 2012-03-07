/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.wizards.conversion.java;

import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCorePlugin;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.ui.internal.wizards.conversion.java.JpaJavaGlobalMetadataConversionWizard;

public abstract class EclipseLinkJpaJavaGlobalMetadataConversionWizard extends
		JpaJavaGlobalMetadataConversionWizard {


	public EclipseLinkJpaJavaGlobalMetadataConversionWizard(
			JpaProject jpaProject) {
		super(jpaProject);
	}

	@Override
	protected String getDefaultMappingFileRuntimPath() {
		return JptJpaEclipseLinkCorePlugin.DEFAULT_ECLIPSELINK_ORM_XML_RUNTIME_PATH.toString();
	}

	@Override
	protected MappingFile getFirstAvaliableMappingFile() {
		for (MappingFileRef ref : getPersistenceUnit().getMappingFileRefs()) {
			MappingFile mappingFile = ref.getMappingFile();
			if (mappingFile != null) {
				IFile file = ref.getMappingFile().getXmlResource().getFile();
				if (jpaProject.getJpaFile(file).getContentType().isKindOf(JptJpaEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE)) 
					return ref.getMappingFile();
			}
		}
		return null;
	}

	@Override
	protected EclipseLinkPersistenceUnit getPersistenceUnit() {
		return (EclipseLinkPersistenceUnit) super.getPersistenceUnit();
	}
}
