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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.XmlJpaFile;
import org.eclipse.jpt.eclipselink.core.EclipseLinkJpaFile;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkJpaUiFactory;
import org.eclipse.jpt.eclipselink.ui.internal.ddlgen.EclipseLinkDDLGeneratorUi;
import org.eclipse.jpt.eclipselink.ui.internal.java.details.DefaultOneToManyMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.java.details.DefaultOneToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipseLinkBasicCollectionMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipseLinkBasicMapMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipseLinkTransformationMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmBasicMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmDetailsProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmEmbeddableUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmEntityUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmIdMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmManyToManyMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmManyToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmMappedSuperclassUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmOneToManyMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmOneToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmVersionMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.structure.EclipseLinkPersistenceResourceModelStructureProvider;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.DefaultAttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
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
import org.eclipse.jpt.ui.internal.structure.OrmResourceModelStructureProvider;
import org.eclipse.jpt.ui.navigator.JpaNavigatorProvider;
import org.eclipse.jpt.ui.structure.JpaStructureProvider;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

public class EclipseLinkJpaPlatformUi extends BaseJpaPlatformUi
{
	private List<TypeMappingUiProvider<? extends OrmTypeMapping>> eclipseLinkOrmTypeMappingUiProviders;
	private List<AttributeMappingUiProvider<? extends AttributeMapping>> eclipseLinkOrmAttributeMappingUiProviders;
	
	
	public EclipseLinkJpaPlatformUi() {
		super();
	}
	
	
	@Override
	protected EclipseLinkJpaUiFactory createJpaUiFactory() {
		return new EclipseLinkJpaUiFactory();
	}
	
