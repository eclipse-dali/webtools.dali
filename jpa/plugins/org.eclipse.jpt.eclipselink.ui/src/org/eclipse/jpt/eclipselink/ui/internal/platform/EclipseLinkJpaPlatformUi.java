/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.platform;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.eclipselink.ui.internal.ddlgen.EclipseLinkDDLGeneratorUi;
import org.eclipse.jpt.eclipselink.ui.internal.java.details.DefaultOneToManyMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.java.details.DefaultOneToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.java.details.DefaultVariableOneToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipseLinkBasicCollectionMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipseLinkBasicMapMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipseLinkTransformationMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipseLinkVariableOneToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmBasicMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmEmbeddableUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmEntityUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmIdMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmManyToManyMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmManyToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmMappedSuperclassUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmOneToManyMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmOneToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmVersionMappingUiProvider;
import org.eclipse.jpt.ui.JpaPlatformUiProvider;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.DefaultAttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaBasicMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaEmbeddedIdMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaEmbeddedMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaIdMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaManyToManyMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaManyToOneMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaOneToManyMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaOneToOneMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaTransientMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaVersionMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.NullAttributeMappingUiProvider;
import org.eclipse.jpt.ui.internal.orm.details.OrmEmbeddedIdMappingUiProvider;
import org.eclipse.jpt.ui.internal.orm.details.OrmEmbeddedMappingUiProvider;
import org.eclipse.jpt.ui.internal.orm.details.OrmTransientMappingUiProvider;
import org.eclipse.jpt.ui.internal.platform.base.BaseJpaPlatformUi;
import org.eclipse.jpt.ui.navigator.JpaNavigatorProvider;
import org.eclipse.jpt.ui.structure.JpaStructureProvider;
import org.eclipse.jpt.utility.internal.iterators.ArrayListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

