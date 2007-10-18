/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.platform.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.ui.internal.IJpaPlatformUi;
import org.eclipse.jpt.ui.internal.IJpaUiFactory;
import org.eclipse.jpt.ui.internal.details.IJpaDetailsProvider;
import org.eclipse.jpt.ui.internal.java.details.IAttributeMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.ITypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaDetailsProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.BasicMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.DefaultBasicMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.DefaultEmbeddedMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.EmbeddableUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.EmbeddedIdMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.EmbeddedMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.EntityUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.IdMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.ManyToManyMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.ManyToOneMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.MappedSuperclassUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.NullTypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.OneToManyMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.OneToOneMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.TransientMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.VersionMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.structure.JavaStructureProvider;
import org.eclipse.jpt.ui.internal.structure.IJpaStructureProvider;
import org.eclipse.jpt.ui.internal.xml.details.XmlDetailsProvider;
import org.eclipse.jpt.ui.internal.xml.structure.XmlStructureProvider;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public abstract class BaseJpaPlatformUi implements IJpaPlatformUi
{
	private Collection<IJpaDetailsProvider> detailsProviders;
	private Collection<IJpaStructureProvider> structureProviders;
	
	private List<ITypeMappingUiProvider> javaTypeMappingUiProviders;
	private List<IAttributeMappingUiProvider> javaAttributeMappingUiProviders;
	private List<IAttributeMappingUiProvider> defaultJavaAttributeMappingUiProviders;
	
	private IJpaUiFactory jpaUiFactory;
	
	protected BaseJpaPlatformUi() {
		super();
		this.jpaUiFactory = createJpaUiFactory();
	}

	// ********** behavior **********
	
	protected abstract IJpaUiFactory createJpaUiFactory();

	public IJpaUiFactory getJpaUiFactory() {
		return this.jpaUiFactory;
	}

	public Iterator<IJpaDetailsProvider> detailsProviders() {
		if (this.detailsProviders == null) {
			this.detailsProviders = new ArrayList<IJpaDetailsProvider>();
			this.addDetailsProvidersTo(this.detailsProviders);
		}
		return new CloneIterator<IJpaDetailsProvider>(this.detailsProviders);
	}
	
	/**
	 * Override this to specify more or different details providers.
	 * The default includes the JPA spec-defined java and orm.xml
	 */
	protected void addDetailsProvidersTo(Collection<IJpaDetailsProvider> providers) {
		providers.add(new JavaDetailsProvider());
		providers.add(new XmlDetailsProvider());
	}
	
	public IJpaDetailsProvider detailsProvider(String fileContentType) {
		for (Iterator<IJpaDetailsProvider> i = this.detailsProviders(); i.hasNext(); ) {
			IJpaDetailsProvider provider = i.next();
			if (provider.fileContentType().equals(fileContentType)) {
				return provider;
			}
		}
		return null;
	}
	
	public Iterator<IJpaStructureProvider> structureProviders() {
		if (this.structureProviders == null) {
			this.structureProviders = new ArrayList<IJpaStructureProvider>();
			this.addStructureProvidersTo(this.structureProviders);
		}
		return new CloneIterator<IJpaStructureProvider>(this.structureProviders);
	}
	
	/**
	 * Override this to specify more or different structure providers.
	 * The default includes the JPA spec-defined java and orm.xml
	 */
	protected void addStructureProvidersTo(Collection<IJpaStructureProvider> providers) {
		providers.add(new JavaStructureProvider());
		providers.add(new XmlStructureProvider());
	}

	public IJpaStructureProvider structureProvider(String fileContentType) {
		for (Iterator<IJpaStructureProvider> i = this.structureProviders(); i.hasNext(); ) {
			IJpaStructureProvider provider = i.next();
			if (provider.fileContentType().equals(fileContentType)) {
				return provider;
			}
		}
		return null;
	}
	
	public ListIterator<ITypeMappingUiProvider> javaTypeMappingUiProviders() {
		if (this.javaTypeMappingUiProviders == null) {
			this.javaTypeMappingUiProviders = new ArrayList<ITypeMappingUiProvider>();
			this.addJavaTypeMappingUiProvidersTo(this.javaTypeMappingUiProviders);
		}
		return new CloneListIterator<ITypeMappingUiProvider>(this.javaTypeMappingUiProviders);
	}
	
	/**
	 * Override this to specify more or different type mapping ui providers
	 * The default includes the JPA spec-defined entity, mapped superclass, embeddable,
	 * and null (when the others don't apply)
	 */
	protected void addJavaTypeMappingUiProvidersTo(List<ITypeMappingUiProvider> providers) {
		providers.add(NullTypeMappingUiProvider.instance());
		providers.add(EntityUiProvider.instance());
		providers.add(MappedSuperclassUiProvider.instance());			
		providers.add(EmbeddableUiProvider.instance());			
	}
	
	public ListIterator<IAttributeMappingUiProvider> javaAttributeMappingUiProviders() {
		if (this.javaAttributeMappingUiProviders == null) {
			this.javaAttributeMappingUiProviders = new ArrayList<IAttributeMappingUiProvider>();
			this.addJavaAttributeMappingUiProvidersTo(this.javaAttributeMappingUiProviders);
		}
		return new CloneListIterator<IAttributeMappingUiProvider>(this.javaAttributeMappingUiProviders);

	}
	
	/**
	 * Override this to specify more or different java attribute mapping ui providers.
	 * The default includes the JPA spec-defined basic, embedded, embeddedId, id, 
	 * manyToMany, manyToOne, oneToMany, oneToOne, transient, and version
	 */
	protected void addJavaAttributeMappingUiProvidersTo(List<IAttributeMappingUiProvider> providers) {
		providers.add(BasicMappingUiProvider.instance());
		providers.add(EmbeddedMappingUiProvider.instance());
		providers.add(EmbeddedIdMappingUiProvider.instance());
		providers.add(IdMappingUiProvider.instance());			
		providers.add(ManyToManyMappingUiProvider.instance());			
		providers.add(ManyToOneMappingUiProvider.instance());			
		providers.add(OneToManyMappingUiProvider.instance());			
		providers.add(OneToOneMappingUiProvider.instance());
		providers.add(TransientMappingUiProvider.instance());
		providers.add(VersionMappingUiProvider.instance());
	}
	
	public ListIterator<IAttributeMappingUiProvider> defaultJavaAttributeMappingUiProviders() {
		if (this.defaultJavaAttributeMappingUiProviders == null) {
			this.defaultJavaAttributeMappingUiProviders = new ArrayList<IAttributeMappingUiProvider>();
			this.addDefaultJavaAttributeMappingUiProvidersTo(this.defaultJavaAttributeMappingUiProviders);
		}
		return new CloneListIterator<IAttributeMappingUiProvider>(this.defaultJavaAttributeMappingUiProviders);

	}
	
	/**
	 * Override this to specify more or different default java attribute mapping ui providers.
	 * The default includes the JPA spec-defined basic, embedded
	 */
	protected void addDefaultJavaAttributeMappingUiProvidersTo(List<IAttributeMappingUiProvider> providers) {
		providers.add(DefaultBasicMappingUiProvider.instance());
		providers.add(DefaultEmbeddedMappingUiProvider.instance());
	}


	public void generateEntities(IJpaProject project, IStructuredSelection selection) {
		EntitiesGenerator.generate(project, selection);
	}
}
