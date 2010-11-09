/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbRootContextNode;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;

/**
 * the context model root
 */
public class GenericRootContextNode
		extends AbstractJaxbContextNode
		implements JaxbRootContextNode {
	
	/* This object has no parent, so it must point to the JAXB project explicitly. */
	protected final JaxbProject jaxbProject;
	
	/* Main context objects. */
	protected final PackageContainer packageContainer;
	
	
	public GenericRootContextNode(JaxbProject jaxbProject) {
		super(null);  // the JPA project is not really a "parent"...
		if (jaxbProject == null) {
			throw new NullPointerException();
		}
		this.jaxbProject = jaxbProject;
		this.packageContainer = new PackageContainer();
	}
	
	
	@Override
	protected boolean requiresParent() {
		return false;
	}
	
	public void synchronizeWithResourceModel() {
		this.syncPackages();
	}
	
	public void update() {
		this.updatePackages();
	}
	
	
	// ********** AbstractJaxbNode overrides **********
	
	@Override
	public JaxbProject getJaxbProject() {
		return this.jaxbProject;
	}
	
	@Override
	public IResource getResource() {
		return this.getProject();
	}
	
	protected IProject getProject() {
		return this.jaxbProject.getProject();
	}
	
	
	// ************* packages ***************
	
	public Iterable<JaxbPackage> getPackages() {
		return this.packageContainer.getContextElements();
	}
	
	public int getPackagesSize() {
		return this.packageContainer.getContextElementsSize();
	}
	
	protected JaxbPackage addPackage(JaxbPackage contextPackage, List<JaxbPackage> packages) {
		this.addItemToCollection(contextPackage, packages, PACKAGES_COLLECTION);
		return contextPackage;
	}
	
	protected void removePackage(JaxbPackage contextPackage, List<JaxbPackage> packages) {
		this.removeItemFromCollection(contextPackage, packages, PACKAGES_COLLECTION);
	}
	
	protected void syncPackages() {
		this.packageContainer.synchronizeWithResourceModel();
	}
	
	protected void updatePackages() {
		//In this case we need to actually "sync" the list of packages since this is dependent on JaxbFiles
		//and an update will be called when jaxb files are added/removed, not a synchronizeWithResourceModel
		this.packageContainer.update();
	}
	
	protected JaxbPackage buildPackage(String packageName) {
		return this.getFactory().buildPackage(this, packageName);
	}
	
	protected Iterable<String> getPackageNames() {
		Set<String> packageNames = new HashSet<String>();
		for (JavaResourcePackage each : getJaxbProject().getAnnotatedJavaResourcePackages()) {
			packageNames.add(each.getName());
		}
		
		return packageNames;
	}
	
	
	/**
	 * package container adapter
	 */
	protected class PackageContainer
			extends CollectionContainer<JaxbPackage, String> {
		
		@Override
		protected String getContextElementsPropertyName() {
			return PACKAGES_COLLECTION;
		}
		
		@Override
		public JaxbPackage buildContextElement(String packageName) {
			return GenericRootContextNode.this.buildPackage(packageName);
		}
		
		@Override
		public Iterable<String> getResourceElements() {
			return GenericRootContextNode.this.getPackageNames();
		}
		
		@Override
		public String getResourceElement(JaxbPackage jaxbPackage) {
			return jaxbPackage.getName();
		}
	}
}
