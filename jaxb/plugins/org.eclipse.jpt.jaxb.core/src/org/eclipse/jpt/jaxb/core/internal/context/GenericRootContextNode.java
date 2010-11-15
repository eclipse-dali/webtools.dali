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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbRootContextNode;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.utility.internal.ClassName;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;

/**
 * the context model root
 */
public class GenericRootContextNode
		extends AbstractJaxbContextNode
		implements JaxbRootContextNode {
	
	/* This object has no parent, so it must point to the JAXB project explicitly. */
	protected final JaxbProject jaxbProject;
	
	/* The map of package name to JaxbPackage objects */
	protected final Map<String, JaxbPackage> packages;
	
//	/* The map of class name to JaxbType objects */
//	protected final Map<String, JaxbType> classes;
	
	
	public GenericRootContextNode(JaxbProject jaxbProject) {
		super(null);  // the JPA project is not really a "parent"...
		if (jaxbProject == null) {
			throw new NullPointerException();
		}
		this.jaxbProject = jaxbProject;
		this.packages = new HashMap<String, JaxbPackage>();
//		this.classes = new HashMap<String, JaxbType>();
	}
	
	
	@Override
	protected boolean requiresParent() {
		return false;
	}
	
	public void synchronizeWithResourceModel() {
		for (JaxbPackage each : getPackages()) {
			each.synchronizeWithResourceModel();
		}
//		for (JaxbType each : getClasses()) {
//			each.synchronizeWithResourceModel();
//		}
	}
	
	public void update() {
		final Set<String> explicitJaxbContextPackageNames = calculateExplicitJaxbContextPackageNames();
		final Set<String> explicitJaxbContextClassNames = calculateExplicitJaxbContextClassNames();
		final Set<String> implicitJaxbContextClassNames = new HashSet<String>();
		
		for (String packageName : explicitJaxbContextPackageNames) {
			if (! this.packages.containsKey(packageName)) {
				addPackage(buildPackage(packageName));
			}
		}
		
		for (String className : explicitJaxbContextClassNames) {
			String packageName = ClassName.getPackageName(className);
			if (! this.packages.containsKey(packageName)) {
				addPackage(buildPackage(packageName));
			}
//			if (! this.classes.containsKey(className)) {
//				addClass(buildClass(className));
//			}
		}
		
		for (JaxbPackage each : getPackages()) {
			each.update();
		}
		
//		for (JaxbType each : getClasses()) {
//			each.update();
//		}
		
		for (JaxbPackage each : getPackages()) {
			if (isEmpty(each)) {
				removePackage(each);
			}
		}
	}
	
	protected Set<String> calculateExplicitJaxbContextPackageNames() {
		return CollectionTools.set(
				new TransformationIterable<JavaResourcePackage, String>(
						getJaxbProject().getAnnotatedJavaResourcePackages()) {
					@Override
					protected String transform(JavaResourcePackage o) {
						return o.getName();
					}
				});
	}
	
	protected Set<String> calculateExplicitJaxbContextClassNames() {
		return new HashSet<String>();
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
		return new LiveCloneIterable<JaxbPackage>(this.packages.values());
	}
	
	public int getPackagesSize() {
		return this.packages.size();
	}
	
	protected JaxbPackage addPackage(JaxbPackage contextPackage) {
		if (this.packages.containsKey(contextPackage.getName())) {
			throw new IllegalArgumentException("Package with that name already exists.");
		}
		this.packages.put(contextPackage.getName(), contextPackage);
		fireItemAdded(PACKAGES_COLLECTION, contextPackage);
		return contextPackage;
	}
	
	protected void removePackage(JaxbPackage contextPackage) {
		if (! this.packages.containsKey(contextPackage.getName())) {
			throw new IllegalArgumentException("No package with that name exists.");
		}
		this.packages.remove(contextPackage.getName());
		fireItemRemoved(PACKAGES_COLLECTION, contextPackage);
	}
	
	protected JaxbPackage buildPackage(String packageName) {
		return this.getFactory().buildPackage(this, packageName);
	}
	
	protected boolean isEmpty(JaxbPackage jaxbPackage) {
		return jaxbPackage.isEmpty();
	}
	
	
	// ************* classes ***************
	
//	public Iterable<JaxbType> getClasses() {
//		return new LiveCloneIterable<JaxbType>(this.classes.values());
//	}
//	
//	public int getClassesSize() {
//		return this.classes.size();
//	}
//	
//	protected JaxbType addClass(JaxbType jaxbClass) {
//		if (this.classes.containsKey(jaxbClass.getName())) {
//			throw new IllegalArgumentException("Class with that name already exists.");
//		}
//		this.classes.put(jaxbClass.getName(), jaxbClass);
//		fireItemAdded(CLASSES_COLLECTION, jaxbClass);
//		return jaxbClass;
//	}
//	
//	protected void removeClass(JaxbType jaxbClass) {
//		if (! this.classes.containsKey(jaxbClass.getName())) {
//			throw new IllegalArgumentException("No class with that name exists.");
//		}
//		this.classes.remove(jaxbClass.getName());
//		fireItemRemoved(CLASSES_COLLECTION, jaxbClass);
//	}
//	
//	protected JaxbType buildClass(String className) {
//		return this.getFactory().buildClass(this, className);
//	}
}