public class EclipseLinkJpaPlatformUi
	extends BaseJpaPlatformUi
{
	private TypeMappingUiProvider<? extends TypeMapping>[] eclipseLinkOrmTypeMappingUiProviders;
	private AttributeMappingUiProvider<? extends AttributeMapping>[] eclipseLinkOrmAttributeMappingUiProviders;

	public EclipseLinkJpaPlatformUi(
		JpaUiFactory jpaUiFactory,
		JpaNavigatorProvider navigatorProvider,
		JpaStructureProvider persistenceStructureProvider, 
		JpaStructureProvider javaStructureProvider,
		JpaPlatformUiProvider... platformUiProviders) 
	{
		super(jpaUiFactory, navigatorProvider, persistenceStructureProvider, javaStructureProvider, platformUiProviders);
	}


	// ********** Java attribute mapping UI providers **********

	@Override
	protected void addJavaAttributeMappingUiProvidersTo(List<AttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		providers.add(JavaIdMappingUiProvider.instance());
		providers.add(JavaEmbeddedIdMappingUiProvider.instance());
		providers.add(JavaBasicMappingUiProvider.instance());
		providers.add(EclipseLinkBasicCollectionMappingUiProvider.instance());
		providers.add(EclipseLinkBasicMapMappingUiProvider.instance());
		providers.add(JavaVersionMappingUiProvider.instance());
		providers.add(JavaManyToOneMappingUiProvider.instance());
		providers.add(JavaOneToManyMappingUiProvider.instance());
		providers.add(JavaOneToOneMappingUiProvider.instance());
		providers.add(EclipseLinkVariableOneToOneMappingUiProvider.instance());
		providers.add(JavaManyToManyMappingUiProvider.instance());
		providers.add(JavaEmbeddedMappingUiProvider.instance());
		providers.add(EclipseLinkTransformationMappingUiProvider.instance());
		providers.add(JavaTransientMappingUiProvider.instance());
		providers.add(NullAttributeMappingUiProvider.instance());
	}


	// ********** default Java attribute mapping UI providers **********

	@Override
	protected void addDefaultJavaAttributeMappingUiProvidersTo(List<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		super.addDefaultJavaAttributeMappingUiProvidersTo(providers);
		providers.add(DefaultOneToOneMappingUiProvider.instance());
		providers.add(DefaultOneToManyMappingUiProvider.instance());
		providers.add(DefaultVariableOneToOneMappingUiProvider.instance());
	}


	// ********** DDL generation **********

	public void generateDDL(JpaProject project, IStructuredSelection selection) {
		EclipseLinkDDLGeneratorUi.generate(project);
	}


	// ********** ORM attribute mapping UI providers **********

	@Override
	public Iterator<AttributeMappingUiProvider<? extends AttributeMapping>> attributeMappingUiProviders(PersistentAttribute attribute) {
		if (attribute.getContentType().equals(JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE)) {
			return eclipseLinkOrmAttributeMappingUiProviders();
		}
		return super.attributeMappingUiProviders(attribute);
	}
	
	public Iterator<AttributeMappingUiProvider<? extends AttributeMapping>> eclipseLinkOrmAttributeMappingUiProviders() {
		if (this.eclipseLinkOrmAttributeMappingUiProviders == null) {
			this.eclipseLinkOrmAttributeMappingUiProviders = this.buildEclipseLinkOrmAttributeMappingUiProviders();
		}
		return new ArrayListIterator<AttributeMappingUiProvider<? extends AttributeMapping>>(this.eclipseLinkOrmAttributeMappingUiProviders);
	}
	
	protected AttributeMappingUiProvider<? extends AttributeMapping>[] buildEclipseLinkOrmAttributeMappingUiProviders() {
		ArrayList<AttributeMappingUiProvider<? extends AttributeMapping>> providers = new ArrayList<AttributeMappingUiProvider<? extends AttributeMapping>>();
		this.addEclipseLinkOrmAttributeMappingUiProvidersTo(providers);
		@SuppressWarnings("unchecked")
		AttributeMappingUiProvider<? extends AttributeMapping>[] providerArray = providers.toArray(new AttributeMappingUiProvider[providers.size()]);
		return providerArray;
	}
	
	protected void addEclipseLinkOrmAttributeMappingUiProvidersTo(List<AttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		providers.add(EclipseLinkOrmIdMappingUiProvider.instance());
		providers.add(OrmEmbeddedIdMappingUiProvider.instance());
		providers.add(EclipseLinkOrmBasicMappingUiProvider.instance());
		providers.add(EclipseLinkBasicCollectionMappingUiProvider.instance());
		providers.add(EclipseLinkBasicMapMappingUiProvider.instance());
		providers.add(EclipseLinkOrmVersionMappingUiProvider.instance());
		providers.add(EclipseLinkOrmManyToOneMappingUiProvider.instance());
		providers.add(EclipseLinkOrmOneToManyMappingUiProvider.instance());
		providers.add(EclipseLinkOrmOneToOneMappingUiProvider.instance());
		providers.add(EclipseLinkVariableOneToOneMappingUiProvider.instance());
		providers.add(EclipseLinkOrmManyToManyMappingUiProvider.instance());
		providers.add(OrmEmbeddedMappingUiProvider.instance());
		providers.add(EclipseLinkTransformationMappingUiProvider.instance());
		providers.add(OrmTransientMappingUiProvider.instance());
	}
	
	public Iterator<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> defaultEclipseLinkOrmAttributeMappingUiProviders() {
		return EmptyIterator.instance();
	}
	
	public Iterator<TypeMappingUiProvider<? extends TypeMapping>> eclipseLinkOrmTypeMappingUiProviders() {
		if (this.eclipseLinkOrmTypeMappingUiProviders == null) {
			this.eclipseLinkOrmTypeMappingUiProviders = this.buildEclipseLinkOrmTypeMappingUiProviders();
		}
		return new ArrayListIterator<TypeMappingUiProvider<? extends TypeMapping>>(this.eclipseLinkOrmTypeMappingUiProviders);
	}
	
	protected TypeMappingUiProvider<? extends TypeMapping>[] buildEclipseLinkOrmTypeMappingUiProviders() {
		ArrayList<TypeMappingUiProvider<? extends TypeMapping>> providers = new ArrayList<TypeMappingUiProvider<? extends TypeMapping>>();
		this.addEclipseLinkOrmTypeMappingUiProvidersTo(providers);
		@SuppressWarnings("unchecked")
		TypeMappingUiProvider<? extends TypeMapping>[] providerArray = providers.toArray(new TypeMappingUiProvider[providers.size()]);
		return providerArray;
	}
	
	protected void addEclipseLinkOrmTypeMappingUiProvidersTo(List<TypeMappingUiProvider<? extends TypeMapping>> providers) {
		providers.add(EclipseLinkOrmEntityUiProvider.instance());
		providers.add(EclipseLinkOrmMappedSuperclassUiProvider.instance());
		providers.add(EclipseLinkOrmEmbeddableUiProvider.instance());
	}

}
