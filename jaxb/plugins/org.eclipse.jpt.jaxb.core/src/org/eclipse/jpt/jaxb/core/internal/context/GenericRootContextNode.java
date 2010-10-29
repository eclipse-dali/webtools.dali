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

import java.util.Vector;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbRootContextNode;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;

/**
 * the context model root
 */
public class GenericRootContextNode
	extends AbstractJaxbContextNode
	implements JaxbRootContextNode
{
	/* This object has no parent, so it must point to the JAXB project explicitly. */
	protected final JaxbProject jaxbProject;

	/* Main context objects. */
	protected final Vector<JaxbPackage> packages = new Vector<JaxbPackage>();
	protected final PackageContainerAdapter packageContainerAdapter = new PackageContainerAdapter();


	public GenericRootContextNode(JaxbProject jaxbProject) {
		super(null);  // the JPA project is not really a "parent"...
		if (jaxbProject == null) {
			throw new NullPointerException();
		}
		this.jaxbProject = jaxbProject;
		this.initializePackages();
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
		return new LiveCloneIterable<JaxbPackage>(this.packages);
	}

	public int getPackagesSize() {
		return this.packages.size();
	}

	protected void addPackage(JavaResourcePackage resourcePackage) {
		addPackage(this.buildPackage(resourcePackage));
	}

	protected void addPackage(JaxbPackage contextPackage) {
		this.addItemToCollection(contextPackage, this.packages, PACKAGES_COLLECTION);
	}
	
	protected void removePackage(JaxbPackage contextPackage) {
		this.removeItemFromCollection(contextPackage, this.packages, PACKAGES_COLLECTION);
	}

	protected void initializePackages() {
		for (JavaResourcePackage resourcePackage : this.getJaxbProject().getAnnotatedJavaResourcePackages()) {
			this.packages.add(this.buildPackage(resourcePackage));
		}
	}

	protected void syncPackages() {
		ContextContainerTools.synchronizeWithResourceModel(this.packageContainerAdapter);
	}

	protected void updatePackages() {
		//In this case we need to actually "sync" the list of packages since this is dependent on JaxbFiles
		//and an update will be called when jaxb files are added/removed, not a synchronizeWithResourceModel
		ContextContainerTools.update(this.packageContainerAdapter);
	}

	protected JaxbPackage buildPackage(JavaResourcePackage resourcePackage) {
		return this.getFactory().buildPackage(this, resourcePackage);
	}


	/**
	 * package container adapter
	 */
	protected class PackageContainerAdapter
		implements ContextContainerTools.Adapter<JaxbPackage, JavaResourcePackage>
	{
		public Iterable<JaxbPackage> getContextElements() {
			return GenericRootContextNode.this.getPackages();
		}
		public Iterable<JavaResourcePackage> getResourceElements() {
			return GenericRootContextNode.this.getJaxbProject().getAnnotatedJavaResourcePackages();
		}
		public JavaResourcePackage getResourceElement(JaxbPackage contextElement) {
			return contextElement.getResourcePackage();
		}
		public void moveContextElement(int index, JaxbPackage element) {
			//ignore since we don't need order for packages
		}
		public void addContextElement(int index, JavaResourcePackage resourceElement) {
			GenericRootContextNode.this.addPackage(resourceElement);
		}
		public void removeContextElement(JaxbPackage element) {
			GenericRootContextNode.this.removePackage(element);
		}
	}

}
