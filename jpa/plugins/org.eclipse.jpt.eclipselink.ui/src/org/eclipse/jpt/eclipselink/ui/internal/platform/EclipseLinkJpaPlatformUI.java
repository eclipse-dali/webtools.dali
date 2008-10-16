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
import org.eclipse.jpt.core.ResourceModel;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.resource.orm.OrmResourceModel;
import org.eclipse.jpt.eclipselink.core.EclipseLinkResourceModel;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkJpaUiFactory;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkJpaUiFactory;
import org.eclipse.jpt.eclipselink.ui.internal.ddlgen.EclipseLinkDDLGeneratorUi;
import org.eclipse.jpt.eclipselink.ui.internal.java.details.DefaultOneToManyMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.java.details.DefaultOneToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.persistencexml.details.PersistenceDetailsProvider;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
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
		ResourceModel resourceModel = jpaFile.getResourceModel();
		String resourceType = resourceModel.getResourceType();
		
		if (resourceType == EclipseLinkResourceModel.ECLIPSELINK_ORM_RESOURCE_TYPE) {
			return new OrmResourceModelStructureProvider((OrmResourceModel) resourceModel);
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
		super.addDetailsProvidersTo(providers);
		providers.add(new PersistenceDetailsProvider());
	}

	@Override
	protected void addJavaAttributeMappingUiProvidersTo(
							List<AttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		super.addJavaAttributeMappingUiProvidersTo(providers);
	}

	@Override
	protected void addDefaultJavaAttributeMappingUiProvidersTo(
							List<AttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		super.addDefaultJavaAttributeMappingUiProvidersTo(providers);
		providers.add(DefaultOneToOneMappingUiProvider.instance());
		providers.add(DefaultOneToManyMappingUiProvider.instance());		
	}
	
	@Override
	protected void addJavaTypeMappingUiProvidersTo(
							List<TypeMappingUiProvider<? extends TypeMapping>> providers) {
		super.addJavaTypeMappingUiProvidersTo(providers);
	}
}