	public void generateDDL(JpaProject project, IStructuredSelection selection) {
		String projectLocation = project.getProject().getLocation().toString();
		
		EclipseLinkDDLGeneratorUi.generate(project, projectLocation, selection);
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
	
	
	// **************** details view content ***********************************
	
	@Override
	protected void addDetailsProvidersTo(Collection<JpaDetailsProvider> providers) {
		//using a different EclipseLinkOrmDetailsProvider and the one in BaseJpaPlatformUi.
		//This is not the best solution here, just trying to make it work for M3.
		//TODO JpaPlatformUi really needs a complete overhaul
		super.addDetailsProvidersTo(providers);
		providers.add(new EclipseLinkOrmDetailsProvider());
	}
	
	@Override
	public Iterator<AttributeMappingUiProvider<? extends AttributeMapping>> attributeMappingUiProviders(PersistentAttribute attribute) {
		if (attribute instanceof OrmPersistentAttribute
				&& ((OrmPersistentAttribute) attribute).getEResource().getType() == EclipseLinkJpaFile.ECLIPSELINK_ORM_RESOURCE_TYPE) {
			return eclipseLinkOrmAttributeMappingUiProviders();
		}
		return super.attributeMappingUiProviders(attribute);
	}
	
	@Override
	protected void addJavaAttributeMappingUiProvidersTo(
							List<AttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		providers.add(JavaIdMappingUiProvider.instance());
		providers.add(JavaEmbeddedIdMappingUiProvider.instance());
		providers.add(JavaBasicMappingUiProvider.instance());
		providers.add(EclipseLinkBasicCollectionMappingUiProvider.instance());
		providers.add(EclipseLinkBasicMapMappingUiProvider.instance());
		providers.add(JavaVersionMappingUiProvider.instance());
		providers.add(JavaManyToOneMappingUiProvider.instance());
		providers.add(JavaOneToManyMappingUiProvider.instance());
		providers.add(JavaOneToOneMappingUiProvider.instance());
		providers.add(JavaManyToManyMappingUiProvider.instance());
		providers.add(JavaEmbeddedMappingUiProvider.instance());
		providers.add(EclipseLinkTransformationMappingUiProvider.instance());
		providers.add(JavaTransientMappingUiProvider.instance());
		providers.add(NullAttributeMappingUiProvider.instance());
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
	public JpaDetailsProvider getDetailsProvider(JpaStructureNode structureNode) {
		// TODO - overhaul this class hierarchy!
		if (structureNode instanceof XmlContextNode) {
			if (((XmlContextNode) structureNode).getEResource().getType() == EclipseLinkJpaFile.ECLIPSELINK_ORM_RESOURCE_TYPE) {
				return getDetailsProviders().get(2);
			}
		}
		return super.getDetailsProvider(structureNode);
	}
	
	public Iterator<TypeMappingUiProvider<? extends TypeMapping>> eclipseLinkOrmTypeMappingUiProviders() {
		if (this.eclipseLinkOrmTypeMappingUiProviders == null) {
			this.eclipseLinkOrmTypeMappingUiProviders = new ArrayList<TypeMappingUiProvider<? extends OrmTypeMapping>>();
			this.eclipseLinkOrmTypeMappingUiProviders.add(EclipseLinkOrmEntityUiProvider.instance());
			this.eclipseLinkOrmTypeMappingUiProviders.add(EclipseLinkOrmMappedSuperclassUiProvider.instance());
			this.eclipseLinkOrmTypeMappingUiProviders.add(EclipseLinkOrmEmbeddableUiProvider.instance());
		}
		
		return new CloneListIterator<TypeMappingUiProvider<? extends TypeMapping>>(
			this.eclipseLinkOrmTypeMappingUiProviders
		);
	}
	
	public Iterator<AttributeMappingUiProvider<? extends AttributeMapping>> eclipseLinkOrmAttributeMappingUiProviders() {
		if (this.eclipseLinkOrmAttributeMappingUiProviders == null) {
			this.eclipseLinkOrmAttributeMappingUiProviders = new ArrayList<AttributeMappingUiProvider<? extends AttributeMapping>>();
			this.eclipseLinkOrmAttributeMappingUiProviders.add(EclipseLinkOrmIdMappingUiProvider.instance());
			this.eclipseLinkOrmAttributeMappingUiProviders.add(OrmEmbeddedIdMappingUiProvider.instance());
			this.eclipseLinkOrmAttributeMappingUiProviders.add(EclipseLinkOrmBasicMappingUiProvider.instance());
			this.eclipseLinkOrmAttributeMappingUiProviders.add(EclipseLinkBasicCollectionMappingUiProvider.instance());
			this.eclipseLinkOrmAttributeMappingUiProviders.add(EclipseLinkBasicMapMappingUiProvider.instance());
			this.eclipseLinkOrmAttributeMappingUiProviders.add(EclipseLinkOrmVersionMappingUiProvider.instance());
			this.eclipseLinkOrmAttributeMappingUiProviders.add(EclipseLinkOrmManyToOneMappingUiProvider.instance());
			this.eclipseLinkOrmAttributeMappingUiProviders.add(EclipseLinkOrmOneToManyMappingUiProvider.instance());
			this.eclipseLinkOrmAttributeMappingUiProviders.add(EclipseLinkOrmOneToOneMappingUiProvider.instance());
			this.eclipseLinkOrmAttributeMappingUiProviders.add(EclipseLinkOrmManyToManyMappingUiProvider.instance());
			this.eclipseLinkOrmAttributeMappingUiProviders.add(OrmEmbeddedMappingUiProvider.instance());
			this.eclipseLinkOrmAttributeMappingUiProviders.add(EclipseLinkTransformationMappingUiProvider.instance());
			this.eclipseLinkOrmAttributeMappingUiProviders.add(OrmTransientMappingUiProvider.instance());
		}
		
		return new CloneListIterator<AttributeMappingUiProvider<? extends AttributeMapping>>(
			this.eclipseLinkOrmAttributeMappingUiProviders
		);
	}
	
	public Iterator<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> defaultEclipseLinkOrmAttributeMappingUiProviders() {
		return EmptyIterator.instance();
	}
	
	
	// **************** navigator content **************************************
	
	public JpaNavigatorProvider buildNavigatorProvider() {
		return new EclipseLinkNavigatorProvider();
	}
}
