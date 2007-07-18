/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.ui.internal.details.IJpaDetailsProvider;
import org.eclipse.jpt.ui.internal.generic.EntitiesGenerator;
import org.eclipse.jpt.ui.internal.java.details.ITypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaDetailsProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.EmbeddableUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.EntityUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.MappedSuperclassUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.NullTypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.structure.JavaStructureProvider;
import org.eclipse.jpt.ui.internal.structure.IJpaStructureProvider;
import org.eclipse.jpt.ui.internal.xml.details.XmlDetailsProvider;
import org.eclipse.jpt.ui.internal.xml.structure.XmlStructureProvider;

public abstract class BaseJpaPlatformUi implements IJpaPlatformUi
{
	private Collection<IJpaDetailsProvider> detailsProviders;
	private Collection<IJpaStructureProvider> structureProviders;
	
	private List<ITypeMappingUiProvider> javaTypeMappingUiProviders;
	
	protected BaseJpaPlatformUi() {
		super();
	}

	// ********** behavior **********
	
	public Collection<IJpaDetailsProvider> detailsProviders() {
		if (this.detailsProviders == null) {
			this.detailsProviders = new ArrayList<IJpaDetailsProvider>();
			this.addDetailsProvidersTo(this.detailsProviders);
		}
		return this.detailsProviders;
	}
	
	/**
	 * Override this to specify more or different details providers.
	 * The default includes the JPA spec-defined java and orm.xml
	 */
	protected void addDetailsProvidersTo(Collection<IJpaDetailsProvider> providers) {
		providers.add(new JavaDetailsProvider());
		providers.add(new XmlDetailsProvider());
	}
	
	public Collection<IJpaStructureProvider> structureProviders() {
		if (this.structureProviders == null) {
			this.structureProviders = new ArrayList<IJpaStructureProvider>();
			this.addStructureProvidersTo(this.structureProviders);
		}
		return this.structureProviders;
	}
	
	/**
	 * Override this to specify more or different structure providers.
	 * The default includes the JPA spec-defined java and orm.xml
	 */
	protected void addStructureProvidersTo(Collection<IJpaStructureProvider> providers) {
		providers.add(new JavaStructureProvider());
		providers.add(new XmlStructureProvider());
	}

	public List<ITypeMappingUiProvider> javaTypeMappingUiProviders() {
		if (this.javaTypeMappingUiProviders == null) {
			this.javaTypeMappingUiProviders = new ArrayList<ITypeMappingUiProvider>();
			this.addJavaTypeMappingUiProvidersTo(this.javaTypeMappingUiProviders);
		}
		return this.javaTypeMappingUiProviders;
	}
	
	/**
	 * Override this to specify more or different structure providers.
	 * The default includes the JPA spec-defined entity, mapped superclass, embeddable,
	 * and null (when the others don't apply)
	 */
	protected void addJavaTypeMappingUiProvidersTo(Collection<ITypeMappingUiProvider> providers) {
		providers.add(NullTypeMappingUiProvider.instance());
		providers.add(EntityUiProvider.instance());
		providers.add(MappedSuperclassUiProvider.instance());			
		providers.add(EmbeddableUiProvider.instance());			
	}
	
	public void generateEntities(IJpaProject project, IStructuredSelection selection) {
		EntitiesGenerator.generate(project, selection);
	}
}
