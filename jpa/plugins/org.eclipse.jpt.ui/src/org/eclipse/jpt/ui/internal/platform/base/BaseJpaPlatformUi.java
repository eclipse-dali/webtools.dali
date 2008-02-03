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
import org.eclipse.jpt.core.internal.context.base.IAttributeMapping;
import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaJpaContextNode;
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
import org.eclipse.jpt.ui.internal.structure.IJpaStructureProvider;
import org.eclipse.jpt.ui.internal.xml.details.XmlDetailsProvider;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.ui.navigator.ICommonContentProvider;
import org.eclipse.ui.navigator.ICommonLabelProvider;

public abstract class BaseJpaPlatformUi implements IJpaPlatformUi
{
	// TODO: Transformed into a List for testing
	private List<IJpaDetailsProvider> detailsProviders;
	private Collection<IJpaStructureProvider> structureProviders;

	private List<ITypeMappingUiProvider<? extends ITypeMapping>> javaTypeMappingUiProviders;
	private List<IAttributeMappingUiProvider<? extends IAttributeMapping>> javaAttributeMappingUiProviders;
	private List<IAttributeMappingUiProvider<? extends IAttributeMapping>> defaultJavaAttributeMappingUiProviders;

	private IJpaUiFactory jpaUiFactory;


	protected BaseJpaPlatformUi() {
		super();
		this.jpaUiFactory = createJpaUiFactory();
	}


	// **************** navigator content **************************************

	public ICommonContentProvider buildNavigatorContentProvider() {
		return new BaseJpaNavigatorContentProvider();
	}

	public ICommonLabelProvider buildNavigatorLabelProvider() {
		return new BaseJpaNavigatorLabelProvider();
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

	public IJpaDetailsProvider detailsProvider(IJpaContextNode contextNode) {
		// TODO: To implement, this is written only for testing
		detailsProviders();
		if (contextNode instanceof IJavaJpaContextNode) {
			return detailsProviders.get(0);
		}
		else {
			return detailsProviders.get(1);
		}
//		for (Iterator<IJpaDetailsProvider> i = this.detailsProviders(); i.hasNext(); ) {
//			IJpaDetailsProvider provider = i.next();
//			if (provider.fileContentType().equals(fileContentType)) {
//				return provider;
//			}
//		}
//		return null;
	}

	public ListIterator<ITypeMappingUiProvider<? extends ITypeMapping>> javaTypeMappingUiProviders() {
		if (this.javaTypeMappingUiProviders == null) {
			this.javaTypeMappingUiProviders = new ArrayList<ITypeMappingUiProvider<? extends ITypeMapping>>();
			this.addJavaTypeMappingUiProvidersTo(this.javaTypeMappingUiProviders);
		}
		return new CloneListIterator<ITypeMappingUiProvider<? extends ITypeMapping>>(
			this.javaTypeMappingUiProviders
		);
	}

	/**
	 * Override this to specify more or different type mapping ui providers
	 * The default includes the JPA spec-defined entity, mapped superclass, embeddable,
	 * and null (when the others don't apply)
	 */
	protected void addJavaTypeMappingUiProvidersTo(List<ITypeMappingUiProvider<? extends ITypeMapping>> providers) {
		providers.add(NullTypeMappingUiProvider.instance());
		providers.add(EntityUiProvider.instance());
		providers.add(MappedSuperclassUiProvider.instance());
		providers.add(EmbeddableUiProvider.instance());
	}

	public ListIterator<IAttributeMappingUiProvider<? extends IAttributeMapping>> javaAttributeMappingUiProviders() {
		if (this.javaAttributeMappingUiProviders == null) {
			this.javaAttributeMappingUiProviders = new ArrayList<IAttributeMappingUiProvider<? extends IAttributeMapping>>();
			this.addJavaAttributeMappingUiProvidersTo(this.javaAttributeMappingUiProviders);
		}

		return new CloneListIterator<IAttributeMappingUiProvider<? extends IAttributeMapping>>(
			this.javaAttributeMappingUiProviders
		);
	}

	/**
	 * Override this to specify more or different java attribute mapping ui providers.
	 * The default includes the JPA spec-defined basic, embedded, embeddedId, id,
	 * manyToMany, manyToOne, oneToMany, oneToOne, transient, and version
	 */
	protected void addJavaAttributeMappingUiProvidersTo(List<IAttributeMappingUiProvider<? extends IAttributeMapping>> providers) {
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

	public ListIterator<IAttributeMappingUiProvider<? extends IAttributeMapping>> defaultJavaAttributeMappingUiProviders() {
		if (this.defaultJavaAttributeMappingUiProviders == null) {
			this.defaultJavaAttributeMappingUiProviders = new ArrayList<IAttributeMappingUiProvider<? extends IAttributeMapping>>();
			this.addDefaultJavaAttributeMappingUiProvidersTo(this.defaultJavaAttributeMappingUiProviders);
		}

		return new CloneListIterator<IAttributeMappingUiProvider<? extends IAttributeMapping>>(
			this.defaultJavaAttributeMappingUiProviders
		);
	}

	/**
	 * Override this to specify more or different default java attribute mapping ui providers.
	 * The default includes the JPA spec-defined basic, embedded
	 */
	protected void addDefaultJavaAttributeMappingUiProvidersTo(List<IAttributeMappingUiProvider<? extends IAttributeMapping>> providers) {
		providers.add(DefaultBasicMappingUiProvider.instance());
		providers.add(DefaultEmbeddedMappingUiProvider.instance());
	}

	public void generateEntities(IJpaProject project, IStructuredSelection selection) {
		EntitiesGenerator.generate(project, selection);
	}
}
