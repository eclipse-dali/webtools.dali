/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.platform;

import java.util.Collection;
import java.util.List;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.internal.XmlJpaFile;
import org.eclipse.jpt.eclipselink.core.EclipseLinkJpaFile;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkJpaUiFactory;
import org.eclipse.jpt.eclipselink.ui.internal.ddlgen.EclipseLinkDDLGeneratorUi;
import org.eclipse.jpt.eclipselink.ui.internal.java.details.DefaultOneToManyMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.java.details.DefaultOneToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.BasicCollectionMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.BasicMapMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.TransformationMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmBasicMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmIdMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmVersionMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.OrmDetailsProvider;
import org.eclipse.jpt.eclipselink.ui.internal.persistencexml.details.PersistenceDetailsProvider;
import org.eclipse.jpt.eclipselink.ui.internal.structure.EclipseLinkPersistenceResourceModelStructureProvider;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.DefaultAttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.EmbeddedIdMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.EmbeddedMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.ManyToManyMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.ManyToOneMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.OneToManyMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.OneToOneMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.TransientMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaDetailsProvider;
import org.eclipse.jpt.ui.internal.platform.base.BaseJpaPlatformUi;
import org.eclipse.jpt.ui.internal.structure.OrmResourceModelStructureProvider;
import org.eclipse.jpt.ui.navigator.JpaNavigatorProvider;
import org.eclipse.jpt.ui.structure.JpaStructureProvider;

public class EclipseLinkJpaPlatformUI extends BaseJpaPlatformUi
{
	public EclipseLinkJpaPlatformUI() {
		super();
	}
	
	
	// **************** structure view content *********************************
	
	@Override
	public JpaStructureProvider buildStructureProvider(JpaFile jpaFile) {
		String resourceType = jpaFile.getResourceType();

		if (resourceType == EclipseLinkJpaFile.ECLIPSELINK_ORM_RESOURCE_TYPE) {
			return new OrmResourceModelStructureProvider((XmlJpaFile) jpaFile);
		}
		if (resourceType == JpaFile.PERSISTENCE_RESOURCE_TYPE) {
			return new EclipseLinkPersistenceResourceModelStructureProvider((XmlJpaFile) jpaFile);
		}

		return super.buildStructureProvider(jpaFile);
	}
	
	
	// **************** navigator content **************************************
	
	public JpaNavigatorProvider buildNavigatorProvider() {
		return new EclipseLinkNavigatorProvider();
	}
	
	@Override
	protected EclipseLinkJpaUiFactory createJpaUiFactory() {
		return new EclipseLinkJpaUiFactory();
	}
	
	public void generateDDL(JpaProject project, IStructuredSelection selection) {
		String projectLocation = project.getProject().getLocation().toString();
		
		EclipseLinkDDLGeneratorUi.generate(project, projectLocation, selection);
	}

	@Override
	protected void addDetailsProvidersTo(Collection<JpaDetailsProvider> providers) {
		//using a different OrmDetailsProvider and the one in BaseJpaPlatformUi.
		//This is not the best solution here, just trying to make it work for M3.
		//TODO JpaPlatformUi really needs a complete overhaul
		providers.add(new JavaDetailsProvider());
		providers.add(new OrmDetailsProvider());
		providers.add(new PersistenceDetailsProvider());
	}

	@Override
	protected void addJavaAttributeMappingUiProvidersTo(
							List<AttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		super.addJavaAttributeMappingUiProvidersTo(providers);
		providers.add(BasicCollectionMappingUiProvider.instance());
		providers.add(BasicMapMappingUiProvider.instance());
		providers.add(TransformationMappingUiProvider.instance());
	}

	@Override
	protected void addDefaultJavaAttributeMappingUiProvidersTo(
							List<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		super.addDefaultJavaAttributeMappingUiProvidersTo(providers);
		providers.add(DefaultOneToOneMappingUiProvider.instance());
		providers.add(DefaultOneToManyMappingUiProvider.instance());		
	}
	
	@Override
	protected void addJavaTypeMappingUiProvidersTo(
							List<TypeMappingUiProvider<? extends TypeMapping>> providers) {
		super.addJavaTypeMappingUiProvidersTo(providers);
	}
	
	@Override
	protected void addOrmAttributeMappingUiProvidersTo(List<AttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		providers.add(EclipseLinkOrmBasicMappingUiProvider.instance());
		providers.add(BasicCollectionMappingUiProvider.instance());
		providers.add(BasicMapMappingUiProvider.instance());
		providers.add(EmbeddedMappingUiProvider.instance());
		providers.add(EmbeddedIdMappingUiProvider.instance());
		providers.add(EclipseLinkOrmIdMappingUiProvider.instance());
		providers.add(ManyToManyMappingUiProvider.instance());
		providers.add(ManyToOneMappingUiProvider.instance());
		providers.add(OneToManyMappingUiProvider.instance());
		providers.add(OneToOneMappingUiProvider.instance());
		providers.add(TransformationMappingUiProvider.instance());
		providers.add(TransientMappingUiProvider.instance());
		providers.add(EclipseLinkOrmVersionMappingUiProvider.instance());
	}
	
}
